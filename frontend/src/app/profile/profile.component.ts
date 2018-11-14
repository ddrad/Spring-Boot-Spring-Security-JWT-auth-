import { Component, OnInit } from '@angular/core';
import {User} from "../_models";
import {first} from "rxjs/operators";
import {UserService} from "../_services";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent  implements OnInit {
  currentUser: User;
  users: User[] = [];

  constructor(private userService: UserService) {
    // this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
    this.loadCurrentUser();
  }

  deleteUser(id: number) {
    this.userService.delete(id).pipe(first()).subscribe(() => {
      this.loadAllUsers()
    });
  }

  private loadAllUsers() {
    this.userService.getAll().pipe(first()).subscribe(users => {
      this.users = users;
    });
  }

  private loadCurrentUser() {
    console.log('loadCurrentUser');
    this.userService.getByJWT().pipe(first()).subscribe(user => {
      console.log('loadCurrentUsers: ', user);
      this.currentUser = <User>user;
    });
  }
}
