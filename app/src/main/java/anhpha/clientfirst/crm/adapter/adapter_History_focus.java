package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.net.ParseException;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Focus;

/**
 * Created by MinhTran on 7/24/2017.
 */

public class adapter_History_focus extends RecyclerView.Adapter<adapter_History_focus.MyViewHolder> {
    private Context context;
    private List<Focus> list;

    public adapter_History_focus(Context context, List<Focus> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public adapter_History_focus.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_history_focus, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(adapter_History_focus.MyViewHolder holder, int position) {
        if (position % 2 == 0)
            holder.lay_display.setBackgroundResource(R.color.color1);
        Focus focus = list.get(position);
        if (focus.getFocusTypeId() == 2)
            holder.tvSetupTime.setText(focus.getFocusTypeName());
        else
            holder.tvSetupTime.setText(context.getResources().getString(R.string.srtTo) + " " + convertStringToData(focus.getBeginDate()) + " " + context.getResources().getString(R.string.srtFrom) + " " + convertStringToData(focus.getEndDate()));


        if(focus.getNumberDate()>0)
        holder.tvDate.setText(focus.getNumberDate()+"" + " " + context.getResources().getString(R.string.srtDate));
        holder.tvName.setText(focus.getClientName());
        holder.tvTarget.setText(focus.getFocusTargetName());
        holder.tvStatus.setText(focus.getFocusStatusName());
        if(focus.getNumberOrder()>0){
            holder.imageOrder.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberMeeting()>0){
            holder.imageMeeting.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberCall()>0){
            holder.imageCall.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberEmail()>0){
            holder.imageEmail.setVisibility(View.VISIBLE);
        }
        if(focus.getNumberEvent()>0){
            holder.imageEvent.setVisibility(View.VISIBLE);
        }
    }
    public static String convertStringToData(String stringData)
            throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");//yyyy-MM-dd'T'HH:mm:ss
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");
        Date data = null;
        try {
            data = sdf.parse(stringData);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String formattedTime = output.format(data);
        return formattedTime;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout lay_display;
        private TextView tvSetupTime, tvDate,tvName,tvTarget,tvStatus;
        private ImageView imageOrder,imageMeeting,imageCall,imageEmail,imageEvent;
        public MyViewHolder(View itemView) {
            super(itemView);
            lay_display = (LinearLayout) itemView.findViewById(R.id.lay_display);
            tvSetupTime = (TextView) itemView.findViewById(R.id.tvSetupTime);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
            tvTarget = (TextView) itemView.findViewById(R.id.tvTarget);
            imageOrder = (ImageView) itemView.findViewById(R.id.imageOrder);
            imageMeeting = (ImageView) itemView.findViewById(R.id.imageMeeting);
            imageCall = (ImageView) itemView.findViewById(R.id.imageCall);
            imageEmail = (ImageView) itemView.findViewById(R.id.imageEmail);
            imageEvent = (ImageView) itemView.findViewById(R.id.imageEvent);
        }
    }
}
