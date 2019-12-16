package Service;

import Dao.BasketDao;
import Entity.Basket;

import java.util.List;

public class BasketService {

    private BasketDao basketDao = new BasketDao();

    public BasketService() {}

    public List<Basket> getBasketsByIDСlient(final long id){
        return basketDao.getBasketsByIDClient(id);
    }

    public Basket getProductInBasketByIDClientAndProduct(final long id_client, final long id_product){
        return basketDao.getProductInBasketByIDClientAndProduct(id_client, id_product);
    }

    public Long getAmountOfBasketIDClient(final long id_client){
        return basketDao.getAmountOfBasketIDClient(id_client);
    }

    public List<Basket> getAllBaskets(){
        return basketDao.getAllBaskets();
    }

    public void deleteBasket(Basket basket){
        basketDao.deleteBasket(basket);
    }

    public void updateBasket(Basket basket){
        basketDao.updateBasket(basket);
    }

    public void addBasket(Basket basket){
        basketDao.addBasket(basket);
    }

}
