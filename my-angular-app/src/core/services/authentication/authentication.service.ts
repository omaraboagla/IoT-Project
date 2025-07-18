import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private _httpclient: HttpClient) { }

  signUp(data: string): Observable<any> {
    const body = { email: data };
return this._httpclient.post(`${environment.apiBaseUrl}/api/auth/initiate-signup`, body);
  }

  verify(email: string, otp: string): Observable<any> {
    const body = { email: email, otp: otp };
    return this._httpclient.post(`${environment.apiBaseUrl}/api/auth/verify-otp`, body);
  }

  register(email: string, username: string, password: string, otp: string): Observable<any> {
    const body = { email: email, password: password, username: username, otp: otp };
    return this._httpclient.post(`${environment.apiBaseUrl}/api/auth/complete-registration`, body);
  }

  signIn(email: string, password: string): Observable<any> {
    const body = { email: email, password: password };
    return this._httpclient.post(`${environment.apiBaseUrl}/api/auth/login`, body);
  }

}
