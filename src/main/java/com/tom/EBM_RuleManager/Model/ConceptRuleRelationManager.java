package com.tom.EBM_RuleManager.Model;
import java.util.ArrayList;
import java.util.List;

public class ConceptRuleRelationManager {
	private ArrayList<Relation> relations;

	public ConceptRuleRelationManager() {
		relations = new ArrayList<Relation>();
	}

	public void add(Relation CRR) {
		if (findName(CRR.getConceptName()) == -1) {//check if this relation already exists
			relations.add(CRR);
		}
	}

	public void setRelations(List<Relation> c) {
		relations.clear();
		relations.addAll(c);
	}
	
	public ArrayList<Relation> getRelations(){
		return relations;
	}

	public int size() {
		return relations.size();
	}
	
	/*
	 * returns a list of rules for the concept
	 */
	public ArrayList<Rule> getRules(String name) {
		int tmp = findName(name);
		if(tmp >= 0) {
			return relations.get(tmp).getRules();
		}else {
			return new ArrayList<Rule>();
		}
	}
	
	/*
	 * returns relation for the concept
	 */
	public Relation getRelation(String name) {
		int tmp = findName(name);
		if(tmp >= 0) {
			return relations.get(tmp);
		}else {//no relations found for this concept
			Relation tmpRelation = new Relation(name);
			return tmpRelation;
		}
		
	}
	
	public Relation getRelation(int index) {
		return relations.get(index);
	}

	public boolean containsName(String name) {
		for (int i = 0; i < relations.size(); i++) {
			if (relations.get(i).getConceptName().compareTo(name) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public int findName(String name) {
		for (int i = 0; i < relations.size(); i++) {
			if (relations.get(i).getConceptName().compareTo(name) == 0) {
				return i;
			}
		}
		return -1;
	}

	public boolean containsRule(String rule) {
		for (int i = 0; i < relations.size(); i++) {
			if (relations.get(i).getConceptName().compareTo(rule) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Rule> getAllRules(){
		ArrayList<Rule> rules = new ArrayList<Rule>();
		Relation CRR;
		for(int i = 0; i < relations.size(); i++) {
			CRR = relations.get(i);
			for(int j = 0; j < CRR.size(); j++) {
				if(!rules.contains(CRR.getRuleAtIndex(j))) {
					rules.add(CRR.getRuleAtIndex(j));
				}
			}
		}
		return rules;
	}
	
	public ArrayList<String> getAllTopics(){
		ArrayList<String> topics = new ArrayList<>();
		ArrayList<Rule> conceptRules = getAllRules();
		for(int i = 0; i < conceptRules.size(); i++) {
			if(findIndex(topics, conceptRules.get(i).getTopic()) == -1) {
				topics.add(conceptRules.get(i).getTopic());
			}
		}
		return topics;
	}
	
	public ArrayList<Rule> findRules(String topic){
		ArrayList<Rule> conceptRules = getAllRules();
		ArrayList<Rule> tmpConceptRules = new ArrayList<Rule>();
		for(int i = 0; i < conceptRules.size(); i++) {
			if( conceptRules.get(i).getTopic().equals(topic) && (!tmpConceptRules.contains(conceptRules.get(i)))) {
				tmpConceptRules.add(conceptRules.get(i));
			}
		}
		return tmpConceptRules;
	}
	
	public ArrayList<Rule> findRules(ArrayList<Rule> list, String recommendation){
		ArrayList<Rule> tmpRules = new ArrayList<Rule>();
		for(int i = 0; i < list.size(); i++) {
			if( list.get(i).getRecommendation().equals(recommendation) && (!tmpRules.contains(list.get(i)))) {
				tmpRules.add(list.get(i));
			}
		}
		return tmpRules;
	}
	
	public ArrayList<Rule> findRules(ArrayList<Rule> list, String recommendation, String name){
		ArrayList<Rule> tmpRules = new ArrayList<Rule>();
		for(int i = 0; i < list.size(); i++) {
			if( list.get(i).getRecommendation().equals(recommendation) && list.get(i).getRuleName().equals(name) && (!tmpRules.contains(list.get(i)))) {
				tmpRules.add(list.get(i));
			}
		}
		return tmpRules;
	}
	
	private int findIndex(ArrayList<String> list, String term) {
		for(int i = 0; i < list.size(); i++) {
			if(list.get(i).equals(term)) {
				return i;
			}
		}
		return -1;
	}
}
