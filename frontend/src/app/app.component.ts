import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { PuzzleRequest } from './model/puzzle-request';
import { PuzzleResponse } from './model/puzzle-response';
import { PuzzleService } from './service/puzzle.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  word: String[] = [];

  constructor(private service: PuzzleService) { }

  write(letter: String) {
    if(this.word.length < 5) {
      this.word.push(letter);
    }
  }

  backspace() {
    if(this.word.length > 0) {
      this.word.pop();
    }
  }

  makeGuess() {
    let request: PuzzleRequest = {word: this.word.join('')};
    this.service.guess(request).subscribe(
      (response: PuzzleResponse) => {
        
      },
      (error: HttpErrorResponse) => {
       
      }
    );
  }

}
