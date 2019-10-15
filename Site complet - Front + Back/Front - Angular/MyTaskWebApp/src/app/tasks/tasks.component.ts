import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../service/authentication.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-tasks',
  templateUrl: './tasks.component.html',
  styleUrls: ['./tasks.component.css']
})
export class TasksComponent implements OnInit {
  tasks;
  mode = 0;
  constructor(private authService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    this.authService.getTasks().subscribe(data => { // subscribe est un observateur et va retourner les valeurs recus par le get
    this.tasks = data;
      // tslint:disable-next-line:no-shadowed-variable
    }, error => {
      this.authService.logout(); // Si erreur on se logout (peut etre token expire)
      this.router.navigateByUrl('/login'); // on redirige sur le login
    });
    if (this.authService.isAdmin() === false) {
      this.mode = 0;
    } else {
      this.mode = 1;
    }
  }

  onNewTask() {
    this.router.navigateByUrl('/new-tasks');
  }
}
