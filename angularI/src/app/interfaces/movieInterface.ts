import { SafeResourceUrl } from '@angular/platform-browser';

export interface movieInterface {
  adult: boolean;
  image: string; //path imagem padrao começa com /
  genre_ids: number[];
  id: number;
  original_language: string;
  overview: string; // descricao
  popularity: number;
  poster_path: string; // path imagem do poster / no começo
  release_date: string;
  title: string;
  video: boolean; //tem ou nao video
  vote_average: number;
  vote_count: number;
}
