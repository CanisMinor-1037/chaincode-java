package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class DataAsset {
    @Property(schema = {"pattern", "^DataAsset\\d{1,20}$"})
    private String id; // 数据资产id

    @Property()
    private String name; // 数据资产名

    @Property(schema = {"pattern", "^Department\\d{1,20}$"})
    private String ownerId; // 所属平台id

    @Property()
    private String policy; // 访问策略

    @Property()
    private String location; // 地区

    @Property()
    private String field; // 应用领域

    @Property()
    private String cid; // cid密文

    @Property()
    private String aesKey; // AES密钥

    @Property()
    private int encType; // 加密模式

    @Property()
    private String videoPlace; //

    @Property()
    private String videoTime; //

    // Getter
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

    public String getCid() {
        return cid;
    }

    public String getAesKey() {
        return aesKey;
    }

    public int getEncType() {
        return encType;
    }

    public String getVideoPlace() {
        return videoPlace;
    }

    public String getVideoTime() {
        return videoTime;
    }

    // Constructor
    public DataAsset(@JsonProperty("id") final String id,
                     @JsonProperty("name") final String name,
                     @JsonProperty("ownerId") final String ownerId,
                     @JsonProperty("policy") final String policy,
                     @JsonProperty("location") final String location,
                     @JsonProperty("field") final String field,
                     @JsonProperty("cid") final String cid,
                     @JsonProperty("aesKey") final String aesKey,
                     @JsonProperty("encType") final int encType,
                     @JsonProperty("videoPlace") final String videoPlace,
                     @JsonProperty("videoTime") final String videoTime) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.policy = policy;
        this.location = location;
        this.field = field;
        this.cid = cid;
        this.aesKey = aesKey;
        this.encType = encType;
        this.videoPlace = videoPlace;
        this.videoTime = videoTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataAsset dataAsset = (DataAsset) o;
        return  Objects.equals(getId(), dataAsset.getId()) &&
                Objects.equals(getName(), dataAsset.getName()) &&
                Objects.equals(getOwnerId(), dataAsset.getOwnerId()) &&
                Objects.equals(getPolicy(), dataAsset.getPolicy()) &&
                Objects.equals(getLocation(), dataAsset.getLocation()) &&
                Objects.equals(getField(), dataAsset.getField()) &&
                Objects.equals(getCid(), dataAsset.getCid()) &&
                Objects.equals(getAesKey(), dataAsset.getAesKey()) &&
                Objects.equals(getEncType(), dataAsset.getEncType()) &&
                Objects.equals(getVideoPlace(), dataAsset.getVideoPlace()) &&
                Objects.equals(getVideoTime(), dataAsset.getVideoTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getOwnerId(), getPolicy(), getLocation(), getField(), getCid(), getAesKey(), getEncType(), getVideoPlace(), getVideoTime());
    }

    @Override
    public String toString() {
        return "DataAsset{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", policy='" + policy + '\'' +
                ", location='" + location + '\'' +
                ", field='" + field + '\'' +
                ", cid='" + cid + '\'' +
                ", aesKey='" + aesKey + '\'' +
                ", encType='" + encType + '\'' +
                ", videoPlace='" + videoPlace + '\'' +
                ", videoTime=" + videoTime +
                '}';
    }
}
