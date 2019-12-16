package Dao;

import Database.DatabaseConnector;
import Entity.Basket;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BasketDao {

    public List<Basket> getAllBaskets(){

        List<Basket> list = (List<Basket>) (DatabaseConnector.createHibernateSession().createQuery("FROM Basket").list());
        return list;
    }

    public void addBasket(Basket basket){
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(basket);
        ts.commit();
        session.close();
    }

    public void updateBasket(Basket basket){
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(basket);
        ts.commit();
        session.close();
    }

    public void deleteBasket(Basket basket){
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(basket);
        ts.commit();
        session.close();
    }

    public List<Basket> getBasketsByIDClient(final long id_client){
        List<Basket> basketList;
        try {
            basketList = (List<Basket>) DatabaseConnector.createHibernateSession().createQuery("FROM Basket WHERE id_client = :id_client")
                    .setParameter("id_client", id_client)
                    .list();
            if (basketList == null || basketList.size() == 0)
                basketList = null;
        }catch (Exception ex){
            System.out.println(ex);
            basketList = null;
        }
        if (basketList == null)
            System.out.println("Лист Объектов не найден");
        else
            System.out.println("Лист Объект = "+ basketList);
        return basketList;
    }

    public Long getAmountOfBasketIDClient(final long id_client){
        Long cntOfBasket= null;
        try {
            cntOfBasket = (Long) DatabaseConnector.createHibernateSession().createQuery("SELECT count(basket.id_product) FROM Basket as basket WHERE basket.id_client = :id_client")
                    .setParameter("id_client", id_client)
                    .uniqueResult();
        }catch (Exception ex){
            System.out.println(ex);
            return new Long(0);
        }finally {
            if (cntOfBasket == null) {
                System.out.println("Объект не найден");
                cntOfBasket = new Long(0);
            }
            else
                System.out.println("Объект = "+ cntOfBasket);
            return cntOfBasket;
        }
    }

    public Basket getProductInBasketByIDClientAndProduct(final long id_client, final long id_product){
        Basket productInBasket = null;
        try {
            List<Basket> productsInBasketList = (List<Basket>) DatabaseConnector.createHibernateSession().createQuery("FROM Basket WHERE id_client = :id_client AND id_product = :id_product")
                    .setParameter("id_client", id_client)
                    .setParameter("id_product", id_product)
                    .list();
            if (productsInBasketList.size() == 0)
                productInBasket = null;
            else
                productInBasket = productsInBasketList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            productInBasket = null;
        }
        if (productInBasket == null)
            System.out.println("Объект Basket не найден");
        else
            System.out.println("Объект Basket = "+ productInBasket);
        return productInBasket;
    }

}
