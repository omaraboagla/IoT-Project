import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule, NgIf, NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-filter-panel',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './filter-panel.component.html',
  styleUrls: ['./filter-panel.component.scss']
})
export class FilterPanelComponent {
  @Input() location = '';
  @Input() sortBy = '';
  @Input() sortMode = 'asc';
  @Input() startDate = '';
  @Input() endDate = '';
  @Input() sortOptions: string[] = []; 

  @Output() filter = new EventEmitter<void>();
}
