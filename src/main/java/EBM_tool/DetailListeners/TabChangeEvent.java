package EBM_tool.DetailListeners;
import java.util.EventObject;

public class TabChangeEvent extends EventObject {
    /**
	 * Class used to store the tab and text related to a tab change event
	 */
	private static final long serialVersionUID = 8992759147332995812L;
	private String text;
    private int tabNum;
    public TabChangeEvent(Object source, String text, int tabNum){
        super(source);//this calls the constructor of the parent class in this case EventObject //If you wanted to call a function from the parent use super.NameOfFunction();

        this.text = text;
        this.tabNum = tabNum;
    }

    public String getText(){
        return text;
    }
    
    public int getTabNum() {
    	return tabNum;
    }
}