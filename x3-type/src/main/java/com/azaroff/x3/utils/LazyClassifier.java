package com.azaroff.x3.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Fluent API/builder for complex conditions, that may be evaluated lazily, may fail, may be composed / reused.
 * Over LazyCondition, this accepts predicates to test against a type parameter, not just current context.
 * Exception generics is ditched, but possibility to throw from higher order parameters is preserved.
 *
 * @author ga2ljur
 */
public class LazyClassifier {

    public static <T,R> Mapping<T,R> create() {
        return new Mapping<>();
    }
    public static <R> Condition<R> createCondition() {
        return new Condition<>();
    }
    public static <T> Effects<T> createEffects() {
        return new Effects<>();
    }

    public static class Mapping<T, R> extends Impl<Mapping<T,R>,T,R> implements Conditions<Mapping<T,R>,T> { }
    public static class Condition<R> extends Impl<Condition<R>,Void,R> implements Conditions<Condition<R>, Void>, Supplier<R> {
        @Override
        public R get() {
            return apply(null).unchecked();
        }
    }
    public static class Effects<T> extends Impl<Effects<T>,T,Void> implements Conditions<Effects<T>,T>, Consumer<T> {
        @Override
        public void accept(T t) {
            apply(t).unchecked(); // using this instead of Void mapping prevents swallowing exceptions implicitly
        }
        public Effects<T> then(Effect<T> effect) {
            return super.then(effect);
        }
        public Effects<T> then(Runnable effect) {
            return then((cond, t) -> {
                if (cond) { effect.run(); }
            });
        }
    }

    public interface ThrowingPredicate<T> extends Predicate<T> {
        boolean testOrThrow(T value) throws Exception;
        default boolean test(T t) {
            // by extending Predicate, when(...) methods can coexist together without compiler complaining about ambiguity
            try {
                return testOrThrow(t);
            } catch (Exception e) {
                throw (RuntimeException) e;
            }
        }
        static <T> ThrowingPredicate<T> of(ThrowingBooleanSupplier tp) {
            return whatever -> tp.get();
        }
        static <T> ThrowingPredicate<T> of(Predicate<T> tp) {
            return tp::test;
        }
    }
    public interface ThrowingBooleanSupplier {
        boolean get() throws Exception;
    }

    public interface HasImplications<T, R> {
        List<Implication<T, R>> getImplications();
    }

    public interface Conditions<S,T> {
        S when(ThrowingPredicate<? super T> condition);
        default S when(ThrowingBooleanSupplier staticCondition) {
            return when(ThrowingPredicate.of(staticCondition));
        }
        default S given(boolean eagerCondition) {
            return when(ThrowingPredicate.of(() -> eagerCondition));
        }
    }

    public interface Rule<T,R> extends Result.ThrowingFunction<T,R> {
        R apply(boolean condition, T arg) throws Exception;
        default R apply(T arg) throws Exception {
            return apply(true, arg);
        }
    }
    public interface Effect<T> extends Rule<T,Void> {
        default Void apply(boolean condition, T arg) throws Exception {
            affect(condition, arg);
            return null;
        }
        void affect(boolean condition, T arg) throws Exception;
        default void affect(T arg) throws Exception {
            apply(true, arg);
        }
    }


    public abstract static class Impl<S extends Impl<S,T,R>, T, R> implements Function<T,Result<R>>, HasImplications<T,R> {

        @SuppressWarnings("unchecked") // its safe by the generic declaration
        private S self = (S) this;

        private List<Implication<T, R>> implications = new ArrayList<>();
        private ThrowingPredicate<T> nextCondition;

        @Override
        public List<Implication<T, R>> getImplications() {
            return implications;
        }

        public S when(ThrowingPredicate<? super T> condition) {
            this.nextCondition = condition::test;
            return self;
        }

        public S when(Predicate<? super T> condition) {
            this.nextCondition = condition::test;
            return self;
        }

        public S then(R variant) {
            return addImplication(this.nextCondition, (condition, unused) -> condition ? variant : null, null);
        }

        public S then(Callable<R> lazyVariant) {
            return addImplication(this.nextCondition, (condition, unused) -> condition ? lazyVariant.call() : null, null);
        }

        public S then(Rule<T, R> rule) {
            return addImplication(this.nextCondition, rule, null);
        }

        public S then(HasImplications<T, R> nested) {
            return addImplication(this.nextCondition, null, nested);
        }

