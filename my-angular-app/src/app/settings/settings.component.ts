import { Component } from '@angular/core';
import { FormsModule, NgModel } from '@angular/forms';
import { SettingsService } from '../../core/services/settings/settings.service';
import { SharedService } from '../../core/services/shared/shared.service';
import { Token } from '@angular/compiler';

@Component({
  selector: 'app-settings',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './settings.component.html',
  styleUrl: './settings.component.scss'
})
export class SettingsComponent {

  constructor(private settings: SettingsService, public shared: SharedService) { }

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
  ]

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
    // console.log('Selected Type:', this.selectedType);
    // console.log('Selected Metric:', this.selectedMetric);
    // console.log('Metric Value:', this.metricValue);
    // console.log('Alert Type:', this.alertType);
    this.shared.updatedSettings = 0;
    const token = sessionStorage.getItem('token');
    this.settings.updateSettings(token!, this.selectedType, this.selectedMetric, this.metricValue, this.alertType).subscribe({
      next: (response) => {
        console.log('Settings updated successfully:', response);
        this.shared.updatedSettings = 1;
      },
      error: (error) => {
        console.error('Error updating settings:', error);
        this.shared.updatedSettings = 2;
      }
    });
  }

}
