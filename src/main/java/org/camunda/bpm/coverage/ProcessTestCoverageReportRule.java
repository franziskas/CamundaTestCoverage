package org.camunda.bpm.coverage;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.rules.ExternalResource;

public class ProcessTestCoverageReportRule extends ExternalResource implements ProcessCoverageCapture {
	private Map<String, ProcessCoverage> processCoverages = new HashMap<>();
	private CoverageReportGenerator reportGenerator;

	public ProcessTestCoverageReportRule(CoverageReportGenerator reportGenerator) {
		this.reportGenerator = reportGenerator;
	}

	@Override
	protected void after() {
		for (ProcessCoverage processCoverage : processCoverages.values()) {
			reportGenerator.generateReport(processCoverage);
		}
	}

	@Override
	public boolean definitionIsMissing(ProcessDefinition processDefinition) {
		return !processCoverages.containsKey(processDefinition.getKey());
	}

	@Override
	public void addDefinitionWithActivities(ProcessDefinition processDefinition, Collection<String> availableActivities) {
		String key = processDefinition.getKey();
		processCoverages.put(key, new ProcessCoverage(key, availableActivities));
	}

	@Override
	public void visit(ProcessDefinition processDefinition, List<String> visitedActivities) {
		ProcessCoverage coverage = processCoverages.get(processDefinition.getKey());
		coverage.visit(visitedActivities);
	}
}
