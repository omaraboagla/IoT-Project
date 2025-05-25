pipeline {
    agent any

    environment {
        DOCKER_IMAGE_BACKEND = 'ahmedyasser02/iotproject'
        DOCKER_IMAGE_FRONTEND = 'ahmedyasser02/my-angular-app'
        DOCKER_TAG = 'v3.0'
        DOCKER_CREDENTIALS_ID = 'docker-hub' 
    }

    stages {
        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/omaraboagla/IoT-Project'
            }
        }
        
        stage('Run Backend Tests') {
    steps {
        dir('backend') {
            catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                sh 'mvn test'
            }
        }
    }
}


        stage('Build Backend with Maven') {
            steps {
                dir('backend') {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

       stage('Build Docker Images') {
            steps {
                script {
                   dir('backend') {
                        docker.build("${DOCKER_IMAGE_BACKEND}:${DOCKER_TAG}")
                    }
                    dir('my-angular-app') {
                        docker.build("${DOCKER_IMAGE_FRONTEND}:${DOCKER_TAG}")
                    }
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_CREDENTIALS_ID) {
                        docker.image("${DOCKER_IMAGE_BACKEND}:${DOCKER_TAG}").push()
                        docker.image("${DOCKER_IMAGE_FRONTEND}:${DOCKER_TAG}").push()
                    }
                }
            }
        }

        stage('Run MySQL and Spring Boot Containers') {
            steps {
                script {
                    sh '''
                        docker rm -f iot-mysql || true
                        docker rm -f iot-app || true
                        docker rm -f my-angular-app || true

                        docker-compose up --build -d
                    '''
                }
            }
        }
    }
}
