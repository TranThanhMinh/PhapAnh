package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MId;
import anhpha.clientfirst.crm.model.MRequestBody;
import anhpha.clientfirst.crm.model.MUser;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.treeviewa.TreeViewData;
import anhpha.clientfirst.crm.treeviewa.TreeViewLists;
import anhpha.clientfirst.crm.treeviewa.TreeViewNode;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.TokenUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends BaseAppCompatActivity implements AdapterView.OnItemClickListener, Callback<MAPIResponse<List<MUser>>>, View.OnClickListener  {

    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.listView)
    ListView listView;
    List<MUser> mUsers;
    private TreeListAdapter mAdapter;

    ArrayList<TreeViewData> dataList = new ArrayList<TreeViewData>();
    ArrayList<TreeViewNode> nodes = new ArrayList<TreeViewNode>();
    ArrayList<TreeViewNode> displayNodes = new ArrayList<TreeViewNode>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_users);
        ButterKnife.bind(this);
        Preferences preferences = new Preferences(mContext);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_users);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        MRequestBody mRequestBody = new MRequestBody();
        mRequestBody.setFrom_date(Utils.getFromDate());
        mRequestBody.setTo_date(Utils.getToDate());
        GetRetrofit().create(ServiceAPI.class)
                .getUsers(preferences.getStringValue(Constants.TOKEN, "")
                        , preferences.getIntValue(Constants.USER_ID, 0)
                        , preferences.getIntValue(Constants.PARTNER_ID, 0)
                )
                .enqueue(this);

        setProgressBarIndeterminateVisibility(true);
        setProgressBarVisibility(true);
        box.showLoadingLayout();

        LogUtils.d(TAG, "getUserActivities ", "start");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                List<MId> mIds = new ArrayList<>();
                for (MUser it : mUsers){
                    if(it.is_select()){
                        mIds.add(new MId(it.getUser_id()));
                     }
                }

                setResult(Constants.RESULT_USERS,new Intent().putExtra("mIds", (Serializable) mIds));
                finish();
            return true;

            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResponse(Call<MAPIResponse<List<MUser>>> call, Response<MAPIResponse<List<MUser>>> response) {
        LogUtils.api(TAG, call, (response.body()));
        box.hideAll();
        TokenUtils.checkToken(mContext,response.body().getErrors());
        mUsers = response.body().getResult();

        MUser mUser0 = new MUser();
        mUser0.setUser_id(0);
        mUser0.setLeve(0);
        mUser0.setUser_name(getResources().getString(R.string.all));
        mUsers.add(0,mUser0);

        dataList = TreeViewLists.LoadInitialData(mUsers);

        nodes = TreeViewLists.LoadInitialNodes(dataList);

        LoadDisplayList();

        mAdapter = new TreeListAdapter();
        listView.setAdapter(mAdapter);
    }

    @Override
    public void onFailure(Call<MAPIResponse<List<MUser>>> call, Throwable t) {
        LogUtils.d(TAG, "getUserActivities ", t.toString());
        box.hideAll();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        TreeViewNode node = displayNodes.get(i);
        //showMessage(node.getNodeName());
    }

    private static class TreeListViewHolder {
        public ImageView arrow;
        public TextView content;
        public CheckBox checkBox;
    }

    private class TreeListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return displayNodes.size();
        }

        @Override
        public TreeViewNode getItem(int position) {
            return displayNodes.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            TreeListViewHolder holder = null;

            if (convertView == null)
            {
                convertView = getLayoutInflater().inflate(R.layout.tree_view_cell, parent, false);

                holder = new TreeListViewHolder();
                holder.content = (TextView) convertView.findViewById(R.id.content);
                holder.arrow = (ImageView) convertView.findViewById(R.id.arrow);
                holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox);

                ((ImageView) convertView.findViewById(R.id.arrow)).setOnClickListener(mArrowClickListener);
                holder.content.setOnClickListener(mArrowClickListener);

                convertView.setTag(holder);
            } else
            {
                holder = (TreeListViewHolder) convertView.getTag();
            }
            final TreeListViewHolder finalHolder = holder;
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectStaff(position,  finalHolder.checkBox.isChecked());

                }
            } );

            TreeViewNode node = displayNodes.get(position);
            holder.content.setText(node.getNodeName());

            holder.checkBox.setChecked(node.is_select());

            if (node.getIsExpanded())
            {
                holder.arrow.setImageResource(R.mipmap.ic_dms_90);
            }
            else
            {
                holder.arrow.setImageResource(R.mipmap.ic_dms_89);
            }

            int lvl = node.getNodeLevel();
            int newWidth = (lvl * 30) + 1;
            ((TextView) convertView.findViewById(R.id.spacer)).getLayoutParams().width = newWidth;
            ((TextView) convertView.findViewById(R.id.spacer)).requestLayout();

            return convertView;
        }
    }

    public ListView getListView() {
        return listView;
    }

    private View.OnClickListener mArrowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int position = getListView().getPositionForView(v);


            if (position != ListView.INVALID_POSITION) {
                TreeViewNode node = displayNodes.get(position);
                boolean is_select = false;
                MId mid  = new MId(displayNodes.get(position).getUser_id());

                if (node.getIsExpanded())
                {
                    node.setIsExpanded(false);
                    is_select = false;
                }
                else
                {
                    if (node.getNodeChildren() != null) {
                        node.setIsExpanded(true);
                        is_select = true;
                    }
                }

                for (MUser it :  mUsers){
                    if(it.getUser_id() == mid.getId()){
                        it.setIs_select(is_select);
                        it.setIs_expanded(is_select);
                        break;
                    }

                }
                LoadDisplayList();
                mAdapter.notifyDataSetChanged();
            }
        }
    };

    private void LoadDisplayList()
    {
        displayNodes = new ArrayList<TreeViewNode>();
        for(int i = 0; i < nodes.size(); i++)
        {
            TreeViewNode node = nodes.get(i);
            displayNodes.add(node);

            if (node.getIsExpanded())
            {
                ArrayList<TreeViewNode> children = node.getNodeChildren();
                if (children != null)
                    if (children.size() != 0)
                        AddChildrenToList(children);
            }
        }
    }
    private void AddChildrenToList(ArrayList<TreeViewNode> children)
    {
        if (children == null) return;

        for(int i = 0; i < children.size(); i++)
        {
            TreeViewNode node = children.get(i);
            displayNodes.add(node);

            Log.v("addchildrentolist", String.format("%d %s %d", i, node.getNodeName(), children.size()));

            if (node.getIsExpanded())
            {
                ArrayList<TreeViewNode> grandChildren = node.getNodeChildren();
                if (grandChildren != null)
                    AddChildrenToList(grandChildren);
            }
        }
    }
    private void selectStaff(int index,boolean is_select){
        MId mid =new MId(displayNodes.get(index).getUser_id());
        boolean is_set = false;
        int leve  = -1;
        int user_id = mid.getId();
        for (MUser it: mUsers){

            if(leve >= it.getLeve() && user_id != 0){
                break;
            }

            if( is_set ){
                it.setIs_select(is_select);
                if(is_select){
                    it.setIs_expanded(is_select);
                }
            }
            if(it.getUser_id() == mid.getId()){
                it.setIs_select(is_select);
                if(is_select){
                    it.setIs_expanded(is_select);
                }
                leve = it.getLeve();
                is_set = true;
            }
        }
        loadData();
    }

    private void loadData() {
        dataList = TreeViewLists.LoadInitialData(mUsers);

        nodes = TreeViewLists.LoadInitialNodes(dataList);

        LoadDisplayList();

        mAdapter.notifyDataSetChanged();
    }

}
