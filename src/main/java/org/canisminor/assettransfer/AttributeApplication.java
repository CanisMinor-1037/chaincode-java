package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class AttributeApplication {
    @Property(schema = {"pattern", "^AttributeApplication\\d{1,20}$"})
    private String id; // 属性申请id

    @Property(schema = {"pattern", "^Department\\d{1,20}$"})
    private String departmentId; // 机构id

    @Property(schema = {"pattern", "^(\\w+:\\w+)( \\w+:\\w+)*$"})
    private String attribute; // 申请属性

    @Property(schema = {"minimum", "0", "maximum", "20"})
    private int status; // 状态

    // Getter
    public String getId() {
        return id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getAttribute() {
        return attribute;
    }

    public int getStatus() {
        return status;
    }

    // Setter
    public void setStatus(final int status) {
        this.status = status;
    }

    // Constructor
    public AttributeApplication(@JsonProperty("id") final String id,
                                @JsonProperty("departmentId") final String departmentId,
                                @JsonProperty("attribute") final String attribute,
                                @JsonProperty("status") final int status) {
        this.id = id;
        this.departmentId = departmentId;
        this.attribute = attribute;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeApplication that = (AttributeApplication) o;
        return getStatus() == that.getStatus() && Objects.equals(getId(), that.getId()) && Objects.equals(getDepartmentId(), that.getDepartmentId()) && Objects.equals(getAttribute(), that.getAttribute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDepartmentId(), getAttribute(), getStatus());
    }

    @Override
    public String toString() {
        return "AttributeApplication{" +
                "id='" + id + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", attribute='" + attribute + '\'' +
                ", status=" + status +
                '}';
    }
}
