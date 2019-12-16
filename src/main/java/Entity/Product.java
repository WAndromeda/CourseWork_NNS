package Entity;

import Interface.Encoding;
import Interface.ProductInterface;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "product")
public class Product implements Encoding, ProductInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_category")
    private long id_category;
    @Column(name = "amount")
    private long amount;
    @Column (name = "description")
    private String description;
    @Column(name = "price")
    private long price;
    @Column (name = "price_with_discount")
    private long price_with_discount;
    @Column(name = "name")
    private String name;
    @Column(name = "specification")
    private String specification;
    @Column(name = "datetime_of_adding")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime datetime_of_adding;
   //protected boolean isInFavorite = false, isInBasket = false;

    public Product() {
    }



    public static Product getProductInOtherCharset(Product product, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        product.setName(new String(product.getName().getBytes(oldCharset), newCharset));
        product.setDescription(new String(product.getDescription().getBytes(oldCharset), newCharset));
        product.setSpecification(new String(product.getSpecification().getBytes(oldCharset), newCharset));
        return product;
    }

    public static Product getProductFromUTF8ToCP1251Charset(Product product) throws UnsupportedEncodingException {
        String oldCharset = StandardCharsets.UTF_8.name();
        String newCharset = "windows-1251";
        product.setName(new String(product.getName().getBytes(oldCharset), newCharset));
        product.setDescription(new String(product.getDescription().getBytes(oldCharset), newCharset));
        product.setSpecification(new String(product.getSpecification().getBytes(oldCharset), newCharset));
        return product;
    }

    public static Product getProductFromCP1251ToUTF8Charset(Product product) throws UnsupportedEncodingException {
        String oldCharset = "windows-1251";
        String newCharset = StandardCharsets.UTF_8.name();
        product.setName(new String(product.getName().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        product.setDescription(new String(product.getDescription().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        product.setSpecification(new String(product.getSpecification().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        return product;
    }

    public Product(long id_category, long amount, String description, long price, String name, String specification, DateTime datetime_of_adding) {
        this.id_category = id_category;
        this.amount = amount;
        this.description = description;
        this.price = price;
        this.name = name;
        this.specification = specification;
        this.datetime_of_adding = datetime_of_adding;
        price_with_discount = price;
    }

    public Product(long id, long id_category, long amount, String description, long price, long price_with_discount, String name, String specification, DateTime datetime_of_adding) {
        this.id = id;
        this.id_category = id_category;
        this.amount = amount;
        this.description = description;
        this.price = price;
        this.price_with_discount = price_with_discount;
        this.name = name;
        this.specification = specification;
        this.datetime_of_adding = datetime_of_adding;
    }



    public Product(long id, long id_category, String name, String description, long price, long amount,  String specification, DateTime datetime_of_adding) {
        this.id = id;
        this.id_category = id_category;
        this.amount = amount;
        this.description = description;
        this.price = price;
        price_with_discount = price;
        this.name = name;
        this.specification = specification;
        this.datetime_of_adding = datetime_of_adding;
    }

    public Product(long id_category, String name, String description, long price, long amount,  String specification, DateTime datetime_of_adding) {
        this.id = id;
        this.id_category = id_category;
        this.amount = amount;
        this.description = description;
        this.price = price;
        price_with_discount = price;
        this.name = name;
        this.specification = specification;
        this.datetime_of_adding = datetime_of_adding;
    }

    public Product(long id, long id_category, long amount, String description, long price_with_discount, String name, long price, String specification) {
        this.id = id;
        this.id_category = id_category;
        this.amount = amount;
        this.description = description;
        this.price_with_discount = price_with_discount;
        this.name = name;
        this.price = price;
        this.specification = specification;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_category() {
        return id_category;
    }

    public void setId_category(long id_category) {
        this.id_category = id_category;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPrice_with_discount() {
        return price_with_discount;
    }

    public void setPrice_with_discount(long price_with_discount) {
        this.price_with_discount = price_with_discount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public DateTime getDatetime_of_adding() {
        return datetime_of_adding;
    }

    public void setDatetime_of_adding(DateTime datetime_of_adding) {
        this.datetime_of_adding = datetime_of_adding;
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
                "\"id\": \"" + id + "\",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"id_category\": \"" + id_category + "\",\n" +
                "\"amount\": \"" + amount + "\",\n" +
                "\"description\": \"" + description + "\",\n" +
                "\"price\": \"" + price + "\",\n" +
                "\"price_with_discount\": \"" + price_with_discount + "\",\n" +
                "\"specification\": " + specification + "\n" +
                "}";
    }
}
