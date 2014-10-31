package com.gooddata.project.outputstage;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.util.UriTemplate;

/**
 * Logical model of ADS output stage
 */
@JsonTypeName("outputStage")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ProjectOutputStage {

    private final String schema;

    private final Links links;

    @JsonCreator
    public ProjectOutputStage(@JsonProperty("schema") String schema, @JsonProperty("links") Links links) {
        this.schema = schema;
        this.links = links;
    }

    public String getSchema() {
        return schema;
    }

    public Links getLinks() {
        return links;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProjectOutputStage that = (ProjectOutputStage) o;

        if (links != null ? !links.equals(that.links) : that.links != null) {
            return false;
        }
        if (schema != null ? !schema.equals(that.schema) : that.schema != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = schema != null ? schema.hashCode() : 0;
        result = 31 * result + (links != null ? links.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ProjectOutputStage{");
        sb.append("schema='").append(schema).append('\'');
        sb.append(", links=").append(links);
        sb.append('}');
        return sb.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public static class Links {

        private final String self;

        private final String outputStageModel;

        private final String outputStageMapping;

        private final String outputStageMetadata;

        public Links(@JsonProperty("self") String self, @JsonProperty("outputStageModel") String outputStageModel,
                @JsonProperty("outputStageMapping") String outputStageMapping, @JsonProperty("outputStageMetadata") String outputStageMetadata) {
            this.self = self;
            this.outputStageModel = outputStageModel;
            this.outputStageMapping = outputStageMapping;
            this.outputStageMetadata = outputStageMetadata;
        }

        public String getSelf() {
            return self;
        }

        public String getOutputStageModel() {
            return outputStageModel;
        }

        public String getOutputStageMapping() {
            return outputStageMapping;
        }

        public String getOutputStageMetadata() {
            return outputStageMetadata;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Links links = (Links) o;

            if (outputStageMapping != null ? !outputStageMapping.equals(links.outputStageMapping) : links.outputStageMapping != null) {
                return false;
            }
            if (outputStageMetadata != null ? !outputStageMetadata.equals(links.outputStageMetadata) : links.outputStageMetadata != null) {
                return false;
            }
            if (outputStageModel != null ? !outputStageModel.equals(links.outputStageModel) : links.outputStageModel != null) {
                return false;
            }
            if (self != null ? !self.equals(links.self) : links.self != null) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = self != null ? self.hashCode() : 0;
            result = 31 * result + (outputStageModel != null ? outputStageModel.hashCode() : 0);
            result = 31 * result + (outputStageMapping != null ? outputStageMapping.hashCode() : 0);
            result = 31 * result + (outputStageMetadata != null ? outputStageMetadata.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Links{");
            sb.append("self='").append(self).append('\'');
            sb.append(", outputStageModel='").append(outputStageModel).append('\'');
            sb.append(", outputStageMapping='").append(outputStageMapping).append('\'');
            sb.append(", outputStageMetadata='").append(outputStageMetadata).append('\'');
            sb.append('}');
            return sb.toString();
        }
    }
}
