import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SettingsService } from '../../core/services/settings/settings.service';
import { SharedService } from '../../core/services/shared/shared.service';
import { AlertsService, Threshold } from '../alerts/alerts.service';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {

  constructor(
    private settings: SettingsService,
    public shared: SharedService,
    private alertsService: AlertsService // ✅ Injected service
  ) {}

  sensors = [
    {
      type: 'Traffic',
      metrics: ["trafficDensity", "avgSpeed"]
    },
    {
      type: 'Air_Pollution',
      metrics: ["co", "ozone"]
    },
    {
      type: 'Street_Light',
      metrics: ["brightnessLevel", "powerConsumption"]
    }
  ];

  selectedType: string = '';
  selectedMetric: string = '';
  metricValue: number = 0;
  alertType: string = '';

  onTypeChange(event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    this.selectedType = selectElement.value;
    this.selectedMetric = '';
    this.metricValue = 0;
  }

  onSubmit() {
    this.shared.updatedSettings = 0;
    const token = sessionStorage.getItem('token');

    this.settings.updateSettings(token!, this.selectedType, this.selectedMetric, this.metricValue, this.alertType).subscribe({
      next: (response) => {
        console.log('Settings updated successfully:', response);
        this.shared.updatedSettings = 1;

        // ✅ Save threshold locally in alerts service
        const newThreshold: Threshold = {
          type: this.selectedType.toLowerCase().replace('_', ''), // normalize to 'air', 'traffic', 'street'
          metric: this.selectedMetric,
          value: this.metricValue,
          condition: this.alertType as 'above' | 'below'
        };

        let existing = this.alertsService.getThresholds();
        existing = existing.filter(
          t => !(t.type === newThreshold.type && t.metric === newThreshold.metric)
        );
        existing.push(newThreshold);

        this.alertsService.setThresholds(existing);
      },
      error: (error) => {
        console.error('Error updating settings:', error);
        this.shared.updatedSettings = 2;
      }
    });
  }
}
