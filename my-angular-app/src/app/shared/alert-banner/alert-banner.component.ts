import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlertsService, Alert } from '../../alerts/alerts.service';

@Component({
  selector: 'app-alert-banner',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alert-banner.component.html',
  styleUrls: ['./alert-banner.component.scss']
})
export class AlertBannerComponent implements OnInit {
  @Input() filterType: string | null = null;
  @Input() limit: number | null = null;
  @Input() autoDismissSeconds: number = 5; // ✅ default to 5s

  alerts: Alert[] = [];

  constructor(private alertsService: AlertsService) {}

  ngOnInit(): void {
    let allAlerts = this.alertsService.getAlerts();

    if (this.filterType) {
      allAlerts = allAlerts.filter(alert => alert.type === this.filterType);
    }

    allAlerts.sort((a, b) =>
      new Date(b.triggeredAt).getTime() - new Date(a.triggeredAt).getTime()
    );

    const limit = this.limit ?? (this.filterType ? 1 : null);
    this.alerts = limit ? allAlerts.slice(0, limit) : allAlerts;

    // ✅ Set up auto-dismiss for each alert
    this.alerts.forEach((_, i) => {
      setTimeout(() => {
        this.dismiss(i);
      }, this.autoDismissSeconds * 1000);
    });
  }

  dismiss(index: number) {
    this.alerts.splice(index, 1);
  }
}
