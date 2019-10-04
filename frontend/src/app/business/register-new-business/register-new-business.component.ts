import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {first} from "rxjs/operators";
import {AlertService, BusinessService, UserService} from "../../_services";
import {ActivatedRoute, Router} from "@angular/router";
import {User} from "../../_models";

@Component({
  selector: 'app-register-new-business',
  templateUrl: './register-new-business.component.html',
  styleUrls: ['./register-new-business.component.css']
})
export class RegisterNewBusinessComponent implements OnInit {

  registerForm: FormGroup;
  loading = false;
  submitted = false;

  currentUser: User;
  userName: string;
  phoneNumber: string;

  constructor(private formBuilder: FormBuilder,
              private businessService: BusinessService,
              private router: Router,
              private alertService: AlertService,
              private userService: UserService,
              private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.currentUser = this.route.snapshot.data.currentUser;
    this.userName = this.currentUser.firstName;
    this.phoneNumber = this.currentUser.phoneNumber;
    this.registerForm = this.formBuilder.group({
      userName: [this.userName, Validators.required],
      phoneNumber: [this.phoneNumber, Validators.required],
      businessName: ['', Validators.required],
      description: ['', Validators.required],
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
    this.businessService.createBusiness(this.registerForm.value, this.currentUser.id)
    .pipe(first())
    .subscribe(
      data => {
        this.alertService.success('Product was added successful', true);
        this.router.navigate(['']);
      },
      error => {
        this.alertService.error(error);
        this.loading = false;
      });
  }

}
