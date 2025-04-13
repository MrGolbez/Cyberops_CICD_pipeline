pipeline {
    agent any

    environment {
        DOCKERHUB_USER = 'mrgolbez'
        IMAGE_VERSION = 'latest'
    }

    stages {

        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Build with Maven') {
            steps {
                sh 'mvn clean package'
            }
        }

        stage('Run Unit Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Code Coverage (JaCoCo)') {
            steps {
                sh 'mvn verify'
                // JaCoCo file in target/site/jacoco/index.html
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONARQUBE_ENV = credentials('sonarqube-token')
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh """
                        mvn sonar:sonar \
                          -Dsonar.projectKey=calculator-app \
                          -Dsonar.host.url=http://localhost:9000 \
                          -Dsonar.login=$SONARQUBE_ENV \
                          -Dsonar.java.binaries=target/classes \
                          -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    """
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    sh """
                        docker build -f Dockerfile-java8 -t $DOCKERHUB_USER/calculator-java8:$IMAGE_VERSION .
                        docker build -f Dockerfile-java11 -t $DOCKERHUB_USER/calculator-java11:$IMAGE_VERSION .
                        docker build -f Dockerfile-java17 -t $DOCKERHUB_USER/calculator-java17:$IMAGE_VERSION .
                    """
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $DOCKERHUB_USER/calculator-java8:$IMAGE_VERSION
                        docker push $DOCKERHUB_USER/calculator-java11:$IMAGE_VERSION
                        docker push $DOCKERHUB_USER/calculator-java17:$IMAGE_VERSION
                    """
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f k8s/deployment.yaml'
                sh 'kubectl apply -f k8s/services.yaml'
            }
        }
    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }
    }
}
