import { Component, Input } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { InputComponentComponent } from "./input-component/input-component.component";
import { ProfileComponent } from './profile/profile.component';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {

}
