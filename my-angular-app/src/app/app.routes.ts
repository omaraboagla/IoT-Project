import { Routes } from '@angular/router';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { LoadingComponent } from './loading/loading.component';
import { OtpPageComponent } from './otp-page/otp-page.component';
import { VerifiedEmailComponent } from './verified-email/verified-email.component';
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import { SettingsComponent } from './settings/settings.component';

export const routes: Routes = [
    { path: 'signin', component: SigninComponent },
    { path: '', redirectTo: 'signin', pathMatch: 'full' },
    { path: 'signup', component: SignupComponent },
    { path: 'loading', component: LoadingComponent },
    { path: 'verify', component: OtpPageComponent },
    { path: 'verified-email', component: VerifiedEmailComponent },
    {
        path: 'home', component: HomeComponent, children: [
            { path: 'profile', component: ProfileComponent },
            { path: 'settings', component: SettingsComponent },
            { path: '', redirectTo: 'profile', pathMatch: 'prefix' }
        ]
    }
];
