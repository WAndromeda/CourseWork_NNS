package Service;

import Dao.ClientDao;
import Entity.Client;
import Interface.ClientInterface;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ClientService implements ClientInterface {

    private ClientDao clientDao = new ClientDao();

    public ClientService() {}

    public Client getClientByID(final long id){
        return clientDao.getClientByID(id);
    }

    public List<Client> getAllClients(){
        return clientDao.getAllClients();
    }

    public void deleteClient(Client client) throws UnsupportedEncodingException {
        clientDao.deleteClient(client);
    }

    public void updateClient(Client client) throws UnsupportedEncodingException {
        clientDao.updateClient(client);
    }

    public void addClient(Client client) throws UnsupportedEncodingException {
        clientDao.addClient(client);
    }

    public OccupiedData isClientDataAlreadyOccupied(Client client){
        return clientDao.isClientDataAlreadyOccupied(client);
    }

    public Client getClientByLogin(final String login){
        return clientDao.getClientByLogin(login);
    }

}
