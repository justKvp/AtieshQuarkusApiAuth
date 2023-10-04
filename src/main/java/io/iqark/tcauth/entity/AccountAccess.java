package io.iqark.tcauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(
        name = "account_access"
)
public class AccountAccess extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID", columnDefinition = "int(10) unsigned", length = 32)
    private Integer accountID;

    @OneToOne(
            cascade = {CascadeType.DETACH}
    )
    @JoinColumn(
            name = "AccountID"
    )
    @JsonIgnore
    public Account account;

    @Column(name = "SecurityLevel", columnDefinition = "tinyint(3) unsigned", length = 3)
    private Integer securityLevel;

    @Column(name = "RealmID", columnDefinition = "int(11)", length = 11)
    private Integer realmID;

    @Column(name = "Comment", columnDefinition = "varchar(255)", length = 255)
    private String comment;

    public Integer getAccountID() {
        return accountID;
    }

    public void setAccountID(Integer accountID) {
        this.accountID = accountID;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(Integer securityLevel) {
        this.securityLevel = securityLevel;
    }

    public Integer getRealmID() {
        return realmID;
    }

    public void setRealmID(Integer realmID) {
        this.realmID = realmID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
