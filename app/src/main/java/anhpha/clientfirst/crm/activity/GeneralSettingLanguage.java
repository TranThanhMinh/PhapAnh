package anhpha.clientfirst.crm.activity;


import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.Locale;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;

public class GeneralSettingLanguage extends BaseAppCompatActivity {
    RadioButton rdbEnglish;
    RadioButton rdbVietNam;

    Button btnChange;
    Preferences preferences;
    Toolbar mToolbar;
    int intLanguage = 0;

    ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //  Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_languge);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        preferences = new Preferences(mContext);
        // Set up the action bar.
        actionBar = getSupportActionBar();
        // Enabling Back navigation on Action Bar icon
        actionBar.setDisplayHomeAsUpEnabled(true);
        //Set custom actionbar
        actionBar.setDisplayShowCustomEnabled(true);

        actionBar.setTitle(getString(R.string.actionbar_languge));

        rdbVietNam = (RadioButton) findViewById(R.id.rdbVietNam);
        rdbEnglish = (RadioButton) findViewById(R.id.rdbEnglish);
        switch (preferences.getIntValue(Constants.LANGUAGE, 0)) {

            case Constants.LANGUAGE_EN:
                rdbEnglish.setChecked(true);
                intLanguage = Constants.LANGUAGE_EN;
                break;
            case Constants.LANGUAGE_VI:
                rdbVietNam.setChecked(true);
                intLanguage = Constants.LANGUAGE_VI;
                break;
            default:
                break;
        }
        rdbVietNam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                intLanguage = Constants.LANGUAGE_EN;

            }
        });
        rdbEnglish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                intLanguage = Constants.LANGUAGE_VI;

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                // app icon in action bar clicked; go home
                finish();
                return true;
            case R.id.done:
                Configuration config = new Configuration();

                switch (intLanguage) {
                    case Constants.LANGUAGE_VI:
                        config.locale = Locale.ROOT;
                        break;
                    case Constants.LANGUAGE_EN:
                        config.locale = Locale.ENGLISH;
                        break;
                    default:
                        config.locale = Locale.ROOT;
                        break;
                }
                getResources().updateConfiguration(config, null);

                preferences.putIntValue(Constants.LANGUAGE, intLanguage);

                // Android version
                int AndroidVersion = Build.VERSION.SDK_INT;
                setResult(Constants.KEY_PUT_RESTART_LANGUAGE);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
