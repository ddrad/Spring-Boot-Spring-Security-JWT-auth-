import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import {UserService} from "../_services";


@Injectable()
export class UserResolver implements Resolve<Observable<Object>> {

  constructor(private api: UserService) { }

  resolve() {
    console.log("run resolver");
    let byJWT = this.api.getByJWT();
    console.log("resolver result: ", byJWT);
    return byJWT;
  }
}
