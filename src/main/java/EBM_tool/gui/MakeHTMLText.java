package EBM_tool.gui;

public class MakeHTMLText {
	public static String HTML_with_P_tags(String text) {
		String tmp = "<html><p>" + text + "</p></html>";//the <p> is supposed to make the break occur whenever it is needed
		return tmp;
	}
}
