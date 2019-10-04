import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from 'rxjs/operators';

import {AlertService, AuthenticationService, UserService} from '../_services/index';
import {Router} from "@angular/router";

@Component({
  selector: 'createCustomer',
  templateUrl: 'register-new-customer.component.html'
})
export class RegisterNewCustomerComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;

   email: string;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private authService: AuthenticationService,
    private userService: UserService,
    private alertService: AlertService) {
  }

  ngOnInit() {
    this.email = this.authService.getEmail();
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: [''],
      email: [this.email, Validators.required],
      phoneNumber: ['', Validators.required],
      // password: ['', [Validators.required, Validators.minLength(6)]]
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
    this.userService.createCustomer(this.registerForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('Registration successful', true);
          this.router.navigate(['/login']);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
