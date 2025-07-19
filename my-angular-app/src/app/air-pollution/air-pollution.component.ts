import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { Chart, registerables } from 'chart.js';
import { AlertsService } from '../alerts/alerts.service';

import { ReadingsService } from '../../core/services/readings/readings.service';
import { SharedService } from '../../core/services/shared/shared.service';
import { AlertBannerComponent } from '../shared/alert-banner/alert-banner.component';
import { FilterPanelComponent } from '../shared/filter-panel/filter-panel.component';

@Component({
  selector: 'app-air-pollution',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    HttpClientModule,
    AlertBannerComponent,
    FilterPanelComponent
  ],
  templateUrl: './air-pollution.component.html',
  styleUrls: ['./air-pollution.component.scss']
})
export class AirPollutionComponent implements OnInit, AfterViewInit {
  constructor(
    private readingsService: ReadingsService,
    public shared: SharedService,
    private alertsService: AlertsService // ✅ Add this

  ) {}

  airData: any[] = [];
  displayedColumns = ['#', 'location', 'co', 'ozone', 'pollutionLevel', 'timestamp'];

  pageNumber = 0;
  nextPage = 1;
  previousPage = -1;

  location = '';
  sortBy = '';
  sortMode = 'asc';
  startDate = '';
  endDate = '';

  chart: Chart | undefined;

  ngOnInit(): void {
    Chart.register(...registerables);
    this.readData();
    setInterval(() => this.readData(), 30000);
  }

  ngAfterViewInit(): void {
    // Removed viewChart() from here to avoid drawing chart before data is ready
  }

  readData(): void {
    this.readingsService.getReadingsAir(
      this.location,
      this.pageNumber,
      10,
      this.sortBy,
      this.sortMode,
      this.startDate,
      this.endDate
    ).subscribe((data: any) => {
      this.airData = data.data;

for (const row of this.airData) {
  this.alertsService.checkAlert('air', 'co', row['co']);
  this.alertsService.checkAlert('air', 'ozone', row['ozone']);
}

this.viewChart();


      const totalPages = data.meta.totalPages;
      this.nextPage = (this.pageNumber + 1) % totalPages;
      this.previousPage = (this.pageNumber - 1 + totalPages) % totalPages;
    });
  }

  putFilter(): void {
    this.pageNumber = 0;
    this.nextPage = 1;
    this.previousPage = -1;
    this.readData();
  }

  nextPageData(): void {
    this.pageNumber = this.nextPage;
    this.readData();
  }

  previousPageData(): void {
    this.pageNumber = this.previousPage;
    this.readData();
  }

  viewChart(): void {
    if (this.chart) {
      this.chart.destroy();
    }

    const labels = this.airData.map((_, i) => i + 1);
    const coValues = this.airData.map(row => row['co']);
    const ozoneValues = this.airData.map(row => row['ozone']);

    this.chart = new Chart('AirPollutionChart', {
      type: 'bar',
      data: {
        labels,
        datasets: [
          {
            label: 'CO',
            data: coValues,
            backgroundColor: 'rgba(54, 162, 235, 0.6)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
          },
          {
            label: 'Ozone',
            data: ozoneValues,
            backgroundColor: 'rgba(255, 159, 64, 0.6)',
            borderColor: 'rgba(255, 159, 64, 1)',
            borderWidth: 1
          }
        ]
      },
      options: {
        responsive: true,
        plugins: {
          legend: {
            display: true
          }
        },
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  }
}
