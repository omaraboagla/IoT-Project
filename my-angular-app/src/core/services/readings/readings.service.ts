import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ReadingsService {

  constructor(private _httpclient: HttpClient) { }

  getReadings(location: string, congestionControl: string, page: number, size: number, sort: string, sortMode: string, startDate: string, endDate: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    console.log(startDate, " ", endDate);
    let queryParameters = `page=${page}&size=5`;
    if (location != "") {
      queryParameters += `&location=${location}`;
    }

    if (congestionControl != "") {
      queryParameters += `&congestionLevel=${congestionControl}`;
    }

    if (startDate != "") {
      queryParameters += `&startDate=${startDate}`;
    }

    if (endDate != "") {
      queryParameters += `&endDate=${endDate}`;
    }
    if (sort != "") {
      queryParameters += `&sort=${sort},${sortMode}`
    }

    // return this._httpclient.get(`http://localhost:8080/api/readings?location=${location}&congestionControl=${congestionControl}&page=${page}&size=${size}&sort=${sort}`, { headers });
    console.log(`http://localhost:8080/api/sensor/traffic-sensor/filter?${queryParameters}`);
    return this._httpclient.get(`${environment.apiBaseUrl}/api/sensor/traffic-sensor/filter?${queryParameters}`, { headers });
  }


  getAlertReadings(token: string): Observable<any> {
    token = token.slice(1, -1);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this._httpclient.get(`${environment.apiBaseUrl}/api/alerts/traffic/list`, { headers });
  }


  changeAlertReadings(token: string, id: number): Observable<any> {
    token = token.slice(1, -1);
    console.log(token, " ", id);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    const body = {};
    return this._httpclient.put(`${environment.apiBaseUrl}/api/alerts/traffic/${id}/acknowledge`, body, { headers });
  }


  getReadingsStreet(location: string, congestionControl: string, page: number, size: number, sort: string, sortMode: string, startDate: string, endDate: string): Observable<any> {
    const headers = new HttpHeaders({
      'Content-Type': 'application/json'
    });
    console.log(startDate, " ", endDate);
    let queryParameters = `page=${page}&size=5`;
    if (location != "") {
      queryParameters += `&location=${location}`;
    }

    if (congestionControl != "") {
      queryParameters += `&brightnessLevel=${congestionControl}`;
    }

    if (startDate != "") {
      queryParameters += `&startDate=${startDate}`;
    }

    if (endDate != "") {
      queryParameters += `&endDate=${endDate}`;
    }
    if (sort != "") {
      queryParameters += `&sort=${sort},${sortMode}`
    }

    // return this._httpclient.get(`http://localhost:8080/api/readings?location=${location}&congestionControl=${congestionControl}&page=${page}&size=${size}&sort=${sort}`, { headers });
    console.log(`http://localhost:8080/api/sensor/street-light/filter?${queryParameters}`);
    return this._httpclient.get(`${environment.apiBaseUrl}/api/sensor/street-light/filter?${queryParameters}`, { headers });
  }


  getAlertReadingsStreet(token: string): Observable<any> {
    token = token.slice(1, -1);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    return this._httpclient.get(`${environment.apiBaseUrl}/api/alerts/street-light/list`, { headers });
  }


  changeAlertReadingsStreet(token: string, id: number): Observable<any> {
    token = token.slice(1, -1);
    console.log(token, " ", id);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
    const body = {};
    return this._httpclient.put(`${environment.apiBaseUrl}/api/alerts/street-light/${id}/acknowledge`, body, { headers });
  }
}
