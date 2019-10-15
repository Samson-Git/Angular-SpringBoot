import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../service/authentication.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  user: any;
  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
  }

  saveUser(user) {
    this.authService.register(user).subscribe(resp => {
    this.user = resp; // on recupere la donnée user provenant de resp
    });
  }

}
