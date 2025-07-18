import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlertsService {
  constructor(private _httpclient: HttpClient) {}

  getAlertsByType(type: string): Observable<any> {
    return this._httpclient.get(`${environment.apiBaseUrl}/api/alerts/type?type=${type}`);
  }
}