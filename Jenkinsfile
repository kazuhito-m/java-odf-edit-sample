final PRODUCT_NAME = 'java-odf-edit-sample'

final JAVA_CONTAINER_TAG = 'openjdk:11.0.4-jdk-slim'
final SELENIUM_CONTAINER_TAG = 'selenium/standalone-chrome:3.141.59-vanadium'

final SLACK_CHANNEL = ''
final SLACK_DOMAIN = ''
final SLACK_TOKEN = ''
final SLACK_MSG_HEAD = ''

def dbImage = null
def dbContainer = null
def dbContainer2 = null
def dbIp
def seleniumContainer = null
def seleniumIp

pipeline {
    agent any
    stages {
        stage('Build need container images') {
            steps {
                script {
                    dbImage = docker.build('postgres-for-test', './env/local/postgres')
                }
            }
        }
        stage('Unit test with DB') {
            steps {
                script {
                    dbContainer = dbImage.run()
                    dbIp = ipAddressOf(dbContainer)
                }

                withDockerContainer(image: JAVA_CONTAINER_TAG) {
                    sh "SPRING_DATASOURCE_URL=jdbc:postgresql://${dbIp}:5432/odf_edit_sample " +
                            './gradlew clean test jacocoTestReport'
                    // TODO glaphbiz入りであるとか、特殊な環境での実行が必要。別で実現する。
                    // sh './gradlew jigReports || echo "JigReportは失敗したが続行。"'
                }
            }
        }
        stage('Integration test') {
            steps {
                script {
                    seleniumContainer = docker.image(SELENIUM_CONTAINER_TAG).run()
                    seleniumIp = ipAddressOf(seleniumContainer)
                    dbContainer2 = dbImage.run()
                    dbIp = ipAddressOf(dbContainer2)
                }

                withDockerContainer(image: JAVA_CONTAINER_TAG) {
                    sh "SPRING_DATASOURCE_URL=jdbc:postgresql://${dbIp}:5432/odf_edit_sample " +
                            "REMOTE_SELENIUM_HOST=${seleniumIp} " +
                            './gradlew integrationTest jacocoTestReport'
                    }
            }
        }
    }
    post {
        failure {
            script {
                def message = "${PRODUCT_NAME} のテストが失敗しました。 - 失敗の原因を確認してください。:${env.JOB_URL}"
                showInfomation(message, 'danger', SLACK_CHANNEL, SLACK_DOMAIN, SLACK_TOKEN, SLACK_MSG_HEAD)
            }
        }
        always {
            script {
                if (dbContainer) dbContainer.stop()
                if (dbContainer2) dbContainer2.stop()
                if (seleniumContainer) seleniumContainer.stop()
            }
            step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/test/*.xml', allowEmptyArchive: true])
            step([$class: 'JUnitResultArchiver', testResults: '**/build/test-results/integrationTest/*.xml', allowEmptyArchive: true])
            archiveArtifacts artifacts: 'build/reports/tests/integrationTest/snapshots/**', allowEmptyArchive: true
            // TODO CI側にPlugin入れるAsCodeする。
            // step([$class: 'JacocoPublisher', execPattern: '**/build/jacoco/*.exec', classPattern: 'build/classes/main', sourcePattern: 'src/main/java'])
            // TODO JIGを入れられるようになれば保存する。
            // archiveArtifacts artifacts: 'build/jig/*', allowEmptyArchive: true
        }
    }
}

def ipAddressOf(container) {
    final command = "docker inspect -f '{{ .NetworkSettings.IPAddress }}' ${container.id}"
    return sh(script: command, returnStdout: true).replaceAll(/(\r|\n)/, '') // chomp
}

/**
 * Slack&コンソールにメッセージを表示する。
 */
def showInfomation(message, color, channel, domain, taken, header) {
    if (SLACK_CHANNEL == '') return
    def caption = header + message
    echo caption
    slackSend channel: channel, color: color, message: caption, teamDomain: domain, token: taken
}
