import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../service/authentication.service';

@Component({
  selector: 'app-new-task',
  templateUrl: './new-task.component.html',
  styleUrls: ['./new-task.component.css']
})
export class NewTaskComponent implements OnInit {
  task: any;
  mode;
  valide = 0;
  constructor(private authService: AuthenticationService) { }

  ngOnInit() {
    if (this.authService.isAdmin() === false) {
      this.mode = 1;
    } else {
      this.mode = 2;
    }
  }
  saveTask(task) {
    this.authService.addTask(task).subscribe(resp => {
      this.task = resp; // on recupere la donnée task provenant de resp
      this.mode = 2;
      this.valide = 1;
    }, error => {
      this.mode = 0;
      //console.log(error);
    });
  }
}
