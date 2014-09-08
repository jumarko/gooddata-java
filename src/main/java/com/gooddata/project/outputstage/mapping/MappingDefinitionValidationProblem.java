/*
 * Copyright (c) 2014. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.gooddata.project.outputstage.mapping;

import static com.google.common.base.Predicates.equalTo;

import com.google.common.base.Objects;
import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;
import org.apache.commons.lang.Validate;
import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.List;
import java.util.Set;

@JsonTypeName("validationProblem")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonPropertyOrder({"message", "messageParameters", "type", "severity", "context"})
public class MappingDefinitionValidationProblem {

    private final String message;
    private final List<String> messageParameters;

    private final Type type;
    private final Severity severity;
    private final Context context;

    @JsonCreator
    public MappingDefinitionValidationProblem(@JsonProperty("message") String message,
                                              @JsonProperty("messageParameters") List<String> messageParameters,
                                              @JsonProperty("type") Type type,
                                              @JsonProperty("severity") Severity severity,
                                              @JsonProperty("context") Context context) {
        Validate.notEmpty(message);
        Validate.notNull(type);
        Validate.notNull(severity);

        this.message = message;
        this.messageParameters = messageParameters;
        this.type = type;
        this.severity = severity;
        this.context = context;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getMessageParameters() {
        return messageParameters;
    }

    /**
     * Returns formatted message (message with replaced parameter placeholders)
     */
    @JsonIgnore
    public String getFormattedMessage() {
        return String.format(message, messageParameters.toArray(new String[messageParameters.size()]));
    }

    @JsonIgnore
    public boolean isError() {
        return this.getSeverity() == Severity.ERROR;
    }

    @JsonIgnore
    public boolean isWarning() {
        return this.getSeverity() == Severity.WARNING;
    }

    @JsonIgnore
    public boolean isInfo() {
        return this.getSeverity() == Severity.INFO;
    }

    public Type getType() {
        return type;
    }

    public Severity getSeverity() {
        return severity;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(message, messageParameters, type, severity, context);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final MappingDefinitionValidationProblem other = (MappingDefinitionValidationProblem) obj;
        final Set<Boolean> rules = Sets.newHashSet(
                Objects.equal(this.message, other.message),
                Objects.equal(this.messageParameters, other.messageParameters),
                Objects.equal(this.type, other.type),
                Objects.equal(this.severity, other.severity),
                Objects.equal(this.context, other.context)
        );

        return Iterables.all(rules, equalTo(Boolean.TRUE));
    }

    @Override
    public String toString() {
        return getFormattedMessage();
    }

    public static enum Severity {
        ERROR,
        WARNING,
        INFO;
    }

    public static enum Type {
        UNMAPPED_OS_TABLE,
        UNMAPPED_OS_TABLE_COLUMN,
        UNMAPPED_DATASET,
        UNMAPPED_DATASET_ITEM,

        UNCONFIRMED_AUTOMAPPING,

        DUPLICIT_MAPPING_OS_TABLE,
        DUPLICIT_MAPPING_OS_TABLE_COLUMN,
        DUPLICIT_MAPPING_DATASET,
        DUPLICIT_MAPPING_DATASET_ITEM,

        MISSING_OS_TABLE,
        MISSING_OS_TABLE_COLUMN,
        MISSING_DATASET,
        MISSING_DATASET_ITEM,

        INCOMPATIBLE_TYPES,
        INVALID_TEMPLATE;
    }

    @JsonTypeName("context")
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
    public static class Context {
        private final String osTable;
        private final String osTableColumn;
        private final String dataset;
        private final String datasetItem;

        @JsonCreator
        public Context(@JsonProperty("osTable") String osTable,
                       @JsonProperty("osTableColumn") String osTableColumn,
                       @JsonProperty("dataset") String dataset,
                       @JsonProperty("datasetItem") String datasetItem) {
            this.osTable = osTable;
            this.osTableColumn = osTableColumn;
            this.dataset = dataset;
            this.datasetItem = datasetItem;
        }

        public String getOsTable() {
            return osTable;
        }

        public String getOsTableColumn() {
            return osTableColumn;
        }

        public String getDataset() {
            return dataset;
        }

        public String getDatasetItem() {
            return datasetItem;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(osTable, osTableColumn, dataset, datasetItem);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final Context other = (Context) obj;
            final Set<Boolean> rules = Sets.newHashSet(
                    Objects.equal(this.osTable, other.osTable),
                    Objects.equal(this.osTableColumn, other.osTableColumn),
                    Objects.equal(this.dataset, other.dataset),
                    Objects.equal(this.datasetItem, other.datasetItem)
            );

            return Iterables.all(rules, equalTo(Boolean.TRUE));
        }

        public static class Builder {
            private String osTable;
            private String osTableColumn;
            private String dataset;
            private String datasetItem;

            public Builder withOsTable(String osTable) {
                this.osTable = osTable;
                return this;
            }

            public Builder withOsTableColumn(String osTableColumn) {
                this.osTableColumn = osTableColumn;
                return this;
            }

            public Builder withDataset(String dataset) {
                this.dataset = dataset;
                return this;
            }

            public Builder withDatasetItem(String datasetItem) {
                this.datasetItem = datasetItem;
                return this;
            }


            public Context build() {
                return new Context(osTable, osTableColumn, dataset, datasetItem);
            }

        }
    }

    public static class Builder {
        private String message;
        private List<String> messageParameters;
        private Type type;
        private Severity severity = Severity.ERROR;
        private Context context;

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withMessageParameters(List<String> messageParameters) {
            this.messageParameters = messageParameters;
            return this;
        }

        public Builder withType(Type type) {
            this.type = type;
            return this;
        }

        public Builder withSeverity(Severity severity) {
            this.severity = severity;
            return this;
        }

        public Builder withContext(Context context) {
            this.context = context;
            return this;
        }

        public MappingDefinitionValidationProblem build() {
            return new MappingDefinitionValidationProblem(message, messageParameters, type, severity, context);
        }

    }
}

