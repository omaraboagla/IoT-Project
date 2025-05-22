import { NgClass, NgStyle } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgStyle, RouterOutlet, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  toggleSidebar: boolean = false;
  toggleSidebarMenu() {
    this.toggleSidebar = !this.toggleSidebar;
  }

}
