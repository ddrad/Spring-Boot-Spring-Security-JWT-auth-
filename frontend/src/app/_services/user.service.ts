import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {environment} from '../../environments/environment';
import {User} from '../_models';
import {first} from "rxjs/operators";
import {register} from "ts-node";
import {Observable} from "rxjs";

@Injectable()
export class UserService {
  constructor(private http: HttpClient) {
  }

  getAll() {
    return this.http.get<User[]>(`${environment.apiUrl}/users`);
  }

  getById(id: number) {
    return this.http.get(`${environment.apiUrl}/users/` + id);
  }

  getByJWT() {
    return this.http.get(`${environment.apiUrl}/user/`);
  }

  register(user: User) {
    return this.http.post(`${environment.apiUrl}/sign-up`, user);
  }

  createCustomer(user: User) {
    return this.http.post(`${environment.apiUrl}/user`, user);
  }

  update(user: User) {
    console.log(user);
    return this.http.put(`${environment.apiUrl}/user`, user);
  }

  delete(id: number) {
    return this.http.delete(`${environment.apiUrl}/users/` + id);
  }
}
