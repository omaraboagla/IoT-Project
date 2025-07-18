import { Component, Output } from '@angular/core';
import { FormcardComponent } from '../formcard/formcard.component';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { Validators } from '@angular/forms';
import { EventEmitter } from '@angular/core';
import { RouterLink } from '@angular/router';
import { SharedService } from '../../core/services/shared/shared.service';
import { AuthenticationService } from '../../core/services/authentication/authentication.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-signin',
  standalone: true,
  imports: [FormcardComponent, ReactiveFormsModule, RouterLink],
  templateUrl: './signin.component.html',
  styleUrl: './signin.component.scss'
})
export class SigninComponent {

  constructor(public shared: SharedService, private authorization: AuthenticationService, private router: Router) { }

  signInCredentials: FormGroup = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)]),
    password: new FormControl('', [Validators.required])
  });

  get email() {
    return this.signInCredentials.get('email') as FormControl;
  }

  get password() {
    return this.signInCredentials.get('password') as FormControl;
  }
  onSubmit() {
    this.router.navigate(['/loading']);
    this.authorization.signIn(this.signInCredentials.value.email, this.signInCredentials.value.password).subscribe({
      next: (response) => {
        console.log("Sign In successful", response);
        // sessionStorage.setItem('user', JSON.stringify(response));
        this.shared.alreadyRegistered = false;
        this.shared.failedSignIn = false;
        sessionStorage.setItem('token', JSON.stringify(response.token));
        this.router.navigate(['/home/dashboard']);
      },
      error: (error) => {
        console.error("Sign In failed", error);
        this.shared.alreadyRegistered = false;
        this.shared.failedSignIn = true;
        this.router.navigate(['/signin']);
      }
    });
  }

}
