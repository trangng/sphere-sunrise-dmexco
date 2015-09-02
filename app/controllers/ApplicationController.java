package controllers;

import common.cms.CmsPage;
import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import common.pages.SunrisePageData;
import play.libs.F;
import play.mvc.Result;
import productcatalog.pages.HomePageContent;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Controller for main web pages like index, imprint and contact.
 */
@Singleton
public final class ApplicationController extends SunriseController {

    @Inject
    public ApplicationController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> index() {
        return getCmsPage("home").map(this::getResult);
    }

    private Result getResult(final CmsPage cms) {
        final HomePageContent content = new HomePageContent();
        final SunrisePageData pageData = SunrisePageData.of(cms, context(), content);
        return ok(templateService().renderToHtml("home", pageData));
    }
}
