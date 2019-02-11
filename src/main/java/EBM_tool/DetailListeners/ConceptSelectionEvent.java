package EBM_tool.DetailListeners;

import java.util.EventObject;

import com.tom.EBM_RuleManager.Model.Relation;

public class ConceptSelectionEvent extends EventObject{
	/**
	 * Class to store event information related to a selected concept
	 */
	private static final long serialVersionUID = 7663606188968659198L;
	private String text;
	Relation relation;
	public ConceptSelectionEvent(Object source, String text, Relation relation){
        super(source);

        this.relation = relation;
        this.text = text;
    }

    public String getText(){
        return text;
    }
    
    public Relation getRelations(){
    	return relation;
    }
}
