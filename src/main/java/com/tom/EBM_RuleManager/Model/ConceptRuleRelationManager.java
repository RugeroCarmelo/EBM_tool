package com.tom.EBM_RuleManager.Model;
import java.util.ArrayList;
import java.util.List;

public class ConceptRuleRelationManager {
	private ArrayList<Relation> relations = new ArrayList<Relation>();

	public ConceptRuleRelationManager() {
	}

	public void add(Relation CRR) {
		if (findName(CRR.getConceptName()) == -1) {
			relations.add(CRR);
		}
	}

	public void setRelations(List<Relation> c) {
		relations.clear();
		relations.addAll(c);
		//relations = c;
	}
	
	public ArrayList<Relation> getRelations(){
		return relations;
	}

	public int size() {
		return relations.size();
	}
	
	public ArrayList<Rule> getRules(String name) {
		int tmp = findName(name);
		if(tmp >= 0) {
			return relations.get(tmp).getRules();
		}else {
			return new ArrayList<Rule>();
		}
	}
	
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
	
	public ArrayList<Rule> getAllConceptRules(){
		ArrayList<Rule> conceptRules = new ArrayList<Rule>();
		Relation CRR;
		for(int i = 0; i < relations.size(); i++) {
			CRR = relations.get(i);
			for(int j = 0; j < CRR.size(); j++) {
				if(!conceptRules.contains(CRR.getRuleAtIndex(j))) {
					conceptRules.add(CRR.getRuleAtIndex(j));
				}
			}
		}
		return conceptRules;
	}
	
	public ArrayList<String> getAllTopics(){
		ArrayList<String> topics = new ArrayList<>();
		ArrayList<Rule> conceptRules = getAllConceptRules();
		for(int i = 0; i < conceptRules.size(); i++) {
			if(findIndex(topics, conceptRules.get(i).getTopic()) == -1) {
				topics.add(conceptRules.get(i).getTopic());
			}
		}
		return topics;
	}
	
	public ArrayList<Rule> getRulesWithTopic(String topic){//TODO finish this
		ArrayList<Rule> conceptRules = getAllConceptRules();
		ArrayList<Rule> tmpConceptRules = new ArrayList<Rule>();
		for(int i = 0; i < conceptRules.size(); i++) {
			if( conceptRules.get(i).equals(topic) && (!tmpConceptRules.contains(conceptRules.get(i)))) {
				tmpConceptRules.add(conceptRules.get(i));
			}
		}
		
		return conceptRules;
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
