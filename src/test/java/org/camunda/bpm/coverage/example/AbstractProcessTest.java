package org.camunda.bpm.coverage.example;

import static org.junit.rules.RuleChain.outerRule;

import org.camunda.bpm.coverage.ProcessTestCoverageReportRule;
import org.camunda.bpm.coverage.ProcessTestCoverageRule;
import org.camunda.bpm.coverage.XmlCoverageReportGenerator;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.ProcessEngineAssertions;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.RuleChain;

public class AbstractProcessTest {

	@ClassRule
	public static ProcessTestCoverageReportRule processTestCoverageReportRule = new ProcessTestCoverageReportRule(
				new XmlCoverageReportGenerator("target/surefire-reports"));
	protected ProcessEngineRule processEngineRule = new ProcessEngineRule();
	private ProcessTestCoverageRule processTestCoverageRule = new ProcessTestCoverageRule(processTestCoverageReportRule);
	@Rule
	public RuleChain ruleChain = outerRule(processEngineRule).around(processTestCoverageRule);

	public AbstractProcessTest() {
		super();
	}

	@Before
	public void setUp() throws Exception {
		ProcessEngineAssertions.init(processEngineRule.getProcessEngine());
	
		processTestCoverageRule.setProcessEngine(processEngineRule.getProcessEngine());
	}

}