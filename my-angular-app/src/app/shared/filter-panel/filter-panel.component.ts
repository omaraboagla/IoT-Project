import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
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
  @Output() locationChange = new EventEmitter<string>();

  @Input() sortBy = '';
  @Output() sortByChange = new EventEmitter<string>();

  @Input() sortMode = 'asc';
  @Output() sortModeChange = new EventEmitter<string>();

  @Input() startDate = '';
  @Output() startDateChange = new EventEmitter<string>();

  @Input() endDate = '';
  @Output() endDateChange = new EventEmitter<string>();

  @Input() sortOptions: string[] = [];

  @Output() filter = new EventEmitter<void>();
}
