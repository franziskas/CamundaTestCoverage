package org.camunda.bpm.coverage;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class XmlCoverageReportGenerator implements CoverageReportGenerator {
	private String targetDir;

	public XmlCoverageReportGenerator(String targetDir) {
		this.targetDir = targetDir;
	}

	@Override
	public void generateReport(ProcessCoverage processCoverage) {
		StringBuilder reportBuilder = new StringBuilder();
		reportBuilder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		reportBuilder.append("<testsuite name=\"" + processCoverage.getProcessDefinitionId() + "\" time=\"0\">\n");

		for (Entry<String, Boolean> activityEntry : processCoverage.activities.entrySet()) {
			reportBuilder.append("<testcase name=\"" + activityEntry.getKey() + "\">\n");
			if (!activityEntry.getValue()) {
				reportBuilder.append("<failure type=\"notVisited\">Not visited</failure>\n");
			}
			reportBuilder.append("</testcase>\n");
		}
		reportBuilder.append("</testsuite>\n");

		try {
			FileUtils.forceMkdir(new File(targetDir));
			String reportName = processCoverage.getProcessDefinitionId() + ".xml";
			FileUtils.writeStringToFile(new File(targetDir + "/" + reportName), reportBuilder.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
