package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class AuthenticationApplication {
    @Property(schema = {"pattern", "^AuthenticationApplication\\d{1,20}$"})
    private String id; // 属性申请id

    // schema = {"pattern", "^DepartmentId_User\\d{1,20}$"}
    @Property()
    private String userId; // 用户id

    @Property(schema = {"pattern", "^(\\w+:\\w+)( \\w+:\\w+)*$"})
    private String attribute; // 申请属性

    @Property(schema = {"minimum", "0", "maximum", "20"})
    private int status; // 状态

    // Getter
    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
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
    public AuthenticationApplication(@JsonProperty("id") final String id,
                                @JsonProperty("userId") final String userId,
                                @JsonProperty("attribute") final String attribute,
                                @JsonProperty("status") final int status) {
        this.id = id;
        this.userId = userId;
        this.attribute = attribute;
        this.status = status;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticationApplication that = (AuthenticationApplication) o;
        return getStatus() == that.getStatus() && Objects.equals(getId(), that.getId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getAttribute(), that.getAttribute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getAttribute(), getStatus());
    }

    @Override
    public String toString() {
        return "AuthenticationApplication{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", attribute='" + attribute + '\'' +
                ", status=" + status +
                '}';
    }
}
