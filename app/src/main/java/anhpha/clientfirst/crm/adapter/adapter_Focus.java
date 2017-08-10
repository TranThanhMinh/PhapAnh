package anhpha.clientfirst.crm.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.activity.CallActivity;
import anhpha.clientfirst.crm.activity.CheckinActivity;
import anhpha.clientfirst.crm.activity.ClientActivity;
import anhpha.clientfirst.crm.activity.EmailActivity;
import anhpha.clientfirst.crm.activity.EventsClientActivity;
import anhpha.clientfirst.crm.activity.HistoryFocusActivity;
import anhpha.clientfirst.crm.activity.LabelsActivity;
import anhpha.clientfirst.crm.activity.OrderActivity;
import anhpha.clientfirst.crm.activity.WorkActivity;
import anhpha.clientfirst.crm.model.Focus;
import anhpha.clientfirst.crm.model.MClient;
import anhpha.clientfirst.crm.model.MClientLabel;
import anhpha.clientfirst.crm.model.MLabel;
import anhpha.clientfirst.crm.model.Result_focus;
import anhpha.clientfirst.crm.sweet.SweetAlert.SweetAlertDialog;
import anhpha.clientfirst.crm.utils.Utils;
import retrofit2.Callback;


/**
 * Created by MinhTran on 7/24/2017.
 */

public class adapter_Focus extends RecyclerView.Adapter<adapter_Focus.MyViewHolder> {
    private Context context;
    private List<Focus> list;
    String ToDay;
    public  String convertStringToDate(String stringData)
            throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");//yyyy-MM-dd'T'HH:mm:ss
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    private Event event;

    public interface Event {
        void Click(int i, int ii);
    }

