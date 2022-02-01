import { Component, Input, OnInit } from '@angular/core';
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

  constructor() { }

  ngOnInit(): void {
  }

  newGuess(guess: Guess) {
    this.guesses.push(guess);
    this.remainingGuesses = Array(5-this.guesses.length).fill(true);
  }

  get activeBlanks(): boolean[] {
    return Array(5-this.active.length).fill(true);
  }
}
