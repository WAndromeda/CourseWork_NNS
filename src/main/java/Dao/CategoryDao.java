package Dao;

import Database.DatabaseConnector;
import Entity.Category;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class CategoryDao {

    public List<Category> getAllCategories(){

        List<Category> list = (List<Category>) (DatabaseConnector.createHibernateSession().createQuery("FROM Category").list());
        return list;
    }

    public List<Category> getLimitCategories(int limit){
        if (limit > 8 || limit < 1)
            limit = 8;
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        Query q = session.createQuery("FROM Category");
        List<Category> categoryList =  q.setMaxResults(limit).list();
        ts.commit();
        return categoryList;
    }

    public void addCategory(Category category) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(category);
        ts.commit();
        session.close();
    }

    public void updateCategory(Category category) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(category);
        ts.commit();
        session.close();
    }

    public void deleteCategory(Category category) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(category);
        ts.commit();
        session.close();
    }

    public Category getCategoryByID(final long id){
        Session session = DatabaseConnector.createHibernateSession();
        Category category;
        try {
            List<Category> categoryList = (List<Category>) session.createQuery("FROM Category WHERE id = ?")
                    .setParameter(0, id)
                    .list();
            if (categoryList == null || categoryList.size() == 0)
                category = null;
            else
                category = categoryList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            category = null;
        }
        if (category == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ category);
        return category;
    }
}
