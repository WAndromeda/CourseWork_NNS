package Entity;

import javax.persistence.*;

@Entity
@Table(name = "confirmation_key")
public class ConfirmationKey {

    public static final int ConfirmationKeyLength = 30;
    public enum IS_EMAIL_SENT{нет, да}

    @Id
    @Column(name = "id_client")
    private long id_client;
    @Column(name="confirmation_key")
    private String confirmation_key;
    @Column(name="is_email_sent")
    @Enumerated(EnumType.STRING)
    private IS_EMAIL_SENT is_email_sent = IS_EMAIL_SENT.нет;
    @Column(name="amount_of_checks")
    private short amount_of_checks = 0;

    public ConfirmationKey() {
    }

    public ConfirmationKey(long id_client) {
        this.id_client = id_client;
    }

    public ConfirmationKey(long id_client, String confirmation_key) {
        this.id_client = id_client;
        this.confirmation_key = confirmation_key;
    }

    public ConfirmationKey(long id_client, String confirmation_key, IS_EMAIL_SENT is_email_sent) {
        this.id_client = id_client;
        this.confirmation_key = confirmation_key;
        this.is_email_sent = is_email_sent;
    }

    public ConfirmationKey(long id_client, String confirmation_key, short amount_of_checks) {
        this.id_client = id_client;
        this.confirmation_key = confirmation_key;
        this.amount_of_checks = amount_of_checks;
    }

    public ConfirmationKey(long id_client, String confirmation_key, IS_EMAIL_SENT is_email_sent, short amount_of_checks) {
        this.id_client = id_client;
        this.confirmation_key = confirmation_key;
        this.is_email_sent = is_email_sent;
        this.amount_of_checks = amount_of_checks;
    }

    public long getId_client() {
        return id_client;
    }

    public void setId_client(long id_client) {
        this.id_client = id_client;
    }

    public String getConfirmation_key() {
        return confirmation_key;
    }

    public void setConfirmation_key(String confirmation_key) {
        this.confirmation_key = confirmation_key;
    }

    public IS_EMAIL_SENT getIs_email_sent() {
        return is_email_sent;
    }

    public void setIs_email_sent(IS_EMAIL_SENT is_email_sent) {
        this.is_email_sent = is_email_sent;
    }

    public short getAmount_of_checks() {
        return amount_of_checks;
    }

    public void setAmount_of_checks(short amount_of_checks) {
        this.amount_of_checks = amount_of_checks;
    }

    public void increaseAmount_of_checks(){
        amount_of_checks++;
    }

    public void decreaseAmount_of_checks(){
        if (amount_of_checks == 0)
            return;
        amount_of_checks--;
    }
}
