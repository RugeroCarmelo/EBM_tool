package EBM_tool.DetailListeners;
import java.util.EventObject;

public class TabChangeEvent extends EventObject {
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