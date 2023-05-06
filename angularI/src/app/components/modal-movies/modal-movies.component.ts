import { MovieServiceService } from 'src/app/services/movieService/movie-service.service';
import { Component, Input } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { movieInterface } from 'src/app/interfaces/movieInterface';

@Component({
  selector: 'app-modal-movies',
  templateUrl: './modal-movies.component.html',
  styleUrls: ['./modal-movies.component.css'],
})
export class ModalMoviesComponent {
  @Input() movies!: movieInterface[];
  @Input() moviesCard!: movieInterface[];

  constructor(
    private sanitizer: DomSanitizer,
    private movieService: MovieServiceService
  ) {}

  sani(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
