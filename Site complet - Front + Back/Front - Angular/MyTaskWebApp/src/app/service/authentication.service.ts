import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {JwtHelper} from 'angular2-jwt';


@Injectable()
export class AuthenticationService {
  private  host = 'http://localhost:8080';
  private jwtToken = null;
  private roles: Array<any> = [];
  // tslint:disable-next-line:no-shadowed-variable
  constructor(private http: HttpClient) {

  }
  login(user) {
    return this.http.post(this.host + '/login', user, {observe: 'response'});
  }


  saveToken(jwtToken: string) {
    this.jwtToken = jwtToken;
    localStorage.setItem('token', jwtToken);
    const jwtHelper = new JwtHelper();
    // tslint:disable-next-line:max-line-length
    this.roles = jwtHelper.decodeToken(this.jwtToken).roles;
  }

  loadToken() {
    this.jwtToken = localStorage.getItem('token');
  }

  getTasks() {
    if (this.jwtToken == null) {
      this.loadToken();
    }
    // tslint:disable-next-line:max-line-length
    return this.http.get(this.host + '/tasks', {headers: new HttpHeaders({authorization: this.jwtToken})}); // on envoie un get a l'url tasks avec dans le header authorization le token et on retourne les données
  }

  isAdmin() {
    for (let r of this.roles) {
      if (r.authority === 'ADMIN') { return true; }
    }
    return false;
  }

  addTask(task) {
    if (this.jwtToken == null) {
      this.loadToken();
    }
    return this.http.post(this.host + '/task', task ,  {headers: new HttpHeaders({authorization: this.jwtToken})});
  }

  register(user) {
    return this.http.post(this.host + '/register' , user);
  }

  logout() {
    this.jwtToken = null;
    localStorage.removeItem('token');
  }
}
