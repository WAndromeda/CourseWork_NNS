package Service;

import Dao.OrderShopDao;
import Entity.OrderShop;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class OrderShopService {

    private OrderShopDao orderShopDao = new OrderShopDao();

    public OrderShopService() {}

    public OrderShop getOrderShopByID(final long id){
        return orderShopDao.getOrderShopByID(id);
    }

    public List<OrderShop> getOrdersShopByIDClient(final long id_client){
        return orderShopDao.getOrdersShopByIDClient(id_client);
    }

    public OrderShop getOrderShopByIDOrderAndClient(final long id_order, final long id_client) {
        return orderShopDao.getOrderShopByIDOrderAndClient(id_order, id_client);
    }

    public List<OrderShop> getLimitOrdersShops(int limit, final long id_client){
            return orderShopDao.getLimitOrdersShopsByIDClient(limit, id_client);
    }

    public List<OrderShop> getAllOrdersShop(){
        return orderShopDao.getAllOrdersShop();
    }

    public void deleteOrderShop(OrderShop orderShop) throws UnsupportedEncodingException {
        orderShopDao.deleteOrderShop(orderShop);
    }

    public void updateOrderShop(OrderShop orderShop) throws UnsupportedEncodingException {
        orderShopDao.updateOrderShop(orderShop);
    }

    public long addOrderShop(OrderShop orderShop) throws UnsupportedEncodingException {
        return orderShopDao.addOrderShop(orderShop);
    }

}
