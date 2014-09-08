/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.gooddata.project.outputstage.mapping;

import com.google.common.base.Objects;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Maps ADS table column to a "model target" (either existing or new)
 */
@JsonTypeName("columnMapping")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"column", "target", "acknowledged"})
public class ColumnMapping {

    /**
     * Output stage column name
     */
    private final String column;

    /**
     * Specification of model target
     */
    private final LdmTarget target;

    /**
     * Has this been confirmed by George?
     */
    private final boolean acknowledged;

    @JsonCreator
    public ColumnMapping(@JsonProperty("column") String column, @JsonProperty("target") LdmTarget target,
                         @JsonProperty("acknowledged") boolean acknowledged) {

        this.column = column;
        this.target = target;
        this.acknowledged = acknowledged;
    }

    public String getColumn() {
        return column;
    }

    public LdmTarget getTarget() {
        return target;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(column, target, acknowledged);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ColumnMapping other = (ColumnMapping) obj;
        return Objects.equal(this.column, other.column) && Objects.equal(this.target, other.target) && Objects.equal(this.acknowledged, other.acknowledged);
    }

    @Override
    public String toString() {
        return "ColumnMapping{" +
                "column='" + column + '\'' +
                ", target=" + target +
                ", acknowledged=" + acknowledged +
                '}';
    }
}
