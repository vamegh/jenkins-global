def runServerSpec(String cmdPath, String cmdParams = '', keepInstanceRunning=true) {
    if (! cmdPath || cmdPath == null ) {
        cmdPath = "${env.WORKSPACE}/tests/"
    }

    try {
        sh """ cd ${cmdPath}; rake ${cmdParams} """
        notify.success()
    } catch (error) {
        notify.failure(keepInstanceRunning)
    }
}

def dlGoss(def user, def pass, def host) {
    try {
        dlCmd = 'echo \"Doing Nothing\" '
    } catch (error) {
        notify.failure(true)
    }
}

def userInputStep() {
    try {
        input(id: 'plan', message: 'Terraform plan Acceptance')
        return true
    } catch(err) {
        echo "User Aborted"
        return false
    }
}

