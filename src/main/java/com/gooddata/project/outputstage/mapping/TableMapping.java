/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.gooddata.project.outputstage.mapping;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.Collections;
import java.util.List;

/**
 * Mapping for a single "output stage table".
 */
@JsonTypeName("tableMapping")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"table", "dataset", "columnPropagation","acknowledged", "columnMappings"})
public class TableMapping {

    /**
     * Source ADS table name
     */
    private final String table;

    /**
     * Target LDM dataset identifier
     */
    private final String dataset;

    /**
     * Policy for handling of new columns
     */
    private final ColumnPropagation columnPropagation;

    /**
     * Has this been confirmed by George?
     */
    private final boolean acknowledged;

    /**
     * Mappings for individual table columns
     */
    private final List<ColumnMapping> columnMappings;

    @JsonCreator
    public TableMapping(
            @JsonProperty("title") String table,
            @JsonProperty("dataset") String dataset,
            @JsonProperty("columnPropagation") ColumnPropagation columnPropagation,
            @JsonProperty("acknowledged") boolean acknowledged,
            @JsonProperty("columnMappings") List<ColumnMapping> columnMappings){

        this.table = table;
        this.dataset = dataset;
        this.columnPropagation = columnPropagation;
        this.acknowledged = acknowledged;
        this.columnMappings = (columnMappings == null) ? Collections.<ColumnMapping>emptyList() : Collections.unmodifiableList(columnMappings);
    }

    public String getTable() {
        return table;
    }

    public String getDataset() {
        return dataset;
    }

    public ColumnPropagation getColumnPropagation() {
        return columnPropagation;
    }

    public List<ColumnMapping> getColumnMappings() {
        return columnMappings;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(table, dataset, columnPropagation, Sets.newHashSet(columnMappings), acknowledged);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final TableMapping other = (TableMapping) obj;
        return Objects.equal(this.table, other.table)
                && Objects.equal(this.dataset, other.dataset)
                && Objects.equal(this.columnPropagation, other.columnPropagation)
                && Objects.equal(this.acknowledged, other.acknowledged)
                && Objects.equal(Sets.newHashSet(this.columnMappings), Sets.newHashSet(other.columnMappings));
    }

    @Override
    public String toString() {
        return "TableMapping{" +
                "table='" + table + '\'' +
                ", dataset='" + dataset + '\'' +
                ", columnPropagation=" + columnPropagation +
                ", acknowledged=" + acknowledged +
                ", columnMappings=" + columnMappings +
                '}';
    }
}
