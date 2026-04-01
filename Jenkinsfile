pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/Canzova/Smart-Study-Planner'
            }
        }

        stage('Build JAR') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t myapp .'
            }
        }
    }
}