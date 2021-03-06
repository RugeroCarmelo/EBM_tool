package EBM_tool.OWL2Prefuse.graph;

import EBM_tool.OWL2Prefuse.OWL2Prefuse.Constants;
import prefuse.Visualization;
import prefuse.action.assignment.ColorAction;
import prefuse.visual.VisualItem;

/**
 * This class is a specific ColorAction for the nodes in the graph.
 * <p/>
 * Project OWL2Prefuse <br/>
 * NodeColorAction.java created 3 januari 2007, 13:37
 * <p/>
 * Copyright &copy 2006 Jethro Borsje
 * @author <a href="mailto:info@jborsje.nl">Jethro Borsje adapted by Tomas</a>
 * @version $$Revision:$$, $$Date:$$
 */

public class NodeColorAction extends ColorAction
{
    /**
     * Creates a new instance of NodeColorAction.
     * @param p_group The data group for which this ColorAction provides the colors.
     * @param p_vis A reference to the visualization processed by this Action.
     */
    public NodeColorAction(String p_group, Visualization p_vis)
    {
        super(p_group, VisualItem.FILLCOLOR);
        m_vis = p_vis;
    }

    /**
     * This method returns the color of the given VisualItem.
     * @param p_item The node for which the color needs to be retreived.
     * @return The color of the given node.
     */
    public int getColor(VisualItem p_item)
    {
        int retval = Constants.NODE_DEFAULT_COLOR;
        
        if (m_vis.isInGroup(p_item, Visualization.SEARCH_ITEMS)) retval = Constants.NODE_COLOR_SEARCH;
        else if (p_item.isHighlighted()) retval = Constants.NODE_COLOR_HIGHLIGHTED;
        else if (p_item.isFixed()) retval = Constants.NODE_COLOR_SELECTED;
        else if (p_item.canGetString("type"))
        {
            if (p_item.getString("type") != null)
            {
            	if (p_item.getString("type").contains("class")) retval = Constants.NODE_COLOR_CLASS;
                else if (p_item.getString("type").contains("individual")) retval = Constants.NODE_COLOR_INDIVIDUAL;
                else if(p_item.getString("type").contains("rule")) retval = Constants.NODE_COLOR_HAS_RULE;
            }
        }
        
        return retval;
    }
}