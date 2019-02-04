package EBM_tool.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.tom.EBM_RuleManager.Model.Relation;

import EBM_tool.DetailListeners.RecommendationChangeEvent;
import EBM_tool.DetailListeners.RecommendationChangeListener;

public class DetailPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6949183290895349273L;

	private JPanel pane;
	private JScrollPane scroll;
	private JLabel label;

	public DetailPanel(Dimension dim, Relation CRR, org.w3c.dom.Document document) {
		this.label = new JLabel(CRR.getConceptName());
		initialize(dim, CRR, document);
	}

	public void setLabel(String label) {
		this.label.setText(label);
	}

	public void initialize(Dimension dim, Relation CRR, org.w3c.dom.Document document) {
		pane = new JPanel();
		pane.setPreferredSize(dim);
		pane.setBackground(new Color(254, 254, 254));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		pane.add(label);
		int localHeight = 0;

		/////// Definitions//////
		DescriptionDisplay definitions = new DescriptionDisplay(CRR.getConceptName(), document,
				DescriptionDisplay.Type.DEFINITION);
		pane.add(definitions);
		localHeight += definitions.getTotalHeight();
		///////

		if (CRR.size() > 0) {
			for (int i = 0; i < CRR.size(); i++) {
				if (CRR.getRuleAtIndex(i).getFile() != null) {
					RuleDisplayPane tmp = new RuleDisplayPane(CRR.getRuleAtIndex(i));
					tmp.addRecommendationChangeListener(new RecommendationChangeListener() {
						public void recommendationChangeEventOcurred(RecommendationChangeEvent event) {
							fireRecommendationChangeEvent(event);
						}
					});
					pane.add(tmp);
					localHeight += tmp.getTotalHeight();
				}
			}
		} else {
			RuleDisplayPane tmp = new RuleDisplayPane();
			pane.add(tmp);
			localHeight += tmp.getTotalHeight();
		}

		/////// Comment/////////
		DescriptionDisplay comments = new DescriptionDisplay(CRR.getConceptName(), document,
				DescriptionDisplay.Type.COMMENT);
		pane.add(comments);
		localHeight += comments.getTotalHeight();
		///////

		/////// Refrences//////
		DescriptionDisplay references = new DescriptionDisplay(CRR.getConceptName(), document,
				DescriptionDisplay.Type.REFERENCE);
		pane.add(references);
		localHeight += references.getTotalHeight();
		///////

		pane.setPreferredSize(new Dimension(500, localHeight + 20));// TODO: Calculate these numbers better;
		scroll = new JScrollPane(pane);

		if (dim.getHeight() > localHeight + 30) {
			scroll.setPreferredSize(new Dimension((int) dim.getWidth(), localHeight + 30));
		} else {
			scroll.setPreferredSize(new Dimension((int) dim.getWidth(), (int) dim.getHeight() - 21));
		}

		scroll.setBackground(new Color(254, 254, 254));
		setPreferredSize(dim);

		add(scroll, BorderLayout.CENTER);
	}
	
	public void fireRecommendationChangeEvent(RecommendationChangeEvent event) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == RecommendationChangeListener.class) {
				((RecommendationChangeListener) listeners[i + 1]).recommendationChangeEventOcurred(event);
			}
		}
	}

	public void addRecommendationChangeListener(RecommendationChangeListener listener) {
		listenerList.add(RecommendationChangeListener.class, listener);
	}

	public void removeRecommendationChangeListener(RecommendationChangeListener listener) {
		listenerList.remove(RecommendationChangeListener.class, listener);
	}

}
