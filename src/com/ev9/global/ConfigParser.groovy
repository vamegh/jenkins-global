/**
 * Json Config Parser for Pipeline Job Creation
 * (c) vhedayati @ 2019
 **/
package com.ev9.global

import groovy.json.JsonSlurperClassic

public class ConfigParser implements Serializable {

    /*
     * name (optional)
     */
    String name

    /*
     * journey
     */
    String journey

    /*
     * Deployment type
     */
    String type

    /*
     * Git Credentials to use
     */
    String git_credentials

    /*
     * Git Base URL
     */
    String git_url

    /*
     *  Git Branch (defaults to master)
     */
    String git_branch

    /*
     * Git Extra Library
     */
    String git_extra_lib

    /*
     * List of Components
     */
    List components

    /*
     * List of deployments to be made
     */
    List<Deployments> deployments = new ArrayList<>()

    ConfigParser() {
    }

    ConfigParser(String configuration) {
        def config = (new HashMap(new JsonSlurperClassic().parseText(configuration))).asImmutable()
        this.name = config.name
        this.type = config.type
        this.git_credentials = config.git_credentials
        this.git_url = config.git_url
        this.git_branch = config.git_branch
        this.git_extra_lib = config.git_extra_lib
        this.journey = config.journey
        this.components = config.components
        def cfg = new ConfigParser(config) // getting around lazymaps
        this.deployments = cfg.deployments
    }

    @Override
    String toString() {
        return "ConfigParser{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", journey='" + journey + '\'' +
                ", git_credentials='" + git_credentials + '\'' +
                ", git_url='" + git_url + '\'' +
                ", git_branch='" + git_branch + '\'' +
                ", git_extra_lib='" + git_extra_lib + '\'' +
                ", components='" + components + '\'' +
                ", deployments=" + deployments +
                '}'
    }
}
