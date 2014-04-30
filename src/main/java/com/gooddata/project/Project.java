/*
 * Copyright (C) 2007-2014, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata.project;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeInfo;
import org.codehaus.jackson.annotate.JsonTypeName;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.web.util.UriTemplate;

/**
 */
@JsonTypeName("project")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class Project {

    public static final String PROJECTS_URI = "/gdc/account/profile/{id}/projects";
    public static final String PROJECT_URI = Projects.URI + "/{id}";
    public static final UriTemplate PROJECT_TEMPLATE = new UriTemplate(PROJECT_URI);

    private Content content;
    private Meta meta;
    private Links links;

    public Project(String title, String authorizationToken) {
        this(new Content(authorizationToken), new Meta(title), null);
    }

    public Project(String title, String authorizationToken, String projectTemplate) {
        this(new Content(authorizationToken), new Meta(title, projectTemplate), null);
    }

    @JsonCreator
    public Project(@JsonProperty("content") Content content, @JsonProperty("meta") Meta meta, @JsonProperty("links") Links links) {
        this.content = content;
        this.meta = meta;
        this.links = links;
    }

    public Content getContent() {
        return content;
    }

    @JsonIgnore
    public String getId() {
        return PROJECT_TEMPLATE.match(getLinks().getSelf()).get("id");
    }

    public Meta getMeta() {
        return meta;
    }

    public Links getLinks() {
        return links;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public static class Content {
        private @JsonProperty String authorizationToken;
        private @JsonProperty Integer guidedNavigation = 1;
        private String state;

        @JsonCreator
        public Content(@JsonProperty("authorizationToken") String authorizationToken) {
            this.authorizationToken = authorizationToken;
        }

        public String getState() {
            return state;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    public static class Meta {
        private String title;
        private String projectTemplate;

        public Meta(final String title, final String projectTemplate) {
            this.title = title;
            this.projectTemplate = projectTemplate;
        }

        @JsonCreator
        public Meta(@JsonProperty("title") String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String getProjectTemplate() {
            return projectTemplate;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Links {
        private final String self;
        private final String uploads;

        @JsonCreator
        public Links(@JsonProperty("self") String self, @JsonProperty("uploads") String uploads) {
            this.self = self;
            this.uploads = uploads;
        }

        public String getSelf() {
            return self;
        }

        public String getUploads() {
            return uploads;
        }
    }

}
