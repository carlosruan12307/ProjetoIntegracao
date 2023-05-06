import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { Observable, forkJoin, map, switchMap } from 'rxjs';
import { movieInterface } from 'src/app/interfaces/movieInterface';
import { typeEnum } from 'src/app/interfaces/typeInterface';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root',
})
export class MovieServiceService {
  movieApiUrl!: string;
  constructor(private http: HttpClient, private sanitizer: DomSanitizer) {}

  getMovies(type: typeEnum, language: string, page: number): Observable<any> {
    this.movieApiUrl = `https://api.themoviedb.org/3/${type}/popular?api_key=${environment.apiKEY_moviedb}&language=${language}&page=${page}`;
    return this.http.get(this.movieApiUrl).pipe(
      switchMap((data: any) => {
        const add: any = data.results.map((r: any) => {
          return this.http
            .get(`https://image.tmdb.org/t/p/original${r.backdrop_path}`, {
              responseType: 'blob',
            })
            .pipe(
              map((d: any) => {
                const image: string = URL.createObjectURL(d);
                if (type == typeEnum.movie) {
                  const filme: movieInterface = {
                    adult: r.adult,
                    image: image,
                    genre_ids: r.genre_ids,
                    id: r.id,
                    original_language: r.original_language,
                    overview: r.overview,
                    popularity: r.popularity,
                    poster_path: r.poster_path,
                    release_date: r.release_date,
                    title: r.title,
                    video: r.video,
                    vote_average: r.vote_average,
                    vote_count: r.vote_count,
                  };
                  return filme;
                } else {
                  const filme: movieInterface = {
                    adult: r.adult,
                    image: image,
                    genre_ids: r.genre_ids,
                    id: r.id,
                    original_language: r.original_language,
                    overview: r.overview,
                    popularity: r.popularity,
                    poster_path: r.poster_path,
                    release_date: r.first_air_date,
                    title: r.name,
                    video: r.video,
                    vote_average: r.vote_average,
                    vote_count: r.vote_count,
                  };
                  return filme;
                }
              })
            );
        });
        return forkJoin(add);
      })
    );
  }

  getPosterImage(poster_path: string) {
    return this.http.get(`https://image.tmdb.org/t/p/original${poster_path}`);
  }
}
