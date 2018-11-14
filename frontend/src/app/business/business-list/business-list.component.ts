import {Component, OnDestroy, OnInit} from '@angular/core';
import {BusinessItem, User} from "../../_models";
import {first} from "rxjs/operators";
import {BusinessService, UserService} from "../../_services";
import {Subscription} from "rxjs/index";

@Component({
  selector: 'app-business-list',
  templateUrl: './business-list.component.html',
  styleUrls: ['./business-list.component.css']
})
export class BusinessListComponent implements OnInit, OnDestroy {

  subscription: Subscription;
  businessItems: BusinessItem[] = [];

  constructor(private businessService: BusinessService) {
  }

  ngOnInit() {
    this.loadBusinessItems();

    this.subscription = this.businessService.businessChangedSbj
      .subscribe((bus: BusinessItem[]) => {
        this.businessItems = bus;
      });
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

  private loadBusinessItems() {
    this.businessService.getAll().pipe(first()).subscribe(bus => {
      console.log(bus);
      this.businessService.updateBbusinessSbj(bus)
    });
  }
}
