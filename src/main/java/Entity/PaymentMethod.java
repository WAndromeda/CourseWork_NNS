package Entity;

import Interface.Encoding;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "payment_method")
public class PaymentMethod implements Encoding {
    @Id
    protected long id;
    @Column (name = "name")
    protected String name;
    @Column (name = "description")
    protected String description;

    public PaymentMethod() {
    }

    public static PaymentMethod getPaymentMethodInOtherCharset(PaymentMethod paymentMethod, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        paymentMethod.setName(new String(paymentMethod.getName().getBytes(oldCharset), newCharset));
        paymentMethod.setDescription(new String(paymentMethod.getDescription().getBytes(oldCharset), newCharset));
        return paymentMethod;
    }

    public static PaymentMethod getPaymentMethodFromUTF8ToCP1251Charset(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        String oldCharset = StandardCharsets.UTF_8.name();
        String newCharset = "windows-1251";
        paymentMethod.setName(new String(paymentMethod.getName().getBytes(oldCharset), newCharset));
        paymentMethod.setDescription(new String(paymentMethod.getDescription().getBytes(oldCharset), newCharset));
        return paymentMethod;
    }

    public static PaymentMethod getPaymentMethodFromCP1251ToUTF8Charset(PaymentMethod paymentMethod) throws UnsupportedEncodingException {
        String oldCharset = "windows-1251";
        String newCharset = StandardCharsets.UTF_8.name();
        paymentMethod.setName(new String(paymentMethod.getName().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        paymentMethod.setDescription(new String(paymentMethod.getDescription().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        return paymentMethod;
    }


    public PaymentMethod(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public PaymentMethod(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": \"" + id + "\",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"description\": \"" + description + "\"\n" +
                "}";
    }
}
