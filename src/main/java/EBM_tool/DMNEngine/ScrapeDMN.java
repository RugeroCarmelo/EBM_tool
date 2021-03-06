package EBM_tool.DMNEngine;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class ScrapeDMN {
	/*
	 * This class is used to get the information for the class Question from a DMN file with a rule
	 * Currently columns in the DMN file can only be of the type string
	 */
	private ArrayList<Question> questions = new ArrayList<Question>();
	
	public void interpreter(InputStream doc) {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		Document document = null;
		try {
			document = documentBuilder.parse(doc);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: input stream didn't work");
			return;
		}

		// find the label on the xml document
		NodeList tmpNodeList = document.getElementsByTagName("input");// the label of the component
		NodeList nodeList = document.getElementsByTagName("inputExpression");// assumes that the number of
																				// inputExpression elements is the same
																				// as the number of input elements
		NodeList optionsList = document.getElementsByTagName("rule");
		
		NodeList decisionIDNL = document.getElementsByTagName("decision");
		Node decisionIDN = decisionIDNL.item(0);
		Element decisionIDE = (Element) decisionIDN;

		// This for loop find the questions and the type of the column
		for (int i = 0; i < tmpNodeList.getLength(); i++) {
			Node nNode = tmpNodeList.item(i);
			Node node = nodeList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				Element element = (Element) node;

				String tmpQ = eElement.getAttribute("label");

				Question tmp = new Question(tmpQ, Question.returnType(element.getAttribute("typeRef")), getTagValue("text", element, 0), decisionIDE.getAttribute("id"), decisionIDE.getAttribute("name"));
				if(tmp.getType() == Question.Type.STRING) {
					getQuestionOptions(tmp, i, optionsList);
				}
				questions.add(tmp);
			}
		}
	}

	private static String getTagValue(String tag, Element element, int index) {
		NodeList nodeList = element.getElementsByTagName(tag).item(index).getChildNodes();
		Node node = (Node) nodeList.item(0);
		if (node != null) {
			return node.getNodeValue();
		} else {
			return "";
		}

	}

	private void getQuestionOptions(Question q, int index, NodeList nodeList) {// for string only
		ArrayList<String> tmpArray = new ArrayList<>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				tmpArray.add(getTagValue("text", element, index).replaceAll("\"", ""));//remove '"'
			}
		}
		q.setStrOptions(tmpArray);
	}
	
	public ArrayList<Question> getQuestions(){
		return questions;
	}
}
