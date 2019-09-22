package com.pandora.imgoptimizer

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.BaseVariant
import com.pandora.imgoptimizer.extension.ImgOptimizerExtension
import com.pandora.imgoptimizer.optimizer.Constants
import com.pandora.imgoptimizer.task.ImgOptimizerTask
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project

class ImgOptimizerPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin(AppPlugin)) {
            applyAndroid(project, (DomainObjectCollection<BaseVariant>) project.android.applicationVariants)
        } else if (project.plugins.hasPlugin(LibraryPlugin)) {
            applyAndroid(project, (DomainObjectCollection<BaseVariant>) project.android.libraryVariants)
        } else {
            throw new IllegalArgumentException('img-optimizer gradle plugin only works in with Android module.')
        }
    }

    static void applyAndroid(Project project, DomainObjectCollection<BaseVariant> variants) {
        //创建扩展属性optimizerOptions
        def ext = project.extensions.create(Constants.EXT_NAME, ImgOptimizerExtension)

        variants.all { variant ->
            println("-------- variant: $variant.name --------")
            List<File> imgDirectories = []
            variant.sourceSets.each { sourceSet ->
                println("sourceSets.${sourceSet.name} -->")
                sourceSet.resDirectories.each { res ->
                    if (res.exists()) {
                        println("${res.name}.directories:")
                        res.eachDir {
                            if (it.directory && (it.name.startsWith("drawable") || it.name.startsWith("mipmap"))) {
                                println("$it.absolutePath")
                                //向列表添加元素
                                imgDirectories << it
                            }
                        }
                    }
                }
            }

            if (!imgDirectories.empty) {
                //添加Task任务,解析扩展属性
                project.task(type: ImgOptimizerTask, overwrite: true, Constants.TASK_NAME.
                        concat(project.name.capitalize()).concat(variant.buildType.name.capitalize())) {
                    it.group = "optimize"
                    it.description = "Optimize ${variant.buildType.name} images"
                    it.imgDirs = imgDirectories
                    it.triggerSize = ext.triggerSize
                    it.suffix = ext.suffix
                    it.type = ext.type
                }
            }
        }
    }

}
