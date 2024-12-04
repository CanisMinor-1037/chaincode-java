package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class User {
    @Property(schema = {"pattern", "^Department\\d{1,20}_User\\d{1,20}$"})
    private String id; // 用户id

    @Property()
    private String name; // 用户名

    @Property(schema = {"pattern", "^(\\w+:\\w+)( \\w+:\\w+)*$"})
    private String attribute; // 属性字符串

    // Getter
    public String getAttribute() {
        return attribute;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    // Setter
    public void setAttribute(final String attribute) {
        this.attribute = attribute;
    }

    // Constructor
    public User(@JsonProperty("id") final String id,
                      @JsonProperty("name") final String name,
                      @JsonProperty("attribute") final String attribute) {
        this.id = id;
        this.name = name;
        this.attribute = attribute;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return Objects.equals(id, that.getId()) && Objects.equals(name, that.getName()) && Objects.equals(attribute, that.getAttribute());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, attribute);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
}
