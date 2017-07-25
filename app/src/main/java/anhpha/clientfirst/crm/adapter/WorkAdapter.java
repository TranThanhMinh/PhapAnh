package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.interfaces.AdapterInterface;
import anhpha.clientfirst.crm.model.MWorkUser;

/**
 * Created by mc975 on 2/6/17.
 */
public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.MyViewHolder>{
    private List<MWorkUser> activityItemList;
    public Context mContext;
    public  int user_id = 0;
    AdapterInterface buttonListener;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView content, user, time, type,title , done, delete, client;
        public ImageButton imageButton;
        public LinearLayout linearLayout2;
        public RelativeLayout views,view1;
        public MyViewHolder(View view) {
            super(view);
            views = (RelativeLayout) view.findViewById(R.id.view);
            view1 = (RelativeLayout) view.findViewById(R.id.view1);
            linearLayout2 = (LinearLayout) view.findViewById(R.id.linearLayout2);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
            type = (TextView) view.findViewById(R.id.type);
            time= (TextView) view.findViewById(R.id.time);
            title= (TextView) view.findViewById(R.id.title);
            user = (TextView) view.findViewById(R.id.user);
            content = (TextView) view.findViewById(R.id.content);
            done = (TextView) view.findViewById(R.id.done);
            delete = (TextView) view.findViewById(R.id.delete);
            client = (TextView) view.findViewById(R.id.client);
        }
    }

    public WorkAdapter(Context context, List<MWorkUser> activityItemList, AdapterInterface buttonListener) {
        this.activityItemList = activityItemList;
        this.mContext = context;
        this.buttonListener = buttonListener;
        Preferences preferences = new Preferences(mContext);
        user_id = preferences.getIntValue(Constants.USER_ID,0);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.work_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MWorkUser activityItem = activityItemList.get(position);
        holder.content.setText(activityItem.getContent_work());
        holder.time.setText(activityItem.getWork_begin_date());
        holder.user.setText(activityItem.getUser_name());
        if(activityItem.getUser_id() != user_id){
            holder.delete.setVisibility(View.GONE);
        }
        holder.done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.buttonPressed(2, position);
            }
        });
        holder.view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.buttonPressed(0, position);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonListener.buttonPressed(3, position);
            }
        });
        if (activityItem.getStatus_id() == 2)
            holder.views.setVisibility(View.VISIBLE);
        else {
            holder.views.setVisibility(View.GONE);
        }
        if(activityItem.getClients().size()>0){
            holder.client.setVisibility(View.VISIBLE);
            holder.client.setText(" > "+ activityItem.getClients().get(0).getClient_name());
        }else{
            holder.client.setVisibility(View.GONE);
        }
        if (activityItem.getStatus_date_id() == 1){
            holder.linearLayout2.setVisibility(View.VISIBLE);
            holder.title.setText(mContext.getString(R.string.coming_of_date));
            holder.time.setTextColor(Color.BLACK);
            holder.user.setTextColor(Color.BLACK);
            holder.content.setTextColor(Color.BLACK);
        }
        else if(activityItem.getStatus_date_id() == -1){
            holder.linearLayout2.setVisibility(View.VISIBLE);
            holder.title.setText(mContext.getString(R.string.out_of_date));
            holder.time.setTextColor(Color.RED);
            holder.user.setTextColor(Color.RED);
            holder.content.setTextColor(Color.RED);
        }else if(activityItem.getStatus_date_id() == 3){
            holder.linearLayout2.setVisibility(View.GONE);
            holder.title.setText(mContext.getString(R.string.out_of_date));
            holder.time.setTextColor(Color.RED);
            holder.user.setTextColor(Color.RED);
            holder.content.setTextColor(Color.RED);
        }
        else{
            holder.linearLayout2.setVisibility(View.GONE);
            holder.time.setTextColor(Color.BLACK);
            holder.user.setTextColor(Color.BLACK);
            holder.content.setTextColor(Color.BLACK);
        }
        //        1	Hop	NULL	NULL	NULL	NULL
//        2	Vieng tham	NULL	NULL	NULL	NULL
//        3	Dien thoai	NULL	NULL	NULL	NULL
//        4	don hang	NULL	NULL	NULL	NULL
//        5	kiem kho	NULL	NULL	NULL	NULL
//        6	khac	NULL	NULL	NULL	NULL
//        7	Email	NULL	NULL	NULL	NULL
//        9	Event	NULL	NULL	NULL	NULL
//        10	Sinh nhat	NULL	NULL	NULL	NULL
//        11	thu phi	NULL	NULL	NULL	NULL
//        12	giao hang	NULL	NULL	NULL	NULL
//        13	ky hop dong	NULL	NULL	NULL	NULL

        switch (activityItem.getWork_user_type_id()){
            case 1:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_30);
                holder.type.setText(mContext.getString(R.string.meeting));
                break;
            case 2:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_99);
                holder.type.setText(mContext.getString(R.string.meet));
                break;
            case 3:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_28);
                holder.type.setText(mContext.getString(R.string.call));
                break;
            case 4:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_29);
                holder.type.setText(mContext.getString(R.string.get_order));
                break;

            case 6:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_60);
                holder.type.setText(mContext.getString(R.string.other));
                break;
            case 7:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_58);
                holder.type.setText(mContext.getString(R.string.email));
                break;
              case 9:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_31);
                holder.type.setText(mContext.getString(R.string.event));
                break;
            case 10:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_38);
                holder.type.setText(mContext.getString(R.string.happy_birtday));
                break;
            case 11:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_93);
                holder.type.setText(mContext.getString(R.string.get_free));
                break;
            case 12:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_94);
                holder.type.setText(mContext.getString(R.string.delivery_do));
                break;
            case 13:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_13);
                holder.type.setText(mContext.getString(R.string.order_get));
                break;
            default:
                holder.imageButton.setImageResource(R.mipmap.ic_crm_60);
                holder.type.setText(mContext.getString(R.string.other) + activityItem.getWork_user_type_id());
                break;
        }
    }
    @Override
    public int getItemCount() {
        return activityItemList.size();
    }
}
