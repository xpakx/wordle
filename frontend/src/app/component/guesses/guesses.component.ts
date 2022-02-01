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
  @Output() fullEvent = new EventEmitter<boolean>();

  constructor() { }

  ngOnInit(): void {
  }

  newGuess(guess: Guess) {
    this.guesses.push(guess);
    let length: number = Math.max(0, 5-this.guesses.length)
    this.remainingGuesses = Array(length).fill(true);
    if(this.guesses.length >= 6) {
      this.fullEvent.emit(true);
    }
  }

  get activeBlanks(): boolean[] {
    return Array(5-this.active.length).fill(true);
  }

  get full(): boolean {
    return this.guesses.length == 6;
  }
}
