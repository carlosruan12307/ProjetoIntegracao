import { Component } from '@angular/core';
import { userData } from 'src/app/interfaces/userData';
import { AuthService } from 'src/app/services/authService/auth-service.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  userData?: userData;

  constructor(private http: AuthService) {
    this.userData = this.http.userData;
  }
}