    public adapter_Focus(Context context, List<Focus> list, Event event) {
        this.context = context;
        this.list = list;
        this.event = event;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
        ToDay = convertStringToDate(df.format(c.getTime()));

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_focus, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Focus focus = list.get(position);
        final MClient activityItem = new MClient();
        activityItem.setClient_id(focus.getClientId());
        activityItem.setClient_type_id(focus.getClientTypeId());
        activityItem.setAddress(focus.getAddress());
        activityItem.setClient_name(focus.getClientName());
        if (focus.getNumberDate()<0) {
                holder.tvDate.setText(-(focus.getNumberDate()) + " " + context.getResources().getString(R.string.srtDate));
                holder.tvDate.setTextColor(context.getResources().getColor(R.color.colorRed));

        }
        else
        {
            if (focus.getNumberDate() == 0) {
                holder.tvDate.setTextColor(context.getResources().getColor(R.color.colorRed));
            }
            holder.tvDate.setText(focus.getNumberDate() + " " + context.getResources().getString(R.string.srtDate));
        }

        holder.tvName_city.setText(focus.getClientName());
        holder.tvNote.setText(focus.getFocusTargetName());
        if (focus.getNumberOrder() > 0) {
            holder.imageOrder.setVisibility(View.VISIBLE);
        }
        if (focus.getNumberMeeting() > 0) {
            holder.imageMeeting.setVisibility(View.VISIBLE);
        }
        if (focus.getNumberCall() > 0) {
            holder.imageCall.setVisibility(View.VISIBLE);
        }
        if (focus.getNumberEmail() > 0) {
            holder.imageEmail.setVisibility(View.VISIBLE);
        }
        if (focus.getNumberEvent() > 0) {
            holder.imageEvent.setVisibility(View.VISIBLE);
        }
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClientActivity.class).putExtra("mClient",activityItem);
                context.startActivity(intent);
            }
        });

        holder.tvName_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClientActivity.class).putExtra("mClient",activityItem);
                context.startActivity(intent);
            }
        });
        holder.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClientActivity.class).putExtra("mClient",activityItem);
                context.startActivity(intent);
            }
        });
        holder.tvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ClientActivity.class).putExtra("mClient",activityItem);
                context.startActivity(intent);
            }
        });

        if (focus.isCheck()) {
            if (focus.getClient_structure_id() == 2) {
                holder.linearLayout3.setVisibility(View.VISIBLE);
                holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_4));
            } else {
                holder.linearLayout3.setVisibility(View.VISIBLE);
                holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_2));
            }
        } else {
            if (focus.getClient_structure_id() == 2) {
                if (focus.getClientTypeId() == 1) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_5));
                } else if (focus.getClientTypeId() == 2) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_8));
                } else {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_40));
                }
            } else {
                if (focus.getClientTypeId() == 1) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_12));
                } else if (focus.getClientTypeId() == 2) {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_86));
                } else {
                    holder.linearLayout3.setVisibility(View.GONE);
                    holder.imageCheck.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_crm_26));
                }
            }
        }


        holder.imageCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Focus focus1 : list) {
                    focus1.setCheck(false);
                }
                if (holder.linearLayout3.getVisibility() == View.GONE) {
                    holder.linearLayout3.setVisibility(View.VISIBLE);
                    list.get(position).setCheck(true);
                } else {
                    holder.linearLayout3.setVisibility(View.GONE);
                    list.get(position).setCheck(false);
                }
                notifyDataSetChanged();
            }
        });

        if (focus.getLabels().size() > 0) {
            holder.linearLayout2.setVisibility(View.VISIBLE);
        } else {
            holder.linearLayout2.setVisibility(View.GONE);
        }

        holder.linearLayout2.removeAllViews();
        int i = 0;
        for (MClientLabel mClientLabel : focus.getLabels()) {

            if (i < 8) {
                Button valueTV = new Button(context);
                if (mClientLabel.getHex().isEmpty())
                    valueTV.setBackgroundColor(Color.GRAY);
                else
                    valueTV.setBackgroundColor(Color.parseColor(mClientLabel.getHex()));
                valueTV.setId((int) System.currentTimeMillis() + new Random().nextInt(255));
                valueTV.setLayoutParams(new ActionBar.LayoutParams(Utils.getWidth(context) / 10, Utils.getWidth(context) / 38));
                Button valueTV2 = new Button(context);
                valueTV2.setId((int) System.currentTimeMillis() + new Random().nextInt(255));
                valueTV2.setBackgroundColor(Color.WHITE);
                valueTV2.setLayoutParams(new ActionBar.LayoutParams(5, Utils.getWidth(context) / 38));
                holder.linearLayout2.addView(valueTV);
                holder.linearLayout2.addView(valueTV2);
                i++;
            }
        }

        holder.imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, OrderActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, WorkActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CheckinActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.imageButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CallActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.imageButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EmailActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.imageButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, EventsClientActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.imageButton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, LabelsActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.imageButton9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, HistoryFocusActivity.class).putExtra("mClient", activityItem));
            }
        });
        holder.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (focus.getNumberOrder() > 0 || focus.getNumberMeeting() > 0 || focus.getNumberCall() > 0 || focus.getNumberEmail() > 0 || focus.getNumberEvent() > 0) {
                    event.Click(position, 1);
                }else {
                    new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText(context.getResources().getString(R.string.srtNotifi))
                            .setContentText(context.getResources().getString(R.string.srtNotifi_activity_customer))
                            .setConfirmText(context.getResources().getString(R.string.srtAgree))
                            .show();
                }
            }
        });
        holder.tvNoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (focus.getNumberOrder() > 0 || focus.getNumberMeeting() > 0 || focus.getNumberCall() > 0 || focus.getNumberEmail() > 0 || focus.getNumberEvent() > 0) {
                    event.Click(position, 2);
                }
                else {
                        new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText(context.getResources().getString(R.string.srtNotifi))
                                .setContentText(context.getResources().getString(R.string.srtNotifi_activity_customer))
                                .setConfirmText(context.getResources().getString(R.string.srtAgree))
                                .show();
                }
            }
        });
        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 event.Click(position, 3);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvName_city, tvNote, tvOk, tvNoOk, tvDelete;
        private ImageView imageCheck, imageOrder, imageMeeting, imageCall, imageEmail, imageEvent, imageFocus;
        private LinearLayout linearLayout2, linearLayout3;
        private RelativeLayout linearLayout;
        public ImageButton imageButton, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6, imageButton7, imageButton8, imageButton9;

        public MyViewHolder(View v) {
            super(v);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvName_city = (TextView) v.findViewById(R.id.tvName_city);
            tvNote = (TextView) v.findViewById(R.id.tvNote);
            tvOk = (TextView) v.findViewById(R.id.tvOk);
            tvNoOk = (TextView) v.findViewById(R.id.tvNoOk);
            tvDelete = (TextView) v.findViewById(R.id.tvDelete);
            imageCheck = (ImageView) v.findViewById(R.id.imageCheck);
            imageOrder = (ImageView) v.findViewById(R.id.imageOrder);
            imageMeeting = (ImageView) v.findViewById(R.id.imageMeeting);
            imageCall = (ImageView) v.findViewById(R.id.imageCall);
            imageEmail = (ImageView) v.findViewById(R.id.imageEmail);
            imageEvent = (ImageView) v.findViewById(R.id.imageEvent);
            linearLayout2 = (LinearLayout) v.findViewById(R.id.linearLayout2);
            linearLayout3 = (LinearLayout) v.findViewById(R.id.linearLayout3);
            linearLayout = (RelativeLayout) v.findViewById(R.id.linearLayout);
            imageButton2 = (ImageButton) v.findViewById(R.id.imageButton2);
            imageButton3 = (ImageButton) v.findViewById(R.id.imageButton3);
            imageButton4 = (ImageButton) v.findViewById(R.id.imageButton4);
            imageButton5 = (ImageButton) v.findViewById(R.id.imageButton5);
            imageButton6 = (ImageButton) v.findViewById(R.id.imageButton6);
            imageButton7 = (ImageButton) v.findViewById(R.id.imageButton7);
            imageButton8 = (ImageButton) v.findViewById(R.id.imageButton8);
            imageButton9 = (ImageButton) v.findViewById(R.id.imageButton9);
        }
    }
}
