package io.qvertx.service.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

/**
 * @author Kislay Verma
 */
public class SpringContextLoader {
    private final ApplicationContext context;
    private static final Logger LOGGER = LoggerFactory.getLogger(SpringContextLoader.class);

    public SpringContextLoader(String[] springContextFilePaths) {
        LOGGER.info("Loading Spring configs...");

        context = new ClassPathXmlApplicationContext(springContextFilePaths);
        LOGGER.info("Loaded Spring configs");
        Arrays.stream(context.getBeanDefinitionNames()).forEach(LOGGER::info);
    }

    public Object getBean(String name) {
        return context.getBean(name);
    }
}
