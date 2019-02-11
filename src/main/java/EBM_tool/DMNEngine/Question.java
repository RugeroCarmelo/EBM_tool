package EBM_tool.DMNEngine;

import java.util.ArrayList;

public class Question {
	/*
	 * This class is used to store information about a column (these have a title
	 * which tends to be a question) from a rule from a DMN file. The information is
	 * then used by the gui
	 * Currently only the type string is supported
	 */
	private String question = "";
	private ArrayList<String> strOptions = new ArrayList<>();// if the type is a string;
	private ArrayList<Integer> intOptions = new ArrayList<>();// if the type is a number
	private Type type;
	private String varName;
	private String decisionId;
	private String answer;
	private int answerIndex;
	private String ruleName;

	public enum Type {
		STRING, INTEGER, BOOLEAN, LONG, DOUBLE, DATE
	}

	public Question(String question, Type type, String varName, String decisionId, String ruleName) {
		this.question = question;
		this.type = type;
		this.varName = varName;
		this.decisionId = decisionId;
		this.ruleName = ruleName;
	}

	public static Type returnType(String strType) {
		Type tmp = Type.STRING;
		switch (strType) {
		case "integer":
			tmp = Type.INTEGER;
			break;
		case "string":
			tmp = Type.STRING;
			break;
		case "boolean":
			tmp = Type.BOOLEAN;
			break;
		case "long":
			tmp = Type.LONG;
			break;
		case "double":
			tmp = Type.DOUBLE;
			break;
		case "date":
			tmp = Type.DATE;
			break;
		default:
			System.out.println("ERROR");// TODO make this an throw statement
			break;
		}
		return tmp;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public void setStrOptions(ArrayList<String> options) {
		// clear all repeated values
		boolean repeated = false;
		for (int i = 0; i < options.size(); i++) {
			repeated = false;
			for (int j = 0; j < strOptions.size(); j++) {
				if (options.get(i).compareTo(strOptions.get(j)) == 0 || options.get(i).compareTo("") == 0
						|| options.size() == 0) {
					repeated = true;
				}
			}
			if (!repeated && options.get(i).length() > 0) {
				strOptions.add(options.get(i));
			}
		}
		setAnswer(strOptions.get(0));
	}

	public void setIntOptions(ArrayList<Integer> options) {
		intOptions = options;
	}

	public String getQuestion() {
		return question;
	}

	public ArrayList<String> getStrOptions() {
		if (type == Type.STRING) {
			return strOptions;
		} else {
			System.out.println("ERROR: not a string type");
			throw new ArithmeticException("error message");
		}
	}

	public ArrayList<Integer> getIntOptions() {
		if (type == Type.STRING) {
			return intOptions;
		} else {
			System.out.println("ERROR: not a string type");
			throw new ArithmeticException("error message");
		}
	}

	public String returnTypeAsString() {
		switch (type) {
		case STRING:
			return "string";
		case INTEGER:
			return "integer";
		case BOOLEAN:
			return "bolean";
		default:
			return "error";
		}
	}

	public String getVarName() {
		return varName;
	}

	public String getDecisionId() {
		return decisionId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		if (answer != null) {
			this.answer = answer;
			for (int i = 0; i < strOptions.size(); i++) {
				if (strOptions.get(i).compareTo(answer) == 0) {
					answerIndex = i;
				}
			}
		}
	}

	public String getRuleName() {
		return ruleName;
	}

	public int getAnswerIndex() {
		return answerIndex;
	}
}
