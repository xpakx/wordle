import { Letter } from "./letter";

export class Guess {
    letters: Letter[] = [];

    addLetter(letter: Letter) {
        this.letters.push(letter);
    }
}