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
import anhpha.clientfirst.crm.model.MCommunication;
import anhpha.clientfirst.crm.model.MPhoto;
import butterknife.Bind;
import butterknife.ButterKnife;
import anhpha.clientfirst.crm.utils.DynamicBox;

public class CommunicationActivity extends BaseAppCompatActivity implements View.OnClickListener  {
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.name)
    TextView name;
    @Bind(R.id.date)
    TextView date;
    @Bind(R.id.description)
    TextView description;
    @Bind(R.id.image)
    ImageView image;
    @Bind(R.id.tvView)
    TextView tvView;
    @Bind(R.id.layout_image)
    LinearLayout layout_image;
    MCommunication mCommunication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_communication);
        ButterKnife.bind(this);
        mCommunication = (MCommunication) getIntent().getSerializableExtra("mCommunication");
        setSupportActionBar(toolbar);
        ActionBar  actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.title_activity_communication);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        name.setText(mCommunication.getCommunication_name());
        date.setText(getString(R.string.from) + ": "+ mCommunication.getBegin_date().split(" ")[0] +" "+ getString(R.string.to) + ": " +mCommunication.getEnd_date().split(" ")[0]);
        description.setText(Html.fromHtml(mCommunication.getCommunication_content()));
        //get image --
        if(mCommunication.getPhotos().size() > 0) {
            Picasso.with(getApplicationContext()).load(mCommunication.getPhotos().get(0).url.indexOf("http") < 0 ? "http://" : mCommunication.getPhotos().get(0).url + mCommunication.getPhotos().get(0).name).fit().centerInside().error(R.drawable.no_img_big).into(image);
            if(mCommunication.getPhotos().size() >1){
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
            for (final MPhoto item : mCommunication.getPhotos()) {
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
