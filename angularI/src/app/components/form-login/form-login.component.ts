import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/authService/auth-service.service';

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.css'],
})
export class FormLoginComponent implements OnInit {
  formGroup!: FormGroup;
  error!: string;
  constructor(
    private service: AuthService,
    private router: Router,
    private rout: ActivatedRoute
  ) {}
  ngOnInit(): void {
    this.formGroup = new FormGroup({
      email: new FormControl('', [Validators.required, Validators.email]),
      password: new FormControl('', Validators.required),
    });
  }
  submit() {
    // console.log(this.formGroup.get('email')?.value);
    // this.service.login(this.formGroup).subscribe({
    //   next: (userData) => {
    //     if (userData != null) {
    //       this.service.LoggedIn = true;
    //       this.service.userData = userData;
    //       this.error = '';
    //       this.router.navigate(['/home']);
    //     }
    //   },
    //   error: (error) => {
    //     this.error = 'login ou senha invalida';
    //     console.log('deu erro barao, tai o erro pra tu: ' + error.message);
    //     console.log(error);
    //   },
    // });
    this.service.LoggedIn = true;
    this.router.navigate(['/movies']);
  }

  get email() {
    return this.formGroup.get('email');
  }

  get password() {
    return this.formGroup.get('password');
  }
}
