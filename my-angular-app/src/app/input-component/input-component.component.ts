import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-input-component',
  standalone: true,
  imports: [],
  templateUrl: './input-component.component.html',
  styleUrl: './input-component.component.scss'
})
export class InputComponentComponent {

  @Input({ required: true }) labelText: string = '';

  @Input({ required: true }) inputType: string = '';

  @Output() changingTextFromEmail = new EventEmitter<string>();

  changeText(text: string): void {
    this.changingTextFromEmail.emit(text);
  }

}


