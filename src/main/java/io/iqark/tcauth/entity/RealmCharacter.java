package io.iqark.tcauth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(
        name = "realmcharacters"
)
public class RealmCharacter extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(10) unsigned", length = 32)
    private Integer id;

    @Column(name = "realmid", columnDefinition = "int(10) unsigned", length = 10)
    private Integer realmid;

    @Column(name = "numchars", columnDefinition = "tinyint(3) unsigned", length = 3)
    private Integer numchars;

    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "acctid"
    )
    @JsonIgnore
    public Account account;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRealmid() {
        return realmid;
    }

    public void setRealmid(Integer realmid) {
        this.realmid = realmid;
    }

    public Integer getNumchars() {
        return numchars;
    }

    public void setNumchars(Integer numchars) {
        this.numchars = numchars;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
