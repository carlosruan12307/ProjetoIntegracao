import { Component, Input } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { movieInterface } from 'src/app/interfaces/movieInterface';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrls: ['./card.component.css'],
})
export class CardComponent {
  @Input() moviesCard!: movieInterface[];
  constructor(private sanitizer: DomSanitizer) {}

  sani(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }
}
