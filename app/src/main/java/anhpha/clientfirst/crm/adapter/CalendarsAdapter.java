package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.interfaces.AdapterInterface;
import anhpha.clientfirst.crm.model.MCalendar;

/**
 * Created by mc975 on 2/6/17.
 */
public class CalendarsAdapter extends RecyclerView.Adapter<CalendarsAdapter.MyViewHolder>{
    private List<MCalendar> activityItemList;
    public Context mContext;
    AdapterInterface buttonListener;
    public MCalendar get(int position) {
        return activityItemList.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView address, client_name,delete,date;
        public MyViewHolder(View view) {
            super(view);
            client_name = (TextView) view.findViewById(R.id.client_name);
            address = (TextView) view.findViewById(R.id.address);
            delete = (TextView) view.findViewById(R.id.delete);
            date = (TextView) view.findViewById(R.id.tvDate);
        }

    }

    public CalendarsAdapter(Context context, List<MCalendar> activityItemList, AdapterInterface buttonListener) {
        this.activityItemList = activityItemList;
        this.mContext = context;
        this.buttonListener = buttonListener;
    }

    public void setActivityItemList(List<MCalendar> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choose_calendar_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        final MCalendar activityItem = activityItemList.get(position);
        holder.address.setText(activityItem.getCalendar_title());
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(Long.parseLong(activityItem.getCalendar_date()));
        String date = (c.get(Calendar.DAY_OF_MONTH))+ "/"+ (c.get(Calendar.MONTH) + 1) + "/"+(c.get(Calendar.YEAR)) + " " + (c.get(Calendar.HOUR_OF_DAY) + ":" + (c.get(Calendar.MINUTE)));
        holder.date.setText(date);
        holder.client_name.setText(activityItem.getCalendar_content());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.buttonPressed(3, position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
