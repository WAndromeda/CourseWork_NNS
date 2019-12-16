package Service;

import Dao.ProductDao;
import Entity.Product;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class ProductService {

    private ProductDao productDao = new ProductDao();

    public ProductService() {}

    public Product getProductByID(final long id){
        return productDao.getProductByID(id);
    }

    public List<Product> getProductsByIDCategory(final long id_category) {
        return productDao.getProductsByIDCategory(id_category);
    }

    public List<Product> getProductInFavoriteByIDClient(final long id_client){
        return productDao.getProductInFavoriteByIDClient(id_client);
    }

    public List<Product> getProductInBasketByIDClient(final long id_client) {
        return productDao.getProductsInBasketByIDClient(id_client);
    }

    public List<Product> getAllProducts(){
        return productDao.getAllProducts();
    }

    public void deleteProduct(Product product) throws UnsupportedEncodingException {
        productDao.deleteProduct(product);
    }

    public void updateProduct(Product product) throws UnsupportedEncodingException {
        productDao.updateProduct(product);
    }

    public void addProduct(Product product) throws UnsupportedEncodingException {
        productDao.addProduct(product);
    }

}
