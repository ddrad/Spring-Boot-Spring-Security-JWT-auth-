import { Component, OnInit } from '@angular/core';
import {User} from "../_models";
import {first} from "rxjs/operators";
import {AlertService, BusinessService, UserService} from "../_services";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent  implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  currentUser: User;

  canUpdate = true;

  constructor(private formBuilder: FormBuilder,
              private businessService: BusinessService,
              private router: Router,
              private alertService: AlertService,
              private userService: UserService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.currentUser = this.route.snapshot.data.currentUser;
    console.log(this.currentUser);
    this.registerForm = this.formBuilder.group({
      id: [this.currentUser.id],
      firstName: [this.currentUser.firstName, Validators.required],
      lastName: [this.currentUser.lastName, Validators.required],
      userName: [this.currentUser.username],
      email: [this.currentUser.email, Validators.required],
      phoneNumber: [this.currentUser.phoneNumber, Validators.required],
    });
  }

  get f() {
    return this.registerForm.controls;
  }

  onChange() {
    console.log('onChange');
    this.canUpdate = !this.canUpdate;
  }

  onSubmit() {
    console.log('onSubmit', this.currentUser);
    this.submitted = true;
    // stop here if form is invalid
    if (this.registerForm.invalid) {
      console.error('Validation Form is invalid');
      return;
    }

    this.loading = true;
    this.userService.update(this.registerForm.value)
      .pipe(first())
      .subscribe(
        data => {
          this.alertService.success('User was updated successful', true);
          this.router.navigate(['']);
        },
        error => {
          this.alertService.error(error);
          this.loading = false;
        });
  }
}
