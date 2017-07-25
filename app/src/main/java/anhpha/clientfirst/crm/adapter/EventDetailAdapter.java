package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MEventDetail;

/**
 * Created by mc975 on 2/6/17.
 */
public class EventDetailAdapter extends BaseAdapter {
    private List<MEventDetail> activityItemList;
    private Context context;

    public EventDetailAdapter(List<MEventDetail> activityItemList, Context context) {
        this.activityItemList = activityItemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (activityItemList == null) ? 0 : activityItemList.size();
    }

    public MEventDetail getItem(int i) {
        return activityItemList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;

        ViewHolder holder = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.event_detail_list_row, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.address = (TextView) view.findViewById(R.id.address);
            holder.status = (TextView) view.findViewById(R.id.status);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MEventDetail activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getClient_name());
        holder.address.setText((activityItem.getAddress()));
       switch (activityItem.getEvent_detail_status_id()){
           case 1:
               holder.status.setText(context.getResources().getString(R.string.invite));
               holder.status.setTextColor(context.getResources().getColor(R.color.colorApp));
               break;
           case 2:
               holder.status.setText(context.getResources().getString(R.string.cancel));
               holder.status.setTextColor(context.getResources().getColor(R.color.red));
               break;
           case 3:
               holder.status.setText(context.getResources().getString(R.string.confirm));
               holder.status.setTextColor(context.getResources().getColor(R.color.green));
               break;
           case 4:
               holder.status.setText(context.getResources().getString(R.string.attend));
               holder.status.setTextColor(context.getResources().getColor(R.color.orange));
               break;
            default:
                holder.status.setText(context.getResources().getString(R.string.invite));
                holder.status.setTextColor(context.getResources().getColor(R.color.colorApp));
                break;
       }

        return view;
    }

    static class ViewHolder {
        public TextView name, status, address;
    }
    public void setActivityItemList(List<MEventDetail> activityItemList) {
        this.activityItemList = activityItemList;
    }

}
