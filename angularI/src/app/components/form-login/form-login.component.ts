import { Router, Routes } from '@angular/router';
import { User } from './../../interfaces/userInterface';
import {
  FormGroup,
  FormControl,
  Validators,
  AbstractControl,
} from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { HttpServiceService } from 'src/app/services/http-service.service';

@Component({
  selector: 'app-form-login',
  templateUrl: './form-login.component.html',
  styleUrls: ['./form-login.component.css'],
})
export class FormLoginComponent implements OnInit {
  formGroup!: FormGroup;
  error!: string;
  constructor(private service: HttpServiceService, private router: Router) {}
  ngOnInit(): void {
    this.formGroup = new FormGroup({
      email: new FormControl('', [Validators.required]),
      password: new FormControl('', Validators.required),
    });
  }

  submit() {
    console.log(this.formGroup.get('email')?.value);
    // this.service.login(this.formGroup).subscribe({
    //   next: (valor) => {
    //     if (valor.mensagem != null) {
    //       this.service.LoggedIn = true;
    //       console.log(valor.mensagem);
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
    this.router.navigate(['/home']);
  }
  get email() {
    return this.formGroup.get('email');
  }

  get password() {
    return this.formGroup.get('password');
  }
}
