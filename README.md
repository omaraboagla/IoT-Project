# IoT Monitoring System

This project is a full-stack IoT monitoring system that collects and displays sensor data in real-time. The system monitors readings from three types of sensors: traffic, street light, and air pollution sensors. It triggers alerts when any sensor reading exceeds a threshold configured by the user based on a specific metric.

The application includes authentication features, allowing users to sign up, log in, and verify their accounts using an OTP sent to their email.

## Project Features

- Collects real-time data from:
  - Traffic sensors
  - Street light sensors
  - Air pollution sensors
- Displays real-time alerts when thresholds are exceeded
- Thresholds are defined by users per metric
- User authentication with email-based OTP verification
- Frontend built with Angular
- Backend built with Spring Boot
- MySQL used for data storage
- Docker used for containerization
- Kubernetes used for orchestration
- Deployed on Red Hat OpenShift
- Jenkins used for CI/CD pipeline

## How to Run the Application

There are two main ways to run the application:

### Option 1: Using OpenShift (Remote Deployment)

You can access the deployed version of the application using the following routes:

- Frontend:  
  http://angular-frontend-ay012941-dev.apps.rm3.7wse.p1.openshiftapps.com/

- Backend API:  
  http://iot-backend-ay012941-dev.apps.rm3.7wse.p1.openshiftapps.com/

For a step-by-step guide to deploying the application on OpenShift, refer to the `openshift-steps.txt` file.

### Option 2: Using Minikube and kubectl (Local Deployment)

1. **Download YAML Files**  
   Download all required Kubernetes YAML files (Deployments, Services, ConfigMaps, PVC, etc.) from the shared drive or this repository.

2. **Apply Kubernetes Manifests**  
   Run the following command inside the directory containing the YAML files:

   ```bash
   kubectl apply -f .
   ```

3. **Expose the Frontend via Minikube**

   Once the services are up and running, expose the Angular frontend locally:

   ```bash
   minikube service angular-frontend
   ```

   This will open the application in your browser using a local Minikube tunnel.

## Tech Stack

- Frontend: Angular
- Backend: Spring Boot
- Database: MySQL
- Containerization: Docker
- Orchestration: Kubernetes
- Deployment Platform: Red Hat OpenShift
- CI/CD: Jenkins

## Project Structure

```
/frontend               Angular frontend application
/backend                Spring Boot backend application
/K8s                    Kubernetes deployment YAML files
/openshift-steps.txt    OpenShift deployment instructions
```

## Author

Ahmed Yasser
