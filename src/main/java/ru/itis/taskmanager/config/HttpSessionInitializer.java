package ru.itis.taskmanager.config;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

public class HttpSessionInitializer extends AbstractHttpSessionApplicationInitializer {
    protected HttpSessionInitializer() {
        super(ApplicationContextConfig.class);
    }
}
