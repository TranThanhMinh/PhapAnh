package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MPhoto;

/**
 * Created by Nguyen on 6/9/2015.
 */
public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.ViewHolder> {

    private IPhotoCallback mCallback;

    private Context mContext;

    private List<MPhoto> photoList;

    public PhotosAdapter(Context context, List<MPhoto> photos, IPhotoCallback callback) {
        mContext = context;
        photoList = photos;
        mCallback = callback;
    }

    public void setPhotoList(List<MPhoto> photos) {
        photoList = photos;
    }

    @Override
    public int getItemCount() {
        return (photoList == null) ? 0 : photoList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        MPhoto o = photoList.get(position);
        if(o.local != null)
            Picasso.with(mContext).load(new File(o.local)).resize(200, 200).into(holder.ivPhoto, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {

                }
            });
        else
            Picasso.with(mContext).load((o.url + o.name)).resize(200, 200).into(holder.ivPhoto, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                }

                @Override
                public void onError() {

                }
            });
        holder.ivPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.select(position);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView ivPhoto;

        public ViewHolder(View v) {
            super(v);
            ivPhoto = (ImageView) v.findViewById(R.id.ivPhoto);
        }
    }
    public interface IPhotoCallback {

        void select(int i);

    }
}
