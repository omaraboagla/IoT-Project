import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  constructor(private _httpclient: HttpClient) { }

  getProfile(token: string): Observable<any> {
    token = token.slice(1, -1);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    return this._httpclient.get(`http://localhost:8081/api/profile`, { headers });
  }

  changePassword(token: string, oldPassword: string, newPassword: string): Observable<any> {
    token = token.slice(1, -1);
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
    const body = { oldPassword: oldPassword, newPassword: newPassword };
    return this._httpclient.put(`http://localhost:8081/api/profile/password`, body, { headers });
  }
}
