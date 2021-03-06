import { HttpErrorResponse } from '@angular/common/http';
import { Component, HostListener, ViewChild } from '@angular/core';
import { GuessesComponent } from './component/guesses/guesses.component';
import { Guess } from './model/guess';
import { Letter } from './model/letter';
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
  green: String[] = [];
  yellow: String[] = [];
  gray: String[] = [];

  @ViewChild(GuessesComponent) guessComponent!: GuessesComponent;

  message?: String;
  won: boolean = false;
  lost: boolean = false;

  constructor(private service: PuzzleService) { }

  write(letter: String) {
    if(this.lost || this.won) {
      return;
    }
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
    if(this.lost || this.won) {
      return;
    }
    let request: PuzzleRequest = {word: this.word.join('')};
    this.service.guess(request).subscribe(
      (response: PuzzleResponse, word: String[] = this.word) => {
        this.checkResponse(response);
        if(this.guessComponent) {
          this.guessComponent.newGuess(this.responseToGuess(response, word));
        }
        for(let i: number=0; i<word.length; i++) {
          this.addToColor(word[i], response.positions[i]);
        }
        this.word = [];
        this.message = undefined;
      },
      (error: HttpErrorResponse) => {
        this.message = error.error.message;
      }
    );
  }

  clearMessage() {
    this.message = undefined;
  }

  checkResponse(response: PuzzleResponse) {
    for(let num of response.positions) {
      if(num != 2) {
        return;
      }
    }
    this.won = true;
  }

  guessesFull() {
    if(!this.won) {
      this.lost = true;
    }
  }

  responseToGuess(response: PuzzleResponse, word: String[]): Guess {
    let guess: Guess = new Guess();
    for(let i: number=0; i<word.length; i++) {
      guess.addLetter(new Letter(word[i], this.toColor(response.positions[i])));
    }
    return guess;
  }

  toColor(num: number): String {
    if(num==0) {return 'gray';}
    if(num==1) {return 'yellow';}
    if(num==2) {return 'green';}
    return '';
  }

  addToColor(letter: String, num: number) {
    if(num==0) {this.gray.push(letter);}
    if(num==1) {this.yellow.push(letter);}
    if(num==2) {this.green.push(letter);}
  }

  @HostListener("window:keypress", ["$event"])
    handleKeyboardLetterEvent(event: KeyboardEvent) {
      let letter: string = event.key;
      if(/^[a-zA-Z]$/.test(letter)) {
        this.write(letter.toLowerCase())
      }
  }

  @HostListener("window:keydown.backspace", ["$event"])
    handleKeyboardBackspaceEvent(event: KeyboardEvent) {
      this.backspace();
  }

  @HostListener("window:keydown.enter", ["$event"])
    handleKeyboardEnterEvent(event: KeyboardEvent) {
      this.makeGuess();
  }
}
