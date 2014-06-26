/*
 * Copyright (C) 2007-2014, GoodData(R) Corporation. All rights reserved.
 */
package com.gooddata;

import com.gooddata.account.Account;
import com.gooddata.account.AccountService;
import com.gooddata.dataset.DatasetManifest;
import com.gooddata.dataset.DatasetService;
import com.gooddata.md.Attribute;
import com.gooddata.md.Fact;
import com.gooddata.md.MetadataService;
import com.gooddata.md.Metric;
import com.gooddata.md.Restriction;
import com.gooddata.md.report.AttributeInGrid;
import com.gooddata.md.report.GridElement;
import com.gooddata.md.report.GridReportDefinitionContent;
import com.gooddata.md.report.Report;
import com.gooddata.md.report.ReportDefinition;
import com.gooddata.model.ModelDiff;
import com.gooddata.model.ModelService;
import com.gooddata.project.Project;
import com.gooddata.project.ProjectService;
import com.gooddata.report.ReportExportFormat;
import com.gooddata.report.ReportService;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

import static java.util.Arrays.asList;

public class Example {

    public static void main(String... args) {
        final GoodData gd = new GoodData("roman@gooddata.com", "Roman1");

        final AccountService accountService = gd.getAccountService();
        final Account current = accountService.getCurrent();
        System.out.println(current.getId());

        final ProjectService projectService = gd.getProjectService();
        final Collection<Project> projects = projectService.getProjects();
        System.out.println(projects);

        final Project project = projectService.createProject(new Project("sparkling", "pgroup2")).get();
        System.out.println(project.getSelfLink());

        final ModelService modelService = gd.getModelService();
        final ModelDiff projectModelDiff = modelService.getProjectModelDiff(project,
                new InputStreamReader(Example.class.getResourceAsStream("/person.json"))).get();
        modelService.updateProjectModel(project, projectModelDiff);

        final DatasetService datasetService = gd.getDatasetService();
        final DatasetManifest manifest = datasetService.getDatasetManifest(project, "dataset.person");
        datasetService.loadDataset(project, manifest, Example.class.getResourceAsStream("/person.csv")).get();

        final MetadataService md = gd.getMetadataService();

        final String factUri = md.getObjUri(project, Fact.class, Restriction.title("Person Shoe Size"));
        final Metric metric = md.createObj(project, new Metric("Shoe size sum", "SELECT SUM([" + factUri + "])", "#,##0"));
        final Attribute attr = md.getObj(project, Attribute.class, Restriction.title("Department"));

        final ReportDefinition reportDefinition = GridReportDefinitionContent.create(
                "my report definition",
                asList("metricGroup"),
                Arrays.<GridElement>asList(new AttributeInGrid(attr.getDefaultDisplayForm().getUri())),
                asList(new GridElement(metric.getUri(), ""))
        );
        final ReportDefinition definition = md.createObj(project, reportDefinition);
        md.createObj(project, new Report("my report", definition));

        final ReportService reportService = gd.getReportService();
        final String imgUri = reportService.exportReport(definition, ReportExportFormat.PNG);
        System.out.println(imgUri);

        gd.logout();
    }

}
