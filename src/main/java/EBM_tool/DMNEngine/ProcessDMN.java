package EBM_tool.DMNEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionRuleResult;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;

public class ProcessDMN {
	
	public String getDecision(ArrayList<String> fields, ArrayList<String> fieldValues, InputStream inputStream, String decisionID) {

		if(inputStream == null) {
			return "error occured";
		}
		
		VariableMapImpl foo = new VariableMapImpl();
		for (int i = 0; i < fields.size(); i++) {
			foo.putValue(fields.get(i), fieldValues.get(i));
		}
		VariableMap variables = foo;

		// create a new default DMN engine
		DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

		String recommendation;

		try {
			DmnDecision decision = dmnEngine.parseDecision(decisionID, inputStream);

			// evaluate decision
			DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

			// get results
			recommendation = result.getSingleResult().getSingleEntry();
			//Object[] tmp = result.getResultList().get(0).values().toArray();//for multiple results?
			//System.out.println(tmp[0].toString());
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.err.println("Could not close stream: " + e.getMessage());
			}
		}

		return recommendation;
	}
	
}
