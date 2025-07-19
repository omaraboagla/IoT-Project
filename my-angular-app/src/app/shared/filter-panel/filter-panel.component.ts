import { Component, EventEmitter, Input, Output } from '@angular/core';
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
  @Input() location: string = '';
  @Output() locationChange = new EventEmitter<string>();

  @Input() sortBy: string = '';
  @Output() sortByChange = new EventEmitter<string>();

  @Input() sortMode: string = 'asc';
  @Output() sortModeChange = new EventEmitter<string>();

  @Input() startDate: string = '';
  @Output() startDateChange = new EventEmitter<string>();

  @Input() endDate: string = '';
  @Output() endDateChange = new EventEmitter<string>();

  @Input() sortOptions: string[] = [];

  @Output() filter = new EventEmitter<void>();

  invalidDateRange: boolean = false;

  onFilterClick(): void {
    this.invalidDateRange = false;

    if (this.startDate && this.endDate && new Date(this.startDate) > new Date(this.endDate)) {
      this.invalidDateRange = true;
      return;
    }

    this.filter.emit();
  }
}
