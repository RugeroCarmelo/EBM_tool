package EBM_tool.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

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
		label.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		initialize(dim, CRR, document);
	}

	public void setLabel(String label) {
		this.label.setText(label);
	}

	public void initialize(Dimension dim, Relation CRR, org.w3c.dom.Document document) {
		pane = new JPanel();
		Color defaultColor = pane.getBackground();
		//pane.setPreferredSize(dim);
		//pane.setBackground(new Color(254, 254, 254));
		// pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
		pane.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.anchor = GridBagConstraints.PAGE_START;
		gc.weightx = 0.1;
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy++;
		pane.add(label, gc);

		pane.add(label);
		int localHeight = 0;

		/////// Definitions//////
		DescriptionDisplay definitions = new DescriptionDisplay(CRR.getConceptName(), document,
				DescriptionDisplay.Type.DEFINITION);
		// pane.add(definitions);
		gc.anchor = GridBagConstraints.CENTER;
		gc.weightx = 0.1;
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy++;
		pane.add(definitions, gc);

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
					// pane.add(tmp);
					// new row
					gc.gridy++;
					gc.weightx = 1;
					gc.weighty = 0.2;
					gc.gridx = 0;
					gc.anchor = GridBagConstraints.CENTER;
					pane.add(tmp, gc);

					localHeight += tmp.getTotalHeight();
				}
			}
		} else {
			RuleDisplayPane tmp = new RuleDisplayPane();
			// pane.add(tmp);
			// new row
			gc.gridy++;
			gc.weightx = 1;
			gc.weighty = 0.2;
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.CENTER;
			pane.add(tmp, gc);

			localHeight += tmp.getTotalHeight();
		}

		/////// Comment/////////
		DescriptionDisplay comments = new DescriptionDisplay(CRR.getConceptName(), document,
				DescriptionDisplay.Type.COMMENT);
		// pane.add(comments);
		// new row
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		pane.add(comments, gc);

		localHeight += comments.getTotalHeight();
		///////

		/////// Refrences//////
		DescriptionDisplay references = new DescriptionDisplay(CRR.getConceptName(), document,
				DescriptionDisplay.Type.REFERENCE);
		// pane.add(references);
		// new row
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 10;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		pane.add(references, gc);
		
		localHeight += references.getTotalHeight();
		///////

		pane.setPreferredSize(new Dimension(500, localHeight));// TODO: Calculate these numbers better;
		scroll = new JScrollPane(pane);

		if (dim.getHeight() > localHeight + 30) {
			// scroll.setPreferredSize(new Dimension((int) dim.getWidth(), localHeight +
			// 30));
		} else {
			// scroll.setPreferredSize(new Dimension((int) dim.getWidth(), (int)
			// dim.getHeight() - 21));
		}

		scroll.setBackground(new Color(254, 254, 254));
		Border border = scroll.getBorder();
		Border emptyBorder = BorderFactory.createMatteBorder(5, 0, 0, 0, defaultColor);
		scroll.setBorder(BorderFactory.createCompoundBorder(emptyBorder, border));
		setPreferredSize(dim);

		setLayout(new BorderLayout());
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
