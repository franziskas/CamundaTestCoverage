package org.camunda.bpm.coverage;

import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

public class TextFileCoverageReportGenerator implements CoverageReportGenerator {
	private String targetDir;

	public TextFileCoverageReportGenerator(String targetDir) {
		this.targetDir = targetDir;
	}

	@Override
	public void generateReport(ProcessCoverage processCoverage) {
		StringBuilder reportBuilder = new StringBuilder();
		for (Entry<String, Boolean> activityEntry : processCoverage.activities.entrySet()) {
			reportBuilder.append(activityEntry.getKey());
			reportBuilder.append(" ");
			if (activityEntry.getValue()) {
				reportBuilder.append("visited");
			} else {
				reportBuilder.append("MISSING");
			}
			reportBuilder.append("\n");
		}

		try {
			FileUtils.forceMkdir(new File(targetDir));
			String reportName = processCoverage.getProcessDefinitionId() + ".coverage.txt";
			FileUtils.writeStringToFile(new File(targetDir + "/" + reportName), reportBuilder.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
