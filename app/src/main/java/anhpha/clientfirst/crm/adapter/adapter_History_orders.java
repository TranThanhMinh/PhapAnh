package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Timer;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.activity.Edit_status_contract_activity;
import anhpha.clientfirst.crm.model.History_order;
import anhpha.clientfirst.crm.model.Photo;
import anhpha.clientfirst.crm.swipe.SwipeRevealLayout;


public class adapter_History_orders extends RecyclerView.Adapter<adapter_History_orders.MyViewHolder> {

    private List<History_order> list;
    private Context context;
    public static List<Photo> lvImage;
    public static List<Photo> list_photo;
    private float downX, downY, upX, upY;

    Timer timer = new Timer();
    Handler handler = new Handler();


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTime, tvPersonnel, tvStatus, tvNote, tvUpdate_history;
        public RecyclerView lvPhoto;
        public LinearLayout lay_display;
        SwipeRevealLayout swipeRevealLayout;
        View view;

        public MyViewHolder(View v) {
            super(v);
            tvUpdate_history = (TextView) v.findViewById(R.id.tvUpdate_history);
            tvTime = (TextView) v.findViewById(R.id.tvTime);
            tvPersonnel = (TextView) v.findViewById(R.id.tvPersonnel);
            tvStatus = (TextView) v.findViewById(R.id.tvStatus);
            tvNote = (TextView) v.findViewById(R.id.tvNote);
            lvPhoto = (RecyclerView) v.findViewById(R.id.lvPhoto);
            lay_display = (LinearLayout) v.findViewById(R.id.lay_display);
            swipeRevealLayout = (SwipeRevealLayout) v.findViewById(R.id.swipe_layout_1);
            view = (View) v.findViewById(R.id.view);


        }

    }

    public adapter_History_orders(Context context, List<History_order> list) {
        this.list = list;
        this.context = context;
        lvImage = null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order_history, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final History_order p = list.get(position);
        final int MIN_DISTANCE = 150;
        holder.tvTime.setText(p.getCreateDate());
        holder.tvPersonnel.setText(p.getUserName());
        holder.tvStatus.setText(p.getOrderStatusName());
        holder.tvNote.setText(p.getNote());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL
                , false);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.lvPhoto.setHasFixedSize(true);
        holder.lvPhoto.setLayoutManager(layoutManager);
        final List<Photo> list = p.getPhotos();
        adapter_Photo adapter = new adapter_Photo(context, list);
        holder.lvPhoto.setAdapter(adapter);
        holder.lvPhoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                holder.tvUpdate_history.setVisibility(View.GONE);
                //holder.swipeRevealLayout.close(true);
                timer.cancel();
//                timer = new Timer();
//                timer.schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                holder.tvUpdate_history.setVisibility(View.VISIBLE);
//                            }
//                        });
//                    }
//                },600*2);
                return false;
            }
        });

        holder.lay_display.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                holder.tvUpdate_history.setVisibility(View.VISIBLE);
                return false;
            }
        });
        holder.tvUpdate_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Edit_status_contract_activity.class);
                Bundle b = new Bundle();
                lvImage = list;
                b.putString("note", p.getNote());
                b.putString("status", p.getOrderStatusName());
                b.putInt("StatusId", p.getOrderStatusId());
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });


    }
    @Override
    public int getItemCount() {
        return list.size();
    }
}
