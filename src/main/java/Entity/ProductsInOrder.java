package Entity;

import Interface.Encoding;
import Interface.ProductInterface;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "products_in_order")
public class ProductsInOrder implements Encoding, Serializable, ProductInterface {
    @Id
    @Column(name = "id_order")
    private long id_order;
    @Id
    @Column (name = "id_product")
    private long id_product;
    @Column (name = "amount_of_product")
    private long amount_of_product;
    @Column (name = "price")
    private long price;
    @Column (name = "price_with_discount")
    private long price_with_discount;
    @Column (name = "name")
    private String name;

    public ProductsInOrder() {
    }

    public static ProductsInOrder getProductsInOrderInOtherCharset(ProductsInOrder productsInOrder, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        productsInOrder.setName(new String(productsInOrder.getName().getBytes(oldCharset), newCharset));
        return productsInOrder;
    }

    public static ProductsInOrder getProductsInOrderFromUTF8ToCP1251Charset(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        String oldCharset = StandardCharsets.UTF_8.name();
        String newCharset = "windows-1251";
        productsInOrder.setName(new String(productsInOrder.getName().getBytes(oldCharset), newCharset));
        return productsInOrder;
    }

    public static ProductsInOrder getProductsInOrderFromCP1251ToUTF8Charset(ProductsInOrder productsInOrder) throws UnsupportedEncodingException {
        String oldCharset = "windows-1251";
        String newCharset = StandardCharsets.UTF_8.name();
        productsInOrder.setName(new String(productsInOrder.getName().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        return productsInOrder;
    }

    public ProductsInOrder(long id_order, long id_product, long amount_of_product, long price, long price_with_discount) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.amount_of_product = amount_of_product;
        this.price = price;
        this.price_with_discount = price_with_discount;
    }

    public ProductsInOrder(long id_order, long id_product, String name, long price, long price_with_discount, long amount_of_product) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.amount_of_product = amount_of_product;
        this.price = price;
        this.price_with_discount = price_with_discount;
        this.name = name;
    }

    public ProductsInOrder(long id_order, long id_product, long amount_of_product, long price) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.amount_of_product = amount_of_product;
        this.price = price;
        this.price_with_discount = price;
    }

    public ProductsInOrder(long id_order, long id_product, long amount_of_product, long price, String name) {
        this.id_order = id_order;
        this.id_product = id_product;
        this.amount_of_product = amount_of_product;
        this.price = price;
        this.price_with_discount = price;
        this.name = name;
    }

    public long getId_order() {
        return id_order;
    }

    public void setId_order(long id_order) {
        this.id_order = id_order;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice_with_discount() {
        return price_with_discount;
    }

    public void setPrice_with_discount(long price_with_discount) {
        this.price_with_discount = price_with_discount;
    }

    public boolean isDiscount(){
        return (this.price_with_discount >= 99 && this.price_with_discount < this.price);
    }

    public long getFactualPrice(){
        return this.isDiscount() ? this.getPrice_with_discount() : this.getPrice();
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id_order\": \"" + id_order + "\",\n" +
                "\"id_product\": \"" + id_product + "\",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"price\": \"" + price + "\",\n" +
                "\"price_with_discount\": \"" + price_with_discount + "\",\n" +
                "\"amount_of_product\": \"" + amount_of_product + "\"\n" +
                "}";
    }
}
