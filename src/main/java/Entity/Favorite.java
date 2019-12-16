package Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "favorite")
public class Favorite implements Serializable {
    @Id
    @Column(name="id_client")
    private long id_client;
    @Id
    @Column(name="id_product")
    private long id_product;

    public Favorite() {
    }

    public Favorite(long id_client, long id_product) {
        this.id_client = id_client;
        this.id_product = id_product;
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

    /*public static void setFlagInFavoriteForList(List<Product> products, List<Favorite> productsInFavorite){
        for (Product product: products){
            for (Favorite favorite: productsInFavorite){
                if (product.getId() == favorite.getId_product()){
                    product.setInFavorite(true);
                    break;
                }
            }
        }
    }

    public static void setFlagInFavorite(Product product, List<Favorite> productsInFavorite){
        for (Favorite favorite: productsInFavorite){
            if (product.getId() == favorite.getId_product()){
                product.setInFavorite(true);
                return;
            }
        }
    }*/

    @Override
    public String toString() {
        return "{\n" +
                "\"id_client\": \"" + id_client + "\",\n" +
                "\"id_product\": \"" + id_product + "\"\n" +
                "}";
    }

}
