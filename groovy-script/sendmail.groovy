Map buildResults = [:]

void notifymail(Map results){
	echo "Job result: ${results.toString()}"
}
pipeline {

    agent any

    stages {

        stage('Build testJob') {

            steps {
                script {
                    def jobBuild = build job: 'testJob', propagate: false

                    def jobResult = jobBuild.getResult()

                    echo "Build of 'testJob' returned result: ${jobResult}"

                    buildResults['testJob'] = jobResult

                    if (jobResult != 'SUCCESS') {
                        error("testJob failed with result: ${jobResult}")
                    }
                }
            }
        }
    }

    post {

        always {
            echo "Build results: ${buildResults.toString()}"
        }

        success {
            echo "Build Successful"
            script {
                nofify_email(buildResults)
            }

        }

        failure {
            echo "Build Failed"

        }
    }
}



