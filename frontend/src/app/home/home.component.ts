import { Component, OnInit } from '@angular/core';
import {BusinessItem} from "../_models/businessItem.model";
import {User} from "../_models";
import {first} from "rxjs/operators";
import {UserService} from "../_services";

@Component({templateUrl: 'home.component.html',  styleUrls: ['home.component.css']}, )
export class HomeComponent  implements OnInit {

  constructor() {
  }

  ngOnInit() {

  }
}
