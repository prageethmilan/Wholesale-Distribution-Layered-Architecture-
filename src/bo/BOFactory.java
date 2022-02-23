package bo;

import bo.custom.impl.*;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBO(BOTypes types) {
        switch (types) {
            case ADMIN:
                return new AdminDashBoardBOImpl();
            case CASHIER:
                return new CashierDashboardBOImpl();
            case SAVED_ORDER:
                return new SavedOrdersBOImpl();
            case LOGIN:
                return new LoginFormBOImpl();
            case SIGNUP:
                return new SignUpFormBOImpl();
            case STOCK:
                return new StockReportBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        ADMIN, CASHIER, LOGIN, SAVED_ORDER, SIGNUP, STOCK
    }
}
