package controllers;

import common.cms.CmsPage;
import common.contexts.UserContext;
import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
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

    public F.Promise<Result> index(final String locale) {
        final UserContext userContext = userContext(locale);
        final F.Promise<CmsPage> cmsPagePromise = cmsService().getPage(userContext.locale(), "home");
        return cmsPagePromise.map(cms -> getResult(userContext));
    }

    private Result getResult(final UserContext userContext) {
        final HomePageContent content = new HomePageContent();
        return ok(templateService().renderToHtml("home", pageData(userContext, content)));
    }
}
