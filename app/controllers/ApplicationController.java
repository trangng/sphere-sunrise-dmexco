package controllers;

import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import org.apache.commons.io.IOUtils;
import play.Application;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Controller for main web pages like index, imprint and contact.
 */
@Singleton
public final class ApplicationController extends SunriseController {

    private final Application application;

    @Inject
    public ApplicationController(final ControllerDependency controllerDependency, final Application application) {
        super(controllerDependency);
        this.application = application;
    }

    public F.Promise<Result> index() {
        return F.Promise.pure(ok("Sunrise Home"));
    }

    public Result version() throws IOException {
        final String jsonString =
                IOUtils.toString(application.resourceAsStream("internal/version.json"), StandardCharsets.UTF_8);
        return ok(jsonString).as(Http.MimeTypes.JSON);
    }
}
