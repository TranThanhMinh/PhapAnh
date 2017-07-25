package anhpha.clientfirst.crm.treeviewa;

/**
 * Created by cindyoakes on 9/11/16.
 */

import java.util.ArrayList;

public class TreeViewNode
{
    private int _nodeLevel;
    private int user_id;
    private boolean _isExpanded;
    private boolean is_select;
    private String _nodeName;
    private ArrayList<TreeViewNode> _nodeChildren;

    public TreeViewNode() {}

    public TreeViewNode(int user_id, int nodeLevel, boolean isExpanded, String nodeName, ArrayList<TreeViewNode> nodeChildren)
    {
        this.user_id = user_id;
        this._nodeLevel = nodeLevel;
        this._isExpanded = isExpanded;
        this._nodeName = nodeName;
        this._nodeChildren = nodeChildren;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getNodeLevel()
    {
        return _nodeLevel;
    }

    public void setNodeLevel(int nodeLevel)
    {
        this._nodeLevel = nodeLevel;
    }

    public boolean getIsExpanded()
    {
        return _isExpanded;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public void setIsExpanded(boolean isExpanded)
    {
        this._isExpanded = isExpanded;
    }

    public String getNodeName()
    {
        return _nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this._nodeName = nodeName;
    }

    public ArrayList<TreeViewNode> getNodeChildren()
    {
        return _nodeChildren;
    }

    public void setNodeChildern(ArrayList<TreeViewNode> nodeChildren)
    {
        this._nodeChildren = nodeChildren;
    }
}
