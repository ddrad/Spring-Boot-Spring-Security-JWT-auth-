import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import { map } from 'rxjs/operators';

import { environment } from '../../environments/environment';

@Injectable()
export class AuthenticationService {

    email:string;

    constructor(private http: HttpClient) { }

    setEmail(email: string) {
      this.email = email;
    }

    getEmail() : string {
      return this.email;
    }

    login(username: string, password: string) {
        this.setEmail(username);
        return this.http.post<Response>(`${environment.apiUrl}/login`, { username: username, password: password },
          {observe: 'response'})
            .pipe(map(resp => {
              console.log(resp);
              console.log(resp.headers.get('Authorization'));
                // login successful if there's a jwt token in the response
                if (resp.headers.get('Authorization')) {
                  const jwt = resp.headers.get('Authorization');
                    // store user details and jwt token in local storage to keep user logged in between page refreshesact_ru_task
                    localStorage.setItem('currentX3Token', JSON.stringify(jwt));
                }
                return resp.headers.get('Authorization');
            }));
    }

    logout() {
        // remove user from local storage to log user out
        localStorage.removeItem('currentX3Token');
    }
}
