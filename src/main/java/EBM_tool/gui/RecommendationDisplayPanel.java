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
import javax.swing.JTextArea;

public class RecommendationDisplayPanel extends JPanel {
	private JLabel label;
	private JPanel recPanel;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	public RecommendationDisplayPanel(String recommendation) {
		label = new JLabel("Recommendation: ");
		label.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 0));
		recPanel = new JPanel();
		setPreferredSize(new Dimension(500, 200));
		textArea = new JTextArea(3, 36);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setRecommendation(recommendation);
		scrollPane = new JScrollPane(textArea);
		recPanel.add(scrollPane);
		recPanel.setPreferredSize(new Dimension(415, 58));
		recPanel.setBackground(new Color(254, 254, 254));

		setLayout(new GridBagLayout());
		setBackground(new Color(254, 254, 254));
		
		GridBagConstraints gc = new GridBagConstraints();

		/// first column
		gc.anchor = GridBagConstraints.LINE_START;
		gc.weightx = 0.5;
		gc.weighty = 0.5;

		gc.gridx = 0;
		gc.gridy = 0;
		add(label, gc);

		gc.anchor = GridBagConstraints.LINE_END;
		gc.weightx = 30;
		gc.gridwidth = 8;
		gc.gridx = 1;
		gc.gridy = 0;
		add(recPanel, gc);
	}
	
	public void setRecommendation(String recommendation) {
		textArea.setText(recommendation);
	}
}
