package com.azaroff.x3.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Result<T> {

    public interface ThrowingFunction<T, R> {
        R apply(T value) throws Exception;
    }

    private final T result;
    private final Exception exception;

    public Result(T result) {
        this.result = result;
        this.exception = null;
    }
    public Result(Exception exception) {
        this.result = null;
        this.exception = exception;
    }

    public static <T> Result<T> empty() {
        return new Result<>((T) null);
    }
    public static <T> Result<T> of(T result) {
        return new Result<>(result);
    }

    public static <T> Result<T> of(Exception e) {
        return new Result<>(e);
    }

    public static <T> Result<T> of(Callable<T> callable) {
        try {
            return new Result<>(callable.call());
        } catch (Exception e) {
            return new Result<>(e);
        }
    }
    public static <T,R> Function<T,Result<R>> tryFn(ThrowingFunction<T,R> fn) {
        return t -> {
            try {
                return new Result<>(fn.apply(t));
            } catch (Exception e) {
                return new Result<>(e);
            }
        };
    }

    public <R> Result<R> map(ThrowingFunction<T,R> fn) {
        return exception == null ? tryFn(fn).apply(result) : Result.of(exception);
    }
    public <R> Result<R> flatmap(ThrowingFunction<T,Result<R>> fn) {
        return exception == null ? tryFn(fn).apply(result).result : Result.of(exception);
    }
    public Result<T> catchEx(ThrowingFunction<Exception,T> fn) {
        return exception == null ? this : Result.tryFn(fn).apply(exception);
    }
    @SuppressWarnings("unchecked")
    public <E extends Exception> Result<T> catchEx(Class<E> exType, ThrowingFunction<E,T> fn) {
        return exception == null || !exType.isAssignableFrom(exception.getClass()) ? this : Result.tryFn(fn).apply((E)exception);
    }
    public Result<T> mapEx(ThrowingFunction<Exception,Exception> fn) {
        return exception == null ? this : Result.of( Result.tryFn(fn).apply(exception).result );
    }
    public Result<T> flatmapEx(ThrowingFunction<Exception,Result<T>> fn) {
        return exception == null ? this : Result.tryFn(fn).apply(exception).result;
    }


    public Result<T> required() {
        return exception == null && result == null ? Result.of(new NullPointerException()) : this;
    }

    public Result<T> required(Predicate<T> requiredProperty) {
        return exception == null && (result == null || !requiredProperty.test(result))
                ? Result.of(new NullPointerException()) : this;
    }

    public T get() throws Exception {
        if (exception != null) {
            throw exception;
        }
        return result;
    }
    public Exception getException() {
        return exception;
    }
    public T orElse(T defaultValue) {
        if (exception != null) {
            return defaultValue;
        }
        return result;
    }
    public T orElse(T defaultValue, Consumer<Exception> handler) {
        if (exception != null) {
            ifFail(handler);
            return defaultValue;
        }
        return result;
    }

    public static class ResultException extends RuntimeException {
        ResultException(Throwable cause) { super(cause); }
    }

    public T unchecked() {
        if (exception != null) {
            throw new ResultException(exception);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <E extends Exception> T checked(Class<E> onlyFor) throws E {
        if (exception != null && onlyFor.isAssignableFrom(exception.getClass())) {
            throw (E) exception;
        }
        return unchecked(); // other exceptions are thrown at runtime
    }

    public boolean contains(T value) {
        return exception == null && Objects.equals(result, value);
    }

    public boolean isSuccess() {
        return exception == null;
    }
    public boolean isFail() {
        return exception != null;
    }
    public Result<T> ifSuccess(Consumer<T> onSuccess) {
        if (exception == null) {
            onSuccess.accept(result);
        }
        return this;
    }
    public Result<T> ifFail(Consumer<Exception> onFail) {
        if (exception != null) {
            onFail.accept(exception);
        }
        return this;
    }

    public Optional<T> optional() {
        return exception == null ? Optional.ofNullable(result) : Optional.empty();
    }
    public Stream<T> stream() {
        return exception == null ? Stream.of(result) : Stream.empty();
    }

}
