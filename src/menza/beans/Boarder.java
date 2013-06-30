package menza.beans;



import java.util.Date;

import javax.jdo.annotations.ForeignKey;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Boarder {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @ForeignKey
    @Persistent
    private Long idUser;

    @Persistent
    private String name;

    @Persistent
    private String surname;

    /*Dont need date of Reg, because there is a date of enter of MenzaUser into system
    @Persistent
    private Date dateOfReg;
    *
    */

    @Persistent
    private Date dateOfAbord;

    public Boarder(Long idUser, String name, String surname, Date dateOfAbord) {
        this.idUser = idUser;
        this.name = name;
        this.surname = surname;
        //this.dateOfReg = dateOfReg;
        this.dateOfAbord = dateOfAbord;
    }

    public Long getId() {
        return id;
    }

    public Long getIdUser() {
        return idUser;
    }
    
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    /*public Date getDateOfReg() {
        return dateOfReg;
    }*/

    public Date getDateOfAbord() {
        return dateOfAbord;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    
    /*public void setDateOfReg(Date dateOfReg) {
        this.dateOfReg = dateOfReg;
    }*/

    public void setDateOfAbord(Date dateOfAbord) {
        this.dateOfAbord = dateOfAbord;
    }
}
