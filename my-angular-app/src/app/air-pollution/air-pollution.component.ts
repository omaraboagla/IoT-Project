import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-air-pollution',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './air-pollution.component.html',
  styleUrls: ['./air-pollution.component.scss']
})
export class AirPollutionComponent implements OnInit {
  shared = {
    alertFlag: false,
    alertMessage: ''
  };

  location = '';
  sortBy = 'timestamp';
  sortMode = 'desc';
  startDate = '';
  endDate = '';
  airData: any[] = [];
  displayedColumns = ['#', 'location', 'co', 'ozone', 'pollutionLevel', 'timestamp'];

  currentPage = 0;
  pageSize = 10;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData(): void {
    let params = new HttpParams()
      .set('page', this.currentPage)
      .set('size', this.pageSize)
      .set('sort', `${this.sortBy},${this.sortMode}`);

    if (this.location) params = params.set('location', this.location);
    if (this.startDate) params = params.set('startDate', this.startDate);
    if (this.endDate) params = params.set('endDate', this.endDate);

    this.http.get<any>('http://iot-backend-ay012941-dev.apps.rm3.7wse.p1.openshiftapps.com/api/sensor/air-pollution/filter', { params }).subscribe({
      next: (res) => {
        this.airData = res.data || [];
      },
      error: (err) => {
        console.error('API error:', err);
        this.shared.alertFlag = true;
        this.shared.alertMessage = 'Failed to load air pollution data.';
      }
    });
  }

  putFilter(): void {
    this.currentPage = 0;
    this.fetchData();
  }

  previousPageData(): void {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.fetchData();
    }
  }

  nextPageData(): void {
    this.currentPage++;
    this.fetchData();
  }
}
