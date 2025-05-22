import { AuthenticationService } from './../../core/services/authentication/authentication.service';
import { Component } from '@angular/core';
import { FormcardComponent } from '../formcard/formcard.component';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Validators } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { checkForCapital } from '../../core/Validators/capital';
import { NgClass } from '@angular/common';
import { Router } from '@angular/router';
import { SharedService } from '../../core/services/shared/shared.service';

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [FormcardComponent, ReactiveFormsModule, RouterLink, NgClass],
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.scss'
})
export class SignupComponent {

  constructor(private authentication: AuthenticationService, private router: Router, public shared: SharedService) { }

  signInCredentials: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required, Validators.minLength(3)]),
    email: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)]),
    password: new FormControl('', [Validators.required, Validators.minLength(8), checkForCapital, Validators.pattern(/[0-9]/)])
  });

  get name() {
    return this.signInCredentials.get('name') as FormControl;
  }

  get email() {
    return this.signInCredentials.get('email') as FormControl;
  }

  get password() {
    return this.signInCredentials.get('password') as FormControl;
  }
  onSubmit() {
    const user = {
      email: this.email.value,
      name: this.name.value,
      password: this.password.value
    };

    this.router.navigate(['/loading']);
    this.authentication.signUp(this.signInCredentials.value.email).subscribe({
      next: (response) => {
        console.log("Signup successful", response);
        sessionStorage.setItem('user', JSON.stringify(user));
        this.router.navigate(['/verify']);
      },
      error: (error) => {
        if (error.error.message === "Email already exists") {
          this.shared.alreadyRegistered = true;
          this.router.navigate(['/signin']);
        }
        else {

          console.error(error);
          this.router.navigate(['/signup']);
        }
      }
    });
  }
}
