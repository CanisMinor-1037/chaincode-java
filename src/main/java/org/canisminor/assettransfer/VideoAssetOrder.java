package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class VideoAssetOrder {
    @Property(schema = {"pattern", "^VideoAssetOrder\\d{1,20}$"})
    private String id; // 数据资产订单id

    @Property(schema = {"pattern", "^VideoAsset\\d{1,20}$"})
    private String videoAssetId; // 数据资产id

    @Property(schema = {"pattern", "^Department\\d{1,20}$"})
    private String applicantId; // 申请平台id

    @Property(schema = {"minimum", "0", "maximum", "20"})
    private int status; // 当前状态

    // Getter
    public String getId() {
        return id;
    }

    public String getVideoAssetId() {
        return videoAssetId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public int getStatus() {
        return status;
    }

    // Setter
    public void setStatus(final int status) {
        this.status = status;
    }

    // Constructor
    public VideoAssetOrder(@JsonProperty("id") final String id,
                          @JsonProperty("videoAssetId") final String videoAssetId,
                          @JsonProperty("applicantId") final String applicantId,
                          @JsonProperty("status") final int status) {
        this.id = id;
        this.videoAssetId = videoAssetId;
        this.applicantId = applicantId;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoAssetOrder that = (VideoAssetOrder) o;
        return getStatus() == that.getStatus() && Objects.equals(getId(), that.getId()) && Objects.equals(getVideoAssetId(), that.getVideoAssetId()) && Objects.equals(getApplicantId(), that.getApplicantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getVideoAssetId(), getApplicantId(), getStatus());
    }

    @Override
    public String toString() {
        return "VideoAssetOrder{" +
                "id='" + id + '\'' +
                ", videoAssetId='" + videoAssetId + '\'' +
                ", applicantId='" + applicantId + '\'' +
                ", status=" + status +
                '}';
    }
}
