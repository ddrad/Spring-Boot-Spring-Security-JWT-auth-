import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {BusinessItemComponent} from "./business-list/business-item/business-item.component";
import {BusinessListComponent} from "./business-list/business-list.component";
import {BusinessComponent} from "./business.component";
import {CommonModule} from "@angular/common";
import {BusinessService} from "../_services";


@NgModule({
  declarations: [
    BusinessComponent,
    BusinessItemComponent,
    BusinessListComponent
  ],
  imports: [
    RouterModule,
    CommonModule
  ],
  providers: [
    BusinessService
  ],
  exports: [
    BusinessComponent
  ]
})

export class BusinessModule {
}
