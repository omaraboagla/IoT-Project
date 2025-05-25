import { Component, NgModule, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TitleCasePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { ReadingsService } from '../../core/services/readings/readings.service';
import { FormsModule, NgModel } from '@angular/forms';
import { Chart, registerables } from 'chart.js';
import { SharedService } from '../../core/services/shared/shared.service';

@Component({
  selector: 'app-traffic',
  standalone: true,
  imports: [CommonModule, TitleCasePipe, RouterModule, FormsModule],
  templateUrl: './traffic.component.html',
  styleUrls: ['./traffic.component.scss']
})
export class TrafficComponent {
  trafficData = [];
  pageNumber = 0;
  nextPage = 1;
  previousPage = -1;
  location = "";
  congestionLevel = "";
  sortBy = "";
  sortMode = "asc";
  startDate = "";
  endDate = "";
  constructor(private readingsService: ReadingsService, public shared: SharedService) { }

  readData() {
    console.log(this.startDate, " ", this.endDate);
    this.readingsService.getReadings(this.location, this.congestionLevel, this.pageNumber, 10, this.sortBy, this.sortMode, this.startDate, this.endDate).subscribe(data => {
      this.trafficData = data.data;
      this.viewChart();
      this.nextPage %= data.meta.totalPages;
      this.previousPage %= data.meta.totalPages;
      if (this.previousPage == -1)
        this.previousPage = data.meta.totalPages - 1;
      console.log(data);
    });
  }

  ngOnInit() {

    this.readData();
    setInterval(() => { this.readData(); }, 30000);

  }

  previousPageData(): void {
    this.readingsService.getReadings(this.location, this.congestionLevel, this.previousPage, 10, this.sortBy, this.sortMode, this.startDate, this.endDate).subscribe(data => {
      this.trafficData = data.data;
      this.viewChart();
      this.pageNumber = this.previousPage;
      this.nextPage--;
      this.previousPage--;
      this.nextPage %= data.meta.totalPages;
      this.previousPage %= data.meta.totalPages;
      if (this.previousPage == -1)
        this.previousPage = data.meta.totalPages - 1;
      console.log(data.meta.pageNumber);
    });
  }

  nextPageData(): void {
    this.readingsService.getReadings(this.location, this.congestionLevel, this.nextPage, 10, this.sortBy, this.sortMode, this.startDate, this.endDate).subscribe(data => {
      this.trafficData = data.data;
      this.viewChart();
      this.pageNumber = this.nextPage;
      this.nextPage++;
      this.previousPage++;
      this.nextPage %= data.meta.totalPages;
      this.previousPage %= data.meta.totalPages;
      if (this.previousPage == -1)
        this.previousPage = data.meta.totalPages - 1;
      console.log(data.meta.pageNumber);
    });
  }

  putFilter(): void {
    this.nextPage = 1;
    this.previousPage = -1;
    this.pageNumber = 0;
    this.readingsService.getReadings(this.location, this.congestionLevel, 0, 10, this.sortBy, this.sortMode, this.startDate, this.endDate).subscribe(data => {
      this.trafficData = data.data;
      this.viewChart();
      this.nextPage %= data.meta.totalPages;
      this.previousPage %= data.meta.totalPages;
      if (this.previousPage == -1)
        this.previousPage = data.meta.totalPages - 1;
      console.log(data.meta.pageNumber);
    });
  }


  displayedColumns: string[] = ['id', 'location', 'status', 'vehicles', 'timestamp'];
  // trafficData = [
  //   { id: 1, location: 'Main Street', status: 'heavy', vehicles: 120, timestamp: '2023-05-24 08:30' },
  //   { id: 2, location: 'Central Avenue', status: 'moderate', vehicles: 75, timestamp: '2023-05-24 08:45' },
  //   { id: 3, location: 'Riverside Drive', status: 'light', vehicles: 30, timestamp: '2023-05-24 09:00' },
  //   { id: 4, location: 'Hilltop Road', status: 'heavy', vehicles: 150, timestamp: '2023-05-24 09:15' },
  //   { id: 5, location: 'Park Lane', status: 'moderate', vehicles: 90, timestamp: '2023-05-24 09:30' }
  // ];

  getStatusClass(status: string): string {
    return {
      'severe': 'status-heavy',
      'high': 'status-heavy',
      'moderate': 'status-moderate',
      'low': 'status-light'
    }[status.toLowerCase()] || '';
  }



  chart: Chart | undefined;

  ngAfterViewInit() {
    Chart.register(...registerables);
    this.viewChart();
  }

  viewChart() {
    // Register all chart types and components

    // Create the chart
    if (this.chart) {
      this.chart.destroy();
    }

    let indexArray = [];
    for (let i = 0; i < this.trafficData.length; i++) {
      indexArray.push(i + 1);
    }

    this.chart = new Chart('trafficChart', {
      type: 'bar', // Change to 'line', 'pie', etc.
      data: {
        labels: indexArray,
        datasets: [{
          label: 'Traffic Data',
          data: this.trafficData.map(row => row['trafficDensity']),
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true,
          }
        }
      }
    });
  }
}