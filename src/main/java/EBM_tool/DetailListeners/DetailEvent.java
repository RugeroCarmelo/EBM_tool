package EBM_tool.DetailListeners;
import java.util.EventObject;

public class DetailEvent extends EventObject {
    /**
	 * Class used to store text related to an event
	 */
	private static final long serialVersionUID = -6109786854296270659L;
	private String text;
    public DetailEvent(Object source, String text){
        super(source);

        this.text = text;
    }

    public String getText(){
        return text;
    }
}
