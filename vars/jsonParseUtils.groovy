import com.cloudbees.groovy.cps.NonCPS
import groovy.json.*

@NonCPS
def parseJson (String configuration) {
    return new JsonSlurperClassic().parseText(configuration)
}

@NonCPS
def returnJson (def dataMap) {
    return new JsonBuilder(dataMap).toPrettyString()
}