        public S otherwise(R variant) {
            return addImplication(null, (condition, unused) -> condition ? variant : null, null);
        }

        public S otherwise(Callable<R> lazyVariant) {
            return addImplication(null, (condition, unused) -> condition ? lazyVariant.call() : null, null);
        }

        public S otherwise(Rule<T, R> rule) {
            return addImplication(null, rule, null);
        }

        public S otherwise(HasImplications<T, R> nested) {
            return addImplication(null, null, nested);
        }

        private S addImplication(ThrowingPredicate<T> predicate, Rule<T,R> rule, HasImplications<T,R> implications) {
            this.implications.add(new Implication<>(predicate, implications, rule));
            return self;
        }

        public Function<T,R> asUncheckedFunction() {
            return t -> apply(t).unchecked();
        }


        @Override
        public Result<R> apply(T arg) {
            List<Implication<T, R>> stack = new ArrayList<>(this.implications);
            prepareDebugSample(arg, stack);

            while (!stack.isEmpty()) {
                Implication<T, R> current = stack.remove(0);
                Result<Boolean> condition = Result.of(() -> current.predicate == null || current.predicate.test(arg));
                if (condition.isFail()) {
                    return condition.map(bool -> null); // just cast the failure to failure of R
                } else if (current.isSubcondition() && condition.contains(true)) {
                    stack.addAll(0, current.subcondition.getImplications());
                    prepareDebugSample(arg, current.subcondition.getImplications());
                } else if (!current.isSubcondition() && !condition.contains(false)) {
                    return condition.map(c -> current.rule.apply(c, arg));
                }
            }
            return Result.empty();
        }

        public List<R> evaluateAllOrThrow(T arg) {
            return evaluateAll(arg).stream().map(Result::unchecked).collect(Collectors.toList());
        }
        /**
         * evaluate all conditions independently (no else behaviour), otherwise clauses will always be true
         * this function is very similar to apply, but since the differences concern control flow, the combined version
         * was too ugly, asking about which variant it is on every step.
         * @param arg
         * @return
         */
        public List<Result<R>> evaluateAll(T arg) {
            List<Implication<T, R>> stack = new ArrayList<>(this.implications);
            prepareDebugSample(arg, stack);
            List<Result<R>> target = new ArrayList<>();;

            while (!stack.isEmpty()) {
                Implication<T, R> current = stack.remove(0);
                Result<Boolean> condition = Result.of(() -> current.predicate == null || current.predicate.test(arg)).required();
                if (condition.isFail()) {
                    target.add(condition.map(bool -> null)); // just cast the failure to failure of R
                } else if (current.isSubcondition()) {
                    stack.addAll(0, current.subcondition.getImplications());
                    prepareDebugSample(arg, current.subcondition.getImplications());
                } else {
                    Result<R> result = condition.map(c -> current.rule.apply(c, arg));
                    if (!result.contains(null)) {
                        target.add(result);
                    }
                }
            }
            return target;
        }

        private void prepareDebugSample(T sample, List<Implication<T,R>> target) {
            // this serves to show some reasonable toString when debugging conditions
            target.forEach(implication -> implication.setDebugSample(sample));
        }
    }

    private static class Implication<T, R> {
        final ThrowingPredicate<T> predicate;
        final Rule<T,R> rule;
        final HasImplications<T,R> subcondition;
        T debugSample;

        Implication(ThrowingPredicate<T> predicate, HasImplications<T,R> subcondition, Rule<T,R> rule) {
            this.predicate = predicate;
            this.subcondition = subcondition;
            this.rule = rule;
        }

        boolean isSubcondition() {
            return this.subcondition != null;
        }

        private void setDebugSample(T sample) {
            this.debugSample = sample;
        }

        private String toStringTemplate(String cond) {
            return "Implication{for sample [" + this.debugSample + "]" +
                    ", predicate=" + cond +
                    ", subconditions=" + (this.subcondition != null) +
                    ", rule=" + rule + '}';
        }

        public String toString() {
            try {
                // note that for debug purposes we run conditions eagerly
                return toStringTemplate(predicate == null ? "is null" : Boolean.toString(predicate.test(debugSample)));
            } catch (Exception e) { // NOSONAR this exception is only silenced
                                    // to show reasonable toString in debug, if it happens, it explodes later
                return toStringTemplate( "ERROR "+e.getClass().getSimpleName() + "("+e.getMessage()+")");
            }
        }
    }


}