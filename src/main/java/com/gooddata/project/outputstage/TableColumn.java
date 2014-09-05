package com.gooddata.project.outputstage;

import static org.apache.commons.lang.Validate.notNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Warehouse column model.
 */
@JsonTypeName("column")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class TableColumn {

    private final String name;

    private final DataType dataType;

    @JsonCreator
    public TableColumn(@JsonProperty("name") String name, @JsonProperty("dataType") DataType dataType) {
        notNull(name, "name cannot be null");
        notNull(dataType, "dataType cannot be null");

        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final TableColumn that = (TableColumn) o;

        if (dataType != null ? !dataType.equals(that.dataType) : that.dataType != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (dataType != null ? dataType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return name + " " + dataType;
    }
}
