package com.gooddata.project.outputstage;

import static org.apache.commons.lang.Validate.notNull;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Warehouse table model
 */
@JsonTypeName("table")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Table {

    private final String name;

    private final List<TableColumn> columns;

    @JsonCreator
    public Table(@JsonProperty("name") String name, @JsonProperty("columns") List<TableColumn> columns) {
        notNull(name, "name cannot be null");

        this.name = name;
        this.columns = columns == null ? Collections.<TableColumn>emptyList() : new ArrayList<TableColumn>();
    }

    public String getName() {
        return name;
    }

    public List<TableColumn> getColumns() {
        return Collections.unmodifiableList(columns);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Table table = (Table) o;

        if (columns != null ? !columns.equals(table.columns) : table.columns != null) {
            return false;
        }
        if (name != null ? !name.equals(table.name) : table.name != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (columns != null ? columns.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(name);
        sb.append(" {\n");
        for (TableColumn column : columns) {
            sb.append("  ").append(column).append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
