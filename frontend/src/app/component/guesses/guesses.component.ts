import { Component, Input, OnInit } from '@angular/core';
import { Guess } from 'src/app/model/guess';
import { PuzzleResponse } from 'src/app/model/puzzle-response';

@Component({
  selector: 'app-guesses',
  templateUrl: './guesses.component.html',
  styleUrls: ['./guesses.component.css']
})
export class GuessesComponent implements OnInit {
  guesses: Guess[] = [];
  @Input() active: String[] = ['t','e','s','t'];

  constructor() { }

  ngOnInit(): void {
  }

  newGuess(guess: Guess) {
    this.guesses.push(guess);
  }
}
