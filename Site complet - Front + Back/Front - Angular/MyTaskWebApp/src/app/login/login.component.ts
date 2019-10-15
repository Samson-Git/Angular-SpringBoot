import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../service/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  mode = 0;

  constructor(
   private authService: AuthenticationService,
   private router: Router) {
  }

  ngOnInit() {
  }

  onLogin(user) {
    this.authService.login(user)
      .subscribe(resp => {
        let jwtToken = resp.headers.get('authorization'); // on recupere le header 'authorization' --> la ou on a stocker le token
        console.log(jwtToken);
        this.authService.saveToken(jwtToken); // on l'enrengistre dans le local storage
        this.router.navigateByUrl('/tasks'); // On redirige vers la page /task
        },
          error =>  {
        this.mode = 1;
        });
  }
}
