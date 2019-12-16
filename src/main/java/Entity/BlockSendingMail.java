package Entity;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "block_sending_mail")
public class BlockSendingMail {
    @Id
    @Column(name = "id")
    protected int id;
    @Column(name = "datetime_block_until_date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    protected DateTime datetime_block_until_date;

    public BlockSendingMail() {
    }

    public BlockSendingMail(int id, DateTime datetime_block_until_date) {
        this.id = id;
        this.datetime_block_until_date = datetime_block_until_date;
    }

    public BlockSendingMail(DateTime datetime_block_until_date) {
        this.datetime_block_until_date = datetime_block_until_date;
    }

    public DateTime getDatetime_block_until_date() {
        return datetime_block_until_date;
    }

    public void setDatetime_block_until_date(DateTime datetime_block_until_date) {
        this.datetime_block_until_date = datetime_block_until_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
