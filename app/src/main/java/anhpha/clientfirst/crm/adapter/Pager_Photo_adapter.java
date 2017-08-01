package anhpha.clientfirst.crm.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.Photo;


/**
 * Created by MinhTran on 7/12/2016.
 */
public class Pager_Photo_adapter extends PagerAdapter {
    Context context;
    List<Photo> lvPhotop;
    LayoutInflater lay;
    public Pager_Photo_adapter(Context context, List<Photo> lvPhotop){
        this.context =context;
        this.lvPhotop =lvPhotop;
        lay = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return lvPhotop.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v =lay.inflate(R.layout.item_show_photo,null);
        ImageView imPhoto = (ImageView) v.findViewById(R.id.imShow_photo);
        Photo photo = lvPhotop.get(position);
        Picasso.with(context)
                .load(photo.getUrl()+photo.getName())
                .fit().centerInside()
                .into(imPhoto);
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
