package Service;

import Dao.FavoriteDao;
import Entity.Favorite;

import java.util.List;

public class FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDao();

    public FavoriteService() {}

    public List<Favorite> getFavoritesByIDClient(final long id){
        return favoriteDao.getFavoritesByIDClient(id);
    }

    public Favorite getFavoriteByIDClientAndProduct(final long id_client, final long id_product) {
        return favoriteDao.getFavoriteByIDClientAndProduct(id_client, id_product);
    }

    public Long getAmountOfFavoritesIDClient(final long id_client){
        return favoriteDao.getAmountOfFavoritesIDClient(id_client);
    }

    public List<Favorite> getAllFavorites(){
        return favoriteDao.getAllFavorites();
    }

    public void deleteFavorite(Favorite favorite){
        favoriteDao.deleteFavorite(favorite);
    }

    public void updateFavorite(Favorite favorite){
        favoriteDao.updateFavorite(favorite);
    }

    public void addFavorite(Favorite favorite){
        favoriteDao.addFavorite(favorite);
    }

}
