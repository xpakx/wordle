import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  word: String[] = [];

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

}
