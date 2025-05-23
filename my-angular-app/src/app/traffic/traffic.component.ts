import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TitleCasePipe } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-traffic',
  standalone: true,
  imports: [CommonModule, TitleCasePipe, RouterModule],
  templateUrl: './traffic.component.html',
  styleUrls: ['./traffic.component.scss']
})
export class TrafficComponent {
  displayedColumns: string[] = ['id', 'location', 'status', 'vehicles', 'timestamp'];
  trafficData = [
    { id: 1, location: 'Main Street', status: 'heavy', vehicles: 120, timestamp: '2023-05-24 08:30' },
    { id: 2, location: 'Central Avenue', status: 'moderate', vehicles: 75, timestamp: '2023-05-24 08:45' },
    { id: 3, location: 'Riverside Drive', status: 'light', vehicles: 30, timestamp: '2023-05-24 09:00' },
    { id: 4, location: 'Hilltop Road', status: 'heavy', vehicles: 150, timestamp: '2023-05-24 09:15' },
    { id: 5, location: 'Park Lane', status: 'moderate', vehicles: 90, timestamp: '2023-05-24 09:30' }
  ];

  getStatusClass(status: string): string {
    return {
      'heavy': 'status-heavy',
      'moderate': 'status-moderate',
      'light': 'status-light'
    }[status.toLowerCase()] || '';
  }
}