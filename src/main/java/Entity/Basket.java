package Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "basket")
public class Basket implements Serializable {
    @Id
    @Column (name = "id_client")
    private long id_client;
    @Id
    @Column (name = "id_product")
    private long id_product;
    @Column (name = "amount_of_product")
    private long amount_of_product;

    public Basket() {
    }

    public Basket(long id_client, long id_product, long amount_of_product) {
        this.id_client = id_client;
        this.id_product = id_product;
        this.amount_of_product = amount_of_product;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public long getId_product() {
        return id_product;
    }

    public void setId_product(long id_product) {
        this.id_product = id_product;
    }

    public long getAmount_of_product() {
        return amount_of_product;
    }

    public void setAmount_of_product(long amount_of_product) {
        this.amount_of_product = amount_of_product;
    }


    /*public static void setFlagInBasketForList(List<Product> products, List<Basket> productsInBasket){
        for (Product product: products){
            for (Basket basket: productsInBasket){
                if (product.getId() == basket.getId_product()){
                    product.setInBasket(true);
                    break;
                }
            }
        }
    }

    public static void setFlagInBasket(Product product, List<Basket> productsInBasket){
        for (Basket basket: productsInBasket){
            if (product.getId() == basket.getId_product()){
                product.setInBasket(true);
                return;
            }
        }
    }*/

    @Override
    public String toString() {
        return "{\n" +
                "\"id_client\": \"" + id_client + "\",\n" +
                "\"id_product\": \"" + id_product + "\",\n" +
                "\"amount_of_product\": \"" + amount_of_product + "\"\n" +
                "}";
    }
}
