package com.tom.EBM_RuleManager.Model;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RuleRelationSerial implements Serializable {
	private List<Rule> rules;
	private List<Relation> relations;
	private String info;
	private byte[] ontology;
	/**
	 * 
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
		/*
		 * System.out.println("get"); for(int i = 0; i < relations.size(); i++) {
		 * System.out.println(relations.get(i).getConceptName()); }
		 */
		return relations;
	}

	public void setRelations(List<Relation> relations) {
		// System.out.println("set");
		this.relations = relations;
		/*
		 * for(int i = 0; i < this.relations.size(); i++) {
		 * System.out.println(this.relations.get(i).getConceptName()); }
		 */
	}
}
