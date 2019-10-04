import { Routes, RouterModule } from '@angular/router';

import { HomeComponent } from './home';
import { LoginComponent } from './login';
import { RegisterComponent } from './register';
import { AuthGuard } from './_guards';
import {RegisterNewCustomerComponent} from "./customer";
import {RegisterNewBusinessComponent} from "./business/register-new-business/register-new-business.component";
import {ProfileComponent} from "./profile/profile.component";
import {UserResolver} from "./_resolver/user-resolver";

const appRoutes: Routes = [
    { path: '', component: HomeComponent, canActivate: [AuthGuard] },
    { path: 'login', component: LoginComponent },
    { path: 'register', component: RegisterComponent },
    { path: 'new-customer', component: RegisterNewCustomerComponent },
    { path: 'register-new-business', component: RegisterNewBusinessComponent, resolve: {currentUser: UserResolver} },
    { path: 'profile', component: ProfileComponent, resolve: {currentUser: UserResolver} },

    // otherwise redirect to home
    { path: '**', redirectTo: '' }
];

export const routing = RouterModule.forRoot(appRoutes);
