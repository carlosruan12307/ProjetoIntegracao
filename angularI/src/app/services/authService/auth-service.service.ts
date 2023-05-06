import { userData } from '../../interfaces/userData';
import { FormControl, FormGroup } from '@angular/forms';
import { UserForm } from '../../interfaces/userForm';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment.development';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  LoggedIn: boolean = false;
  apiURL: string = environment.urlBack;
  userData?: userData;

  constructor(private http: HttpClient) {}

  cadastrar(user: UserForm) {
    this.http.post('', user);
  }

  logout(): Observable<any> {
    this.LoggedIn = false;
    return this.http.get(this.apiURL + '/logout', {
      withCredentials: true,
    });
  }

  login(user: FormGroup): Observable<any> {
    console.log(
      window.btoa(`${user.get('email')?.value}:${user.get('password')?.value}`)
    );
    return this.http.get<any>(this.apiURL + '/login', {
      withCredentials: true,
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
        Authorization: `Basic ${window.btoa(
          `${user.get('email')?.value}:${user.get('password')?.value}`
        )}`,
      },
    });
  }
  teste(): Observable<any> {
    return this.http.get<any>(this.apiURL + '/admin', {
      withCredentials: true,
    });
  }
  LoginWithGoogle(credentials: string): Observable<any> {
    console.log(credentials);
    return this.http.get<any>(this.apiURL + '/loginGoogle', {
      withCredentials: true,
      headers: {
        'Content-type': 'application/json',
        credentials: credentials,
      },
    });
  }
}
