package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Photo;

/**
 * Created by Minh Tran on 06/20/2017.
 */

public class adapter_Photo_history_order extends RecyclerView.Adapter<adapter_Photo_history_order.MyViewHolder> {

    public interface funcDelete_lvImage{
        void Delete_photo_off(int pos);
        void Delete_photo_onl(int pos);
    }
    private funcDelete_lvImage delete_lvImage;
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
    public adapter_Photo_history_order(Context context, List<Photo> list, funcDelete_lvImage delete_lvImage) {
        this.list = list;
        this.delete_lvImage = delete_lvImage;
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
       final Photo photo = list.get(position);

        if(photo.getUrl().contains("http")) {
            Picasso.with(context)
                    .load(photo.getUrl() + photo.getName())
                    .fit().centerCrop()
                    .into(holder.imHistory);

            holder.imHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_lvImage.Delete_photo_onl(position);
                }
            });
        }

        else {
            Picasso.with(context)
                    .load( photo.getFilePart())
                    .fit().centerCrop()
                    .into(holder.imHistory);
            holder.imHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete_lvImage.Delete_photo_off(position);
                }
            });
        }

    }




    @Override
    public int getItemCount() {
        return list.size();
    }
}
