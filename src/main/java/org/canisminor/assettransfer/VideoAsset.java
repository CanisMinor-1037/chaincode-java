package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;


@DataType()
public class VideoAsset {
    @Property(schema = {"pattern", "^VideoAsset\\d{1,20}$"})
    private String id; // 摄像头资产id

    @Property()
    private String name; // 摄像头资产名

    @Property(schema = {"pattern", "^Department\\d{1,20}$"})
    private String ownerId; // 所属平台id

    @Property()
    private String policy; // 访问策略

    @Property()
    private String location; // 地区

    @Property()
    private String field; // 应用领域

    @Property()
    private String rtspUrl; // rtspUrl密文

    @Property()
    private String aesKey; // AES密钥

    @Property()
    private int encType; // 加密模式

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

    public String getRtspUrl() {
        return rtspUrl;
    }

    public String getAesKey() {
        return aesKey;
    }

    public int getEncType() {
        return encType;
    }

    // Constructor
    public VideoAsset(@JsonProperty("id") final String id,
                     @JsonProperty("name") final String name,
                     @JsonProperty("ownerId") final String ownerId,
                     @JsonProperty("policy") final String policy,
                     @JsonProperty("location") final String location,
                     @JsonProperty("field") final String field,
                     @JsonProperty("rtspUrl") final String rtspUrl,
                     @JsonProperty("aesKey") final String aesKey,
                     @JsonProperty("encType") final int encType) {
        this.id = id;
        this.name = name;
        this.ownerId = ownerId;
        this.policy = policy;
        this.location = location;
        this.field = field;
        this.rtspUrl = rtspUrl;
        this.aesKey = aesKey;
        this.encType = encType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoAsset that = (VideoAsset) o;
        return getEncType() == that.getEncType() && Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName()) && Objects.equals(getOwnerId(), that.getOwnerId()) && Objects.equals(getPolicy(), that.getPolicy()) && Objects.equals(getLocation(), that.getLocation()) && Objects.equals(getField(), that.getField()) && Objects.equals(getRtspUrl(), that.getRtspUrl()) && Objects.equals(getAesKey(), that.getAesKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getOwnerId(), getPolicy(), getLocation(), getField(), getRtspUrl(), getAesKey(), getEncType());
    }

    @Override
    public String toString() {
        return "VideoAsset{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", policy='" + policy + '\'' +
                ", location='" + location + '\'' +
                ", field='" + field + '\'' +
                ", rtspUrl='" + rtspUrl + '\'' +
                ", aesKey='" + aesKey + '\'' +
                ", encType=" + encType +
                '}';
    }
}
