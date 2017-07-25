package anhpha.clientfirst.crm.treeviewa;

/**
 * Created by cindyoakes on 9/11/16.
 */

import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import anhpha.clientfirst.crm.model.MUser;

public class TreeViewLists
{

    public static ArrayList<TreeViewData> LoadInitialData(List<MUser> mUsers)
    {
        ArrayList<TreeViewData> data = new ArrayList<TreeViewData>();

        for(MUser u: mUsers){
            data.add(new TreeViewData(u.getLeve(), u.getUser_name(), u.getUser_id(), u.getParent_id(), u.is_select(), u.is_expanded()));

        }

        return data;
    }

    public static ArrayList<TreeViewNode> LoadInitialNodes(ArrayList<TreeViewData> dataList)
    {
        ArrayList<TreeViewNode> nodes = new ArrayList<TreeViewNode>();

        for(int i = 0; i < dataList.size(); i++)
        {
            TreeViewData data = dataList.get(i);
            if (data.getLevel() != 0) continue;

            Log.v("LoadInitialNodes", data.getName());

            TreeViewNode node = new TreeViewNode();
            node.setNodeLevel(data.getLevel());
            node.setIsExpanded(true);
            node.setNodeName(data.getName());
            node.setIs_select(data.is_select());
            node.setUser_id(data.getID());
            node.setNodeChildern(null);
            int newLevel = data.getLevel() + 1;
            ArrayList<TreeViewNode> children = LoadChildrenNodes(dataList, newLevel, data.getID());
            //node.setNodeChildern(LoadChildrenNodes(dataList, newLevel, data.getID()));
            //if (node.getNodeChildren().size() == 0)
            if (children.size() == 0)
            {
                node.setNodeChildern(null);
            }
            else
            {
                node.setNodeChildern(children);
            }

            nodes.add(node);

        }


        return nodes;
    }

    private static ArrayList<TreeViewNode> LoadChildrenNodes(ArrayList<TreeViewData> dataList, int level, int parentID)
    {
        ArrayList<TreeViewNode> nodes = new ArrayList<TreeViewNode>();

        for(int i = 0; i < dataList.size(); i++)
        {
            TreeViewData data = dataList.get(i);
            if ((data.getLevel() != level) || (data.getParentID() != parentID)) continue;


            TreeViewNode node = new TreeViewNode();
            node.setNodeLevel(data.getLevel());
            node.setNodeName(data.getName());
            node.setIsExpanded(data.is_expanded());
            node.setIs_select(data.is_select());
            node.setUser_id(data.getID());
            int newLevel = level + 1;
            node.setNodeChildern(null);
            ArrayList<TreeViewNode> children = LoadChildrenNodes(dataList, newLevel, data.getID());
            //node.setNodeChildern(LoadChildrenNodes(dataList, newLevel, data.getID()));
            if (children.size() == 0)
            {
                node.setNodeChildern(null);
            }
            else
            {
                node.setNodeChildern(children);
            }

            nodes.add(node);

            Log.v("LoadChildrenNodes", String.format("%s %d",data.getName(), children.size()));
        }

        return nodes;
    }


}
