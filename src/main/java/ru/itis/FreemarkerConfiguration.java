package ru.itis;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author Bulat Giniyatullin
 * 17 Октябрь 2017
 */

public class FreemarkerConfiguration {
    private static Configuration configuration;

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration(Configuration.VERSION_2_3_26);
            configuration.setClassForTemplateLoading(FreemarkerConfiguration.class, "/templates/");
            configuration.setDefaultEncoding("utf-8");
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            configuration.setLogTemplateExceptions(false);
        }
        return configuration;
    }
}
