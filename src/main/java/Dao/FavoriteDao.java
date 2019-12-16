package Dao;

import Database.DatabaseConnector;
import Entity.Favorite;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FavoriteDao {

    public List<Favorite> getAllFavorites(){

        List<Favorite> list = (List<Favorite>) (DatabaseConnector.createHibernateSession().createQuery("FROM Favorite").list());
        return list;
    }

    public void addFavorite(Favorite favorite){
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(favorite);
        ts.commit();
        session.close();
    }

    public void updateFavorite(Favorite favorite){
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(favorite);
        ts.commit();
        session.close();
    }

    public void deleteFavorite(Favorite favorite){
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(favorite);
        ts.commit();
        session.close();
    }

    public Long getAmountOfFavoritesIDClient(final long id_client){
        Long cntOfFavorites= null;
        try {
            cntOfFavorites = (Long) DatabaseConnector.createHibernateSession().createQuery("SELECT count(fav.id_product) FROM Favorite as fav WHERE fav.id_client = :id_client")
                    .setParameter("id_client", id_client)
                    .uniqueResult();
        }catch (Exception ex){
            System.out.println(ex);
            return new Long(0);
        }finally {
            if (cntOfFavorites == null) {
                System.out.println("Объект не найден");
                cntOfFavorites = new Long(0);
            }
            else
                System.out.println("Объект = "+ cntOfFavorites);
            return cntOfFavorites;
        }
    }

    public Favorite getFavoriteByIDClientAndProduct(final long id_client, final long id_product){
        Favorite favorite = null;
        try {
            List<Favorite> favoriteList = (List<Favorite>) DatabaseConnector.createHibernateSession().createQuery("FROM Favorite WHERE id_client = :id_client AND id_product = :id_product")
                    .setParameter("id_client", id_client)
                    .setParameter("id_product", id_product)
                    .list();
            if (favoriteList == null || favoriteList.size() == 0)
                favoriteList = null;
            else
                favorite = favoriteList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            favorite = null;
        }
        if (favorite == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ favorite);
        return favorite;
    }

    public List<Favorite> getFavoritesByIDClient(final long id_client){
        List<Favorite> favoriteList;
        try {
            favoriteList = (List<Favorite>) DatabaseConnector.createHibernateSession().createQuery("FROM Favorite WHERE id_client = :id_client")
                    .setParameter("id_client", id_client)
                    .list();
            if (favoriteList == null || favoriteList.size() == 0)
                favoriteList = null;
        }catch (Exception ex){
            System.out.println(ex);
            favoriteList = null;
        }
        if (favoriteList == null)
            System.out.println("Лист Объектов не найден");
        else
            System.out.println("Лист Объект = "+ favoriteList);
        return favoriteList;
    }
}
