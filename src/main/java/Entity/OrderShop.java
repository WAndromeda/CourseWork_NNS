package Entity;

import Interface.Encoding;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "order_shop")
public class OrderShop implements Encoding {

    enum STATUS{выполнен, отменён, доставлен, в_процессе, создан}

    enum IS_PAID{нет, да}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "id_client")
    private long id_client;
    @Column (name = "delivery_price")
    private long delivery_price = 0;
    @Column (name = "address_shop")
    private String address_shop;
    @Column(name = "id_payment_method")
    private int id_payment_method;
    @Column(name = "recipient_name")
    private String recipient_name;
    @Column(name = "recipient_email")
    private String recipient_email;
    @Column(name = "recipient_phone")
    private String recipient_phone;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private STATUS status = STATUS.создан;
    @Column(name = "date_of_ready")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    private LocalDate date_of_ready;
    @Column(name = "datetime_of_creation")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime datetime_of_creation;
    @Column(name = "is_paid")
    @Enumerated(EnumType.STRING)
    private IS_PAID is_paid = IS_PAID.нет;

    public OrderShop() {
    }

    public static OrderShop getOrderShopInOtherCharset(OrderShop orderShop, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        orderShop.setRecipient_name(new String(orderShop.getRecipient_name().getBytes(oldCharset), newCharset));
        orderShop.setAddress_shop(new String(orderShop.getAddress_shop().getBytes(oldCharset), newCharset));
        return orderShop;
    }

    public static OrderShop getOrderShopFromUTF8ToCP1251Charset(OrderShop orderShop) throws UnsupportedEncodingException {
        String oldCharset = StandardCharsets.UTF_8.name();
        String newCharset = "windows-1251";
        orderShop.setRecipient_name(new String(orderShop.getRecipient_name().getBytes(oldCharset), newCharset));
        orderShop.setAddress_shop(new String(orderShop.getAddress_shop().getBytes(oldCharset), newCharset));
        return orderShop;
    }

    public static OrderShop getOrderShopFromCP1251ToUTF8Charset(OrderShop orderShop) throws UnsupportedEncodingException {
        String oldCharset = "windows-1251";
        String newCharset = StandardCharsets.UTF_8.name();
        orderShop.setRecipient_name(new String(orderShop.getRecipient_name().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        orderShop.setAddress_shop(new String(orderShop.getAddress_shop().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        return orderShop;
    }

    public OrderShop(long id, long id_client, long delivery_price, String address_shop, int id_payment_method, String recipient_name,
                     String recipient_email, String recipient_phone, STATUS status, LocalDate date_of_ready, DateTime datetime_of_creation, IS_PAID is_paid) {
        this.id = id;
        this.id_client = id_client;
        this.delivery_price = delivery_price;
        this.address_shop = address_shop;
        this.id_payment_method = id_payment_method;
        this.recipient_name = recipient_name;
        this.recipient_email = recipient_email;
        this.recipient_phone = recipient_phone;
        this.status = status;
        this.date_of_ready = date_of_ready;
        this.datetime_of_creation = datetime_of_creation;
        this.is_paid = is_paid;
    }

    public OrderShop(long id_client, int id_payment_method, String recipient_name, String recipient_email, String recipient_phone, String address_shop, LocalDate date_of_ready, DateTime datetime_of_creation) {
        this.id_client = id_client;
        this.address_shop = address_shop;
        this.id_payment_method = id_payment_method;
        this.recipient_name = recipient_name;
        this.recipient_email = recipient_email;
        this.recipient_phone = recipient_phone;
        this.date_of_ready = date_of_ready;
        this.datetime_of_creation = datetime_of_creation;
        this.delivery_price = 0;
        this.status = STATUS.создан;
        this.is_paid = IS_PAID.нет;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public long getDelivery_price() {
        return delivery_price;
    }

    public void setDelivery_price(long delivery_price) {
        this.delivery_price = delivery_price;
    }

    public String getAddress_shop() {
        return address_shop;
    }

    public void setAddress_shop(String address_shop) {
        this.address_shop = address_shop;
    }

    public int getId_payment_method() {
        return id_payment_method;
    }

    public void setId_payment_method(int id_payment_method) {
        this.id_payment_method = id_payment_method;
    }

    public String getRecipient_name() {
        return recipient_name;
    }

    public void setRecipient_name(String recipient_name) {
        this.recipient_name = recipient_name;
    }

    public String getRecipient_email() {
        return recipient_email;
    }

    public void setRecipient_email(String recipient_email) {
        this.recipient_email = recipient_email;
    }

    public String getRecipient_phone() {
        return recipient_phone;
    }

    public void setRecipient_phone(String recipient_phone) {
        this.recipient_phone = recipient_phone;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public LocalDate getDate_of_ready() {
        return date_of_ready;
    }

    public void setDate_of_ready(LocalDate date_of_ready) {
        this.date_of_ready = date_of_ready;
    }

    public DateTime getDatetime_of_creation() {
        return datetime_of_creation;
    }

    public void setDatetime_of_creation(DateTime datetime_of_creation) {
        this.datetime_of_creation = datetime_of_creation;
    }

    public IS_PAID getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(IS_PAID is_paid) {
        this.is_paid = is_paid;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": \"" + id + "\",\n" +
                "\"id_client\": \"" + id_client +"\",\n" +
                "\"delivery_price\": \"" + delivery_price + "\",\n" +
                "\"address_shop\": \"" + address_shop + "\",\n" +
                "\"payment_method\": \"" + id_payment_method + "\",\n" +
                "\"date_of_ready\": \"" + date_of_ready.getDayOfMonth() + "." + date_of_ready.getMonthOfYear() + "." +  date_of_ready.getYear() + "\",\n" +
                "\"recipient_name\": \"" + recipient_name + "\",\n" +
                "\"recipient_email\": \"" + recipient_email + "\",\n" +
                "\"recipient_phone\": \"" + recipient_phone + "\",\n" +
                "\"status\": \"" + status + "\",\n" +
                "\"is_paid\": \"" + is_paid + "\"\n" +
                "}";
    }
}
