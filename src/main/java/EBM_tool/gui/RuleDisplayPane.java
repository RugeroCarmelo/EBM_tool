package EBM_tool.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.EventListenerList;

import com.tom.EBM_RuleManager.Model.Rule;

import EBM_tool.DMNEngine.Question;
import EBM_tool.DMNEngine.ScrapeDMN;
import EBM_tool.DMNEngine.ProcessDMN;
import EBM_tool.DetailListeners.DetailEvent;
import EBM_tool.DetailListeners.DetailListener;
import EBM_tool.DetailListeners.RecommendationChangeEvent;
import EBM_tool.DetailListeners.RecommendationChangeListener;

public class RuleDisplayPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7447078625615369571L;

	private ArrayList<QuestionSelect> questionSelects = new ArrayList<QuestionSelect>();
	private JPanel pane;
	private EventListenerList listenerList = new EventListenerList();
	private int numberOfQuestions = 0;
	private int totalHeight = 0;
	private RecommendationDisplayPanel RDP;
	private JPanel title;

	private JLabel ruleName;
	ArrayList<Question> questions;
	private JButton infoBtn;

	public RuleDisplayPane(Rule CR) {
		RDP = new RecommendationDisplayPanel("");
		ruleName = new JLabel("");
		initialize(CR);
		// formatComponents();
	}

	public void formatComponents() {
		GridBagConstraints gc = new GridBagConstraints();
		pane = new JPanel();
		pane.setPreferredSize(new Dimension(550, 300));
		pane.setBackground(new Color(254, 254, 254));

		pane.setLayout(new GridBagLayout());

		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		gc.weightx = 0.1;
		gc.weighty = 0.1;
		gc.gridx = 0;
		gc.gridy = 0;
		pane.add(title, gc);

		for (int i = 0; i < questionSelects.size(); i++) {
			// new row
			gc.gridy++;
			gc.weightx = 1;
			gc.weighty = 0.2;
			gc.gridx = 0;
			gc.anchor = GridBagConstraints.FIRST_LINE_START;
			pane.add(questionSelects.get(i), gc);
		}

		// new row
		gc.gridy++;
		gc.weightx = 1;
		gc.weighty = 0.2;
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		pane.add(RDP, gc);

		CalculateHeightBasedOnNumberOfQuestions(numberOfQuestions);
		// CalculateHeightBasedOnNumberOfQuestions(numberOfQuestions)));
		// setSize(new Dimension(550,
		// CalculateHeightBasedOnNumberOfQuestions(numberOfQuestions)));
		Border placardBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);

		pane.setBorder(placardBorder);

		add(pane);
	}

	public RuleDisplayPane() {
		ruleName = new JLabel("No rules to display");
		pane = new JPanel();
		pane.setPreferredSize(new Dimension(550, 300));
		pane.setBackground(new Color(254, 254, 254));
		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		ruleName.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

		pane.add(ruleName);
		pane.setPreferredSize(new Dimension(550, CalculateHeightBasedOnNumberOfQuestions(numberOfQuestions)));
		setSize(new Dimension(550, CalculateHeightBasedOnNumberOfQuestions(numberOfQuestions)));
		Border placardBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);

		pane.setBorder(placardBorder);
		add(pane);
	}

	public void initialize(final Rule CR) {
		pane = new JPanel();
		pane.setPreferredSize(new Dimension(550, 300));
		pane.setBackground(new Color(254, 254, 254));

		pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

		////////////// Information button/////////////////
		infoBtn = new JButton("i");
		infoBtn.setToolTipText("information about the rule");
		if (CR.getInformation().length() == 0) {
			infoBtn.setEnabled(false);
		}
		infoBtn.setMargin(new Insets(0, 2, 0, 2));
		infoBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int w = 375;
				JOptionPane.showMessageDialog(null,
						String.format("<html><body width='%1s'><h1>Info</h1><p>" + CR.getInformation() + "<br>", w, w));
			}
		});

		JPanel title = new JPanel();
		title.setLayout(new GridBagLayout());
		GridBagConstraints GBC = new GridBagConstraints();
		ruleName.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 7));
		GBC.gridx = 0;
		GBC.gridy = 0;
		title.add(ruleName, GBC);
		GBC.gridx = 1;
		title.add(infoBtn, GBC);
		title.setBackground(new Color(254, 254, 254));
		title.setBorder(BorderFactory.createEmptyBorder(2, 0, 10, 0));
		/////////////////////////////////////////////////
		pane.add(title);

		getTheQuestions(CR);
		updateRecommendationAction(CR);
		pane.add(RDP);

		pane.setPreferredSize(new Dimension(550, CalculateHeightBasedOnNumberOfQuestions(numberOfQuestions)));
		setSize(new Dimension(550, CalculateHeightBasedOnNumberOfQuestions(numberOfQuestions)));
		Border placardBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY);

		pane.setBorder(placardBorder);
		add(pane);
	}

	private int CalculateHeightBasedOnNumberOfQuestions(int nOfQuestions) {
		int height = nOfQuestions * 38 + 90;
		totalHeight = height + 15;
		return height;
	}

	protected int getTotalHeight() {
		return totalHeight;
	}

	public void getTheQuestions(final Rule CR) {
		ScrapeDMN tmp = new ScrapeDMN();
		tmp.interpreter(Utils.byteToStream(CR.getFile()));// -------------
		questions = tmp.getQuestions();

		numberOfQuestions = questions.size();
		if (questions == null || numberOfQuestions == 0) {
			System.out.println("ERROR: There are no questions");
			return;
		}
		ruleName.setText(questions.get(0).getRuleName());// TODO what if there aren't any questions

		for (int i = 0; i < numberOfQuestions; i++) {
			if (i < CR.getAnswers().size()) {
				questions.get(i).setAnswer(CR.getAnswer(i));
			}
			QuestionSelect tmpQS = new QuestionSelect(questions.get(i).getQuestion(), questions.get(i).getStrOptions(),
					questions.get(i).getAnswerIndex());
			tmpQS.addDetailListener(new DetailListener() {// listener for changes in selection
				public void detailEventOccurred(DetailEvent e) {
					updateRecommendationAction(CR);
				}
			});
			int borderSpace = 4;
			Border insideBorder = BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK);
			if (i > 0) {
				insideBorder = BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK);
				insideBorder = BorderFactory.createCompoundBorder(insideBorder, BorderFactory.createEmptyBorder(borderSpace, 0, 0, 0));
			}
			Border outsideBorder = BorderFactory.createEmptyBorder(0, 8, 0, 8);
			Border outsideBorder2 = BorderFactory.createEmptyBorder(0, 0, borderSpace, 0);
			Border combinedBorder = BorderFactory.createCompoundBorder(outsideBorder, insideBorder);
			combinedBorder = BorderFactory.createCompoundBorder(outsideBorder2, combinedBorder);
			tmpQS.setBorder(combinedBorder);
			questionSelects.add(tmpQS);
			pane.add(tmpQS);
		}

	}

	public void fireDetailEvent(DetailEvent event) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == DetailListener.class) {
				((DetailListener) listeners[i + 1]).detailEventOccurred(event);
			}
		}
	}

	public void updateRecommendationAction(Rule CR) {

		for (int i = 0; i < questions.size(); i++) {
			questions.get(i).setAnswer(questionSelects.get(i).getSelectedValue());
		}

		ProcessDMN p_dmn = new ProcessDMN();
		ArrayList<String> fields = new ArrayList<>();
		ArrayList<String> fieldValues = new ArrayList<>();
		if (questions.size() > 0) {
			String decisionID = questions.get(0).getDecisionId();
			for (int i = 0; i < questions.size(); i++) {
				fields.add(questions.get(i).getVarName());// find the name of the variable
				fieldValues.add(questions.get(i).getAnswer());
			}

			CR.setAnswers(fieldValues);
			String recommendation = p_dmn.getDecision(fields, fieldValues, Utils.byteToStream(CR.getFile()),
					decisionID);// ---------
			RDP.setRecommendation(recommendation);
			if (!(CR.getRecommendation().equals(recommendation)) || CR.getRecommendation() == null) {
				CR.setRecommendation(recommendation);
				fireRecommendationChangeEvent(new RecommendationChangeEvent(this, ""));
			}
		} else {
			CR.setRecommendation("ERROR occured");
		}
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
