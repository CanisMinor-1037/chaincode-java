package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class DBAssetOrder {
    @Property(schema = {"pattern", "^DBAssetOrder\\d{1,20}$"})
    private String id; // 数据资产订单id

    @Property(schema = {"pattern", "^DBAsset\\d{1,20}$"})
    private String dbAssetId; // 数据资产id

    @Property(schema = {"pattern", "^Department\\d{1,20}$"})
    private String applicantId; // 申请平台id

    @Property()
    private String sql; // 当前状态

    // Getter
    public String getId() {
        return id;
    }

    public String getDbAssetId() {
        return dbAssetId;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public String getSql() {
        return sql;
    }


    // Constructor
    public DBAssetOrder(@JsonProperty("id") final String id,
                        @JsonProperty("dbAssetId") final String dbAssetId,
                        @JsonProperty("applicantId") final String applicantId,
                        @JsonProperty("sql") final String sql) {
        this.id = id;
        this.dbAssetId = dbAssetId;
        this.applicantId = applicantId;
        this.sql = sql;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DBAssetOrder that = (DBAssetOrder) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDbAssetId(), that.getDbAssetId()) && Objects.equals(getApplicantId(), that.getApplicantId()) && Objects.equals(getSql(), that.getSql());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDbAssetId(), getApplicantId(), getSql());
    }

    @Override
    public String toString() {
        return "DBAssetOrder{" +
                "id='" + id + '\'' +
                ", dbAssetId='" + dbAssetId + '\'' +
                ", applicantId='" + applicantId + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
