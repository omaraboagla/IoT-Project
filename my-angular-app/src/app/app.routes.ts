import { Routes } from '@angular/router';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { LoadingComponent } from './loading/loading.component';
import { OtpPageComponent } from './otp-page/otp-page.component';
import { VerifiedEmailComponent } from './verified-email/verified-email.component';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import { SettingsComponent } from './settings/settings.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TrafficComponent } from './traffic/traffic.component';
import { StreetComponent } from './street/street.component';

export const routes: Routes = [
  { path: 'signin', component: SigninComponent },
  { path: '', redirectTo: 'signin', pathMatch: 'full' },
  { path: 'signup', component: SignupComponent },
  { path: 'loading', component: LoadingComponent },
  { path: 'verify', component: OtpPageComponent },
  { path: 'verified-email', component: VerifiedEmailComponent },

  // Standalone traffic route (accessible directly)
  { path: 'traffic', component: TrafficComponent },
  { path: 'street-light', component: StreetComponent },

  // Home route with nested children
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'settings', component: SettingsComponent },

      // Optionally add traffic as a child route too if you want /home/traffic
      { path: 'traffic', component: TrafficComponent },

      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  // Fallback route
  { path: '**', redirectTo: 'signin' }
];