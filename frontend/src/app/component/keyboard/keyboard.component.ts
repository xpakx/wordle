import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-keyboard',
  templateUrl: './keyboard.component.html',
  styleUrls: ['./keyboard.component.css']
})
export class KeyboardComponent implements OnInit {
  @Input() green: String[] = [];
  @Input() yellow: String[] = [];
  @Input() gray: String[] = [];

  @Output() guessEvent = new EventEmitter<boolean>();
  @Output() backspaceEvent = new EventEmitter<boolean>();
  @Output() letterEvent = new EventEmitter<String>();

  letters1: String[] = ['q','w','e','r','t','y','u','i','o','p'];
  letters2: String[] = ['a','s','d','f','g','h','j','k','l'];
  letters3: String[] = ['z','x','c','v','b','n','m'];

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
