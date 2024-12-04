package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class DBAsset {
    @Property(schema = {"pattern", "^DBAsset\\d{1,20}$"})
    private String id; // 数据库资产id

    @Property()
    private String name; // 数据库资产名

    @Property(schema = {"pattern", "^User\\d{1,20}$"})
    private String ownerId; // 所属用户id

    @Property()
    private String policy; // 访问策略

    @Property()
    private String location; // 地区

    @Property()
    private String field; // 应用领域

    @Property()
    private String jdbcUrl; // jdbcUrl密文

    @Property()
    private String username; // 用户名密文

    @Property()
    private String password; // 密码密文

    @Property()
    private String aesKey; // AES密钥

    @Property()
    private int encType; // 加密模式

    public DBAsset(@JsonProperty("id") final String id,
                   @JsonProperty("name") final String name,
                   @JsonProperty("ownerId") final String ownerId,
                   @JsonProperty("policy") final String policy,
                   @JsonProperty("location") final String location,
                   @JsonProperty("field") final String field,
                   @JsonProperty("jdbcUrl") final String jdbcUrl,
                   @JsonProperty("username") final String username,
                   @JsonProperty("password") final String password,
                   @JsonProperty("aesKey") final String aesKey,
                   @JsonProperty("encType") final int encType) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.policy = policy;
        this.location = location;
        this.field = field;
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
        this.aesKey = aesKey;
        this.encType = encType;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getPolicy() {
        return policy;
    }

    public String getLocation() {
        return location;
    }

    public String getField() {
        return field;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public String getAesKey() {
        return aesKey;
    }

    public int getEncType() {
        return encType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBAsset dbAsset = (DBAsset) o;
        return getEncType() == dbAsset.getEncType() && Objects.equals(getId(), dbAsset.getId()) && Objects.equals(getName(), dbAsset.getName()) && Objects.equals(getOwnerId(), dbAsset.getOwnerId()) && Objects.equals(getPolicy(), dbAsset.getPolicy()) && Objects.equals(getLocation(), dbAsset.getLocation()) && Objects.equals(getField(), dbAsset.getField()) && Objects.equals(getJdbcUrl(), dbAsset.getJdbcUrl()) && Objects.equals(getUsername(), dbAsset.getUsername()) && Objects.equals(getPassword(), dbAsset.getPassword()) && Objects.equals(getAesKey(), dbAsset.getAesKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getOwnerId(), getPolicy(), getLocation(), getField(), getJdbcUrl(), getUsername(), getPassword(), getAesKey(), getEncType());
    }

    @Override
    public String toString() {
        return "DBAsset{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", policy='" + policy + '\'' +
                ", location='" + location + '\'' +
                ", field='" + field + '\'' +
                ", jdbcUrl='" + jdbcUrl + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", aesKey='" + aesKey + '\'' +
                ", encType=" + encType +
                '}';
    }


}
