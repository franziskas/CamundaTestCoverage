package org.camunda.bpm.coverage;

import java.util.Collection;
import java.util.List;

import org.camunda.bpm.engine.repository.ProcessDefinition;

public interface ProcessCoverageCapture {

	boolean definitionIsMissing(ProcessDefinition processDefinition);

	void addDefinitionWithActivities(ProcessDefinition processDefinition, Collection<String> availableActivities);

	void visit(ProcessDefinition processDefinition, List<String> visitedActivities);
}