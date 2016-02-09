package services;

import controllers.routes;
import models.Menu;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by aivlev on 1/26/16.
 */
public class MenuService extends Action.Simple {
    @Override
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        List<Menu> menuList = new LinkedList<>();
        Menu rechargeOperation = new Menu("Recharge balance", routes.OperationsController.recharge().toString());
        Menu expensesOperation = new Menu("Expenses", routes.OperationsController.expenses().toString());
        Menu operations = new Menu("Operations", "#", Arrays.asList(rechargeOperation, expensesOperation));
        Menu other = new Menu("Other", "other");

        menuList.add(operations);
        menuList.add(other);

        ctx.args.put("menu", menuList);
        return delegate.call(ctx);
    }

    public static List<Menu> current() {
        return (List<Menu>)Http.Context.current().args.get("menu");
    }
}
