import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class JwtInterceptor implements HttpInterceptor {
    intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // add authorization header with jwt token if available
        let jwt = JSON.parse(localStorage.getItem('currentX3Token'));
        console.log('jwt: ', jwt);
        if (jwt) {
            request = request.clone({
                setHeaders: { 
                    Authorization: `${jwt}`
                }
            });
        }

        return next.handle(request);
    }
}
