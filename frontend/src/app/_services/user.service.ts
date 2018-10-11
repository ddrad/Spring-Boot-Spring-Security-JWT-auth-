import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {environment} from '../../environments/environment';
import {User} from '../_models';

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
    console.log('getByEmail');
    return this.http.get(`${environment.apiUrl}/user/`);
  }

  register(user: User) {
    return this.http.post(`${environment.apiUrl}/sign-up`, user);
  }

  createCustomer(user: User) {
    return this.http.post(`${environment.apiUrl}/user`, user);
  }

  update(user: User) {
    return this.http.put(`${environment.apiUrl}/users/` + user.id, user);
  }

  delete(id: number) {
    return this.http.delete(`${environment.apiUrl}/users/` + id);
  }
}
