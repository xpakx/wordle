import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-keyboard',
  templateUrl: './keyboard.component.html',
  styleUrls: ['./keyboard.component.css']
})
export class KeyboardComponent implements OnInit {
  @Input() green: String[] = ["q"];
  @Input() yellow: String[] = ["y"];
  @Input() gray: String[] = ["u"];

  @Output() guessEvent = new EventEmitter<boolean>();
  @Output() backspaceEvent = new EventEmitter<boolean>();
  @Output() letterEvent = new EventEmitter<String>();

  constructor() { }

  ngOnInit(): void {
  }

  chooseLetter(letter: String) {
    this.letterEvent.emit(letter);
  }

  guess() {
    this.guessEvent.emit(true);
  }

  backspace() {
    this.backspaceEvent.emit(true);
  }

  color(letter: String): String {
    if(this.green.includes(letter)) {
      return 'green';
    } 
    if(this.yellow.includes(letter)) {
      return 'yellow';
    }  
    if(this.gray.includes(letter)) {
      return 'gray';
    }
    return '';
  }

}
