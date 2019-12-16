package Dao;

import Database.DatabaseConnector;
import Entity.Product;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ProductDao {

    public List<Product> getAllProducts(){

        List<Product> list = (List<Product>) (DatabaseConnector.createHibernateSession().createQuery("FROM Product").list());
        return list;
    }

    public void addProduct(Product product) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save( product);
        ts.commit();
        session.close();
    }

    public void updateProduct(Product product) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(product);
        ts.commit();
        session.close();
    }

    public void deleteProduct(Product product) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(product);
        ts.commit();
        session.close();
    }

    public Product getProductByID(final long id){
        Product product;
        try {
            List<Product> productList = (List<Product>) DatabaseConnector.createHibernateSession().createQuery("FROM Product WHERE id = :id")
                    .setParameter("id", id)
                    .list();
            if (productList == null || productList.size() == 0)
                product = null;
            else
                product = productList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            product = null;
        }
        if (product == null)
            System.out.println("Объект Product не найден");
        else
            System.out.println("Объект Product = "+ product);
        return product;
    }

    public List<Product> getProductsByIDCategory(final long id_category){
        List<Product> productList = null;
        try {
            productList = (List<Product>) DatabaseConnector.createHibernateSession().createQuery("FROM Product WHERE id_category = :id_category")
                    .setParameter("id_category", id_category)
                    .list();
            if (productList.size() == 0)
                productList = null;
        }catch (Exception ex){
            System.out.println(ex);
        }
        if (productList == null)
            System.out.println("Список Объектов не найден");
        else
            System.out.println("Список Объектов = "+ productList);
        return productList;
    }

    public List<Product> getProductInFavoriteByIDClient(final long id_client){
        List<Product> productList = null;
        try {
            productList = (List<Product>) DatabaseConnector.createHibernateSession().createQuery("FROM Product as product WHERE product.id IN (SELECT fav.id_product FROM Favorite as fav WHERE fav.id_client = :id_client)")
                    .setParameter("id_client", id_client)
                    .list();
            if (productList.size() == 0)
                productList = null;
        }catch (Exception ex){
            System.out.println(ex);
        }
        if (productList == null)
            System.out.println("Список Объектов не найден");
        else
            System.out.println("Список Объектов = "+ productList);
        return productList;
    }

    public List<Product> getProductsInBasketByIDClient(final long id_client){
        List<Product> productList = null;
        try {
            productList = (List<Product>) DatabaseConnector.createHibernateSession().createQuery("FROM Product as product WHERE product.id IN (SELECT basket.id_product FROM Basket as basket WHERE basket.id_client = :id_client)")
                    .setParameter("id_client", id_client)
                    .list();
            if (productList.size() == 0)
                productList = null;
        }catch (Exception ex){
            System.out.println(ex);
        }
        if (productList == null)
            System.out.println("Список Объектов не найден");
        else
            System.out.println("Список Объектов = "+ productList);
        return productList;
    }
}
