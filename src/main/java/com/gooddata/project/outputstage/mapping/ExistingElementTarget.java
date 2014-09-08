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
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * Column mapping target representing an element that already exists in LDM
 */
@JsonTypeName(ExistingElementTarget.TYPE_NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ExistingElementTarget implements LdmTarget {

    public static final String TYPE_NAME = "existingElement";

    /**
     * Identifier of target LDM element
     */
    private final String reference;

    @JsonCreator
    public ExistingElementTarget(@JsonProperty("reference") String reference) {
        this.reference = reference;
    }

    public String getReference() {
        return reference;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(reference);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ExistingElementTarget other = (ExistingElementTarget) obj;
        return Objects.equal(this.reference, other.reference);
    }

    @Override
    public String toString() {
        return reference;
    }
}
