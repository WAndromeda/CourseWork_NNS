package Dao;

import Database.DatabaseConnector;
import Entity.ProductsInOrder;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ProductsInOrderDao {

    public List<ProductsInOrder> getAllProductsInOrder(){

        List<ProductsInOrder> list = (List<ProductsInOrder>) (DatabaseConnector.createHibernateSession().createQuery("FROM ProductsInOrder").list());
        return list;
    }

    public void addProductInOrder(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(productsInOrder);
        ts.commit();
        session.close();
    }

    public void updateProductInOrder(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(productsInOrder);
        ts.commit();
        session.close();
    }

    public void deleteProductInOrder(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(productsInOrder);
        ts.commit();
        session.close();
    }

    public List<ProductsInOrder> getProductsInOrderByIDOrder(final long id_order){
        List<ProductsInOrder>  productsList;
        try {
            productsList = (List<ProductsInOrder>) DatabaseConnector.createHibernateSession().createQuery("FROM ProductsInOrder WHERE id_order = :id_order")
                    .setParameter("id_order", id_order)
                    .list();
            if (productsList == null || productsList.size() == 0)
                productsList = null;
            if (productsList == null)
                System.out.println("Список не найден");
            else
                System.out.println("Список Объектов = "+ productsList);
        }catch (Exception ex){
            System.out.println(ex);
            productsList = null;
        }
       return productsList;
    }
}
