package com.tom.EBM_RuleManager.Model;

import java.io.Serializable;
import java.util.List;

public class RuleRelationSerial implements Serializable {
	private List<Rule> rules;
	private List<Relation> relations;
	private String info;
	private byte[] ontology;
	/**
	 * This classed is used to store the data that is Serialized and writen to a file or loaded from a file
	 * needs to be the same as the equivalend class in the protgege plugin so that the serialized data is loaded properly
	 */
	private static final long serialVersionUID = -5263862396186518446L;

	public RuleRelationSerial() {
		String info = "";

	}

	public byte[] getOntology() {
		return ontology;
	}

	public void setOntology(byte[] ontology) {
		this.ontology = ontology;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}

	public List<Relation> getRelations() {
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		this.relations = relations;
	}
}
