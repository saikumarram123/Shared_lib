def call(pipelineParams) {
    pipeline {
        agent any
        stages {
               stage('Compile') {
            steps {

                echo 'Clean previous build output'
                sh './gradlew clean'

                echo 'Compile pipelineParams.app'
                sh "./gradlew :${pipelineParams.app}:compileJava :${pipelineParams.app}:compileTestJava --stacktrace"
                
                echo 'Compile pipelineParams.base'
                sh "./gradlew :${pipelineParams.base}:compileJava :${pipelineParams.base}:compileTestJava --stacktrace"
                
                echo 'Compile pipelineParams.common'
                sh "./gradlew :${pipelineParams.common}:compileJava :${pipelineParams.common}:compileTestJava --stacktrace"
                
            }
        }

            stage ('test') {
                steps {
               echo 'running tests on pipelineParams.app'
               sh "./gradlew cleanTest ${pipelineParams.app}:test --stacktrace"
                  
               echo 'running tests on pipelineParams.base'
               sh "./gradlew cleanTest ${pipelineParams.base}:test --stacktrace"   
                  
               echo 'running tests on pipelineParams.common'
               sh "./gradlew cleanTest ${pipelineParams.common}:test --stacktrace"    
                }
            }
}
}
}

