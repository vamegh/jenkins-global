def call(String packerScript) {
    def response = sh(returnStdout: true, script: packerScript).trim()
    println "Response :: " + response
}
