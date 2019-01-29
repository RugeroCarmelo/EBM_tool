package EBM_tool.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import EBM_tool.DMNEngine.DescriptionGetter;

public class DescriptionDisplay extends JPanel {
	private JPanel pane;
	private int numberOfLabels = 0;
	private int totalHeight = 0;
	private JLabel annotationType;

	enum Type {
		COMMENT, REFERENCE, DEFINITION;
	}

	public DescriptionDisplay(String name, org.w3c.dom.Document document, Type type) {
		DescriptionGetter DG = new DescriptionGetter(document);//
		ArrayList<String> list = null;
		String strType = "Coment";
		if (type == Type.COMMENT) {
			list = DG.getComment(name);
			strType = "Comment";
			if (list != null && list.size() > 0) {
				initialize(list, strType);
			}
		} else if (type == Type.REFERENCE) {
			list = DG.getReferences(name);
			strType = "Reference";
			if (list != null && list.size() > 0) {
				initialize(list, strType);
			}
		} else if (type == Type.DEFINITION) {
			list = DG.getDefinition(name);
			strType = "Definition";
			if (list != null && list.size() > 0) {
				initialize(list, strType);
			} else {
				initializeNoDesc();
			}
		}
	}

	private void initialize(ArrayList<String> list, String type) {
		if (list.size() > 1) {
			annotationType = new JLabel(type + "s");
		} else {
			annotationType = new JLabel(type);
		}
		pane = new JPanel();
		pane.setPreferredSize(new Dimension(550, 300));
		pane.setBackground(new Color(254, 254, 254));

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		annotationType.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		pane.add(annotationType);

		// getTheQuestions(CR);
		for (int i = 0; i < list.size(); i++) {
			JLabel comp = new JLabel(MakeHTMLText.HTML_with_P_tags(list.get(i)));

			//
			Border insideBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);
			if (i > 0) {
				insideBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
			}
			Border outsideBorder = BorderFactory.createEmptyBorder(0, 8, 0, 8);
			comp.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
			//

			pane.add(comp);
			numberOfLabels = numberOfLabels + list.get(i).length() / 150 + 1;// TODO: There has to be a better way of
																				// doing this
		}

		pane.setPreferredSize(new Dimension(550, CalculateHeightBasedOnNumberOfLabels(numberOfLabels)));
		setSize(new Dimension(550, CalculateHeightBasedOnNumberOfLabels(numberOfLabels)));
		Border placardBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);

		pane.setBorder(placardBorder);
		add(pane);
	}

	private int CalculateHeightBasedOnNumberOfLabels(int length) {
		int height = numberOfLabels * 37 + 10;
		totalHeight = height + 20;
		return height;
	}

	protected int getTotalHeight() {
		return totalHeight;
	}

	private void initializeNoDesc() {
		annotationType = new JLabel("Definition");
		pane = new JPanel();
		pane.setPreferredSize(new Dimension(550, 300));
		pane.setBackground(new Color(254, 254, 254));

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		annotationType.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
		pane.add(annotationType);

		// getTheQuestions(CR);
		JLabel comp = new JLabel("There is no definition to display");
		Border insideBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);

		Border outsideBorder = BorderFactory.createEmptyBorder(0, 8, 0, 8);
		comp.setBorder(BorderFactory.createCompoundBorder(outsideBorder, insideBorder));
		pane.add(comp);
		numberOfLabels++;

		pane.setPreferredSize(new Dimension(550, CalculateHeightBasedOnNumberOfLabels(numberOfLabels)));
		setSize(new Dimension(550, CalculateHeightBasedOnNumberOfLabels(numberOfLabels)));
		Border placardBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);

		pane.setBorder(placardBorder);
		add(pane);
	}
}
