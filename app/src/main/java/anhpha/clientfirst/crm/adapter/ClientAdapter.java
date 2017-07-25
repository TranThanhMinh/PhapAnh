package anhpha.clientfirst.crm.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.activity.CallActivity;
import anhpha.clientfirst.crm.activity.CheckInventoryActivity;
import anhpha.clientfirst.crm.activity.CheckinActivity;
import anhpha.clientfirst.crm.activity.ClientActivity;
import anhpha.clientfirst.crm.activity.EmailActivity;
import anhpha.clientfirst.crm.activity.EventsClientActivity;
import anhpha.clientfirst.crm.activity.LabelsActivity;
import anhpha.clientfirst.crm.activity.OrderActivity;
import anhpha.clientfirst.crm.activity.WorkActivity;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientLabel;
import anhpha.clientfirst.crm.utils.Utils;

/**
 * Created by mc975 on 2/6/17.
 */
public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyViewHolder>{
    private List<MClient> activityItemList;
    public Context mContext;
    private  boolean is_distance = false;
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView address, client_name, distance;
        public ImageButton imageButton,imageButton2,imageButton3,imageButton4,imageButton5,imageButton6,imageButton7,imageButton8;
        public LinearLayout linearLayout2;
        public ImageView im_activity;
        public LinearLayout linearLayout3;
        public MyViewHolder(View view) {
            super(view);
            client_name = (TextView) view.findViewById(R.id.client_name);
            address= (TextView) view.findViewById(R.id.address);
            distance = (TextView) view.findViewById(R.id.distance);
            linearLayout2 = (LinearLayout) view.findViewById(R.id.linearLayout2);
            linearLayout3 = (LinearLayout) view.findViewById(R.id.linearLayout3);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
            imageButton2 = (ImageButton) view.findViewById(R.id.imageButton2);
            imageButton3 = (ImageButton) view.findViewById(R.id.imageButton3);
            imageButton4 = (ImageButton) view.findViewById(R.id.imageButton4);
            imageButton5 = (ImageButton) view.findViewById(R.id.imageButton5);
            imageButton6 = (ImageButton) view.findViewById(R.id.imageButton6);
            imageButton7 = (ImageButton) view.findViewById(R.id.imageButton7);
            imageButton8 = (ImageButton) view.findViewById(R.id.imageButton8);
            im_activity = (ImageView) view.findViewById(R.id.im_activity);
        }
    }

    public ClientAdapter(Context context, List<MClient> activityItemList,boolean is_distance) {
        this.activityItemList = activityItemList;
        this.mContext = context;
        this.is_distance = is_distance;
    }

    public void setIs_distance(boolean is_distance) {
        this.is_distance = is_distance;
    }

    public void setActivityItemList(List<MClient> activityItemList) {
        this.activityItemList = activityItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.client_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)  {
        final MClient activityItem = activityItemList.get(position);
        holder.address.setText(activityItem.getAddress());
        holder.client_name.setText(activityItem.getClient_name());
        if(is_distance){
            holder.distance.setText(Utils.convertDistance((int) activityItem.getDistance()));
        }else{
            holder.distance.setText("");
        }
          if(activityItem.is_activity()){
              holder.im_activity.setVisibility(View.VISIBLE);
                }else{
              holder.im_activity.setVisibility(View.GONE);
                }

        holder.address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click(view, activityItem);
            }
        });
        holder.client_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Click(view,activityItem);
            }
        });

        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (MClient c : activityItemList) {
                    c.setIs_select(false);
                }
                if (holder.linearLayout3.getVisibility() == View.GONE) {
                    holder.linearLayout3.setVisibility(View.VISIBLE);
                    activityItemList.get(position).setIs_select(true);
                } else {
                    holder.linearLayout3.setVisibility(View.GONE);
                    activityItemList.get(position).setIs_select(false);
                }
                notifyDataSetChanged();
            }
        });

        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, OrderActivity.class).putExtra("mClient",activityItem));
            }
        });
        holder.imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, WorkActivity.class).putExtra("mClient",activityItem));
            }
        });
        holder.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CheckinActivity.class).putExtra("mClient",activityItem));
            }
        });
        holder.imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, CallActivity.class).putExtra("mClient",activityItem));
            }
        });
        holder.imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, EmailActivity.class).putExtra("mClient",activityItem));
            }
        });
        holder.imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, EventsClientActivity.class).putExtra("mClient",activityItem));
            }
        });
        holder.imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, LabelsActivity.class).putExtra("mClient",activityItem));
            }
        });


        if(activityItem.is_select()){
            if(activityItem.getClient_structure_id() == 2) {
                holder.linearLayout3.setVisibility(View.VISIBLE);
                holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_4));
            }else {
                holder.linearLayout3.setVisibility(View.VISIBLE);
                holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_2));
            }
        }else{
            if(activityItem.getClient_structure_id() == 2) {
                if (activityItem.getClient_type_id() == 1) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_5));
                } else if (activityItem.getClient_type_id() == 2) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_8));
                } else {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_40));
                }
            }else{
                if (activityItem.getClient_type_id() == 1) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_12));
                } else if (activityItem.getClient_type_id() == 2) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_86));
                } else {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageButton.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_crm_26));
                }
            }
        }

        if(activityItem.getLabels().size()>0){
            holder.linearLayout2.setVisibility(View.VISIBLE);
        }else {
            holder.linearLayout2.setVisibility(View.GONE);
        }

        holder.linearLayout2.removeAllViews();
        int i = 0 ;
        for (MClientLabel mClientLabel : activityItem.getLabels()){

            if(i < 8){
            Button valueTV = new Button(mContext);
            if(mClientLabel.getHex().isEmpty())
                valueTV.setBackgroundColor(Color.GRAY);
            else
                valueTV.setBackgroundColor(Color.parseColor(mClientLabel.getHex()));
                valueTV.setId((int) System.currentTimeMillis() + new Random().nextInt(255));
                valueTV.setLayoutParams(new ActionBar.LayoutParams(Utils.getWidth(mContext) / 10, Utils.getWidth(mContext)/38));
                Button valueTV2 = new Button(mContext);
                valueTV2.setId((int) System.currentTimeMillis() + new Random().nextInt(255));
                valueTV2.setBackgroundColor(Color.WHITE);
                valueTV2.setLayoutParams(new ActionBar.LayoutParams(5, Utils.getWidth(mContext)/38));
                holder.linearLayout2.addView(valueTV);
                holder.linearLayout2.addView(valueTV2);
                i++;
            }
        }
    }
    public void Click(View view,MClient activityItem) {
        switch (view.getId()){
            case R.id.address:
            case R.id.client_name:
            case R.id.distance:
                Intent intent = new Intent(mContext, ClientActivity.class).putExtra("mClient",activityItem);
                mContext.startActivity(intent);
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
