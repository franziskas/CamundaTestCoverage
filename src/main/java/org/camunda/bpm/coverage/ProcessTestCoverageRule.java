package org.camunda.bpm.coverage;

import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.impl.RepositoryServiceImpl;
import org.camunda.bpm.engine.impl.pvm.PvmActivity;
import org.camunda.bpm.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.junit.rules.ExternalResource;

public class ProcessTestCoverageRule extends ExternalResource {
	private ProcessEngine processEngine;
	private ProcessCoverageCapture processTestCoverageReportRule;

	public ProcessTestCoverageRule(ProcessCoverageCapture processTestCoverageReportRule) {
		this.processTestCoverageReportRule = processTestCoverageReportRule;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	@Override
	protected void after() {
		for (ProcessDefinition processDefinition : findAllProcessDefinitions()) {
			initProcessDefinition(processDefinition);
			captureCoverage(processDefinition);
		}
	}

	private void initProcessDefinition(ProcessDefinition processDefinition) {
		if (processTestCoverageReportRule.definitionIsMissing(processDefinition)) {
			processTestCoverageReportRule.addDefinitionWithActivities(processDefinition,
					findAllAvailableActivityIds(processDefinition));
		}
	}

	private void captureCoverage(ProcessDefinition processDefinition) {
		processTestCoverageReportRule.visit(processDefinition, findAllVisitedActivityIds(processDefinition));
	}

	private List<String> findAllVisitedActivityIds(ProcessDefinition processDefinition) {
		return findAllVisitedActivities(processDefinition).stream().map(activity -> activity.getActivityId())
				.collect(toList());
	}

	private Collection<String> findAllAvailableActivityIds(ProcessDefinition processDefinition) {
		return findAllAvailableActivities(processDefinition).stream().map(activity -> activity.getId())
				.collect(Collectors.toList());
	}

	private List<? extends PvmActivity> findAllAvailableActivities(ProcessDefinition processDefinition) {
		RepositoryServiceImpl repositoryService = (RepositoryServiceImpl) processEngine.getRepositoryService();
		ReadOnlyProcessDefinition readOnlyProcessDefinition = repositoryService
				.getDeployedProcessDefinition(processDefinition.getId());
		return readOnlyProcessDefinition.getActivities();
	}

	private List<HistoricActivityInstance> findAllVisitedActivities(ProcessDefinition processDefinition) {
		return processEngine.getHistoryService().createHistoricActivityInstanceQuery()
				.processDefinitionId(processDefinition.getId()).list();
	}

	private List<ProcessDefinition> findAllProcessDefinitions() {
		return processEngine.getRepositoryService().createProcessDefinitionQuery().latestVersion().list();
	}
}
