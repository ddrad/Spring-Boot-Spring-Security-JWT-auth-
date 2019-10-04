import {NgModule} from '@angular/core';
import {RouterModule} from '@angular/router';
import {BusinessItemComponent} from "./business-list/business-item/business-item.component";
import {BusinessListComponent} from "./business-list/business-list.component";
import {BusinessComponent} from "./business.component";
import {CommonModule} from "@angular/common";
import {BusinessService} from "../_services";
import { RegisterNewBusinessComponent } from './register-new-business/register-new-business.component';
import {ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    BusinessComponent,
    BusinessItemComponent,
    BusinessListComponent,
    RegisterNewBusinessComponent
  ],
  imports: [
    RouterModule,
    CommonModule,
    ReactiveFormsModule
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
