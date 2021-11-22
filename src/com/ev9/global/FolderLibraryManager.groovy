/**
 * Folder Library Manager
 * (c) vhedayati @ 2019
 */

package com.ev9.global

import jenkins.model.Jenkins
import jenkins.plugins.git.GitSCMSource
import org.jenkinsci.plugins.workflow.libs.LibraryConfiguration
import org.jenkinsci.plugins.workflow.libs.SCMSourceRetriever
import org.jenkinsci.plugins.workflow.libs.FolderLibraries
import com.cloudbees.hudson.plugins.folder.Folder

void setFolderLibrary(String projectPath, String libraryGitSource, String libraryConfiguration, String gitCredentials) {
    Jenkins.getInstance().checkPermission(Jenkins.RUN_SCRIPTS)

    def item = Jenkins.instance.getItemByFullName(projectPath)
    if (item == null) {
        throw new RuntimeException("ERROR: " + projectPath + ' item not found')
    }
    else if (!Folder.class.isInstance(item)) {
        throw new RuntimeException("ERROR: " + projectPath + ' is not a Folder')
    }

    def workflowlib = new GitSCMSource(null,
            libraryGitSource,
            gitCredentials,
            "*",
            "",
            false)
    LibraryConfiguration sandboxlib = new LibraryConfiguration(libraryConfiguration,
            new SCMSourceRetriever(workflowlib))
    sandboxlib.setDefaultVersion("master")
    sandboxlib.setImplicit(false);
    sandboxlib.setAllowVersionOverride(true)

    def jobProperties = item.getProperties()
    for (Object property : jobProperties) {
        if (property.class.simpleName == 'FolderLibraries') {
            jobProperties.remove(property)
        }
    }
    jobProperties.add(new FolderLibraries(Arrays.asList(sandboxlib)))

    item.save()
    echo "Configured Library ${libraryGitSource} for ${projectPath}"
}
