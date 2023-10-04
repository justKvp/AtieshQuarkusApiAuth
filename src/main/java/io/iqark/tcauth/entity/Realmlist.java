package io.iqark.tcauth.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

@Entity
@Table(
        name = "realmlist"
)
public class Realmlist extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "int(10) unsigned", length = 32)
    private Integer id;

    @Column(name = "name", columnDefinition = "varchar(32)", length = 32)
    private String name;

    @Column(name = "address", columnDefinition = "varchar(255)", length = 255)
    private String address;

    @Column(name = "localAddress", columnDefinition = "varchar(255)", length = 255)
    private String localAddress;

    @Column(name = "localSubnetMask", columnDefinition = "varchar(255)", length = 255)
    private String localSubnetMask;

    @Column(name = "port", columnDefinition = "smallint(5) unsigned", length = 5)
    private Integer port;

    @Column(name = "icon", columnDefinition = "tinyint(3) unsigned", length = 3)
    private Integer icon;

    @Column(name = "flag", columnDefinition = "tinyint(3) unsigned", length = 3)
    private Integer flag;

    @Column(name = "timezone", columnDefinition = "tinyint(3) unsigned", length = 3)
    private Integer timezone;

    @Column(name = "allowedSecurityLevel", columnDefinition = "tinyint(3) unsigned", length = 3)
    private Integer allowedSecurityLevel;

    @Column(name = "population", columnDefinition = "float unsigned")
    private Float population;

    @Column(name = "gamebuild", columnDefinition = "int(10) unsigned", length = 10)
    private Integer gamebuild;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public String getLocalSubnetMask() {
        return localSubnetMask;
    }

    public void setLocalSubnetMask(String localSubnetMask) {
        this.localSubnetMask = localSubnetMask;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getIcon() {
        return icon;
    }

    public void setIcon(Integer icon) {
        this.icon = icon;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public Integer getAllowedSecurityLevel() {
        return allowedSecurityLevel;
    }

    public void setAllowedSecurityLevel(Integer allowedSecurityLevel) {
        this.allowedSecurityLevel = allowedSecurityLevel;
    }

    public Float getPopulation() {
        return population;
    }

    public void setPopulation(Float population) {
        this.population = population;
    }

    public Integer getGamebuild() {
        return gamebuild;
    }

    public void setGamebuild(Integer gamebuild) {
        this.gamebuild = gamebuild;
    }

    @Override
    public String toString() {
        return "Realmlist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", localAddress='" + localAddress + '\'' +
                ", localSubnetMask='" + localSubnetMask + '\'' +
                ", port=" + port +
                ", icon=" + icon +
                ", flag=" + flag +
                ", timezone=" + timezone +
                ", allowedSecurityLevel=" + allowedSecurityLevel +
                ", population=" + population +
                ", gamebuild=" + gamebuild +
                '}';
    }
}
