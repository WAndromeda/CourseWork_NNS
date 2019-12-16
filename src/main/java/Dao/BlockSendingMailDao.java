package Dao;

import Database.DatabaseConnector;
import Entity.BlockSendingMail;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class BlockSendingMailDao {

    public List<BlockSendingMail> getAllBlockSendingMails(){

        List<BlockSendingMail> list = (List<BlockSendingMail>) (DatabaseConnector.createHibernateSession().createQuery("FROM BlockSendingMail").list());
        return list;
    }

    public void addBlockSendingMail(BlockSendingMail blockSendingMail){
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(blockSendingMail);
        ts.commit();
        session.close();
    }

    public void updateBlockSendingMail(BlockSendingMail basket){
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(basket);
        ts.commit();
        session.close();
    }

    public void deleteBlockSendingMail(BlockSendingMail basket){
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(basket);
        ts.commit();
        session.close();
    }

}
