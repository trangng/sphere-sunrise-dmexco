package cart;

import common.controllers.ControllerDependency;
import common.controllers.SunriseController;
import play.libs.F;
import play.mvc.Result;

import javax.inject.Inject;

public class CartDetailPageController extends SunriseController {

    @Inject
    public CartDetailPageController(final ControllerDependency controllerDependency) {
        super(controllerDependency);
    }

    public F.Promise<Result> show(final String language) {
        return F.Promise.pure(ok("present"));
    }
}
