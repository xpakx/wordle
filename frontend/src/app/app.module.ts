import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { GuessesComponent } from './component/guesses/guesses.component';
import { KeyboardComponent } from './component/keyboard/keyboard.component';

@NgModule({
  declarations: [
    AppComponent,
    GuessesComponent,
    KeyboardComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
