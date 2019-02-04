package EBM_tool.gui;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;

import com.tom.EBM_RuleManager.Model.ConceptRuleRelationManager;
import com.tom.EBM_RuleManager.Model.Relation;
import com.tom.EBM_RuleManager.Model.RuleRelationSerial;

import EBM_tool.DetailListeners.ConceptSelectionEvent;
import EBM_tool.DetailListeners.ConceptSelectionListener;
import EBM_tool.DetailListeners.RecommendationChangeEvent;
import EBM_tool.DetailListeners.RecommendationChangeListener;
import EBM_tool.DetailListeners.TabChangeEvent;
import EBM_tool.DetailListeners.TabChangeListener;
import EBM_tool.OWL2Prefuse.OWLViewComp;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

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
	private OWLViewComp VC;
	private JSplitPane splitPane;
	
	Dimension dim = new Dimension(600, 930);

	public MainFrame(String title) {
		super(title);
		setJMenuBar(createMenuBar());
		fileChooser = new JFileChooser();
		FileFilter FF = new M_FileFilter("per", "Files with relations and rules");
		fileChooser.addChoosableFileFilter(new M_FileFilter("per", "Files with relations and rules"));
		fileChooser.setFileFilter(FF);
		setLayout(new FlowLayout());// set layout manager
		Relation CRR = new Relation("");
		pane = new QuestionPane(dim, CRR, getOWLDocument());
		pane.addRecommendationChangeListener(new RecommendationChangeListener() {
			public void recommendationChangeEventOcurred(RecommendationChangeEvent event) {
				VC.updateSummaryPanel(CRRM);
			}
		});
	}

	private Document getOWLDocument() {
		return null;
	}

	private Document getOWLDocument(byte[] bt) {

		InputStream file = Utils.byteToStream(bt);

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();// TODO: deal with errors
		}
		Document document = null;
		try {
			document = documentBuilder.parse(file);
		} catch (Exception e) {
			System.out.println("Error: input stream didn't work");
			return null;
		}
		return document;
	}

	private void setDetailPane(Relation relation, org.w3c.dom.Document document) {
		c.remove(pane);
		c.revalidate();
		c.repaint();
		revalidate();
		repaint();

		pane = new QuestionPane(dim, relation, document);
		pane.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
		pane.addRecommendationChangeListener(new RecommendationChangeListener() {
			public void recommendationChangeEventOcurred(RecommendationChangeEvent event) {
				VC.updateSummaryPanel(CRRM);
			}
		});
		c.add(pane);
		c.revalidate();
		c.repaint();
		revalidate();
		repaint();
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
						e.printStackTrace();
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

			final Document document = getOWLDocument(RRS.getOntology());

			if (viewOWL != null) {
				c.remove(viewOWL);
				c.remove(pane);
			}
			c.revalidate();
			c.repaint();
			revalidate();
			repaint();

			int count = 0;
			
			ByteArrayInputStream tmp2 = new ByteArrayInputStream(RRS.getOntology());
			
			//---------------Very hacky way of getting the ontology from the byte[] for some reason converting it to an InputStream did not work
			OutputStream os = new FileOutputStream("./new_source.owl");

			byte[] buffer = new byte[1024];
			int bytesRead;
			// read from is to buffer
			while ((bytesRead = tmp2.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			tmp2.close();
			// flush OutputStream to write any buffered data to file
			os.flush();
			os.close();

			InputStream tmp3 = new FileInputStream("./new_source.owl");
			//-----------------
			VC = new OWLViewComp(tmp3, CRRM);//

			VC.addConceptSelectionListener(new ConceptSelectionListener() {
				public void conceptSelectionOccurred(ConceptSelectionEvent event) {
					setDetailPane(event.getRelations(), document);
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
			pane.addRecommendationChangeListener(new RecommendationChangeListener() {
				public void recommendationChangeEventOcurred(RecommendationChangeEvent event) {
					VC.updateSummaryPanel(CRRM);
				}
			});
			setDetailPane(new Relation(""), document);
			c.add(viewOWL);
			c.add(pane);

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

		oos.writeObject(RRS);
		oos.close();
	}
}