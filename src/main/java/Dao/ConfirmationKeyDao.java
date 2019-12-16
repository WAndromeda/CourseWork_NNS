package Dao;

import Database.DatabaseConnector;
import Entity.ConfirmationKey;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ConfirmationKeyDao {

    public List<ConfirmationKey> getAllConfirmationKeys(){

        List<ConfirmationKey> list = (List<ConfirmationKey>) (DatabaseConnector.createHibernateSession().createQuery("FROM ConfirmationKey").list());
        return list;
    }

    public void addConfirmationKey(ConfirmationKey confirmationKey) {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(confirmationKey);
        ts.commit();
        session.close();
    }

    public void updateConfirmationKey(ConfirmationKey confirmationKey) {
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(confirmationKey);
        ts.commit();
        session.close();
    }

    public void deleteConfirmationKey(ConfirmationKey confirmationKey) {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(confirmationKey);
        ts.commit();
        session.close();
    }

    public ConfirmationKey getConfirmationKeyByIDClient(final long id_client){
        Session session = DatabaseConnector.createHibernateSession();
        ConfirmationKey confirmationKey;
        try {
            List<ConfirmationKey> confirmationKeyList = (List<ConfirmationKey>) session.createQuery("FROM ConfirmationKey WHERE id_client = ?")
                    .setParameter(0, id_client)
                    .list();
            if (confirmationKeyList == null || confirmationKeyList.size() == 0)
                confirmationKey = null;
            else
                confirmationKey = confirmationKeyList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            confirmationKey = null;
        }
        if (confirmationKey == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ confirmationKey);
        return confirmationKey;
    }
}
