package anhpha.clientfirst.crm.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.model.MEvent;
import anhpha.clientfirst.crm.model.MPhoto;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;

public class EventViewActivity extends BaseAppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.number)
    TextView number;
    @Bind(R.id.address)
    TextView address;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.tvView)
    TextView tvView;
    @Bind(R.id.layout_image)
    LinearLayout layout_image;
    MEvent mEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_event_view);
        ButterKnife.bind(this);
        mEvent = (MEvent) getIntent().getSerializableExtra("mEvent");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_event);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        name.setText(mEvent.getEvent_name());
        date.setText(getString(R.string.date)+ ": "+ mEvent.getDate_begin());
        address.setText(getString(R.string.address_point)+ ": "+ mEvent.getAddress());
        number.setText(getString(R.string.number_invite)+ ": "+ Utils.formatCurrency(mEvent.getNum_client()));
        description.setText(Html.fromHtml(mEvent.getEvent_content()));
        //get image --
        if(mEvent.getPhotos().size() > 0) {
            Picasso.with(getApplicationContext()).load(mEvent.getPhotos().get(0).url.indexOf("http") < 0 ? "http://" : mEvent.getPhotos().get(0).url + mEvent.getPhotos().get(0).name).fit().centerInside().error(R.drawable.no_img_big).into(image);
            if(mEvent.getPhotos().size() >1){
                tvView.setVisibility(View.VISIBLE);
            }
            tvView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (layout_image.getVisibility() == View.VISIBLE) {
                        layout_image.setVisibility(View.GONE);
                        tvView.setText(R.string.view_more);
                        image.setVisibility(View.VISIBLE);
                        tvView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.y32, 0, 0, 0);
                    } else {
                        layout_image.setVisibility(View.VISIBLE);
                        image.setVisibility(View.GONE);
                        tvView.setText(R.string.view_hide);
                        tvView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.y33, 0, 0, 0);
                    }
                }
            });
            View view = null;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            for (final MPhoto item : mEvent.getPhotos()) {
                view = inflater.inflate(R.layout.item_image, null);
                ImageView thumbNail = (ImageView) view.findViewById(R.id.imgProgram);
                try {
                    Picasso.with(getApplicationContext()).load(item.url.indexOf("http") < 0 ? "http://" : item.url + item.name).fit().centerInside().error(R.drawable.no_img_big).into(thumbNail);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                layout_image.addView(view);
            }
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_null, menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.calendar:

                return true;
            case R.id.user:

                return true;
            case android.R.id.home:
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View view) {

    }
}
