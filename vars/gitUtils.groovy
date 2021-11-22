
def setGitUser(config) {
    try {
        gitUser = config.git_user
        gitEmail = config.git_email
    } catch (error) {
        gitUser = "gituser"
        gitEmail = "git-user@unknown.com"
    }
    sh("git config user.name \"${gitUser}\"")
    sh("git config user.email \"${gitEmail}\"")

}

def gitClone(config) {
    String gitRef, repoPath, gitCreds, gitUrl, gitUser, gitEmail
    try {
        gitRef = config.gitRef
        repoPath = config.repo_path
        gitCreds = config.git_creds
        gitUrl = config.git_url
    } catch (error) {
        echo "Error: ${error.message}"
        notify.failure(false, "Error:: Missing Config HashMap Keys")
    }
    setGitUser(config)

    checkout([$class                            : 'GitSCM',
              branches                          : [[name: "*/${gitRef}"]],
              doGenerateSubmoduleConfigurations : false,
              extensions:                       [[$class: 'RelativeTargetDirectory',
                                                  relativeTargetDir: "${repoPath}"],
                                                 [$class: 'SubmoduleOption',
                                                  disableSubmodules: false,
                                                  parentCredentials: true,
                                                  recursiveSubmodules: true,
                                                  reference: '',
                                                  timeout: 3,
                                                  trackingSubmodules: true],
                                                 [$class: 'UserIdentity',
                                                  email: "${gitEmail}",
                                                  name:  "${gitUser}"],
                                                 [$class: 'AuthorInChangelog']],
              submoduleCfg:                     [],
              userRemoteConfigs:                [[credentialsId: "${gitCreds}",
                                                  url: "${gitUrl}",
                                                  name: 'origin',
                                                  refspec: "${gitRef}"]]
    ])
}

def gitCommit(Map config) {
    String repoPath, message, commitFile, tagVer, tagMsg, gitCmd, type
    setGitUser(config)

    try {
        repoPath = config.repo_path
        type = config.type
        commitFile = config.commit_file
    } catch (error) {
        echo "Error: ${error.message}"
        notify.failure(false, "Error:: Missing Config HashMap Keys")
    }

    try {
        message = config.message
    } catch(error) {
        message = "Jenkins Commit"
    }

    if (type == "tag" || type == "commit-and-tag") {
       try {
           tagMsg = config.tag_msg
           tagVer = config.tag_ver
       } catch (error) {
           echo "Error: ${error.message}"
           notify.failure(false, "Error:: Missing Config HashMap Keys")
       }
    }

    switch (type) {
        case "commit":
            gitCmd = "cd ${repoPath}; git add ${commitFile} && git status && git commit -m \"${message}\""
            break
        case "tag":
            gitCmd = "cd ${repoPath}; git tag -a ${tagVer} -m \"${tagMsg}\""
            break
        case "commit-and-tag":
            gitCmd = "cd ${repoPath}; git add ${commitFile} && git status && git commit -m \"${message}\" && git tag -a ${tagVer} -m \"${tagMsg}\""
            break
        default:
            notify.failure(false, "gitCommit: Unknown Type Passed: ${type}")
            break
    }

    try {
        def response = sh(returnStdout: true, script: gitCmd).trim()
        println "Response :: " + response
    } catch (error) {
        echo "Error ::  $error.message"
        notify.failure(false, "Error:: Git ADD")
    }
}

def gitPush(Map config) {
    String repoPath, gitCmd, type, gitRef
    setGitUser(config)

    try {
        repoPath = config.repo_path
        type = config.type
        gitRef = config.gitRef
    } catch (error) {
        echo "Error: ${error.message}"
        notify.failure(false, "Error:: Missing Config HashMap Keys")
    }

    if (config.clone_method == "https"){
        def auth_user = config.auth_user
        def auth_pass = config.auth_pass
        def url_endpoint = config.url_endpoint
        sh("git remote set-url origin https://${auth_user}:${auth_pass}@${url_endpoint}")
    }

    switch (type) {
        case "push":
            gitCmd = "cd ${repoPath}; git show && git push origin HEAD:refs/heads/${gitRef}"
            break
        case "tag":
            gitCmd = "cd ${repoPath}; git show && git push origin --tags"
            break
        case "push-and-tag":
            gitCmd = "cd ${repoPath}; git show && git push origin HEAD:refs/heads/${gitRef} && git push origin --tags"
            break
        default:
            notify.failure(false, "gitPush: Unknown Type Passed: ${type}")
            break
    }

    try {
        def response = sh(returnStdout: true, script: gitCmd).trim()
        println "Response :: " + response
    } catch (error) {
        echo "Error ::  $error.message"
        notify.failure(false, "Error:: Git PUSH")
    }
}

