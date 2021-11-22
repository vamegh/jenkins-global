/**
 * A Library that adds shared pipeline libraries to a folder
 */
import com.ev9.global.ConfigParser

def call(String configuration) {
  def cfgHash = new ConfigParser(configuration)
  return cfgHash
}
