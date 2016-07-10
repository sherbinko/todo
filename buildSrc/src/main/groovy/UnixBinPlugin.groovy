import org.gradle.api.*;

/*
    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
    Copyright 2016, Andrey Shcherbinko. All rights reserved.
*/
public class UnixBinPlugin implements Plugin<Project> {
    void apply(Project project) {
        UnixBinExtension ext = project.extensions.create("unixBin", UnixBinExtension)

        def createUnixBinTask = project.task('unixBin', dependsOn: project.tasks.jar) {
            description 'Converts a jar into an executable unix file'

            doLast {
                project.ant.concat (destfile : ext.bin, binary:true) {
                    path(location: ext.script)
                    path(location: ext.jar)
                }
            }
        }

        project.afterEvaluate {
            createUnixBinTask.inputs.files ext.jar, ext.script
            createUnixBinTask.outputs.file ext.bin
        }
    }
}