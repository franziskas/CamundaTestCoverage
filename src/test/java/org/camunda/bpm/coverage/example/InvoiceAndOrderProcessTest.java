package org.camunda.bpm.coverage.example;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;

public class InvoiceAndOrderProcessTest extends AbstractProcessTest {

	private static final String ORDER_BPMN = "order.bpmn";

	private static final String OTHER_PROCESS = "order";
	private static final String INVOICE_BPMN = "invoice.bpmn";

	private static final String INVOICE_PROCESS = "invoice-process";

	@Test
	@Deployment(resources = ORDER_BPMN)
	public void testPathA() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("path", "A");
		processEngineRule.getRuntimeService().startProcessInstanceByKey(OTHER_PROCESS, variables);
	}

	@Test
	@Deployment(resources = INVOICE_BPMN)
	public void testInvoicePathA() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("path", "A");
		processEngineRule.getRuntimeService().startProcessInstanceByKey(INVOICE_PROCESS, variables);
	}
}
