/**
 * Json Config Parser for Pipeline Job Creation
 * deployments hash of arrays definition
 * (c) vhedayati @ 2019
 **/
package com.ev9.global

class Deployments implements Serializable {
    /*
     * Git repo / codebase
     */
    String codebase
    String git_repo

    /*
     * Git repo / codebase Branch defaults to master
     */
    String git_branch

    /*
     * process for Pipeline parameters (a List)
     */
    List process

    /*
     * Codebase Deployment Environments
     */
    List environments


    /*
     * Codebase Deployment Paths
     */
    List paths

    /*
     * List of Components
     */
    List components

    Deployments() {
    }

    @Override
    String toString() {
        return "Deployments{" +
                "codebase='" + codebase + '\'' +
                ", git_repo='" + git_repo + '\'' +
                ", git_branch='" + git_branch + '\'' +
                ", process='" + process + '\'' +
                ", components='" + components + '\'' +
                ", paths='" + paths + '\'' +
                ", environments=" + environments +
                '}'
    }
}
