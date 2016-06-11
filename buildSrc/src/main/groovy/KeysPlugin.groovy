import org.gradle.api.*;

/*
    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
    Copyright 2016, Andrey Shcherbinko. All rights reserved.
*/
public class KeysPlugin implements Plugin<Project> {
    void apply(Project project) {
        def ext = project.extensions.create("keys", KeysPluginExtension)

        def genKeyTask = project.task('genkey') {
            description 'Generates ssl keystore'

            doLast {
                ext.storeFile.delete()

                ant.genkey(alias: ext.alias, keystore: ext.storeFile, storepass: ext.storePass, storetype: 'pkcs12',
                        keyalg: 'RSA', sigalg: 'SHA256withRSA', validity: 10000,
                        dname: "CN=${ext.domain}, O=Andrey Shcherbinko, L=Sydney, S=NSW, C=AU")

                println "The keystore file \"${ext.storeFile}\" has been created"
            }
        }

        def exportCertTask = project.task('exportCert') {
            description 'Exports the certificate for browsers'

            doLast {
                assert ext.storeFile.exists()

                def cmd = "keytool -export -alias site -storetype pkcs12 -keystore ${ext.storeFile} -storepass \"${ext.storePass}\" -file ${ext.certFile}"
                assert cmd.execute().waitFor() == 0

                println "The certificate \"${ext.certFile}\" has been exported"
            }
        }

        project.afterEvaluate {
            genKeyTask.outputs.file ext.storeFile

            exportCertTask.inputs.file ext.storeFile
            exportCertTask.outputs.file ext.certFile
        }
    }
}