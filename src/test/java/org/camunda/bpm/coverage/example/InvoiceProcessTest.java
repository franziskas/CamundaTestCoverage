package org.camunda.bpm.coverage.example;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.engine.test.Deployment;
import org.junit.Test;

public class InvoiceProcessTest extends AbstractProcessTest {

	private static final String INVOICE_BPMN = "invoice.bpmn";

	private static final String INVOICE_PROCESS = "invoice-process";

	@Test
	@Deployment(resources = INVOICE_BPMN)
	public void testInvoicePathB() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("path", "B");
		processEngineRule.getRuntimeService().startProcessInstanceByKey(INVOICE_PROCESS, variables);
	}
}
