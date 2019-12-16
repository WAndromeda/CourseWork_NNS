package Dao;

import Database.DatabaseConnector;
import Entity.Client;
import Interface.ClientInterface;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao implements ClientInterface {


    public List<Client> getAllClients(){

        List<Client> list = (List<Client>) (DatabaseConnector.createHibernateSession().createQuery("FROM Client").list());
        return list;
    }

    public void addClient(Client client) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.save(client);
        ts.commit();
        session.close();
    }

    public void updateClient(Client client) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSessionFactory().openSession();
        Transaction ts = session.beginTransaction();
        session.update(client);
        ts.commit();
        session.close();
    }

    public void deleteClient(Client client) throws UnsupportedEncodingException {
        Session session = DatabaseConnector.createHibernateSession();
        Transaction ts = session.beginTransaction();
        session.delete(client);
        ts.commit();
        session.close();
    }

    public Client getClientByID(final long id){
        Client client;
        try {
            List<Client> clientList = (List<Client>) DatabaseConnector.createHibernateSession().createQuery("FROM Client WHERE id = " + id).list();
            if (clientList == null || clientList.size() == 0)
                client = null;
            else
                client = clientList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            client = null;
        }
        if (client == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ client);
        return client;
    }

    public Client getClientByLogin(final String login){
        Client client;
        try {
            List<Client> clientList = (List<Client>) DatabaseConnector.createHibernateSession().createQuery("FROM Client WHERE email = :login OR phone = :login OR spare_phone = :login")
                    .setParameter("login", login)
                    .list();
            if (clientList == null || clientList.size() == 0)
                client = null;
            else
                client = clientList.get(0);
        }catch (Exception ex){
            System.out.println(ex);
            client = null;
        }
        if (client == null)
            System.out.println("Объект не найден");
        else
            System.out.println("Объект = "+ client);
        return client;
    }

    public OccupiedData isClientDataAlreadyOccupied(Client client){
        String hqlEmail = "FROM Client c " +
                "WHERE c.email = :new_email";
        String hqlPhone = "FROM Client c " +
                "WHERE c.phone = :new_phone OR c.spare_phone = :new_phone";
        String hqlSparePhone = "FROM Client c " +
                "WHERE c.phone = :new_spare_phone OR  c.spare_phone = :new_spare_phone";
        List<Client> clientListEmail = (List<Client>) DatabaseConnector.createHibernateSession().createQuery(hqlEmail)
                .setParameter("new_email", client.getEmail())
                .list();
        List<Client> clientListPhone = (List<Client>) DatabaseConnector.createHibernateSession().createQuery(hqlPhone)
                .setParameter("new_phone", client.getPhone())
                .list();
        List<Client> clientListSparePhone;
        if (client.getSpare_phone() == null || client.getSpare_phone().trim().equals(""))
            clientListSparePhone = new ArrayList<>();
        else
            clientListSparePhone = (List<Client>) DatabaseConnector.createHibernateSession().createQuery(hqlSparePhone)
                .setParameter("new_spare_phone", client.getSpare_phone())
                .list();
        OccupiedData occupiedData = OccupiedData.NONE;
        if (!clientListEmail.isEmpty() && !clientListPhone.isEmpty() && !clientListSparePhone.isEmpty())
            occupiedData = OccupiedData.EMAIL_PHONE_SPAREPHONE;
        else
            if (!clientListEmail.isEmpty() && !clientListPhone.isEmpty())
                occupiedData = OccupiedData.EMAIL_PHONE;
            else
                if (!clientListPhone.isEmpty()  && !clientListSparePhone.isEmpty())
                    occupiedData = OccupiedData.PHONE_SPAREPHONE;
                else
                    if (!clientListEmail.isEmpty()  && !clientListSparePhone.isEmpty())
                        occupiedData = OccupiedData.EMAIL_SPAREPHONE;
                    else
                        if (!clientListEmail.isEmpty())
                            occupiedData = OccupiedData.EMAIL;
                        else
                            if (!clientListPhone.isEmpty())
                                occupiedData = OccupiedData.PHONE;
                            else
                                if (!clientListSparePhone.isEmpty())
                                    occupiedData = OccupiedData.SPAREPHONE;
        return occupiedData;
    }
}
