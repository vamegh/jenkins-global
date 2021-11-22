/**
 * A Library that adds shared pipeline libraries to a folder
 */
import com.ev9.global.FolderLibraryManager

def call(String projectPath, String libraryGitSource, String libraryConfiguration, String gitCredentials='') {
	def manager = new FolderLibraryManager()
	manager.setFolderLibrary(projectPath, libraryGitSource, libraryConfiguration, gitCredentials)
}

