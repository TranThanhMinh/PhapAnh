package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MColor;

/**
 * Created by mc975 on 2/6/17.
 */
public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.MyViewHolder>{
    private List<MColor> activityItemList;
    public Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name , color;
        public ImageButton check;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            check= (ImageButton) view.findViewById(R.id.check);
            color= (TextView) view.findViewById(R.id.color);

        }
    }

    public void setActivityItemList(List<MColor> activityItemList) {
        this.activityItemList = activityItemList;
    }

    public ColorAdapter(Context context, List<MColor> activityItemList) {
        this.activityItemList = activityItemList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MColor activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getColor_name());
        holder.color.setBackgroundColor(Color.parseColor(activityItem.getHex()));
        if(activityItem.is_select()){
            holder.check.setVisibility(View.VISIBLE);
        }else{
            holder.check.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
