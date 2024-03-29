package com.pandora.imgoptimizer.optimizer

import com.pandora.imgoptimizer.util.Logger
import org.gradle.api.Project


interface Optimizer {

    /**
     * @param project Project
     * @param log Logger
     * @param suffix String
     * @param files List<File>
     */
    void optimize(Project project, Logger log, String suffix, List<File> files)

}