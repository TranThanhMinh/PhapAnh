package anhpha.clientfirst.crm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.io.File;

import anhpha.clientfirst.crm.R;


public class ViewImageActivity extends BaseAppCompatActivity {

    String url;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_fullscreen_image);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        final ImageView imgDisplay = (ImageView) findViewById(R.id.imgDisplay);
        Button btnClose = (Button) findViewById(R.id.btnClose);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Picasso.with(mContext).load(new File(url)).into(imgDisplay, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onError() {
                Picasso.with(getApplicationContext()).load(url).fit().centerInside().error(R.drawable.no_img_big).into(imgDisplay);
            }
        });

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
