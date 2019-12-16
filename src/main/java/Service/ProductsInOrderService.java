package Service;

import Dao.ProductsInOrderDao;
import Entity.ProductsInOrder;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ProductsInOrderService {

    private ProductsInOrderDao productsInOrderDao = new ProductsInOrderDao();

    public ProductsInOrderService() {}

    public List<ProductsInOrder> getProductsInOrderByIDOrder(final long id){
        return productsInOrderDao.getProductsInOrderByIDOrder(id);
    }

    public List<ProductsInOrder> getAllProductsInOrder(){
        return productsInOrderDao.getAllProductsInOrder();
    }

    public void deleteProductInOrder(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        productsInOrderDao.deleteProductInOrder(productsInOrder);
    }

    public void updateProductInOrder(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        productsInOrderDao.updateProductInOrder(productsInOrder);
    }

    public void addProductInOrder(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        productsInOrderDao.addProductInOrder(productsInOrder);
    }

}
