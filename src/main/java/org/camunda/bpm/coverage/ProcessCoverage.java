package org.camunda.bpm.coverage;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

class ProcessCoverage {
	public Map<String, Boolean> activities;
	private String processDefinitionId;

	public ProcessCoverage(String processDefinitionId, Collection<String> availableActivities) {
		this.processDefinitionId = processDefinitionId;
		this.activities = availableActivities.stream()
				.collect(toMap(activityId -> activityId, activityId -> false));
	}

	public void visit(String activityId) {
		activities.put(activityId, true);
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void visit(List<String> visitedActivities) {
		for (String activityId : visitedActivities) {
			visit(activityId);
		}
	}
}