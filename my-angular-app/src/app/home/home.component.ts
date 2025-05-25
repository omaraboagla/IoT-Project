import { NgClass, NgStyle } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { ReadingsService } from '../../core/services/readings/readings.service';
import { SharedService } from '../../core/services/shared/shared.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NgStyle, RouterOutlet, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  constructor(private alert: ReadingsService, public shared: SharedService) { }
  alertData = [];

  removeAlertFlag(token: string, id: number) {
    this.shared.alertFlag = false;
    this.alert.changeAlertReadings(token, id).subscribe({
      next: (response) => {
        console.log("done");
      },
      error: (response) => {
        console.log("error");
      }
    });
  }

  ngOnInit() {

    const token = sessionStorage.getItem('token');
    if (token != null) {
      this.alert.getAlertReadings(token).subscribe(data => {
        this.alertData = data;
      });
      setInterval(() => {
        this.alert.getAlertReadings(token).subscribe(data => {
          this.alertData = data;
          let lastAlert = this.alertData[this.alertData.length - 1];
          if (lastAlert != null) {
            if (lastAlert['acknowledged'] == false) {
              this.shared.alertFlag = true;
              this.shared.alertMessage = lastAlert['message'];
              setTimeout(() => this.removeAlertFlag(token, lastAlert['id']), 5000);
              console.log("alertsssss", lastAlert);
            }
          }
        });
      }, 10000);
    }


  }

  toggleSidebar: boolean = false;
  toggleSidebarMenu() {
    this.toggleSidebar = !this.toggleSidebar;
  }

}
