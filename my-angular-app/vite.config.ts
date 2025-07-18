import { defineConfig } from 'vite';
import angular from '@analogjs/vite-plugin-angular';

export default defineConfig({
  plugins: [angular()],
  server: {
    allowedHosts: ['angular-frontend-ay012941-dev.apps.rm3.7wse.p1.openshiftapps.com'],
    proxy: {
      '/api': {
        target: 'http://iot-backend-ay012941-dev.apps.rm3.7wse.p1.openshiftapps.com',
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, ''),
      }
    }
  }
});
