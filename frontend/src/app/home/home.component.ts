import { Component, OnInit } from '@angular/core';
import {BusinessItem} from "../_models/businessItem.model";

@Component({templateUrl: 'home.component.html',  styleUrls: ['home.component.css']}, )
export class HomeComponent  implements OnInit {
  businessItems: BusinessItem[]

  ngOnInit() {
    console.log("sdsdasdasdasd")
  }
}
