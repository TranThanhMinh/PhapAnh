package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MUser;

/**
 * Created by mc975 on 2/6/17.
 */
public class ChooseUserAdapter extends RecyclerView.Adapter<ChooseUserAdapter.MyViewHolder>{
    private List<MUser> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView address, client_name;
        public MyViewHolder(View view) {
            super(view);
            client_name = (TextView) view.findViewById(R.id.client_name);
            address= (TextView) view.findViewById(R.id.address);

        }

    }

    public ChooseUserAdapter(Context context, List<MUser> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    public void setActivityItemList(List<MUser> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_user_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        final MUser activityItem = activityItemList.get(position);
        holder.client_name.setText(activityItem.getUser_name());
    }
    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
