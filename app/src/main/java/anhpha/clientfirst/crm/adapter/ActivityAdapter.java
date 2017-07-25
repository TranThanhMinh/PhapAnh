package anhpha.clientfirst.crm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MActivityItem;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder>{
    private List<MActivityItem> activityItemList;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user_name, client_name, content_activity, time;
        public  ImageButton imageButton;

        public MyViewHolder(View view) {
            super(view);
            user_name = (TextView) view.findViewById(R.id.user_name);
            client_name= (TextView) view.findViewById(R.id.client_name);
            content_activity = (TextView) view.findViewById(R.id.content_activity);
            time = (TextView) view.findViewById(R.id.time);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        }
    }

    public MActivityItem getItem(int position) {
        return activityItemList.get(position);
    }

    public ActivityAdapter(List<MActivityItem> activityItemList) {
        this.activityItemList = activityItemList;
    }

    public void setActivityItemList(List<MActivityItem> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MActivityItem activityItem = activityItemList.get(position);
        holder.content_activity.setText(activityItem.getActivity_content());
        holder.client_name.setText(activityItem.getClient_name());
        holder.user_name.setText(activityItem.getUser_name());
        holder.time.setText(Utils.formatTime(activityItem.getActivity_date()));

        switch (activityItem.getActivity_type()){
            case 1:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_99);
                break;
            case 2:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_29);
                holder.content_activity.setText(Utils.formatCurrency(activityItem.getActivity_amount()));
                break;
            case 3:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_28);
                break;
            case 9:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_31);
                holder.content_activity.setText(activityItem.getActivity_content());
                break;
            case 5:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_26);
                // holder.content_activity.setText(activityItem.getActivity_phone());
                holder.content_activity.setText("");
                break;
            case 6:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_32);
                break;
            case 7:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_58);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
