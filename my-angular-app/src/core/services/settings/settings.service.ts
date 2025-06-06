import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class SettingsService {

  constructor(private _httpclient: HttpClient) { }

  updateSettings(token: string, type: string, metric: string, thresholdValue: number, alertType: string): Observable<any> {
    token = token.slice(1, -1);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    const body = { type: type, metric: metric, thresholdValue: thresholdValue, alertType: alertType };
    console.log(body);
    console.log(headers);
    return this._httpclient.post('http://localhost:8081/api/settings', body, {
      headers,
      'responseType': 'text' as 'json'
    });
  }
}
