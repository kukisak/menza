package menza.beans;

import java.util.Date;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Account {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @ForeignKey
    @Persistent
    private Long idBoarder;
    
    @Persistent
    private String accountName;

    @Persistent
    private Integer accountBalance;

    @Persistent
    private String bankAccount;

    @Persistent
    private Date dateOfCreate;

    /*
    @Persistent
    private Date dateOfClose;
    */

    public Account(Long idBoarder, String accountName, Integer accountBalance, String bankAccount, Date dateOfCreate) {
        this.idBoarder = idBoarder;
        this.accountName = accountName;
        this.accountBalance = accountBalance;
        this.bankAccount = bankAccount;
        this.dateOfCreate = dateOfCreate;
    }

    public Long getId() {
        return id;
    }

    public Long getIdBoarder() {
        return idBoarder;
    }
    
    public String getAccountName() {
        return accountName;
    }

    public Integer getAccountBalance() {
        return accountBalance;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public Date getDateOfCreate() {
        return dateOfCreate;
    }

    public void setIdBoarder(Long idBoarder) {
        this.idBoarder = idBoarder;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

   public void setAccountBalance(Integer accountBalance) {
        this.accountBalance = accountBalance;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setDateOfCreate(Date dateOfCreate) {
        this.dateOfCreate = dateOfCreate;
    }
}
