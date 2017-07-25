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

/**
 * Created by mc975 on 2/6/17.
 */
public class EventAdapter extends BaseAdapter {
    private List<MEvent> activityItemList;
    private Context context;

    public EventAdapter(List<MEvent> activityItemList, Context context) {
        this.activityItemList = activityItemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return (activityItemList == null) ? 0 : activityItemList.size();
    }

    public MEvent getItem(int i) {
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
            view = inflater.inflate(R.layout.communication_list_row, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.content = (TextView) view.findViewById(R.id.content);
            holder.date = (TextView) view.findViewById(R.id.date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        MEvent activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getEvent_name());
        holder.content.setText(Html.fromHtml(activityItem.getEvent_content()));
        holder.date.setText(context.getString(R.string.date)+ ": "+ activityItem.getDate_begin());

        return view;
    }

    static class ViewHolder {
        public TextView name, content, date;
    }
    public void setActivityItemList(List<MEvent> activityItemList) {
        this.activityItemList = activityItemList;
    }

}
