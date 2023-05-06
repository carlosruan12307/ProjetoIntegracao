import { AuthService } from 'src/app/services/authService/auth-service.service';
import { HttpClient } from '@angular/common/http';
import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { MovieServiceService } from 'src/app/services/movieService/movie-service.service';
import { Observable } from 'rxjs';
import { movieInterface } from 'src/app/interfaces/movieInterface';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { typeEnum } from 'src/app/interfaces/typeInterface';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  @ViewChild('buttonT') buttonT: ElementRef = new ElementRef({});
  movies: movieInterface[] = [];
  modalActive!: movieInterface;
  moviesCard: movieInterface[] = [];

  constructor(
    private serviceAuth: AuthService,
    private router: Router,
    private serviceMovies: MovieServiceService,
    private sanitizer: DomSanitizer
  ) {}
  sani(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
  ngOnInit(): void {
    console.log(this.serviceAuth.LoggedIn);
    this.getMovies();
  }

  getMovies() {
    if (this.router.url == '/movies') {
      this.serviceMovies
        .getMovies(typeEnum.movie, 'pt-br', 1)
        .subscribe((e) => {
          console.log('wtf is that', typeEnum.movie);
          console.log('saca so', this.router.url);
          console.log(e);

          this.moviesCard = e.splice(0, 3);
          this.movies = e;
        });
    } else if (this.router.url == '/series') {
      this.serviceMovies.getMovies(typeEnum.tv, 'pt-br', 3).subscribe((e) => {
        console.log('saca so', this.router.url);
        console.log(e);
        this.moviesCard = e.splice(0, 3);
        this.movies = e;
      });
    }
    console.log(this.movies);
  }

  logout() {
    this.serviceAuth.logout().subscribe({
      next: (valor) => {
        this.router.navigate(['']);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }
  sub() {
    this.serviceAuth.teste().subscribe({
      next: (valor) => {
        console.log(valor);
      },
      error: (error) => {
        console.log(error);
      },
    });
  }

  teste(movie: movieInterface) {
    this.modalActive = movie;
  }
}
