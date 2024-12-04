package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class DataAssetOrder {
    @Property(schema = {"pattern", "^DataAssetOrder\\d{1,20}$"})
    private String id; // 数据资产订单id

    @Property(schema = {"pattern", "^DataAsset\\d{1,20}$"})
    private String dataAssetId; // 数据资产id

    @Property(schema = {"pattern", "^User\\d{1,20}$"})
    private String applicantId; // 申请用户id

    @Property(schema = {"minimum", "0", "maximum", "20"})
    private int status; // 当前状态

    // Getter
    public String getId() {
        return id;
    }

    public String getDataAssetId() {
        return dataAssetId;
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
    public DataAssetOrder(@JsonProperty("id") final String id,
                          @JsonProperty("dataAssetId") final String dataAssetId,
                          @JsonProperty("applicantId") final String applicantId,
                          @JsonProperty("status") final int status) {
        this.id = id;
        this.dataAssetId = dataAssetId;
        this.applicantId = applicantId;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataAssetOrder that = (DataAssetOrder) o;
        return getStatus() == that.getStatus() && Objects.equals(getId(), that.getId()) && Objects.equals(getDataAssetId(), that.getDataAssetId()) && Objects.equals(getApplicantId(), that.getApplicantId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDataAssetId(), getApplicantId(), getStatus());
    }

    @Override
    public String toString() {
        return "DataAssetOrder{" +
                "id='" + id + '\'' +
                ", dataAssetId='" + dataAssetId + '\'' +
                ", applicantId='" + applicantId + '\'' +
                ", status=" + status +
                '}';
    }
}
