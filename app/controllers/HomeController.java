package controllers;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import play.libs.F;
import play.mvc.Result;
import productcatalog.pages.HomePageContent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Locale;

/**
 * Controller for main web pages like index, imprint and contact.
 */
@Singleton
public final class HomeController extends SunriseController {

    @Inject
    public HomeController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> show() {
        final String language = context().project().languages().stream().findFirst().map(Locale::toLanguageTag)
                .orElse("en");
        return F.Promise.pure(redirect(reverseRouter().home(language)));
    }

    public F.Promise<Result> show(final String language) {
        return F.Promise.pure(getResult(userContext(language)));
    }

    private Result getResult(final UserContext userContext) {
        final HomePageContent content = new HomePageContent();
        return ok(templateService().renderToHtml("home", pageData(userContext, content)));
    }
}
