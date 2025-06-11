pipeline {
    agent any

    environment {
        GIT_URL = 'https://github.com/SKALA-10/SKoro-Infra.git'
        GIT_BRANCH = 'main'
        GIT_USER_NAME = 'KimJ4ehyun'
        GIT_USER_EMAIL = 'kimjaehyun158@gmail.com'

        IMAGE_REGISTRY = 'amdp-registry.skala-ai.com/skala25a'
        IMAGE_NAME = 'skoro-backend'
        IMAGE_TAG = '1.0.0'

        GIT_CREDENTIAL_ID = 'skala-github-id'
        HARBOR_CREDENTIAL_ID = 'skala-image-registry-id'

        SKORO_INFRA_DIR = 'SKoro-Infra'
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

                    docker.withRegistry("https://${IMAGE_REGISTRY}", "${HARBOR_CREDENTIAL_ID}") {
                        def image = docker.build("${IMAGE_REGISTRY}/${IMAGE_NAME}:${FINAL_IMAGE_TAG}", "--platform linux/amd64 .")
                        image.push()
                    }
                }
            }
        }

        stage('Update Infra Repository') {
            steps {
                script {
                    def gitRepoPath = GIT_URL.replaceFirst(/^https?:\/\//, '')

                    withCredentials([usernamePassword(credentialsId: "${GIT_CREDENTIAL_ID}", usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                        sh """
                            rm -rf ${SKORO_INFRA_DIR}
                            git clone -b ${GIT_BRANCH} https://${GIT_USERNAME}:${GIT_PASSWORD}@${gitRepoPath} ${SKORO_INFRA_DIR}
                        """
                        dir("${SKORO_INFRA_DIR}") {
                            sh """
                                sed -i 's|backend-INIT_TAG|backend-${env.FINAL_IMAGE_TAG}|g' apps/skoro-backend/base/deployment.yaml
                                git config user.name "${GIT_USER_NAME}"
                                git config user.email "${GIT_USER_EMAIL}"
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
