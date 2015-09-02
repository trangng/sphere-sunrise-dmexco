package cart;

import common.cms.CmsPage;
import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import common.pages.SunrisePageData;
import play.libs.F;
import play.mvc.Result;

import javax.inject.Inject;

public class CartDetailPageController extends SunriseController {

    @Inject
    public CartDetailPageController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> show(final String language) {
        final F.Promise<CmsPage> commonCmsPagePromise = getCommonCmsPage();
        return commonCmsPagePromise.map(commonCmsPage -> {
            final CartDetailPageContent content = new CartDetailPageContent();
            final SunrisePageData pageData = SunrisePageData.of(commonCmsPage, context(), content);
            return ok(templateService().renderToHtml("cart", pageData));
        });
    }
}
