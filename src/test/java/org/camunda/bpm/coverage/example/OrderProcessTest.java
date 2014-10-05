package org.camunda.bpm.coverage.example;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;

public class OrderProcessTest extends AbstractProcessTest {

	private static final String ORDER_BPMN = "order.bpmn";

	private static final String OTHER_PROCESS = "order";

	@Test
	@Deployment(resources = ORDER_BPMN)
	public void testPathB() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("path", "B");
		processEngineRule.getRuntimeService().startProcessInstanceByKey(OTHER_PROCESS, variables);
	}
}
