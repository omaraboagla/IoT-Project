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
import { AirPollutionComponent } from './air-pollution/air-pollution.component'; // ✅ NEW
import { AuthGuard } from './core/guards/auth.guard';
export const routes: Routes = [
  { path: 'signin', component: SigninComponent },
  { path: '', redirectTo: 'signin', pathMatch: 'full' },
  { path: 'signup', component: SignupComponent },
  { path: 'loading', component: LoadingComponent },
  { path: 'verify', component: OtpPageComponent },
  { path: 'verified-email', component: VerifiedEmailComponent },

  // Standalone routes
  { path: 'traffic', component: TrafficComponent },
  { path: 'street', component: StreetComponent },
  { path: 'air-pollution', component: AirPollutionComponent }, // ✅ NEW

  // Home route with nested children
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [AuthGuard], // <-- Protects /home and its children

    children: [
      { path: 'dashboard', component: DashboardComponent },
      { path: 'profile', component: ProfileComponent },
      { path: 'settings', component: SettingsComponent },
      { path: 'traffic', component: TrafficComponent },
      { path: 'street', component: StreetComponent },
      { path: 'air-pollution', component: AirPollutionComponent }, // ✅ NEW
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ]
  },

  // Fallback route
  { path: '**', redirectTo: 'signin' }
];
