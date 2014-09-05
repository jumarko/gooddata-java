package com.gooddata.project.outputstage;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.util.UriTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Logical model of ADS output stage
 */
@JsonTypeName("outputStage")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class ProjectOutputStage {

    public static final String URI = "/gdc/dataload/internal/projects/{projectId}/outputStage";
    public static final UriTemplate TEMPLATE = new UriTemplate(URI);

    private final List<Table> tables;
    private final Links links;

    public ProjectOutputStage(List<Table> tables) {
        this(tables, null);
    }

    @JsonCreator
    public ProjectOutputStage(@JsonProperty("tables") List<Table> tables, @JsonProperty("links") Links links) {
        this.tables = tables == null ? Collections.<Table>emptyList() : new ArrayList<Table>(tables);
        this.links = links;
    }

    public List<Table> getTables() {
        return Collections.unmodifiableList(tables);
    }

    public Links getLinks() {
        return links;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(tables);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ProjectOutputStage other = (ProjectOutputStage) obj;
        return Objects.equals(this.tables, other.tables);
    }

    @Override
    public String toString() {
        return "ProjectOutputStage{" +
                "tables=" + tables +
                ", links=" + links +
                '}';
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    public static class Links {
        private final String self;
        private final String ads;
        private final String mapping;

        @JsonCreator
        public Links(@JsonProperty("self") String self, @JsonProperty("ads") String ads, @JsonProperty("mapping") String mapping) {
            this.self = self;
            this.ads = ads;
            this.mapping = mapping;
        }

        public String getSelf() {
            return self;
        }

        public String getAds() {
            return ads;
        }

        public String getMapping() {
            return mapping;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            final Links links = (Links) o;

            if (ads != null ? !ads.equals(links.ads) : links.ads != null) {
                return false;
            }
            if (mapping != null ? !mapping.equals(links.mapping) : links.mapping != null) {
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
            result = 31 * result + (ads != null ? ads.hashCode() : 0);
            result = 31 * result + (mapping != null ? mapping.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Links{" +
                    "self='" + self + '\'' +
                    ", ads='" + ads + '\'' +
                    ", mapping='" + mapping + '\'' +
                    '}';
        }
    }
}
