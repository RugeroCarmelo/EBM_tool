package EBM_tool.DetailListeners;
import java.util.EventObject;

public class DetailEvent extends EventObject {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6109786854296270659L;
	private String text;
    public DetailEvent(Object source, String text){
        super(source);//this calls the constructor of the parent class in this case EventObject //If you wanted to call a function from the parent use super.NameOfFunction();

        this.text = text;
    }

    public String getText(){
        return text;
    }
}
