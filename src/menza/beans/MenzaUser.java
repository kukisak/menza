package menza.beans;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import menza.enums.MenzaUserType;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MenzaUser {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;

    @Persistent
    private String email;

    @Persistent
    private MenzaUserType type;					//user type - from enum MANAGER, BOARDER, ROBOT, CHEF, ISSUER;

    @Persistent
    private Date date;                          //date - date, when the user was created/registred

    public MenzaUser(String email, MenzaUserType type, Date date) {
        this.email = email;
        this.type = type;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.toLowerCase();
    }

    public MenzaUserType getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }

    public void setType(MenzaUserType type) {
        this.type = type;
    }    

    public void setDate(Date date) {
        this.date = date;
    }
}
