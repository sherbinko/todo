package util;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.FileConverter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@Component
public class CmdLineConfig {
    private static final CmdLineConfig instance = new CmdLineConfig();

    @Parameter(required = true, listConverter = FileConverter.class, description = "<SQLite database file>")
    List<File> params = new ArrayList<>();

    @Parameter(names = "-p", required = true, description = "server port")
    public Integer port;

    @Parameter(names = "-ssl", description = "whether HTTPS should be used")
    public boolean ssl = false;

    @Parameter(names = "-keystore", description = "keystore file")
    public File keyStoreFile = null;

    @Parameter(names = "-storepass", description = "keystore password")
    public String storePass = null;

    @Parameter(names = "-keypass", description = "key password")
    public String keyPass = null;

    @Parameter(names = "-logback", description = "Logback configuration file")
    public File logbackFile = null;

    private CmdLineConfig() {}
    public static CmdLineConfig getInstance() {return instance;}

    public File getDbFile() {
        return params.get(0);
    }

    public String getKeyStoreResource() {
        return (keyStoreFile == null) ? null : "file:"+keyStoreFile.getAbsolutePath();
    }

    public void validate() throws ParameterException {
        if (params.size() != 1) {
            throw new ParameterException("one parameter");
        }
    }
}
