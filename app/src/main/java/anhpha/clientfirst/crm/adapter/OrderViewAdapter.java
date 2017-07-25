package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MContract;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class OrderViewAdapter extends BaseAdapter{
    private Context mContext;
    private List<MContract> mData;
    public OrderViewAdapter(Context context, List<MContract> data ) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return (mData == null) ? 0 : mData.size();
    }

    public MContract getItem(int i) {
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
            view = inflater.inflate(R.layout.order_view_list_row, parent, false);

            holder = new ViewHolder();
            holder.name = (TextView) view.findViewById(R.id.name);
            holder.note = (TextView) view.findViewById(R.id.note);
            holder.number= (TextView) view.findViewById(R.id.number);
            holder.total= (TextView) view.findViewById(R.id.total);
            holder.price= (TextView) view.findViewById(R.id.price);
            holder.type= (TextView) view.findViewById(R.id.type);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MContract activityItem = mData.get(position);
        MContract p = Utils.getPriceContract(activityItem, mContext);
        holder.name.setText(activityItem.getContract_name());
        holder.type.setText(activityItem.getPrice_name().isEmpty()?mContext.getString(R.string.value):activityItem.getPrice_name());

        if(!Utils.isEmpty(activityItem.getNote())){
            holder.note.setText(activityItem.getNote());
            holder.note.setVisibility(View.VISIBLE);
        }
        if (p.getNumber() == 0 && p.getNumber_group()!=0) {
            holder.number.setText(p.getContract_unit_group_name());
            holder.total.setText(Utils.formatCurrency(p.getPrice_group_number()));
            holder.price.setText(Utils.formatCurrency(p.getPrice_group()));

        } else if (p.getNumber() != 0 && p.getNumber_group()!=0) {
            holder.number.setText(p.getContract_unit_group_name() + "\n" + p.getContract_unit_name());
            holder.total.setText(Utils.formatCurrency(p.getPrice_group_number()) + "\n" + Utils.formatCurrency(p.getPrice_number()));
            holder.price.setText(Utils.formatCurrency(p.getPrice_group()) + "\n" + Utils.formatCurrency(p.getPrice()));
        }else if (p.getNumber() != 0 && p.getNumber_group()==0) {
            holder.number.setText( p.getContract_unit_name());
            holder.total.setText(Utils.formatCurrency(p.getPrice_number()));
            holder.price.setText(Utils.formatCurrency(p.getPrice()));

        }else if (p.getNumber() == 0 && p.getNumber_group()==0) {
            holder.number.setText("-");
            holder.total.setText("-");
            holder.price.setText("-");
        }


        return view;
    }

    static class ViewHolder {
        public TextView name, number, total, price,note,type;
    }
    public void setActivityItemList(List<MContract> activityItemList) {
        this.mData = activityItemList;
    }
}
