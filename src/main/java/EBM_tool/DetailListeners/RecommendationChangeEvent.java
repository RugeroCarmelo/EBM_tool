package EBM_tool.DetailListeners;

import java.util.EventObject;

public class RecommendationChangeEvent extends EventObject{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3608332913791900423L;
	private String text;
    public RecommendationChangeEvent(Object source, String text){
        super(source);

        this.text = text;
    }

    public String getText(){
        return text;
    }
}
