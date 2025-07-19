import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AlertsService, Alert } from '../alerts/alerts.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  alerts: Alert[] = [];

  constructor(
    private router: Router,
    private alertsService: AlertsService // ✅ Injected alerts
  ) {}

  ngOnInit(): void {
this.alerts = this.alertsService.getAlerts().sort((a, b) =>
  new Date(b.triggeredAt).getTime() - new Date(a.triggeredAt).getTime()
);
  }

  goTo(path: string) {
    this.router.navigate([`/home/${path}`]);
  }
}
