package Service;

import Dao.ConfirmationKeyDao;
import Entity.ConfirmationKey;

import java.util.List;

public class ConfirmationKeyService {

    private ConfirmationKeyDao confirmationKeyDao = new ConfirmationKeyDao();

    public ConfirmationKeyService() {}

    public ConfirmationKey getConfirmationKeyByIDClient(final long id_client){
        return confirmationKeyDao.getConfirmationKeyByIDClient(id_client);
    }

    public List<ConfirmationKey> getAllConfirmationKeys(){
        return confirmationKeyDao.getAllConfirmationKeys();
    }


    public void deleteConfirmationKey(ConfirmationKey confirmationKey)  {
        confirmationKeyDao.deleteConfirmationKey(confirmationKey);
    }

    public void updateConfirmationKey(ConfirmationKey confirmationKey) {
        confirmationKeyDao.updateConfirmationKey(confirmationKey);
    }

    public void addConfirmationKey(ConfirmationKey confirmationKey)  {
        confirmationKeyDao.addConfirmationKey(confirmationKey);
    }
}
