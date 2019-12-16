package Entity;

import Interface.Encoding;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import javax.persistence.*;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

@Entity
@Table(name = "client")
public class Client implements Encoding {

    public enum SEX{мужской, женский, не_определён}

    public enum CONFIRMED{не_подтверждён, подтверждён}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id = 0;
    @Column (name = "surname")
    protected String surname;
    @Column (name = "name")
    protected  String name;
    @Column (name = "patronymic")
    protected  String patronymic;
    @Column(name = "email")
    protected  String email;
    @Column(name = "phone")
    protected  String phone;
    @Column(name = "spare_phone")
    protected  String spare_phone;
    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    protected  SEX sex;
    @Column(name = "date_of_birth")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    protected LocalDate date_of_birth;
    @Column(name = "datetime_of_registration")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected  DateTime datetime_of_registration;
    @Column(name = "password")
    protected  String password;
    @Column(name = "confirmed")
    @Enumerated(EnumType.STRING)
    protected  CONFIRMED confirmed = CONFIRMED.не_подтверждён;


    public Client() {
        surname = "";
        name = "";
        patronymic = "";
    }

    public Client(long id) {
        this.id = id;
    }

    public static Client getClientInOtherCharset(Client client, String oldCharset, String newCharset) throws UnsupportedEncodingException {
        client.setName(new String(client.getName().getBytes(oldCharset), newCharset));
        client.setSurname(new String(client.getSurname().getBytes(oldCharset), newCharset));
        if (client.getPatronymic() != null) client.setPatronymic(new String(client.getPatronymic().getBytes(oldCharset), newCharset));
        return client;
    }

    public static Client getClientFromUTF8ToCP1251Charset(Client client) throws UnsupportedEncodingException {
        String oldCharset = StandardCharsets.UTF_8.name();
        String newCharset = "windows-1251";
        client.setName(new String(client.getName().getBytes(oldCharset), newCharset));
        client.setSurname(new String(client.getSurname().getBytes(oldCharset), newCharset));
        if (client.getPatronymic() != null) client.setPatronymic(new String(client.getPatronymic().getBytes(oldCharset), newCharset));
        return client;
    }

    public static Client getClientFromCP1251ToUTF8Charset(Client client) throws UnsupportedEncodingException {
        String oldCharset = "windows-1251";
        String newCharset = StandardCharsets.UTF_8.name();
        client.setName(new String(client.getName().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        client.setSurname(new String(client.getSurname().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        if (client.getPatronymic() != null) client.setPatronymic(new String(client.getPatronymic().getBytes(oldCharset), newCharset).replaceAll("�\\?", character));
        return client;
    }

    public Client(long id, String surname, String name, String email, String phone, SEX sex, LocalDate date_of_birth, DateTime datetime_of_registration) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.datetime_of_registration = datetime_of_registration;
    }

    public Client(long id, String surname, String name, String patronymic, String email, String phone,
                  String spare_phone, SEX sex, LocalDate date_of_birth, DateTime datetime_of_registration, String password, CONFIRMED confirmed) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.spare_phone = spare_phone;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.datetime_of_registration = datetime_of_registration;
        this.password = password;
        this.confirmed = confirmed;
    }

    public Client(int id, String surname, String name, String patronymic, String email,
                  String phone, String spare_phone, SEX sex, LocalDate date_of_birth, DateTime datetime_of_registration, String password) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.spare_phone = spare_phone;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.datetime_of_registration = datetime_of_registration;
        this.password = password;
    }

    public Client(String surname, String name, String email, String phone, SEX sex, LocalDate date_of_birth, DateTime datetime_of_registration) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.datetime_of_registration = datetime_of_registration;
    }

    public Client(String surname, String name, String patronymic, String email,
                  String phone, String spare_phone, SEX sex, LocalDate date_of_birth, DateTime datetime_of_registration, String password) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.email = email;
        this.phone = phone;
        this.spare_phone = spare_phone;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.datetime_of_registration = datetime_of_registration;
        this.password = password;
    }

    public Client(String surname, String name, String email,
                  String phone, SEX sex, LocalDate date_of_birth, DateTime datetime_of_registration, String password) {
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.datetime_of_registration = datetime_of_registration;
        this.password = password;
    }

    public Client(int id, String surname, String name, String email,
                  String phone, SEX sex, LocalDate date_of_birth, DateTime datetime_of_registration, String password) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.sex = sex;
        this.date_of_birth = date_of_birth;
        this.datetime_of_registration = datetime_of_registration;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpare_phone() {
        return spare_phone;
    }

    public void setSpare_phone(String spare_phone) {
        this.spare_phone = spare_phone;
    }

    public SEX getSex() {
        return sex;
    }

    public void setSex(SEX sex) {
        this.sex = sex;
    }

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public DateTime getDatetime_of_registration() {
        return datetime_of_registration;
    }

    public void setDatetime_of_registration(DateTime datetime_of_registration) {
        this.datetime_of_registration = datetime_of_registration;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CONFIRMED getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(CONFIRMED confirmed) {
        this.confirmed = confirmed;
    }

    public String getFIO(){
        return (this.surname.trim() + " " + this.name.trim() + (this.patronymic == null || this.patronymic.trim().equals("") ? "" : " " + this.patronymic.trim()));
    }

    public String getPhoneWithout7(){
        return this.phone.trim().substring(1);
    }

    @Override
    public String toString() {
        return "{\n" +
                "\"id\": \"" + id + "\",\n" +
                "\"surname\": \"" + surname +"\",\n" +
                "\"name\": \"" + name + "\",\n" +
                "\"patronymic\": \"" + patronymic + "\",\n" +
                "\"sex\": \"" + sex + "\",\n" +
                "\"date_of_birth\": \"" + date_of_birth.getDayOfMonth() + "." + date_of_birth.getMonthOfYear() + "." +  date_of_birth.getYear() + "\",\n" +
                "\"email\": \"" + email + "\",\n" +
                "\"phone\": \"" + phone + "\",\n" +
                "\"spare_phone\": \"" + spare_phone + "\",\n" +
                "\"confirmed\": \"" + confirmed + "\"\n" +
        "}";
    }
}
