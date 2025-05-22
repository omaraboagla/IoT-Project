import { Injectable } from '@angular/core';
import { User } from '../../classes/User';

@Injectable({
  providedIn: 'root'
})
export class SharedService {
  otpFlag: boolean = true;
  alreadyRegistered: boolean = false;
  failedSignIn: boolean = false;
  loggedUser: User = new User("", "");
  changePasswordError: boolean = false;
  updatedSettings: number = 0;

  constructor() { }
}
