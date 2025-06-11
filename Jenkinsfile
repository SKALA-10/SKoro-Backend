pipeline {
    agent any

    environment {
        IMAGE_NAME = 'skala25a/skoro-backend'
        IMAGE_TAG = '1.0.0'
        GIT_BRANCH = 'main'

        GIT_CREDENTIAL_ID = 'skala-github-id'
        HARBOR_CREDENTIAL_ID = 'skala-image-registry-id'
    }

    stages {
        stage('Docker Build & Push') {
            steps {
                script {
                    def hashcode = sh(
                        script: "date +%s%N | sha256sum | cut -c1-12",
                        returnStdout: true
                    ).trim()
                    def FINAL_IMAGE_TAG = "${IMAGE_TAG}-${BUILD_NUMBER}-${hashcode}"
                    env.FINAL_IMAGE_TAG = FINAL_IMAGE_TAG
                    echo "📦 Final Image Tag: ${FINAL_IMAGE_TAG}"

                    withCredentials([
                        string(credentialsId: 'image-registry', variable: 'REGISTRY_URL')
                    ]) {
                        docker.withRegistry("${REGISTRY_URL}", "${HARBOR_CREDENTIAL_ID}") {
                            def image = docker.build("${REGISTRY_URL}/${IMAGE_NAME}:${FINAL_IMAGE_TAG}", "--platform linux/amd64 .")
                            image.push()
                        }
                    }
                }
            }
        }

        stage('Update Infra Repository') {
            steps {
                script {
                    withCredentials([
                        string(credentialsId: 'git-infra-url', variable: 'GIT_URL'),
                        string(credentialsId: 'git-user-name', variable: 'GIT_NAME'),
                        string(credentialsId: 'git-user-email', variable: 'GIT_EMAIL'),
                        string(credentialsId: 'skoro-infra-dir', variable: 'SKORO_INFRA_DIR'),
                        usernamePassword(credentialsId: "${GIT_CREDENTIAL_ID}", usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')
                    ]) {
                        def gitRepoPath = GIT_URL.replaceFirst(/^https?:\/\//, '')

                        sh """
                            rm -rf ${SKORO_INFRA_DIR}
                            git clone -b ${GIT_BRANCH} https://${GIT_USERNAME}:${GIT_PASSWORD}@${gitRepoPath} ${SKORO_INFRA_DIR}
                        """
                        dir("${SKORO_INFRA_DIR}") {
                            sh """
                                sed -i 's|backend-INIT_TAG|backend-${env.FINAL_IMAGE_TAG}|g' apps/skoro-backend/base/deployment.yaml
                                git config user.name "${GIT_NAME}"
                                git config user.email "${GIT_EMAIL}"
                                git add apps/skoro-backend/base/deployment.yaml
                                git commit -m "[AUTO] Update backend tag: ${env.FINAL_IMAGE_TAG}" || echo "No changes to commit."
                                git push origin ${GIT_BRANCH}
                            """
                        }
                    }
                }
            }
        }
    }

    post {
        success {
            echo "✅ Backend CI Pipeline Completed!"
        }
        failure {
            echo "❌ Pipeline Failed! Check logs."
        }
    }
}
