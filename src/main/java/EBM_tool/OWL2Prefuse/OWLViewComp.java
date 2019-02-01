package EBM_tool.OWL2Prefuse;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.tom.EBM_RuleManager.Model.ConceptRuleRelationManager;
import com.tom.EBM_RuleManager.Model.Relation;
import com.tom.EBM_RuleManager.Model.Rule;

import EBM_tool.DetailListeners.ConceptSelectionEvent;
import EBM_tool.DetailListeners.ConceptSelectionListener;
import EBM_tool.DetailListeners.DetailEvent;
import EBM_tool.DetailListeners.DetailListener;
import EBM_tool.DetailListeners.TabChangeEvent;
import EBM_tool.DetailListeners.TabChangeListener;
import EBM_tool.OWL2Prefuse.OWL2Prefuse.Loader;
import EBM_tool.OWL2Prefuse.OWL2Prefuse.Writer;
import EBM_tool.OWL2Prefuse.graph.GraphDisplay;
import EBM_tool.OWL2Prefuse.graph.GraphPanel;
import EBM_tool.OWL2Prefuse.graph.OWLGraphConverter;
import EBM_tool.OWL2Prefuse.tree.OWLTreeConverter;
import EBM_tool.OWL2Prefuse.tree.TreeDisplay;
import EBM_tool.OWL2Prefuse.tree.TreePanel;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.data.Tree;
import prefuse.util.io.SimpleFileFilter;

/**
 * This class loads a GraphML or a TreeML file and returns a graph or a tree
 * respectively.
 * <p/>
 * Project OWL2Prefuse <br/>
 * Demo.java created 2 januari 2007, 10:40
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 *
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje</a>
 * @version $$Revision:$$, $$Date:$$
 */
