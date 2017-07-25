package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.model.MDebt;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class OrderDebtAdapter extends BaseAdapter{
    private Context mContext;
    private List<MDebt> mData;
    public OrderDebtAdapter(Context context, List<MDebt> data ) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public MDebt getItem(int i) {
        return mData.get(i);
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
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.order_debt__list_row, parent, false);

            holder = new ViewHolder();
            holder.status = (TextView) view.findViewById(R.id.status);
            holder.note = (TextView) view.findViewById(R.id.note);
            holder.get= (TextView) view.findViewById(R.id.get);
            holder.date= (TextView) view.findViewById(R.id.date);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MDebt activityItem = mData.get(position);
        holder.status.setText(activityItem.getDebt_status_id() == 1? mContext.getString(R.string.queue_debt):mContext.getString(R.string.debted) );
        holder.note.setText(activityItem.getNote());
        holder.date.setText(activityItem.getDebt_status_id() == 1?activityItem.getDate_plan_debt(): activityItem.getDate_debt());
        holder.get.setText(activityItem.getDebt_status_id() == 1?Utils.formatCurrency(activityItem.getValue_plan_debt()):Utils.formatCurrency(activityItem.getValue_debt()));

        if(activityItem.getDebt_status_id() == 2){
            holder.status.setTextColor(mContext.getResources().getColor(R.color.colorApp) );
            holder.note.setTextColor(mContext.getResources().getColor(R.color.colorApp) );
            holder.date.setTextColor(mContext.getResources().getColor(R.color.colorApp) );
            holder.get.setTextColor(mContext.getResources().getColor(R.color.colorApp) );

        }else {
            holder.status.setTextColor(mContext.getResources().getColor(R.color.dark) );
            holder.note.setTextColor(mContext.getResources().getColor(R.color.dark) );
            holder.date.setTextColor(mContext.getResources().getColor(R.color.dark) );
            holder.get.setTextColor(mContext.getResources().getColor(R.color.dark) );

            Date date1 =  Utils.convertDateTime(activityItem.getDate_plan_debt());
            Date date2 = Calendar.getInstance().getTime();
            if (date1.before(date2))
            {
                holder.status.setTextColor(mContext.getResources().getColor(R.color.red) );
                holder.note.setTextColor(mContext.getResources().getColor(R.color.red) );
                holder.date.setTextColor(mContext.getResources().getColor(R.color.red) );
                holder.get.setTextColor(mContext.getResources().getColor(R.color.red) );            }
            Log.d(date1.toString(),date2.toString());
        }

        return view;
    }

    static class ViewHolder {
        public TextView status, date, get,note;
    }
    public void setActivityItemList(List<MDebt> activityItemList) {
        this.mData = activityItemList;
    }
}
