package inject;

import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.google.inject.Provider;
import common.templates.HandlebarsTemplateService;
import common.templates.TemplateService;
import play.Configuration;
import play.Logger;

import javax.inject.Inject;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class TemplateServiceProvider implements Provider<TemplateService> {
    private static final String CONFIG_TEMPLATE_LOADERS = "handlebars.templateLoaders";
    private static final String CONFIG_FALLBACK_CONTEXTS = "handlebars.fallbackContexts";
    private static final String CONFIG_CACHING_ENABLED = "handlebars.chaching.enabled";
    private static final String CLASSPATH_TYPE = "classpath";
    private static final String FILE_TYPE = "file";
    private static final String TYPE_ATTR = "type";
    private static final String PATH_ATTR = "path";
    private final Configuration configuration;

    @Inject
    public TemplateServiceProvider(final Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public TemplateService get() {
        final boolean cacheIsEnabled = configuration.getBoolean(CONFIG_CACHING_ENABLED);
        final List<TemplateLoader> templateLoaders = initializeTemplateLoaders(CONFIG_TEMPLATE_LOADERS);
        final List<TemplateLoader> fallbackContexts = initializeTemplateLoaders(CONFIG_FALLBACK_CONTEXTS);
        final String templatePath = templateLoaders.stream().map(TemplateLoader::getPrefix).collect(joining(", "));

        Logger.debug("Provide HandlebarsTemplateService: {}", templatePath);
        Logger.debug("HandlebarsTemplateService cache enabled: {}", cacheIsEnabled);

        return HandlebarsTemplateService.of(templateLoaders, fallbackContexts, cacheIsEnabled);
    }

    private List<TemplateLoader> initializeTemplateLoaders(final String configKey) {
        return configuration.getConfigList(configKey)
                .stream()
                .map(this::initializeTemplateLoader)
                .collect(toList());
    }

    private TemplateLoader initializeTemplateLoader(final Configuration loaderConfig) {
        final String type = loaderConfig.getString(TYPE_ATTR);
        final String path = loaderConfig.getString(PATH_ATTR);
        if (CLASSPATH_TYPE.equals(type)) {
            return new ClassPathTemplateLoader(path);
        } else if (FILE_TYPE.equals(type)) {
            return new FileTemplateLoader(path);
        } else {
            throw new RuntimeException("Cannot build template loader for " + loaderConfig);
        }
    }
}
