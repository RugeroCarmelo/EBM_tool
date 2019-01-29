package EBM_tool.gui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

import com.tom.EBM_RuleManager.Model.ConceptRuleRelationManager;
import com.tom.EBM_RuleManager.Model.Relation;
import com.tom.EBM_RuleManager.Model.Rule;
import com.tom.EBM_RuleManager.Model.RuleRelationSerial;

import EBM_tool.DetailListeners.DetailEvent;
import EBM_tool.DetailListeners.DetailListener;
import EBM_tool.DetailListeners.TabChangeEvent;
import EBM_tool.DetailListeners.TabChangeListener;
import EBM_tool.OWL2Prefuse.OWLViewComp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2081948293235131176L;

	// private DetailPanel detailPanel;
	private QuestionPane pane;
	private ConceptRuleRelationManager CRRM = new ConceptRuleRelationManager();
	private Container c = getContentPane();
	private JPanel viewOWL;
	private JFileChooser fileChooser;

	Dimension dim = new Dimension(650, 930);

	public MainFrame(String title) {
		super(title);
		setJMenuBar(createMenuBar());
		fileChooser = new JFileChooser();
		FileFilter FF = new M_FileFilter("per", "Files with relations and rules");
		fileChooser.addChoosableFileFilter(new M_FileFilter("per", "Files with relations and rules"));
		fileChooser.setFileFilter(FF);
		// getRelations();
		setLayout(new FlowLayout());// set layout manager
		Relation CRR = new Relation("");

		// File tmpFile = new File("data/ontologyMain.owl");
		// Document document = getOWLDocument(tmpFile);

		pane = new QuestionPane(dim, CRR, getOWLDocument());
		// setDetailPane("", document);

		//c.add(pane);
	}
	
	private Document getOWLDocument() {
		return null;
	}
	private Document getOWLDocument(File file) {
		ClassLoader classLoader = getClass().getClassLoader();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();// this needs to be changed to a forwarding throw
		}
		Document document = null;
		try {
			document = documentBuilder.parse(file);
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Error: input stream didn't work");
			return null;
		}
		return document;
	}

	private void setDetailPane(String name, org.w3c.dom.Document document) {
		// pane.setLabel(text);
		c.remove(pane);
		c.revalidate();
		c.repaint();
		revalidate();
		repaint();

		pane = new QuestionPane(dim, CRRM.getRelation(name), document);
		//System.out.println("relation name: " + CRRM.getRelation(name).getConceptName());
		pane.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
		c.add(pane);
		c.revalidate();
		c.repaint();
		revalidate();
		repaint();
	}

	// public DetailPanel getDetailPanel() {
	// return detailPanel;
	// }

	// for test only
	public File getFileFromSource(String resourceLocation) {
		ClassLoader classLoader = getClass().getClassLoader();
		File doc;// File doc
		try {
			doc = new File(classLoader.getResource(resourceLocation).getFile());// doc = new
																				// File(classLoader.getResource(fileName).getFile())
																				// doc =
																				// classLoader.getResourceAsStream(fileName)
		} catch (Exception e) {
			System.out.println("no file with specified name found | File Name: " + resourceLocation);// TODO needs to be
																										// turned into a
																										// throw
																										// exception
			// statement
			return null;
		}
		return doc;
	}

	public void getRelations() {

		Rule CR1 = new Rule("org/camunda/bpm/example/diagram_1.dmn", "Crowdsourcing", "Management", "Info", "ID",
				getFileFromSource("org/camunda/bpm/example/diagram_1.dmn"));
		Rule CR3 = new Rule("org/camunda/bpm/example/diagram_3.dmn", "Crowdsourcing", "People", "Info", "ID",
				getFileFromSource("org/camunda/bpm/example/diagram_3.dmn"));
		Rule CR4 = new Rule("org/camunda/bpm/example/diagram_4.dmn", "Crowdsourcing", "Task_Characteristic", "Info",
				"ID", getFileFromSource("org/camunda/bpm/example/diagram_4.dmn"));
		CR1.setInformation(
				"Demonstration of a description. The description would go in here and then displayed in a pop up box, what happens however if I keep on going and just don't stop will a new line eventually start?");

		Relation CRR = new Relation("Decision_to_crowdsource");
		CRR.addRule(CR4);
		CRR.addRule(CR1);
		CRR.addRule(CR3);
		CRR.addRule(CR4);
		CRR.addRule(CR4);
		CRR.addRule(CR4);
		CRR.addRule(CR4);
		Relation CRR1 = new Relation("Task_Characteristic");
		CRR1.addRule(CR4);
		Relation CRR2 = new Relation("People");
		CRR2.addRule(CR3);
		Relation CRR3 = new Relation("Management");
		CRR3.addRule(CR1);

		CRRM.add(CRR);
		CRRM.add(CRR1);
		CRRM.add(CRR2);
		CRRM.add(CRR3);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu("File");
		JMenuItem exportDataItem = new JMenuItem("Save");
		JMenuItem ImportDataItem = new JMenuItem("Open");
		ImportDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						openFromFile(fileChooser.getSelectedFile());
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		exportDataItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
					try {
						saveToFile((fileChooser.getSelectedFile()));
					} catch (IOException e) {
						JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exportDataItem);
		fileMenu.add(ImportDataItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		menuBar.add(fileMenu);

		fileMenu.setMnemonic(KeyEvent.VK_F);
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int action = JOptionPane.showConfirmDialog(MainFrame.this,
						"Do you really want to exit the application?", "Confirm exit", JOptionPane.OK_CANCEL_OPTION);
				if (action == JOptionPane.OK_OPTION) {
					System.exit(0);
				}
			}
		});

		return menuBar;
	}

	public void openFromFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		try {
			RuleRelationSerial RRS = (RuleRelationSerial) ois.readObject();
			CRRM.setRelations(RRS.getRelations());
			ois.close();
			Relation CRR = new Relation("");

			// File tmpFile = new File("data/ontologyMain.owl");
			final Document document = getOWLDocument(RRS.getOntology());

			if (viewOWL != null) {
				c.remove(viewOWL);
				c.remove(pane);
			}
			c.revalidate();
			c.repaint();
			revalidate();
			repaint();

			final OWLViewComp VC = new OWLViewComp(RRS.getOntology(), CRRM);

			VC.addDetailListener(new DetailListener() {
				public void detailEventOccurred(DetailEvent event) {
					String text = event.getText();
					setDetailPane(text, document);
					//System.out.println("name: " + text);
				}
			});

			VC.addTabChangeListener(new TabChangeListener() {
				public void TabChangeEventOccurred(TabChangeEvent event) {
					VC.updateSummaryPanel(CRRM);
				}
			});

			viewOWL = VC.getPanel();
			
			pane = new QuestionPane(dim, CRR, getOWLDocument(RRS.getOntology()));
			pane.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
			setDetailPane("", document);
			c.add(viewOWL);
			c.add(pane);

			 //for(int i = 0; i < CRRM.size(); i++) {
			 //System.out.println("Concept name: " + CRRM.getRelation(i).getRuleAtIndex(0).getID());
			 //}
			 c.revalidate();
				c.repaint();
				revalidate();
				repaint();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		RuleRelationSerial RRS = new RuleRelationSerial();
		RRS.setRelations(CRRM.getRelations());
		// RRS.setRules(CRRM.getAllConceptRules());

		oos.writeObject(RRS);
		oos.close();
	}
}