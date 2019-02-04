package EBM_tool.gui;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import EBM_tool.DetailListeners.DetailEvent;
import EBM_tool.DetailListeners.DetailListener;

public class QuestionSelect extends JPanel{//select the answer to the question
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//private JPanel pane;
	private JLabel question;
	private JComboBox<String> dropDownMenu;
	
	public QuestionSelect(String q, ArrayList<String> options, int selectedItemIndex) {
		setPreferredSize(new Dimension(400, 90));
		initialize(q, options);
		setSelectedItem(selectedItemIndex);
	}
	
	private String[] putOptions(ArrayList<String> options) {
		//final int size = options.size();
		String[] ops = new String[options.size()];
		for(int i = 0; i < options.size(); i++) {
			ops[i] = options.get(i);
		}
		return ops;
	}
	
	public void initialize(String q, ArrayList<String> options) {
		//set grid back layout
		setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
		question = new JLabel(q);

		//Listener when the selection changes
		dropDownMenu = new JComboBox<String>(putOptions(options));
		dropDownMenu.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
			       if (event.getStateChange() == ItemEvent.SELECTED) {
			          Object item = event.getItem();
			          fireDetailEvent(new DetailEvent(item, dropDownMenu.getSelectedItem().toString()));
			       }
			    }        
		});
		setBackground(new Color(254, 254, 254));
		
		gc.anchor = GridBagConstraints.LINE_START;
        gc.weightx = 10;
        gc.weighty = 10;
        gc.gridwidth = 3;
        gc.gridx = 1;
        gc.gridy = 0;		
		add(question, gc);
		
		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridwidth = 1;
		gc.gridx = 2;
		add(dropDownMenu, gc);
		
		gc.anchor = GridBagConstraints.LINE_END;
		gc.gridwidth = 1;
		gc.gridx = 3;
	}
	
	public String getSelectedValue() {
		return dropDownMenu.getSelectedItem().toString();
	}
	
	public void fireDetailEvent(DetailEvent event){
        Object[] listeners = listenerList.getListenerList();

        for (int i = 0; i < listeners.length; i += 2){
            if (listeners[i] == DetailListener.class){
                ((DetailListener)listeners[i+1]).detailEventOccurred(event);
            }
        }
    }

    public void addDetailListener (DetailListener listener){
        listenerList.add(DetailListener.class, listener);
    }
    public void removeDetailListener(DetailListener listener){
        listenerList.remove(DetailListener.class, listener);
    }
    public void setSelectedItem(int index) {
    	if(index < dropDownMenu.getComponentCount()) {
    	dropDownMenu.setSelectedIndex(index);
    	}
    }
    public void setSelectedItem(String ans) {
    	
    }
}
