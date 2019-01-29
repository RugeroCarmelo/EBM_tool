package com.tom.EBM_RuleManager.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Relation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7507976646470752711L;
	private static int countID;
	private int ID;
	private String conceptName;
	private ArrayList<Rule> rules;
	
	public Relation(String conceptName) {
		this.conceptName = conceptName;
		rules = new ArrayList<Rule>();
	}
	
	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	public String getConceptName() {
		return conceptName;
	}
	
	public ArrayList<Rule> getRules(){
		return rules;
	}
	
	public Rule getRule(int index) {
		return rules.get(index);
	}
	
	public int size() {
		return rules.size();
	}
	
	public int getID() {
		return ID;
	}
	
	public Rule getRuleAtIndex(int index) {
		return rules.get(index);
	}
	
	public String getRuleNames() {
		String tmp = "";
		for(int i = 0; i < rules.size(); i++) {
			tmp += rules.get(i).getRuleName() + ", ";
		}
		return tmp;
	}
	
	public void setRules(ArrayList<Rule> rules) {
		this.rules = rules;
	}

}
