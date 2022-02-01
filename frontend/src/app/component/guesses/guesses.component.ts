import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Guess } from 'src/app/model/guess';

@Component({
  selector: 'app-guesses',
  templateUrl: './guesses.component.html',
  styleUrls: ['./guesses.component.css']
})
export class GuessesComponent implements OnInit {
  guesses: Guess[] = [];
  remainingGuesses: boolean[] = Array(5).fill(true);
  @Input() active: String[] = [];
  @Output() guessEvent = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  newGuess(guess: Guess) {
    this.guesses.push(guess);
    this.remainingGuesses = Array(5-this.guesses.length).fill(true);
    if(this.full) {
      this.guessEvent.emit(true);
    }
  }

  get activeBlanks(): boolean[] {
    return Array(5-this.active.length).fill(true);
  }

  get full(): boolean {
    return this.guesses.length == 6;
  }
}
