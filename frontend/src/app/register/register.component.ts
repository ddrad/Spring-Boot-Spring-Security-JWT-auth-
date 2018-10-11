import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';

import {AlertService, AuthenticationService, UserService} from '../_services';

@Component({templateUrl: 'register.component.html'})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;

  isAuthorizated = false;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authenticationService : AuthenticationService,
    private userService: UserService,
    private alertService: AlertService) {
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;

    // stop here if form is invalid
    if (this.registerForm.invalid) {
      console.error('Validation Form is invalid');
      return;
    }

    this.loading = true;
    this.userService.register(this.registerForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Authorization successful. Please create your account', true);
          //this.router.navigate(['/login']);

          this.authenticationService.login(this.f.email.value, this.f.password.value)
            .pipe(first())
            .subscribe(
              data => {
                this.router.navigate(['/new-customer']);
              },
              error => {
                this.alertService.error(error);
                this.loading = false;
              });
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
