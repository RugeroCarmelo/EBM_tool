package EBM_tool.DMNEngine;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DescriptionGetter {

	/*
	 * This class gets information from the xml (rdf owl format) ontology file,
	 * currently it has function to facilitate getting comments definitions and
	 * references
	 */
	private Document document;

	public DescriptionGetter(Document document) {
		this.document = document;
	}

	/*
	 * gets information from an xml file
	 */
	public ArrayList<String> interpreter(String className, String tagName, String attributeName) {
		if (document == null) {
			return null;
		} else {
			ArrayList<String> descriptions = new ArrayList<>();
			NodeList classNodes = document.getElementsByTagName(tagName);// get the Elements by the tag name
			for (int i = 0; i < classNodes.getLength(); i++) {
				Node classNode = classNodes.item(i);
				if (classNode.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) classNode;
					String name = filteredName(element.getAttribute("rdf:about"));// rdf:about contains the name of a
																					// class
					if (className.equals(name)) {
						NodeList tmpList = element.getElementsByTagName(attributeName);
						for (int j = 0; j < tmpList.getLength(); j++) {
							descriptions.add(tmpList.item(j).getTextContent());
						}
						return descriptions;
					}
				}
			}
			return null;
		}
	}

	// method to make getting certain information more convenient
	public ArrayList<String> getComment(String className) {
		return interpreter(className, "owl:Class", "rdfs:comment");
	}

	// method to make getting certain information more convenient
	public ArrayList<String> getDefinition(String className) {
		return interpreter(className, "owl:Class", "ontologies:definition");
	}

	// method to make getting certain information more convenient
	public ArrayList<String> getReferences(String className) {
		return interpreter(className, "owl:Class", "ontologies:reference");
	}

	// most of the time a class name contains a URL with mostly useless information,
	// this method removes the useless information and leaves just the class name as
	// it apear in the gui
	private String filteredName(String unfilteredName) {
		String name = unfilteredName;
		name = name.substring(name.lastIndexOf("#") + 1);
		return name;
	}

}
