import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { TasksComponent } from './tasks/tasks.component';
import { NewTaskComponent } from './new-task/new-task.component';
import { RegistrationComponent } from './registration/registration.component';
import {RouterModule, Routes} from '@angular/router';
import {FormsModule, NgForm} from '@angular/forms';
import {HttpClient, HttpClientModule} from '@angular/common/http';
import {AuthenticationService} from './service/authentication.service';
import { Observable } from 'rxjs';
import {JwtHelper} from 'angular2-jwt';

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'tasks', component: TasksComponent},
  {path: 'new-tasks', component: NewTaskComponent},
  {path: 'register', component: RegistrationComponent},
  {path: '', redirectTo: 'login', pathMatch : 'full'}
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    TasksComponent,
    NewTaskComponent,
    RegistrationComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [AuthenticationService],
  bootstrap: [AppComponent]
})
export class AppModule { }
