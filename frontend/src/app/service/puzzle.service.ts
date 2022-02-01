import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PuzzleRequest } from '../model/puzzle-request';
import { PuzzleResponse } from '../model/puzzle-response';

@Injectable({
  providedIn: 'root'
})
export class PuzzleService {
  private url = environment.apiServerUrl;

  constructor(private http: HttpClient) { }

  public guess(request: PuzzleRequest): Observable<PuzzleResponse> {
    return this.http.post<PuzzleResponse>(`${this.url}`, request);
  }
}
