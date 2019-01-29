package EBM_tool.gui;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import EBM_tool.DetailListeners.DetailEvent;
import EBM_tool.DetailListeners.DetailListener;

public class TreeView extends JPanel {
	DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("new1");
	DefaultMutableTreeNode root;

	public javax.swing.JTree Tree;
	private javax.swing.JScrollPane jScrollPane1;

	public TreeView(Dimension dim, String rootNode) {// TODO model manager
		// refreshButton.addActionListener(refreshAction);//listener
		// this.modelManager = modelManager;
		root = new DefaultMutableTreeNode(rootNode);
		Tree = new JTree(root);

		initComponents(dim);
	}

	public void addNode(final String nodeToAdd) {
		DefaultTreeModel model = (DefaultTreeModel) Tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) Tree.getModel().getRoot();
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeToAdd);
		model.insertNodeInto(child, root, root.getChildCount());
		Tree.scrollPathToVisible(new TreePath(child.getPath()));
	}

	private void doMouseClicked() {// This function is probably is not needed
		DefaultMutableTreeNode mNode;
		TreePath path = Tree.getSelectionPath();
		mNode = (DefaultMutableTreeNode) (path.getLastPathComponent());
		// changeText(mNode.toString());
	}

	private void initComponents(Dimension dim) {

		jScrollPane1 = new javax.swing.JScrollPane();

		Tree.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				// doMouseClicked();
				// TODO what happens when the mouse is clicked
				DefaultMutableTreeNode mNode;
				TreePath path = Tree.getSelectionPath();
				mNode = (DefaultMutableTreeNode) (path.getLastPathComponent());
				fireDetailEvent(new DetailEvent(Tree, mNode.toString()));
			}
		});

		Tree.setAutoscrolls(true);
		Tree.setRootVisible(true);
		jScrollPane1.setViewportView(Tree);
		jScrollPane1.setPreferredSize(dim);
		// jScrollPane1.setPreferredSize(new Dimension(250, 400));
		Tree.getAccessibleContext().setAccessibleName("");
		Tree.getAccessibleContext().setAccessibleDescription("");

		add(jScrollPane1);
	}

	public void setDimension(Dimension dim) {
		// TODO make this work
	}

	public void refreshComp(ArrayList<String> newList, String r) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(r);
		DefaultTreeModel model = new DefaultTreeModel(root);
		model.setRoot(root);
		model.reload();
		Tree.setModel(model);

		for (int i = 0; i < newList.size(); i++) {
			updateTree(newList.get(i));
		}
	}

	public void updateTree(final String nodeToAdd) {
		DefaultTreeModel model = (DefaultTreeModel) Tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) Tree.getModel().getRoot();
		DefaultMutableTreeNode child = new DefaultMutableTreeNode(nodeToAdd);
		model.insertNodeInto(child, root, root.getChildCount());
		Tree.scrollPathToVisible(new TreePath(child.getPath()));
	}

	public void fireDetailEvent(DetailEvent event) {
		Object[] listeners = listenerList.getListenerList();

		for (int i = 0; i < listeners.length; i += 2) {
			if (listeners[i] == DetailListener.class) {
				((DetailListener) listeners[i + 1]).detailEventOccurred(event);
			}
		}
	}

	public void testButtonPressAction() {

	}

	public void addDetailListener(DetailListener listener) {
		listenerList.add(DetailListener.class, listener);
	}

	public void removeDetailListener(DetailListener listener) {
		listenerList.remove(DetailListener.class, listener);
	}
}
