package cart;

import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import common.pages.SunrisePageData;
import play.mvc.Result;

import javax.inject.Inject;

public class CartDetailPageController extends SunriseController {

    @Inject
    public CartDetailPageController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public Result show(final String language) {
        final CartDetailPageContent content = new CartDetailPageContent();
        final SunrisePageData pageData = pageData(userContext(language), content);
        return ok(templateService().renderToHtml("cart", pageData));
    }
}
