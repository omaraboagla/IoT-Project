import { Injectable } from '@angular/core';

export interface Threshold {
  type: string; // e.g. 'air', 'traffic', 'street'
  metric: string; // e.g. 'co', 'ozone'
  value: number;
  condition: 'above' | 'below';
}

export interface Alert {
  type: string; // e.g. 'air'
  metric: string; // e.g. 'co'
  currentValue: number;
  thresholdValue: number;
  triggeredAt: Date;
  message: string;
}

@Injectable({
  providedIn: 'root'
})
export class AlertsService {
  private alerts: Alert[] = [];
  private thresholds: Threshold[] = [];

  constructor() {
    // Load alerts from localStorage
    const storedAlerts = localStorage.getItem('alerts');
    if (storedAlerts) {
      this.alerts = JSON.parse(storedAlerts);
    }

    // Load thresholds from localStorage
    const storedThresholds = localStorage.getItem('sensorThresholds');
    if (storedThresholds) {
      this.thresholds = JSON.parse(storedThresholds);
    }
  }

  // Store a triggered alert
  addAlert(alert: Alert) {
    this.alerts.push(alert);
    localStorage.setItem('alerts', JSON.stringify(this.alerts));
  }

  // Return all past alerts
  getAlerts(): Alert[] {
    return [...this.alerts];
  }

  // Clear all alerts
  clearAlerts(): void {
    this.alerts = [];
    localStorage.removeItem('alerts');
  }

  // Save custom thresholds from settings
  setThresholds(thresholds: Threshold[]) {
    this.thresholds = thresholds;
    localStorage.setItem('sensorThresholds', JSON.stringify(thresholds));
  }

  // Get thresholds for evaluation
  getThresholds(): Threshold[] {
    return [...this.thresholds];
  }

  // Check if a value triggers any alert
  checkAlert(type: string, metric: string, value: number): Alert | null {
    const threshold = this.thresholds.find(
      t => t.type === type && t.metric === metric
    );

    if (!threshold) return null;

    const isTriggered =
      threshold.condition === 'above'
        ? value > threshold.value
        : value < threshold.value;

    if (isTriggered) {
      const alert: Alert = {
        type,
        metric,
        currentValue: value,
        thresholdValue: threshold.value,
        triggeredAt: new Date(),
        message: `${metric.toUpperCase()} = ${value} triggered ${threshold.condition} ${threshold.value}`
      };

      this.addAlert(alert);
      return alert;
    }

    return null;
  }
}
