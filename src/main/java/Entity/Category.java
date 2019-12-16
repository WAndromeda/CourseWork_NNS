package Entity;

import Interface.Encoding;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "category")
public class Category implements Encoding {
    @Id
    private long id;
    @Column (name = "name")
    private String name;

    public Category() {
    }

    public Category(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(long id) {
        this.id = id;
    }

    public Category(String name) {
        this.name = name;
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

    public static Category getCategoryInOtherCharset(Category category, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        category.setName(new String(category.getName().getBytes(oldCharset), newCharset));
        return category;
    }

    public static Category getCategoryFromUTF8ToCP1251Charset(Category category) throws UnsupportedEncodingException {
        String oldCharset = StandardCharsets.UTF_8.name();
        String newCharset = "windows-1251";
        category.setName(new String(category.getName().getBytes(oldCharset), newCharset));
        return category;
    }

    public static Category getCategoryFromCP1251ToUTF8Charset(Category category) throws UnsupportedEncodingException {
        String oldCharset = "windows-1251";
        String newCharset = StandardCharsets.UTF_8.name();
        category.setName(new String(category.getName().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        return category;
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": \"" + id + "\",\n" +
                "\"name\": \"" + name + "\"\n" +
                "}";
    }
}
