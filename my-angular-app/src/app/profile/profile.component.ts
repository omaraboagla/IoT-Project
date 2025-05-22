import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ProfileService } from '../../core/services/profile/profile.service';
import { SharedService } from '../../core/services/shared/shared.service';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { checkForCapital } from '../../core/Validators/capital';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [ReactiveFormsModule],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {

  changePassword: FormGroup = new FormGroup({
    oldPassword: new FormControl('', [Validators.required, Validators.minLength(8)]),
    newPassword: new FormControl('', [Validators.required, Validators.minLength(8), checkForCapital, Validators.pattern(/[0-9]/)])
  });

  constructor(public shared: SharedService, private profileService: ProfileService, private router: Router) { }

  ngOnInit() {
    if (this.shared.loggedUser.email != '')
      return;
    const token = sessionStorage.getItem('token');
    // console.log(token);
    if (token !== null) {

      this.profileService.getProfile(token).subscribe({
        next: (response) => {
          console.log("Profile fetched successfully", response);
          this.shared.loggedUser.fullname = response.fullName;
          this.shared.loggedUser.email = response.email;
        },
        error: (error) => {
          console.error("Error fetching profile", error);
          this.router.navigate(['/signin']);
        }
      });

    }

    else {
      console.log("Token not found");
      this.router.navigate(['/signin']);
    }
  }

  logout() {
    sessionStorage.removeItem('token');
    this.shared.loggedUser.fullname = '';
    this.shared.loggedUser.email = '';
    this.router.navigate(['/signin']);
  }

  onChangePassword() {
    const token = sessionStorage.getItem('token');
    this.router.navigate(['/loading']);
    if (token !== null) {
      this.profileService.changePassword(token, this.changePassword.value.oldPassword, this.changePassword.value.newPassword).subscribe({
        next: (response) => {
          console.log("Password changed successfully", response);
          this.shared.changePasswordError = false;
          this.router.navigate(['/signin']);
        },
        error: (error) => {
          console.error("Error changing password", error);
          this.shared.changePasswordError = true;
          this.router.navigate(['/home/profile']);
        }
      });
    }
  }

}
