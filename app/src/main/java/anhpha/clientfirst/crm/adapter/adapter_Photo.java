package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.activity.Show_photo_activity;
import anhpha.clientfirst.crm.model.Photo;

/**
 * Created by Minh Tran on 06/20/2017.
 */

public class adapter_Photo extends RecyclerView.Adapter<adapter_Photo.MyViewHolder> {

    private List<Photo> list;
    private Context context;
    public static List<Photo> list_photo;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imHistory;


        public MyViewHolder(View view) {
            super(view);
            imHistory = (ImageView) view.findViewById(R.id.imHistory);

        }

    }
    public adapter_Photo(Context context, List<Photo> list) {
        this.list = list;

        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       Photo photo = list.get(position);
        Picasso.with(context)
                .load(photo.getUrl()+ photo.getName())
                .fit().centerCrop()
                .into(holder.imHistory);
        holder.imHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list_photo = list;
                Intent intent = new Intent(context, Show_photo_activity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
