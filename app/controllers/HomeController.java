package controllers;

import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import play.i18n.Lang;
import play.libs.F;
import play.mvc.Result;
import productcatalog.pages.HomePageContent;

import javax.inject.Inject;
import javax.inject.Singleton;

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
        final String languageTag = request().acceptLanguages().stream().findFirst().map(Lang::code).orElse("en");
        return F.Promise.pure(redirect(reverseRouter().home(languageTag)));
    }

    public F.Promise<Result> show(final String languageTag) {
        return F.Promise.pure(getResult(userContext(languageTag)));
    }

    private Result getResult(final UserContext userContext) {
        final HomePageContent content = new HomePageContent();
        return ok(templateService().renderToHtml("home", pageData(userContext, content)));
    }
}
