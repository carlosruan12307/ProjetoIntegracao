import { HttpServiceService } from 'src/app/services/http-service.service';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css']
})
export class MainComponent implements OnInit {
  constructor(private service:HttpServiceService, private router:Router){

  }

  ngOnInit(): void {

  }

  logout(){
    this.service.logout().subscribe({
      next: valor => {
        this.router.navigate([""] )
      },
      error: error => {
        console.log(error)
      }
    })
  }
}
