package Dao;

import Database.DatabaseConnector;
import Entity.OrderShop;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class OrderShopDao {

    public List<OrderShop> getAllOrdersShop(){

        List<OrderShop> list = (List<OrderShop>) (DatabaseConnector.createHibernateSession().createQuery("FROM OrderShop").list());
        return list;
    }

    public List<OrderShop> getLimitOrdersShopsByIDClient(int limit, final long id_client){
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        Query q = session.createQuery("FROM OrderShop WHERE id_client = :id_c ORDER BY datetime_of_creation DESC");
        List<OrderShop> ordersShopList =  q.setMaxResults(limit).setParameter("id_c", id_client).list();
        ts.commit();
        return ordersShopList;
    }

    public long addOrderShop(OrderShop orderShop) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        long id = (long) session.save(orderShop);
        ts.commit();
        session.close();
        return id;
    }

    public void updateOrderShop(OrderShop orderShop) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(orderShop);
        ts.commit();
        session.close();
    }

    public void deleteOrderShop(OrderShop orderShop) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(orderShop);
        ts.commit();
        session.close();
    }

    public OrderShop getOrderShopByID(final long id){
        OrderShop orderShop;
        try {
            List<OrderShop> orderShopList = (List<OrderShop>) DatabaseConnector.createHibernateSession().createQuery("FROM OrderShop WHERE id = :id")
                    .setParameter("id", id)
                    .list();
            if (orderShopList == null || orderShopList.size() == 0)
                orderShop = null;
            else
                orderShop = orderShopList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            orderShop = null;
        }
        if (orderShop == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ orderShop);
        return orderShop;
    }

    public OrderShop getOrderShopByIDOrderAndClient(final long id_order, final long id_client){
        OrderShop orderShop;
        try {
            List<OrderShop> orderShopList = (List<OrderShop>) DatabaseConnector.createHibernateSession().createQuery("FROM OrderShop WHERE id = :id AND id_client = :id_client")
                    .setParameter("id", id_order)
                    .setParameter("id_client", id_client)
                    .list();
            if (orderShopList == null || orderShopList.size() == 0)
                orderShop = null;
            else
                orderShop = orderShopList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            orderShop = null;
        }
        if (orderShop == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ orderShop);
        return orderShop;
    }


    public List<OrderShop> getOrdersShopByIDClient(final long id_client){
        List<OrderShop> orderShopList;
        try {
            orderShopList = (List<OrderShop>) DatabaseConnector.createHibernateSession().createQuery("FROM OrderShop WHERE id_client = :id_client ORDER BY datetime_of_creation DESC")
                    .setParameter("id_client", id_client)
                    .list();
            if (orderShopList == null || orderShopList.size() == 0)
                orderShopList = null;
        }catch (Exception ex){
            System.out.println(ex);
            orderShopList = null;
        }
        if (orderShopList == null)
            System.out.println("Лист Объектов не найден");
        else
            System.out.println("Лист Объектов = "+ orderShopList);
        return orderShopList;
    }
}
