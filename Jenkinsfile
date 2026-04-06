pipeline {
    agent any

    environment {
        AWS_REGION      = 'us-east-1'
        ECR_REGISTRY    = '123456789.dkr.ecr.us-east-1.amazonaws.com'
        ECR_REPO        = 'my-springboot-app'
        IMAGE_TAG       = "${env.BUILD_NUMBER}"
        ECS_CLUSTER     = 'my-cluster'
        ECS_SERVICE     = 'my-service'
        TASK_FAMILY     = 'my-task-def'
        CONTAINER_NAME  = 'my-app-container'
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-org/your-repo.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean package -DskipTests=false'
            }
        }

        stage('Docker Build') {
            steps {
                sh "docker build -t ${ECR_REPO}:${IMAGE_TAG} ."
            }
        }

        stage('Push to ECR') {
            steps {
                withAWS(credentials: 'aws-credentials', region: "${AWS_REGION}") {
                    sh """
                        aws ecr get-login-password --region ${AWS_REGION} \
                          | docker login --username AWS --password-stdin ${ECR_REGISTRY}

                        docker tag ${ECR_REPO}:${IMAGE_TAG} ${ECR_REGISTRY}/${ECR_REPO}:${IMAGE_TAG}
                        docker push ${ECR_REGISTRY}/${ECR_REPO}:${IMAGE_TAG}
                    """
                }
            }
        }

        stage('Deploy to ECS') {
            steps {
                withAWS(credentials: 'aws-credentials', region: "${AWS_REGION}") {
                    sh """
                        # Grab current task definition and inject new image
                        TASK_DEF=\$(aws ecs describe-task-definition --task-definition ${TASK_FAMILY} --query 'taskDefinition')

                        NEW_TASK_DEF=\$(echo \$TASK_DEF | jq --arg IMAGE "${ECR_REGISTRY}/${ECR_REPO}:${IMAGE_TAG}" \
                          --arg NAME "${CONTAINER_NAME}" \
                          '.containerDefinitions |= map(if .name == \$NAME then .image = \$IMAGE else . end) |
                           {family: .family, containerDefinitions: .containerDefinitions,
                            networkMode: .networkMode, requiresCompatibilities: .requiresCompatibilities,
                            cpu: .cpu, memory: .memory, executionRoleArn: .executionRoleArn,
                            taskRoleArn: .taskRoleArn}')

                        # Register the new revision
                        NEW_REVISION=\$(aws ecs register-task-definition \
                          --cli-input-json "\$NEW_TASK_DEF" \
                          --query 'taskDefinition.taskDefinitionArn' --output text)

                        # Update the service to use the new revision
                        aws ecs update-service \
                          --cluster ${ECS_CLUSTER} \
                          --service ${ECS_SERVICE} \
                          --task-definition \$NEW_REVISION \
                          --force-new-deployment

                        # Wait until stable
                        aws ecs wait services-stable \
                          --cluster ${ECS_CLUSTER} \
                          --services ${ECS_SERVICE}
                    """
                }
            }
        }
    }

    post {
        success {
            echo "Deployment succeeded: ${ECR_REGISTRY}/${ECR_REPO}:${IMAGE_TAG}"
            // slackSend channel: '#deployments', message: "✅ Deployed ${ECR_REPO}:${IMAGE_TAG}"
        }
        failure {
            echo "Pipeline failed — check logs above."
            // slackSend channel: '#deployments', message: "❌ Deploy failed for ${ECR_REPO}:${IMAGE_TAG}"
        }
        always {
            // Clean up local Docker images to avoid disk bloat
            sh "docker rmi ${ECR_REPO}:${IMAGE_TAG} || true"
            sh "docker rmi ${ECR_REGISTRY}/${ECR_REPO}:${IMAGE_TAG} || true"
        }
    }
}