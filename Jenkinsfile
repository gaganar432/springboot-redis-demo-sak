pipeline {
    agent any

    tools {
        maven 'maven'
    }

    environment {
        IMAGE_NAME = "sak_redis_app"
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build Jar') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Deploy with Docker Compose') {
            steps {
                sh '''
                    docker-compose down || true
                    docker-compose build --no-cache
                    docker-compose up -d
                '''
            }
        }
    }

    post {
        success {
            echo "Deployment successful 🎉 Spring Boot + Redis app running!"
        }
        failure {
            echo "❌ Deployment failed. Check logs."
        }
    }
}
