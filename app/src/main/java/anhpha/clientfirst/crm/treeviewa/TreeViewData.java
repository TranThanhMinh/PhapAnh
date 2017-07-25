package anhpha.clientfirst.crm.treeviewa;

/**
 * Created by cindyoakes on 9/11/16.
 */

public class TreeViewData
{
    private int _level;
    private String _name;
    private int _id;
    private int _parentId;
    private boolean is_select;
    private boolean is_expanded;

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    public boolean is_expanded() {
        return is_expanded;
    }

    public void setIs_expanded(boolean is_expanded) {
        this.is_expanded = is_expanded;
    }

    public TreeViewData() {}

    public TreeViewData(int level, String name, int id, int parentId,boolean is_select, boolean is_expanded)
    {
        this._level = level;
        this._name = name;
        this._id = id;
        this._parentId = parentId;
        this.is_select = is_select;
        this.is_expanded = is_expanded;
    }

    public int getLevel()
    {
        return _level;
    }

    public void setLevel(int level)
    {
        this._level = level;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        this._name = name;
    }

    public int getID()
    {
        return _id;
    }

    public void setID(int id)
    {
        this._id = id;
    }

    public int getParentID()
    {
        return _parentId;
    }

    public void setParentID(int parentId)
    {
        this._parentId = parentId;
    }

}
