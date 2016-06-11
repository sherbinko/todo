import ch.qos.logback.core.joran.spi.JoranException;
import com.beust.jcommander.JCommander;
import com.google.common.base.Preconditions;
import data.CommonDAO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.autoconfigure.dao.PersistenceExceptionTranslationAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import services.AuthService;
import services.TaskService;
import util.CmdLineConfig;

import java.io.File;

/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * Copyright 2016, Andrey Shcherbinko. All rights reserved.
 */
@Import({
        CommonDAO.class,
        AuthService.class,
        TaskService.class,

        AopAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        EmbeddedServletContainerAutoConfiguration.class,
        ErrorMvcAutoConfiguration.class,
        HttpEncodingAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        JacksonAutoConfiguration.class,
        JtaAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        PersistenceExceptionTranslationAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        SpringBootWebSecurityConfiguration.class,
        SpringDataWebAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        WebSocketAutoConfiguration.class
})
@ImportResource("classpath:app.xml")
public class EntryPoint {
    private static CmdLineConfig getConfig(String[] args) {
        CmdLineConfig config = CmdLineConfig.getInstance();
        JCommander jCommander = new JCommander(config);

        if (args.length == 0) {
            jCommander.usage();
            config = null;
        } else {
            jCommander.parse(args);
            config.validate();
        }

        return config;
    }

    private static void configureLogback(File logbackFile) throws JoranException {
        if (logbackFile!= null) {
            Preconditions.checkArgument(logbackFile.exists(), "Logback configuration file \"%s\" does not exist", logbackFile);
            System.setProperty("logging.config", logbackFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        try {
            CmdLineConfig config = getConfig(args);

            if (config != null) {
                configureLogback(config.logbackFile);

                SpringApplication springApplication = new SpringApplication(EntryPoint.class);
                springApplication.run();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
