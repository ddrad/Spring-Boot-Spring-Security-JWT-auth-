import {Component, Input, OnInit} from '@angular/core';
import {BusinessItem} from "../../../_models";

@Component({
  selector: 'app-business-item',
  templateUrl: './business-item.component.html',
  styleUrls: ['./business-item.component.css']
})
export class BusinessItemComponent implements OnInit {

  @Input() businessItem: BusinessItem;
  @Input() index: number;

  constructor() { }

  ngOnInit() {
  }

}
