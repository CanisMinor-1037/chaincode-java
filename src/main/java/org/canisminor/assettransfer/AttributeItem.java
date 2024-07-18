package org.canisminor.assettransfer;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

import java.util.Objects;

@DataType()
public class AttributeItem {
    @Property(schema = {"pattern", "^AttributeItem\\d{1,20}$"})
    private String id; // 属性条目id

    @Property(schema = {"pattern", "^\\w+:\\w+$"})
    private String content; // 属性条目内容

    // Getter
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    // Constructor
    public AttributeItem(@JsonProperty("id") final String id,
                         @JsonProperty("content") final String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttributeItem that = (AttributeItem) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getContent(), that.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getContent());
    }

    @Override
    public String toString() {
        return "AttributeItem{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
