package Dao;

import Database.DatabaseConnector;
import Entity.PaymentMethod;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class PaymentMethodDao {

    public List<PaymentMethod> getAllPaymentMethods(){

        List<PaymentMethod> list = (List<PaymentMethod>) (DatabaseConnector.createHibernateSession().createQuery("FROM PaymentMethod").list());
        return list;
    }

    public void addPaymentMethod(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(paymentMethod);
        ts.commit();
        session.close();
    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(paymentMethod);
        ts.commit();
        session.close();
    }

    public void deletePaymentMethod(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(paymentMethod);
        ts.commit();
        session.close();
    }

    public PaymentMethod getPaymentMethodByID(final int id){
        PaymentMethod paymentMethod;
        try {
            List<PaymentMethod> paymentMethodList = (List<PaymentMethod>) DatabaseConnector.createHibernateSession().createQuery("FROM PaymentMethod WHERE id = " + id).list();
            if (paymentMethodList == null || paymentMethodList.size() == 0)
                paymentMethod = null;
            else
                paymentMethod = paymentMethodList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            paymentMethod = null;
        }
        if (paymentMethod == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ paymentMethod);
        return paymentMethod;
    }
}
