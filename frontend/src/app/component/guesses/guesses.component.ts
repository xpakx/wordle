import { Component, Input, OnInit } from '@angular/core';
import { PuzzleResponse } from 'src/app/model/puzzle-response';

@Component({
  selector: 'app-guesses',
  templateUrl: './guesses.component.html',
  styleUrls: ['./guesses.component.css']
})
export class GuessesComponent implements OnInit {
  guesses: PuzzleResponse[] = [];
  @Input() active: String[] = ['t','e','s','t'];

  constructor() { }

  ngOnInit(): void {
  }

  newGuess(response: PuzzleResponse) {
    this.guesses.push(response);
  }
}
