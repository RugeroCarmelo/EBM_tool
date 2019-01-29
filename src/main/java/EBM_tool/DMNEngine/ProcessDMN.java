package EBM_tool.DMNEngine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.Variables;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;

public class ProcessDMN {
	private static ArrayList<String> fields = new ArrayList<String>();
	private static ArrayList<String> fieldValues = new ArrayList<String>();
	private static String decision = "Not calculated yet";

	public static void main(String[] args) {
		fields.add("budget");
		fields.add("expert");
		fields.add("risk");
		fields.add("commitment");

		fieldValues.add("L");
		fieldValues.add("A");
		fieldValues.add("H");
		fieldValues.add("H");

		VariableMapImpl foo = new VariableMapImpl();

		for (int i = 0; i < fields.size(); i++) {
			foo.putValue(fields.get(i), fieldValues.get(i));
		}

		VariableMap variables = foo;

		// create a new default DMN engine
		DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

		// parse decision from resource input stream
		InputStream inputStream = ProcessDMN.class.getResourceAsStream("diagram_1.dmn");

		// System.out.print(inputStream. + "\n");

		try {
			DmnDecision decision = dmnEngine.parseDecision("Decision_13nychf", inputStream);

			// evaluate decision
			DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

			// print result
			String recommendation = result.getSingleResult().getSingleEntry();
			System.out.println("Recommendation: " + recommendation);

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.err.println("Could not close stream: " + e.getMessage());
			}
		}

	}

	public String getDecision(ArrayList<String> fields, ArrayList<String> fieldValues, String resourceName, String decisionID) {// TODO finish this function

		VariableMapImpl foo = new VariableMapImpl();

		for (int i = 0; i < fields.size(); i++) {
			//System.out.println(fields.size());
			foo.putValue(fields.get(i), fieldValues.get(i));
		}

		VariableMap variables = foo;

		// create a new default DMN engine
		DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

		// parse decision from resource input stream
		InputStream inputStream = ProcessDMN.class.getResourceAsStream(resourceName);//diagram_1.dmn

		// System.out.print(inputStream. + "\n");
		String recommendation;

		try {
			DmnDecision decision = dmnEngine.parseDecision(decisionID, inputStream);//Decision_13nychf

			// evaluate decision
			DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

			// print result
			recommendation = result.getSingleResult().getSingleEntry();
			//System.out.println("Recommendation: " + recommendation);

		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				System.err.println("Could not close stream: " + e.getMessage());
			}
		}

		return recommendation;
	}
	
	public String getDecision(ArrayList<String> fields, ArrayList<String> fieldValues, File file, String decisionID) {// TODO finish this function

		InputStream inputStream;
		try {
			inputStream = new FileInputStream(file);
			
			VariableMapImpl foo = new VariableMapImpl();

			for (int i = 0; i < fields.size(); i++) {
				//System.out.println(fields.size());
				foo.putValue(fields.get(i), fieldValues.get(i));
			}

			VariableMap variables = foo;

			// create a new default DMN engine
			DmnEngine dmnEngine = DmnEngineConfiguration.createDefaultDmnEngineConfiguration().buildEngine();

			String recommendation;

			try {
				DmnDecision decision = dmnEngine.parseDecision(decisionID, inputStream);//Decision_13nychf

				// evaluate decision
				DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, variables);

				// print result
				recommendation = result.getSingleResult().getSingleEntry();
				//System.out.println("Recommendation: " + recommendation);

			} finally {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.err.println("Could not close stream: " + e.getMessage());
				}
			}

			return recommendation;
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return "Error occured";
	}
	
}
