package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.activity.BaseAppCompatActivity;
import anhpha.clientfirst.crm.activity.LabelActivity;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MLabel;

/**
 * Created by mc975 on 2/6/17.
 */
public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.MyViewHolder>{
    private List<MLabel> activityItemList;
    public Context mContext;
    public MClient mClient;
    private BaseAppCompatActivity.ItemClickListener clickListener;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView name;
        public ImageButton check, edit;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            check= (ImageButton) view.findViewById(R.id.check);
            edit= (ImageButton) view.findViewById(R.id.edit);
            name.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, getAdapterPosition());
        }
    }
    public void setClickListener(BaseAppCompatActivity.ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }
    public LabelAdapter(Context context, List<MLabel> activityItemList, MClient mClient) {
        this.activityItemList = activityItemList;
        this.mContext = context;
        this.mClient = mClient;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.label_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    public void setActivityItemList(List<MLabel> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MLabel activityItem = activityItemList.get(position);
        holder.name.setText(activityItem.getClient_label_name());
        if(!activityItem.getHex().isEmpty()) {
            holder.name.setBackgroundColor(Color.parseColor(activityItem.getHex()));
            holder.check.setBackgroundColor(Color.parseColor(activityItem.getHex()));
        }else {
            holder.name.setBackgroundColor(Color.GRAY);
            holder.check.setBackgroundColor(Color.GRAY);
        }
        if(activityItem.getIs_has()){
            holder.check.setVisibility(View.VISIBLE);
        }else{
            holder.check.setVisibility(View.GONE);
        }

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, LabelActivity.class);
                intent.putExtra( "mLabel",activityItem);
                intent.putExtra( "mClient",mClient);
                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
