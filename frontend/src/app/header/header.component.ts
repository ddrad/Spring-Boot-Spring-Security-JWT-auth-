import { Component, OnInit } from '@angular/core';
import {AuthGuard} from "../_guards";
import {User} from "../_models";
import {RouterStateSnapshot} from "@angular/router";
import {TreeNode} from "@angular/router/src/utils/tree";
import {T} from "@angular/core/src/render3";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  currentUser: User;

  constructor(private authGuard: AuthGuard) { }

  ngOnInit() {
  }

  openNav() {
    document.getElementById('mySidenav').style.width = '250px';
  }

  closeNav() {
    document.getElementById('mySidenav').style.width = '0';
  }
}
