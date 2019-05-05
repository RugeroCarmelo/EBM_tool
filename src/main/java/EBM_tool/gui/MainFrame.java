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
	 * This class creates the Main window, with 2 main view components:
	 * 	Detail panel - the right hand side panel that includes the details about the selected node in theOntology view component
	 * 	Ontology view component - left hand side component that contains a visual representation of the ontology
	 */
	private static final long serialVersionUID = 2081948293235131176L;

	private DetailPanel detailPanel;
	private ConceptRuleRelationManager CRRM = new ConceptRuleRelationManager();
	private Container container = getContentPane();
	private JPanel ontologyViewContainer;
	private JFileChooser fileChooser;
	private OWLViewComp ontologyView;
	private RuleRelationSerial RRS;
	
	Dimension dim = new Dimension(650, 930);//dimension used for the detail panel

	public MainFrame(String title) {
		super(title);
		
		setJMenuBar(createMenuBar());
		fileChooser = new JFileChooser();
		FileFilter FF = new M_FileFilter("per", "Files with relations and rules");
		fileChooser.addChoosableFileFilter(FF);
		fileChooser.setFileFilter(FF);
		
		setLayout(new BorderLayout());// set layout manager
		container.setLayout(new BorderLayout());
		
		Relation CRR = new Relation("");
		detailPanel = new DetailPanel(dim, CRR, null);
		detailPanel.addRecommendationChangeListener(new RecommendationChangeListener() {
			public void recommendationChangeEventOcurred(RecommendationChangeEvent event) {
				ontologyView.updateSummaryPanel(CRRM);
			}
		});
	}

	private Document getOWLDocument(byte[] bt) {

		InputStream file = Utils.byteToStream(bt);

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

		DocumentBuilder documentBuilder = null;
		try {
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Error reading the ontology file", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		Document document = null;
		try {
			document = documentBuilder.parse(file);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Error with the input stream, ontology data might not be valid", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return null;
		}
		return document;
	}

	/*
	 * updates the detail panel to show the detail of the currently selected node
	 */
	private void setDetailPanel(Relation relation, org.w3c.dom.Document document) {
		container.remove(detailPanel);
		
		container.revalidate();
		container.repaint();
		revalidate();
		repaint();

		detailPanel = new DetailPanel(dim, relation, document);
		detailPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
		detailPanel.addRecommendationChangeListener(new RecommendationChangeListener() {
			public void recommendationChangeEventOcurred(RecommendationChangeEvent event) {
				ontologyView.updateSummaryPanel(CRRM);
			}
		});
		container.add(detailPanel, BorderLayout.EAST);
		
		container.revalidate();
		container.repaint();
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
		//load from serilized file into the RuleRelationSerial class
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		try {
			RRS = (RuleRelationSerial) ois.readObject();
			CRRM.setRelations(RRS.getRelations());//load info into ConceptRuleRelationManger class
			ois.close();
			Relation CRR = new Relation("");

			final Document document = getOWLDocument(RRS.getOntology());

			if (ontologyViewContainer != null) {
				container.remove(ontologyViewContainer);
				container.remove(detailPanel);
			}
			container.revalidate();
			container.repaint();
			revalidate();
			repaint();
			
			ByteArrayInputStream tmp2 = new ByteArrayInputStream(RRS.getOntology());//get the ontology info from serialized file
			
			//Very hacky way of getting the ontology from the byte[] for some reason converting it to an InputStream does not work, so byte[] must first be writen to a file before being read again into the program
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

			InputStream tmp3 = new FileInputStream("./new_source.owl");//load data from file
			//-----------------
			ontologyView = new OWLViewComp(tmp3, CRRM);//

			ontologyView.addConceptSelectionListener(new ConceptSelectionListener() {
				public void conceptSelectionOccurred(ConceptSelectionEvent event) {
					setDetailPanel(event.getRelations(), document);
				}
			});
			
			ontologyView.addTabChangeListener(new TabChangeListener() {
				public void TabChangeEventOccurred(TabChangeEvent event) {
					if(event.getTabNum() == 2) {
						ontologyView.updateSummaryPanel(CRRM);
					}
				}
			});

			ontologyViewContainer = ontologyView.getPanel();

			detailPanel = new DetailPanel(dim, CRR, getOWLDocument(RRS.getOntology()));
			detailPanel.setBorder(BorderFactory.createEmptyBorder(16, 0, 0, 0));
			detailPanel.addRecommendationChangeListener(new RecommendationChangeListener() {
				public void recommendationChangeEventOcurred(RecommendationChangeEvent event) {
					ontologyView.updateSummaryPanel(CRRM);
				}
			});
			setDetailPanel(new Relation(""), document);
			container.add(ontologyViewContainer, BorderLayout.CENTER);
			container.add(detailPanel, BorderLayout.EAST);

			container.revalidate();
			container.repaint();
			revalidate();
			repaint();

		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void saveToFile(File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		RRS.setRelations(CRRM.getRelations());
		
		
		InputStream tmp3 = new FileInputStream("./new_source.owl");
		byte[] bytes = IOUtils.toByteArray(tmp3);
		RRS.setOntology(bytes);

		oos.writeObject(RRS);
		oos.close();
	}
}