import { Component } from '@angular/core';
import {AuthenticationService} from './service/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'MyTaskWebApp';

  constructor(private authService: AuthenticationService, private router: Router) {
  }

  onLogout() {
   this.authService.logout();
   this.router.navigateByUrl('/login');
  }
}
