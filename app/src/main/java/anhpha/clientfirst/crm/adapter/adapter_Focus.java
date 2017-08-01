package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Focus;

import static anhpha.clientfirst.crm.activity.Focus_activity.Id_delete;


/**
 * Created by MinhTran on 7/24/2017.
 */

public class adapter_Focus extends RecyclerView.Adapter<adapter_Focus.MyViewHolder> {
    private Context context;
    private List<Focus> list;


    public interface funcSelect_delete {
        void Click();
    }

    public void setFuncSelect_delete(adapter_Focus.funcSelect_delete funcSelect_delete) {
        this.funcSelect_delete = funcSelect_delete;
    }

    private funcSelect_delete funcSelect_delete;

    public adapter_Focus(Context context, List<Focus> list, funcSelect_delete funcSelect_delete) {
        this.context = context;
        this.list = list;
        this.funcSelect_delete = funcSelect_delete;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_focus, null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Focus focus = list.get(position);
        holder.tvDate.setText(focus.getDate());
        holder.tvName_city.setText(focus.getName_city());
        holder.tvNote.setText(focus.getNote());
        //if (focus.isCheck())
           // holder.ckDelete.setChecked(true);
        //else
          //  holder.ckDelete.setChecked(false);
        holder.ckDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (focus.isCheck()) {
                    focus.setCheck(false);
                    Id_delete = Id_delete.replace(","+position,"");
                    Log.d("Id_delete",Id_delete);
                    Log.d("Id_delete",position+"");
                } else {
                    focus.setCheck(true);
                    Id_delete = Id_delete + "," + position;
                    Log.d("Id_delete",Id_delete);
                }
                funcSelect_delete.Click();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate, tvName_city, tvNote;
        private ImageView ckDelete;

        public MyViewHolder(View v) {
            super(v);
            tvDate = (TextView) v.findViewById(R.id.tvDate);
            tvName_city = (TextView) v.findViewById(R.id.tvName_city);
            tvNote = (TextView) v.findViewById(R.id.tvNote);
            ckDelete = (ImageView) v.findViewById(R.id.ckDelete);
        }
    }
}
