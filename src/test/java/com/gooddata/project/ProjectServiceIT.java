package com.gooddata.project;

import static java.util.Arrays.asList;
import static net.jadler.Jadler.onRequest;
import static net.jadler.Jadler.verifyThatRequest;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import com.gooddata.AbstractGoodDataIT;
import com.gooddata.GoodDataException;
import com.gooddata.GoodDataRestException;
import com.gooddata.gdc.UriResponse;
import com.gooddata.project.outputstage.DataType;
import com.gooddata.project.outputstage.DataTypeName;
import com.gooddata.project.outputstage.Table;
import com.gooddata.project.outputstage.TableColumn;
import com.gooddata.project.outputstage.ProjectOutputStage;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

public class ProjectServiceIT extends AbstractGoodDataIT {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final String PROJECT_ID = "PROJECT_ID";
    private static final String PROJECT_URI = "/gdc/projects/" + PROJECT_ID;

    private Project loading;
    private Project enabled;
    private Project deleted;

    @Before
    public void setUp() throws Exception {
        loading = MAPPER.readValue(readResource("/project/project-loading.json"), Project.class);
        enabled = MAPPER.readValue(readResource("/project/project.json"), Project.class);
        deleted = MAPPER.readValue(readResource("/project/project-deleted.json"), Project.class);
    }

    @Test
    public void shouldCreateProject() throws Exception {
        onRequest()
                .havingMethodEqualTo("POST")
                .havingPathEqualTo(Projects.URI)
                .respond()
                .withBody(MAPPER.writeValueAsString(new UriResponse(PROJECT_URI)))
                .withStatus(202)
        ;
        onRequest()
                .havingMethodEqualTo("GET")
                .havingPathEqualTo(PROJECT_URI)
            .respond()
                .withBody(MAPPER.writeValueAsString(loading))
                .withStatus(202)
            .thenRespond()
                .withBody(MAPPER.writeValueAsString(enabled))
                .withStatus(200)
        ;

        final Project project = gd.getProjectService().createProject(new Project("TITLE", "AUTH_TOKEN")).get();
        assertThat(project, is(notNullValue()));
        assertThat(project.getTitle(), is("TITLE"));
    }

    @Test(expected = GoodDataException.class)
    public void shouldFailWhenPostFails() throws Exception {
        onRequest()
                .havingMethodEqualTo("POST")
                .havingPathEqualTo(Projects.URI)
            .respond()
                .withStatus(400)
        ;
        gd.getProjectService().createProject(new Project("TITLE", "AUTH_TOKEN")).get();
    }

    @Test(expected = GoodDataRestException.class) // todo previous method returns GoodDataException
    public void shouldFailWhenPollFails() throws Exception {
        onRequest()
                .havingMethodEqualTo("POST")
                .havingPathEqualTo(Projects.URI)
            .respond()
                .withBody(MAPPER.writeValueAsString(new UriResponse(PROJECT_URI)))
                .withStatus(202)
        ;
        onRequest()
                .havingMethodEqualTo("GET")
                .havingPathEqualTo(PROJECT_URI)
            .respond()
                .withStatus(400)
        ;

        gd.getProjectService().createProject(new Project("TITLE", "AUTH_TOKEN")).get();
    }


    @Test(expected = GoodDataException.class)
    public void shouldFailWhenCantCreateProject() throws Exception {
        onRequest()
                .havingMethodEqualTo("POST")
                .havingPathEqualTo(Projects.URI)
            .respond()
                .withBody(MAPPER.writeValueAsString(new UriResponse(PROJECT_URI)))
                .withStatus(202)
        ;
        onRequest()
                .havingMethodEqualTo("GET")
                .havingPathEqualTo(PROJECT_URI)
            .respond()
                .withBody(MAPPER.writeValueAsString(deleted))
                .withStatus(200)
        ;

        gd.getProjectService().createProject(new Project("TITLE", "AUTH_TOKEN")).get();
    }

    @Test
    public void shouldRemoveProject() throws Exception {
        onRequest()
                .havingMethodEqualTo("DELETE")
                .havingPathEqualTo(PROJECT_URI)
                .respond()
                .withStatus(202);

        gd.getProjectService().removeProject(enabled);

        verifyThatRequest()
                .havingMethodEqualTo("DELETE")
                .havingPathEqualTo(PROJECT_URI)
                .receivedOnce();
    }

    @Test
    public void shouldGetProjectOutputStage() throws Exception {
        onRequest()
                .havingMethodEqualTo("GET")
                .havingPathEqualTo(ProjectOutputStage.TEMPLATE.expand(PROJECT_ID).toString())
                .respond()
                .withBody(readResource("/project/output-stage.json"))
                .withStatus(200);

        final ProjectOutputStage projectOutputStage = gd.getProjectService().getProjectOutputStage(PROJECT_ID);
        assertThat(projectOutputStage, is(notNullValue()));
        assertThat(projectOutputStage.getTables(), containsInAnyOrder(
                new Table("country",asList(
                        new TableColumn("country_id", new DataType(DataTypeName.VARCHAR)),
                        new TableColumn("country_name", new DataType(DataTypeName.VARCHAR)),
                        new TableColumn("country_abre", new DataType(DataTypeName.VARCHAR)),
                        new TableColumn("region", new DataType(DataTypeName.VARCHAR)))),
                new Table("customer",asList(
                        new TableColumn("id", new DataType(DataTypeName.VARCHAR)),
                        new TableColumn("name", new DataType(DataTypeName.VARCHAR)),
                        new TableColumn("ticker", new DataType(DataTypeName.VARCHAR)),
                        new TableColumn("web", new DataType(DataTypeName.VARCHAR)),
                        new TableColumn("age", new DataType(DataTypeName.INTEGER)),
                        new TableColumn("created_date", new DataType(DataTypeName.DATE))))
                ));
    }
}