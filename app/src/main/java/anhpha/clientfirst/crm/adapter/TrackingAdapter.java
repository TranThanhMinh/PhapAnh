package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MTracking;
import anhpha.clientfirst.crm.utils.HaversineAlgorithm;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class TrackingAdapter extends RecyclerView.Adapter<TrackingAdapter.MyViewHolder>{
    private List<MTracking> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, time, distance;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            time= (TextView) view.findViewById(R.id.time);
            distance= (TextView) view.findViewById(R.id.distance);
        }
    }

    public TrackingAdapter(Context context, List<MTracking> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tracking_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MTracking activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getUser_name());
        holder.time.setText(Utils.convertTime(activityItem.getCreate_date(), mContext));
        holder.distance.setText(Utils.convertDistance(HaversineAlgorithm.HaversineInM(
                activityItem.getLatitude_partner()
                ,activityItem.getLongitude_partner()
                ,activityItem.getLatitude()
                ,activityItem.getLongitude())));


    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
