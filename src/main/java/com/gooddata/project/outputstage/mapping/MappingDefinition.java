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
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
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
 * Top-level ADS - LDM mapping object
 */
@JsonTypeName("mappingDefinition")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"tableMappings", "validationProblems", "links"})
public class MappingDefinition {

    public static final MappingDefinition EMPTY = new MappingDefinition(Collections.<TableMapping>emptyList(), null, null);

    private final List<TableMapping> tableMappings;
    private final List<MappingDefinitionValidationProblem> validationProblems;
    private final Links links;

    @JsonCreator
    public MappingDefinition(@JsonProperty("tableMappings") List<TableMapping> tableMappings,
                             @JsonProperty("validationProblems") List<MappingDefinitionValidationProblem> validationProblems,
                             @JsonProperty("links") Links links) {

        this.tableMappings = tableMappings != null ? Collections.unmodifiableList(tableMappings) : Collections.<TableMapping>emptyList();
        this.validationProblems = validationProblems != null ? Collections.unmodifiableList(validationProblems) : null;
        this.links = links;
    }

    public MappingDefinition toMappingDefinitionWithLinks(Links links) {
        return new MappingDefinition(this.getTableMappings(), validationProblems, links);
    }

    public List<TableMapping> getTableMappings() {
        return tableMappings;
    }

    public List<MappingDefinitionValidationProblem> getValidationProblems() {
        return validationProblems;
    }

    public Links getLinks() {
        return links;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(Sets.newHashSet(tableMappings, validationProblems));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MappingDefinition other = (MappingDefinition) obj;
        return Objects.equal(Sets.newHashSet(this.tableMappings, validationProblems), Sets.newHashSet(other.tableMappings, validationProblems));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                append("tableMappings", tableMappings).
                append("validationProblems", validationProblems).toString();
    }

    @JsonTypeName("links")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public static class Links {
        private final String self;
        private final String outputStageModel;
        private final String projectModel;

        public Links(@JsonProperty("self") String self, @JsonProperty("outputStageModel") String outputStageModel, @JsonProperty("projectModel") String projectModel) {
            this.self = self;
            this.outputStageModel = outputStageModel;
            this.projectModel = projectModel;
        }

        public String getSelf() {
            return self;
        }

        public String getOutputStageModel() {
            return outputStageModel;
        }

        public String getProjectModel() {
            return projectModel;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(self) + Objects.hashCode(outputStageModel) + Objects.hashCode(projectModel);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            final Links other = (Links) obj;

            if (!Objects.equal(this.self, other.self)) {
                return false;
            }

            if (!Objects.equal(this.outputStageModel, other.outputStageModel)) {
                return false;
            }

            if (!Objects.equal(this.projectModel, other.projectModel)) {
                return false;
            }

            return true;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
                    append("self", self).
                    append("outputStageModel", outputStageModel).
                    append("projectModel", projectModel).
                    toString();
        }
    }
}