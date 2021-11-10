package dao;

import dao.custom.impl.*;

/**
 * @author : Prageeth Milan
 * @e-mail : prageethmilan99@gmail.com
 **/

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAO(DAOTypes daoTypes){
        switch (daoTypes) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAIL:
                return new OrderDetailDAOImpl();
            case ORDER_ID:
                return new OrderIdDAOImpl();
            case SAVED_ORDER:
                return new SavedOrderDAOImpl();
            case USER:
                return new UserDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes{
        CUSTOMER,ITEM,ORDER,ORDER_DETAIL,ORDER_ID,SAVED_ORDER,USER
    }
}