public class OWLViewComp extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8667472348804810498L;

	/**
	 * The JFrame of the demo application.
	 */
	// private JFrame m_frame;
	private JPanel m_Panel;

	/**
	 * The tabbed pane of the demo application.
	 */
	private JTabbedPane m_tabbedPane;

	/**
	 * The file chooser, used by this demo.
	 */
	private JFileChooser m_fc;

	/**
	 * The Prefuse graph.
	 */
	private Graph m_graph;

	/**
	 * The graph panel.
	 */
	private GraphPanel m_graphPanel;

	/**
	 * The Prefuse tree.
	 */
	private Tree m_tree;

	/**
	 * The tree panel.
	 */
	private TreePanel m_treePanel;

	/**
	 * The action command for opening an ontology.
	 */
	private final static String OPEN_ONTOLOGY = "Open ontology";

	/**
	 * The action command for opening GraphML.
	 */
	private final static String OPEN_GRAPHML = "Open GraphML";

	/**
	 * The action command for opening TreeML.
	 */
	private final static String OPEN_TREEML = "Open TreeML";

	/**
	 * The action command for exiting the demo application.
	 */
	private final static String EXIT = "Exit";

	/**
	 * The action command for exporting to GraphML
	 */
	private final static String EXPORT_GRAPHML = "Export GraphML";

	/**
	 * The action command for exporting TreeML.
	 */
	private final static String EXPORT_TREEML = "Export TreeML";

	/**
	 * Indicates if the graph distance filter should be used.
	 */
	private final static boolean GRAPH_DISTANCE_FILTER = true;

	/**
	 * Indicates if a legend should be displayed.
	 */
	private final static boolean LEGEND = true;

	/**
	 * Indicates if the hops control widget for the graph distance filter should be
	 * displayed.
	 */
	private final static boolean HOPS_CONTROL_WIDGET = true;

	/**
	 * Indicates if the orientation control widget for the tree should be displayed.
	 */
	private final static boolean ORIENTATION_CONTROL_WIDGET = true;

	private TreePanel summaryTreePanel;

	/**
	 * Creates a new instance of the Demo class.
	 */
	public OWLViewComp(File file, final ConceptRuleRelationManager CRRM) {
		Dimension size = getPreferredSize();
		size.width = 100;
		setPreferredSize(size);

		// Create the tree from an OWL file.
		OWLTreeConverter treeConverter = new OWLTreeConverter(file);
		m_tree = treeConverter.getTree();

		// Create a tree display.
		TreeDisplay treeDisp = new TreeDisplay(m_tree);
		treeDisp.addDetailListener(new DetailListener() {
			public void detailEventOccurred(DetailEvent event) {
				String text = event.getText();
				text = text.substring(text.lastIndexOf("#") + 1);
				fireConceptSelectionEvent(new ConceptSelectionEvent(m_tree, text, CRRM.getRelation(text)));
			}
		});

		// Create a panel for the tree display.
		m_treePanel = new TreePanel(treeDisp, LEGEND, ORIENTATION_CONTROL_WIDGET);
		m_treePanel.setPreferredSize(new Dimension(1000, 900));

		// Create a graph.
		OWLGraphConverter graphConverter = new OWLGraphConverter(file, false);// change to false to remove arrows true
																				// to add arrows
		m_graph = graphConverter.getGraph();

		// Create a graph display.
		GraphDisplay graphDisp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER);
		graphDisp.addDetailListener(new DetailListener() {
			public void detailEventOccurred(DetailEvent event) {
				String text = event.getText();
				text = text.substring(text.lastIndexOf("#") + 1);
				fireConceptSelectionEvent(new ConceptSelectionEvent(m_tree, text, CRRM.getRelation(text)));
			}
		});

		// Create a panel for the graph display, which includes a widget for
		// controlling the number of hops in the graph.
		m_graphPanel = new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);
		m_graphPanel.setPreferredSize(new Dimension(1000, 900));

		// Create the tabbed pane which contains the the home tab, the tree tabs
		// and the graph tabs.
		m_tabbedPane = new JTabbedPane();
		m_tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// System.out.println("Tab: " + m_tabbedPane.getSelectedIndex());
				if (m_tabbedPane.getSelectedIndex() == 2) {
					fireTabChangeEvent(new TabChangeEvent(m_tabbedPane, "", m_tabbedPane.getSelectedIndex()));
				}
			}
		});

		m_tabbedPane.addTab("Tree view", m_treePanel);
		m_tabbedPane.setToolTipTextAt(0, "A display of the tree.");
		m_tabbedPane.setMnemonicAt(0, KeyEvent.VK_T);

		m_tabbedPane.addTab("Graph view", m_graphPanel);
		m_tabbedPane.setToolTipTextAt(1, "A display of the graph.");
		m_tabbedPane.setMnemonicAt(1, KeyEvent.VK_G);

		summaryPanel(CRRM);
		m_tabbedPane.addTab("Summary of rules", summaryTreePanel);
		m_tabbedPane.setToolTipTextAt(2, "A display of the graph.");
		m_tabbedPane.setMnemonicAt(2, KeyEvent.VK_G);

		m_Panel = new JPanel();
		m_Panel.add(m_tabbedPane);
		setSize(new Dimension(1000, 1000));
		setBackground(new Color(0, 0, 0));
		m_Panel.setVisible(true);

		// Create the file chooser.
		m_fc = new JFileChooser();

		add(m_Panel);
	}

	public void updateSummaryPanel(ConceptRuleRelationManager CRRM) {
		summaryPanel(CRRM);
		m_tabbedPane.setComponentAt(2, summaryTreePanel);
		m_tabbedPane.revalidate();
		m_tabbedPane.repaint();
	}

	public void summaryPanel(final ConceptRuleRelationManager CRRM) {
		final Tree tree = new Tree();
		// Add the appropriate columns.
		tree.addColumn("URI", String.class);
		tree.addColumn("name", String.class);
		tree.addColumn("type", String.class);

		Node currTopicNode = null;
		Node currDecisionNode = null;
		Node currRuleName = null;
		Node rootNode = tree.addRoot();
		rootNode.setString("URI", "Recommendations");
		rootNode.setString("name", "Recommendations");
		rootNode.setString("type", "class");

		//Relation CRR;
		// ArrayList<ConceptRule> CRList = CRRM.getAllConceptRules();
		ArrayList<String> topics = CRRM.getAllTopics();

		// System.out.println("This is the recommendation... " +
		// CRRM.getRelation("People").getRuleAtIndex(0).getRecommendation());

		for (int i = 0; i < topics.size(); i++) {
			currTopicNode = tree.addChild(rootNode);
			currTopicNode.setString("URI", "TOPIC#" + topics.get(i));
			currTopicNode.setString("name", topics.get(i));
			currTopicNode.setString("type", "class");

			// System.out.println(topics.get(i));

			ArrayList<Rule> CRList = CRRM.getRulesWithTopic(topics.get(i));
			ArrayList<String> recommendations = findRecommendations(CRList);

			for (int j = 0; j < recommendations.size(); j++) {
				currDecisionNode = tree.addChild(currTopicNode);
				currDecisionNode.setString("URI", "Recommendation" + "#" + topics.get(i) + "#" + recommendations.get(j));
				currDecisionNode.setString("name", recommendations.get(j));
				currDecisionNode.setString("type", "class");
				// System.out.println(recommendations.get(j));
				ArrayList<String> names = findNames(CRList, recommendations.get(j));

				for (int tmp = 0; tmp < names.size(); tmp++) {
					currRuleName = tree.addChild(currDecisionNode);
					currRuleName.setString("URI", "Name_of_the_rules" + "|" + topics.get(i) + "|" + "#" + recommendations.get(j) + "#" + names.get(tmp));
					currRuleName.setString("name", names.get(tmp));
					currRuleName.setString("type", "individual");
					// System.out.println(names.get(tmp));
				}
			}
		}

		TreeDisplay treeDisp = new TreeDisplay(tree);
		treeDisp.addDetailListener(new DetailListener() {
			public void detailEventOccurred(DetailEvent event) {
				String tmp = event.getText();
				//tmp = tmp.substring(tmp.lastIndexOf("#") + 1);
				// CRRM.getRulesWithTopic(topic);
				if(tmp.contains("TOPIC")) {
					ArrayList<Rule> CRList = CRRM.getRulesWithTopic(tmp.substring(tmp.lastIndexOf("#") + 1));
					Relation CRR = new Relation(tmp.substring(tmp.lastIndexOf("#") + 1));
					for(int i = 0; i < CRList.size(); i++) {
						CRR.addRule(CRList.get(i));
					}
					fireConceptSelectionEvent(new ConceptSelectionEvent(tree, tmp, CRR));
				}else if(tmp.contains("Recommendation")) {
					String rec = tmp.substring(tmp.lastIndexOf("#") + 1);
					String topic = tmp.substring((tmp.indexOf("#") + 1), tmp.lastIndexOf("#"));
					ArrayList<Rule> CRList = CRRM.getRulesWithTopic(topic);
					CRList = CRRM.getRulesWithRecommendation(CRList, rec);
					Relation CRR = new Relation("");
					for(int i = 0; i < CRList.size(); i++) {
						CRR.addRule(CRList.get(i));
					}
					fireConceptSelectionEvent(new ConceptSelectionEvent(tree, tmp, CRR));
				}else if(tmp.contains("Name_of_the_rules")) {
					String rec = tmp.substring((tmp.indexOf("#") + 1), tmp.lastIndexOf("#"));
					String topic = tmp.substring((tmp.indexOf("|") + 1), tmp.lastIndexOf("|"));
					String name = tmp.substring(tmp.lastIndexOf("#") + 1);
					ArrayList<Rule> CRList = CRRM.getRulesWithTopic(topic);
					CRList = CRRM.findRules(CRList, rec, name);
					Relation CRR = new Relation(tmp.substring(tmp.lastIndexOf("#") + 1));
					for(int i = 0; i < CRList.size(); i++) {
						CRR.addRule(CRList.get(i));
					}
					fireConceptSelectionEvent(new ConceptSelectionEvent(tree, tmp, CRR));
				}
			}
		});

		// Create a panel for the tree display.
		TreePanel m_graphPanel2 = new TreePanel(treeDisp, LEGEND, HOPS_CONTROL_WIDGET);
		m_graphPanel2.setPreferredSize(new Dimension(1000, 900));
		summaryTreePanel = m_graphPanel2;
	}

	public int getCurrentTab() {
		return m_tabbedPane.getSelectedIndex();
	}

	private ArrayList<String> findNames(ArrayList<Rule> list, String recommendation) {
		ArrayList<String> tmpList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getRecommendation().equals(recommendation) && list.get(i).getRuleName().length() > 0) {
				if (findIndex(tmpList, list.get(i).getRuleName()) == -1) {
					tmpList.add(list.get(i).getRuleName());
				}
			}
		}
		return tmpList;
	}

	private ArrayList<String> findRecommendations(ArrayList<Rule> list) {
		ArrayList<String> tmpList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if (findIndex(tmpList, list.get(i).getRecommendation()) == -1
					&& list.get(i).getRecommendation().length() > 0) {
				tmpList.add(list.get(i).getRecommendation());
			}
		}
		return tmpList;
	}

	private int findIndex(ArrayList<String> list, String term) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(term)) {
				return i;
			}
		}
		return -1;
	}

	public static JPanel makeOWLViewComp(File file) {
		// Create the tree from an OWL file.
		OWLTreeConverter treeConverter = new OWLTreeConverter(file);
		Tree m_tree = treeConverter.getTree();

		// Create a tree display.
		TreeDisplay treeDisp = new TreeDisplay(m_tree);

		// Create a panel for the tree display.
		TreePanel m_treePanel = new TreePanel(treeDisp, LEGEND, ORIENTATION_CONTROL_WIDGET);

		// Create a graph.
		OWLGraphConverter graphConverter = new OWLGraphConverter(file, true);
		Graph m_graph = graphConverter.getGraph();

		// Create a graph display.
		GraphDisplay graphDisp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER);

		GraphPanel m_graphPanel = new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);

		JTabbedPane m_tabbedPane = new JTabbedPane();
		m_tabbedPane.addTab("Tree view", m_treePanel);
		m_tabbedPane.setToolTipTextAt(0, "A display of the tree.");
		m_tabbedPane.setMnemonicAt(0, KeyEvent.VK_T);

		m_tabbedPane.addTab("Graph view", m_graphPanel);
		m_tabbedPane.setToolTipTextAt(1, "A display of the graph.");
		m_tabbedPane.setMnemonicAt(1, KeyEvent.VK_G);

		// Create the frame which shows the application.
		JPanel m_Panel = new JPanel();
		m_Panel.add(m_tabbedPane);
		m_Panel.setVisible(true);

		return m_Panel;
	}

	public void fireConceptSelectionEvent(ConceptSelectionEvent event) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == ConceptSelectionListener.class) {
				((ConceptSelectionListener) listeners[i + 1]).conceptSelectionOccurred(event);
			}
		}
	}

	public void addConceptSelectionListener(ConceptSelectionListener listener) {
		listenerList.add(ConceptSelectionListener.class, listener);
	}

	public void removeConceptSelectionListener(ConceptSelectionListener listener) {
		listenerList.remove(ConceptSelectionListener.class, listener);
	}

	public void fireTabChangeEvent(TabChangeEvent event) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == TabChangeListener.class) {
				((TabChangeListener) listeners[i + 1]).TabChangeEventOccurred(event);
			}
		}
	}

	public void addTabChangeListener(TabChangeListener listener) {
		listenerList.add(TabChangeListener.class, listener);
	}

	public void removeTabChangeListener(TabChangeListener listener) {
		listenerList.remove(TabChangeListener.class, listener);
	}

	public JPanel getPanel() {
		return m_Panel;
	}

	/**
	 * This methods starts the demo.
	 * 
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		// Start the demo application.
		// OWLViewComp example = new OWLViewComp();
	}

	/**
	 * This methods is called when an action is performed, by one of the menu bar
	 * items.
	 * 
	 * @param e The thrown ActionEvent.
	 */
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals(OPEN_ONTOLOGY))
			openOntology();
		else if (action.equals(OPEN_GRAPHML))
			openGraphML();
		else if (action.equals(OPEN_TREEML))
			openTreeML();
		else if (action.equals(EXIT))
			System.exit(0);
		else if (action.equals(EXPORT_GRAPHML))
			exportGraphML();
		else if (action.equals(EXPORT_TREEML))
			exportTreeML();
	}

	/**
	 * This methods opens a filechooser to facilitate the user in opening and saving
	 * files.
	 * 
	 * @param p_opener      Boolean indicating if this filechooser should be an
	 *                      opener or a saver.
	 * @param p_fileFilters An array containing the appropriate file filters.
	 * @return The path to the chosen file or null if no file is chosen.
	 */
	private String openFileChooser(boolean p_opener, SimpleFileFilter[] p_fileFilters) {
		String retval = null;

		// Take care of the file filters.
		if (p_fileFilters.length == 0) {
			m_fc.setAcceptAllFileFilterUsed(true);
		} else {
			m_fc.resetChoosableFileFilters();
			m_fc.setAcceptAllFileFilterUsed(false);
			for (int i = 0; i < p_fileFilters.length; i++) {
				m_fc.addChoosableFileFilter(p_fileFilters[i]);
			}
		}

		// Open a dialog, either an opener of a saver.
		int fcState;
		if (p_opener) {
			m_fc.setDialogTitle("Open file");
			fcState = m_fc.showOpenDialog(m_Panel);
		} else {
			m_fc.setDialogTitle("Save file");
			fcState = m_fc.showSaveDialog(m_Panel);
		}

		if (fcState == JFileChooser.APPROVE_OPTION) {
			File file = m_fc.getSelectedFile();
			retval = file.getPath();
		}

		return retval;
	}

	/**
	 * This method is triggered when a user wants to open an ontology. It provides
	 * the user with a file choser and handles the opening and visiualizing of the
	 * chosen ontology. The opened ontology is visualized both as a simple tree, an
	 * advanced tree and a graph.
	 */
	private void openOntology() {
		// Create the file filter.
		SimpleFileFilter[] filters = new SimpleFileFilter[] { new SimpleFileFilter("owl", "OWL ontologies (*.owl)") };

		// Open the file.
		String fileStr = openFileChooser(true, filters);
		File file = new File(fileStr);

		// Process the file.
		if (file != null) {
			OWLTreeConverter treeConverter = new OWLTreeConverter(file);
			m_tree = treeConverter.getTree();
			TreeDisplay treeDisp = new TreeDisplay(m_tree);
			TreePanel treePanel = new TreePanel(treeDisp, LEGEND, ORIENTATION_CONTROL_WIDGET);
			m_tabbedPane.setComponentAt(1, treePanel);

			OWLGraphConverter graphConverter = new OWLGraphConverter(file, true);
			m_graph = graphConverter.getGraph();
			GraphDisplay graphDisp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER);
			GraphPanel graphPanel = new GraphPanel(graphDisp, LEGEND, HOPS_CONTROL_WIDGET);
			m_tabbedPane.setComponentAt(2, graphPanel);
		}
	}

	/**
	 * This method is triggered when a user wants to open a GraphML file. It
	 * provides the user with a file choser and handles the opening and visiualizing
	 * of the chosen GraphML file. The opened GraphML is loaded after which it is
	 * visualized.
	 */
	private void openGraphML() {
		// Create the file filter.
		SimpleFileFilter[] filters = new SimpleFileFilter[] { new SimpleFileFilter("xml", "Graph ML (*.xml)") };

		// Open the file.
		String file = openFileChooser(true, filters);

		// Process the file.
		if (file != null) {
			m_graph = Loader.loadGraphML(file);
			GraphDisplay disp = new GraphDisplay(m_graph, GRAPH_DISTANCE_FILTER);
			GraphPanel panel = new GraphPanel(disp, LEGEND, HOPS_CONTROL_WIDGET);
			m_tabbedPane.setComponentAt(2, panel);
		}
	}

	/**
	 * This method is triggered when a user wants to open a TreeML file. It provides
	 * the user with a file choser and handles the opening and visiualizing of the
	 * chosen TreeML file. The opened TreeML is loaded after which it is visualized.
	 */
	private void openTreeML() {
		// Create the file filter.
		SimpleFileFilter[] filters = new SimpleFileFilter[] { new SimpleFileFilter("xml", "Tree ML (*.xml)") };

		// Open the file.
		String file = openFileChooser(true, filters);

		// Process the file.
		if (file != null) {
			m_tree = Loader.loadTreeML(file);
			TreeDisplay disp = new TreeDisplay(m_tree);
			TreePanel panel = new TreePanel(disp, LEGEND, ORIENTATION_CONTROL_WIDGET);
			m_tabbedPane.setComponentAt(1, panel);
		}
	}

	/**
	 * This method is triggered when the user wants to export the graph to GraphML.
	 * It presents the user a file chooser, which can be used to pick the file to
	 * which the GraphML is to be saved. After that, the program exports the graph
	 * to GraphML.
	 */
	private void exportGraphML() {
		// Create the file filter.
		SimpleFileFilter[] filters = new SimpleFileFilter[] { new SimpleFileFilter("xml", "Graph ML (*.xml)") };

		// Save the file.
		String file = openFileChooser(false, filters);

		// Write the file.
		if (file != null) {
			String extension = file.substring(file.length() - 4);
			if (!extension.equals(".xml"))
				file = file + ".xml";
			Writer.writeGraphML(m_graph, file);
		}
	}

	/**
	 * This method is triggered when the user wants to export the tree to TreeML. It
	 * presents the user a file chooser, which can be used to pick the file to which
	 * the TreeML is to be saved. After that, the program exports the tree to
	 * TreeML.
	 */
	private void exportTreeML() {
		// Create the file filter.
		SimpleFileFilter[] filters = new SimpleFileFilter[] { new SimpleFileFilter("xml", "Tree ML (*.xml)") };

		// Save the file.
		String file = openFileChooser(false, filters);

		// Write the file.
		if (file != null) {
			String extension = file.substring(file.length() - 4);
			if (!extension.equals(".xml"))
				file = file + ".xml";
			Writer.writeTreeML(m_tree, file);
		}
	}
}