import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-alert-banner',
  standalone: true,
  imports: [CommonModule],
  template: `
    <div *ngIf="show" class="alert alert-warning text-center alertNotify z-1">
      {{ message }}
    </div>
  `,
  styleUrls: ['./alert-banner.component.scss']
})
export class AlertBannerComponent {
  @Input() show = false;
  @Input() message = '';
}
