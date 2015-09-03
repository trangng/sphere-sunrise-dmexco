package controllers;

import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import io.sphere.sdk.products.search.ProductProjectionSearch;
import org.apache.commons.io.IOUtils;
import play.Application;
import play.inject.Injector;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import setupwidget.controllers.SetupController;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Front controller for the {@link HomeController} to enable a widget to set the SPHERE.IO credentials.
 */
@Singleton
public class ApplicationController extends SunriseController {
    private final Application application;
    private final Injector injector;
    private final SetupController setupController;

    @Inject
    public ApplicationController(final ControllerDependency controllerDependency, final Application application, final Injector injector, final SetupController setupController) {
            super(controllerDependency);
            this.application = application;
            this.injector = injector;
            this.setupController = setupController;
    }

    public F.Promise<Result> index() {
        return setupController.handleOrFallback(() -> injector.instanceOf(HomeController.class).show());
    }

    public Result version() throws IOException {
        final String jsonString =
                IOUtils.toString(application.resourceAsStream("internal/version.json"), StandardCharsets.UTF_8);
        return ok(jsonString).as(Http.MimeTypes.JSON);
    }

    public F.Promise<Result> health() throws IOException {
        return sphere().execute(ProductProjectionSearch.ofCurrent().withLimit(1))
                .map(result -> {
                    final boolean ok = !result.getResults().isEmpty();
                    if (!ok) {
                        throw new RuntimeException("cannot fetch any product");
                    }
                    return ok("{\n" +
                            "  \"self\" : {\n" +
                            "    \"healthy\" : true\n" +
                            "  }\n" +
                            "}");
                })
                .recover(e -> status(Http.Status.SERVICE_UNAVAILABLE, "{\n" +
                        "  \"self\" : {\n" +
                        "    \"healthy\" : false\n" +
                        "  }\n" +
                        "}"))
                .map(r -> r.as(Http.MimeTypes.JSON));
    }
}
