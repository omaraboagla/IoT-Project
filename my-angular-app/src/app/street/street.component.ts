import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { Chart, registerables } from 'chart.js';

import { AlertBannerComponent } from '../shared/alert-banner/alert-banner.component';
import { FilterPanelComponent } from '../shared/filter-panel/filter-panel.component';

import { ReadingsService } from '../../core/services/readings/readings.service';
import { SharedService } from '../../core/services/shared/shared.service';

@Component({
  selector: 'app-street',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    HttpClientModule,
    AlertBannerComponent,
    FilterPanelComponent
  ],
  templateUrl: './street.component.html',
  styleUrls: ['./street.component.scss']
})
export class StreetComponent implements OnInit, AfterViewInit {
  constructor(
    private readingsService: ReadingsService,
    public shared: SharedService
  ) {}

  trafficData: any[] = [];
  displayedColumns: string[] = ['#', 'location', 'status', 'brightnessLevel', 'powerConsumption', 'timestamp'];

  pageNumber = 0;
  nextPage = 1;
  previousPage = -1;

  location = '';
  brightnessLevel = '';
  sortBy = '';
  sortMode = 'asc';
  startDate = '';
  endDate = '';

  chart: Chart | undefined;

  ngOnInit(): void {
    Chart.register(...registerables);
    this.readData();
    setInterval(() => this.readData(), 30000); // auto-refresh every 30 sec
  }

  ngAfterViewInit(): void {
    this.viewChart();
  }

  readData(): void {
    this.readingsService.getReadingsStreet(
      this.location,
      this.brightnessLevel,
      this.pageNumber,
      10,
      this.sortBy,
      this.sortMode,
      this.startDate,
      this.endDate
    ).subscribe(data => {
      this.trafficData = data.data;
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

    const labels = this.trafficData.map((_, i) => i + 1);
    const dataPoints = this.trafficData.map(row => row['brightnessLevel']);

    this.chart = new Chart('StreetChart', {
      type: 'bar',
      data: {
        labels,
        datasets: [{
          label: 'Brightness Level',
          data: dataPoints,
        }]
      },
      options: {
        responsive: true,
        plugins: {
          legend: { display: true }
        }
      }
    });
  }
}
