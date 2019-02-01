package EBM_tool.DetailListeners;

import java.util.ArrayList;
import java.util.EventObject;

import com.tom.EBM_RuleManager.Model.Relation;
import com.tom.EBM_RuleManager.Model.Rule;

public class ConceptSelectionEvent extends EventObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7663606188968659198L;
	private String text;
	Relation relation;
	public ConceptSelectionEvent(Object source, String text, Relation relation){
        super(source);//this calls the constructor of the parent class in this case EventObject //If you wanted to call a function from the parent use super.NameOfFunction();

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
