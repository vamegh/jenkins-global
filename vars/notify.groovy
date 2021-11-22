
def success() {
    milestone(label: 'Notify')
    currentBuild.result = 'SUCCESS'
    logger(3, "Current Build :: SUCCESS", 'equal')
    return "success"
}

def failure(boolean destroy=false, String message='Failure', String cmdPath="") {
    milestone(label: 'Notify')
    currentBuild.result = 'Failure'
    if (destroy) {
        tfProvision(cmdPath, 'destroy')
    }
    logger(1, "Finished: Failure :: Error: ${message}", 'star')
    error(message)
}

