import { Component, DoCheck, ElementRef, ViewChild } from '@angular/core';
import { Input } from '@angular/core';
@Component({
  selector: 'app-formcard',
  standalone: true,
  imports: [],
  templateUrl: './formcard.component.html',
  styleUrl: './formcard.component.scss'
})
export class FormcardComponent implements DoCheck {
  @Input() validationStatus: boolean = false;
  @ViewChild('thumbs') thumbsIcon?: ElementRef;

  ngDoCheck() {
    // console.log('Validation status:', this.validationStatus);
    if (this.thumbsIcon) {
      this.thumbsIcon.nativeElement.style.backgroundColor = this.validationStatus ? '#08A518' : 'red';
      this.thumbsIcon.nativeElement.style.transform = this.validationStatus ? 'translate(-50%, -50%) rotate(0deg)' : 'translate(-50%, -50%) rotate(180deg)';
    }
  }


}
