pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh 'mvn clean install'
                    } else if (fileExists('build.gradle')) {
                        sh './gradlew build'
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (fileExists('pom.xml')) {
                        sh 'mvn test'
                    } else if (fileExists('build.gradle')) {
                        sh './gradlew test'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                // Add deployment steps here
            }
        }
    }
}
