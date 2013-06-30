package menza.beans;

import java.util.Date;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.ForeignKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class AccountHistory {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @ForeignKey
    @Persistent
    private Long idAccount;

    @Persistent
    private Integer mount;

    @Persistent
    private Date date;

    @Persistent
    private Boolean type;                           //type of operation - true - increase (top uping), false - decrease (paying for meal)

    public AccountHistory(Long idAccount, Integer mount, Date date, Boolean type) {
        this.idAccount = idAccount;
        this.mount = mount;
        this.date = date;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public Long getIdAccount() {
        return idAccount;
    }

    public Integer getMount() {
        return mount;
    }    

    public Boolean getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public void setIdAccount(Long idAccount) {
        this.idAccount = idAccount;
    }

    public void setMount(Integer mount) {
        this.mount = mount;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
