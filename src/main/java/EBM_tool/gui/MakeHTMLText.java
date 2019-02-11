package EBM_tool.gui;

public class MakeHTMLText {
	/*
	 * simple class to add html tags to test which is used in JLabels so that they
	 * text automatically flows to the next line after a line becomes to long
	 * 
	 */
	public static String HTML_with_P_tags(String text) {
		String tmp = "<html><p>" + text + "</p></html>";// the <p> is supposed to make the break occur whenever it is
														// needed
		return tmp;
	}
}
