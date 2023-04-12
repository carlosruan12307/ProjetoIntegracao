import { Component,OnInit } from '@angular/core';
import {FormGroup,FormControl,Validators, AbstractControl} from '@angular/forms'
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'angularTreino';
  valid:any
  formGroup!:FormGroup;
  ola:{} = {a:1}
  constructor() {

  }

  onchange() {
    console.log("a")
  }
}
