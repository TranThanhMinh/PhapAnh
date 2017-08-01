package anhpha.clientfirst.crm.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.Pager_Photo_adapter;

import static anhpha.clientfirst.crm.adapter.adapter_Photo_history_order.list_photo;


/**
 * Created by MinhTran on 7/12/2017.
 */

public class Show_photo_activity extends Activity implements  ViewPager.OnPageChangeListener,View.OnClickListener{
    private int dotsCount;
    private ImageView imClose;
    private ImageView[] dots;
    private LinearLayout pager_indicator;
    private Pager_Photo_adapter pager_photo_adapter;
    private ViewPager vpPhoto;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_photo);
        pager_indicator = (LinearLayout) findViewById(R.id.viewPagerCountDots);
        imClose = (ImageView) findViewById(R.id.imClose);
        vpPhoto = (ViewPager) findViewById(R.id.vpPhoto);
        vpPhoto.setCurrentItem(0);
        vpPhoto.setOnPageChangeListener(this);
        imClose.setOnClickListener(this);
        getPhoto();
        setUiPageViewController();
    }
    public void getPhoto(){
        pager_photo_adapter = new Pager_Photo_adapter(Show_photo_activity.this,list_photo);
        vpPhoto.setAdapter(pager_photo_adapter);
    }
    private void setUiPageViewController() {

        dotsCount = pager_photo_adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dost));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    20,
                    15
            );
            params.setMargins(2, 0, 2, 0);
            if (dotsCount > 1) {
                pager_indicator.addView(dots[i], params);
            } else {
            }
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.dost_view));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < dotsCount; i++) {
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dost));
        }
        dots[position].setImageDrawable(getResources().getDrawable(R.drawable.dost_view));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View view) {
        if(view == imClose){
            finish();
        }
    }
}
