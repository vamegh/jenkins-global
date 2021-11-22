def call(String cmdPath, String cmdType) {
    if (! cmdPath || cmdPath == null ) {
        notify.failure(false, "No Command Path Provided")
    }
    switch(cmdType) {
        case 'destroy':
            cmdType = cmdType + ' -force'
            sh """ cd ${cmdPath} ; sh run-terraform.sh ${cmdType} """
            return true
            break
        case 'plan':
            cmdType = cmdType + ' -out=tfplan -input=false'
            sh """ cd ${cmdPath}; sh run-terraform.sh ${cmdType} """
            return true
            break
        case 'plan-destroy':
            cmdType = cmdType + ' -destroy -out=tfplan -input=false'
            sh """ cd ${cmdPath}; sh run-terraform.sh ${cmdType} """
            return true
            break
        case 'apply':
            cmdType = cmdType + ' -auto-approve'
            try {
              sh """ cd ${cmdPath}; sh run-terraform.sh ${cmdType} """
              return true
            } catch (error) {
                echo "Error: ${error.message}"
                notify.failure(true, 'Failed To Run Terraform Apply')
                return false
            }
            break
        default:
            notify.failure(false, 'Unknown Option Supplied')
            return null
            break
    }
    return null
}

