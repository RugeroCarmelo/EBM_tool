package com.tom.EBM_RuleManager.Model;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Rule implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -775274536756044825L;
	private String id;
	private String ruleLocation;
	private String topic;
	private String ruleName;
	private String information;
	private byte[] dmn;
	//private File file;
	
	private ArrayList<String> ruleAnswers = new ArrayList<>();//***
	private String recommendation;//***

	
	public Rule(String ruleLocation, String topic, String ruleName, String info, String ID, byte[] dmn) {
		
		information = info;
		this.ruleLocation = ruleLocation;
		this.topic = topic;
		this.ruleName = ruleName;
		this.id = ID;
		this.dmn = dmn;
		
		recommendation = "";//*
	}
	
	public void setInformation(String information) {
		this.information = information;
	}
	
	public String getInformation() {
		return information;
	}
	
	public String getRuleName() {
		return ruleName;
	}
	
	public String getTopic() {
		return topic;
	}
	
	
	public String getRuleLocation() {
		return ruleLocation;
	}
	
	public String getID() {
		return id;
	}
	
	public byte[] getFile(){
		return dmn;
	}
	
	//____________________________________
	
	public void setAnswers(ArrayList<String> ruleAnswers) {
		this.ruleAnswers = ruleAnswers;
	}
	
	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}
	
	public String getRecommendation() {
		return recommendation;
	}
	
	public ArrayList<String> getAnswers() {
		return ruleAnswers;
	}
	
	public String getAnswer(int index) {
		if (index < ruleAnswers.size()) {
			return ruleAnswers.get(index);
		}else{
			//System.out.println("ERROR in CR");//check that this behaves the way it is supposed to
			return null;//this should be a throw statement
		}
	}
}