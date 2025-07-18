import { Component } from '@angular/core';
import { FormcardComponent } from "../formcard/formcard.component";
import { FormControl, FormGroup } from '@angular/forms';
import { Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { NgClass } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthenticationService } from '../../core/services/authentication/authentication.service';
import { Router } from '@angular/router';
import { SharedService } from '../../core/services/shared/shared.service';

@Component({
  selector: 'app-otp-page',
  standalone: true,
  imports: [FormcardComponent, ReactiveFormsModule, RouterLink],
  templateUrl: './otp-page.component.html',
  styleUrl: './otp-page.component.scss'
})
export class OtpPageComponent {
  private user = { email: '', name: '', password: '' };
  emailActivated: boolean = false;
  constructor(private authentication: AuthenticationService, private router: Router, public shared: SharedService) {
    this.user = JSON.parse(sessionStorage.getItem('user') || '{}');

  }

  otpCredentials: FormGroup = new FormGroup({
    number: new FormControl('', [Validators.required, Validators.minLength(6), Validators.maxLength(6), Validators.pattern(/^[0-9]+$/)]),
  });


  onSubmit() {
    this.router.navigate(['/loading']);
    this.authentication.verify(this.user.email, this.otpCredentials.value.number).subscribe({
      next: (response) => {
        console.log("OTP verification successful", response);
        this.shared.otpFlag = true;
        this.emailActivated = true;
        this.authentication.register(this.user.email, this.user.name, this.user.password, this.otpCredentials.value.number).subscribe({
          next: async (response) => {
            this.router.navigate(['/verified-email']);
            await this.delay(3000);
            console.log("Registration successful", response);
            sessionStorage.removeItem('user');
            this.router.navigate(['/signin']);
          },
          error: (error) => {
            console.error("Already registered", error);
            sessionStorage.removeItem('user');
            this.shared.alreadyRegistered = true;
            this.router.navigate(['/signin']);
          }
        });
      },
      error: (error) => {
        console.error("OTP verification failed", error);
        this.shared.otpFlag = false;
        this.router.navigate(['/verify']);
      }
    });
  }

  delay(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  onResend() {
    this.authentication.signUp(this.user.email).subscribe({
      next: (response) => {
        console.log("Resend OTP successful", response);
        this.shared.otpFlag = false;
      },
      error: (error) => {
        console.error("Resend OTP failed", error);
        this.shared.alreadyRegistered = true;
        this.router.navigate(['/signin']);
      }
    });
  }

}
