package anhpha.clientfirst.crm.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.adapter_Focus;
import anhpha.clientfirst.crm.model.Focus;

/**
 * Created by MinhTran on 7/24/2017.
 */

public class Focus_activity extends BaseAppCompatActivity implements adapter_Focus.funcSelect_delete {
    private RecyclerView lvFocus;
    private Toolbar toolbar;
    private boolean isHideMenu;
    public static String Id_delete;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);
        lvFocus = (RecyclerView) findViewById(R.id.lvFocus);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        isHideMenu = false;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.srtFocus);
            toolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        }
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        lvFocus.setHasFixedSize(true);
        lvFocus.setLayoutManager(manager);

        getFocus();
    }

    public void getFocus() {
        Id_delete = "";
        ArrayList<Focus> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Focus focus = new Focus();
            focus.setDate("15/02/2017");
            focus.setName_city("Công ty TNHH Humana Việt Nam");
            focus.setNote("Xin cuộc hẹn");
            list.add(focus);
        }
        adapter_Focus adapter_focus = new adapter_Focus(this, list, this);
        lvFocus.setAdapter(adapter_focus);
    }

    private Menu menu;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_focus, menu);
        this.menu = menu;
        menu.getItem(0).setVisible(isHideMenu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void Click() {
        String[] show = Id_delete.split(",");
        if (show.length < 2)
            menu.getItem(0).setVisible(false);
        else
            menu.getItem(0).setVisible(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.actioSort) {
            menu.getItem(0).setVisible(true);
        }
        return super.onOptionsItemSelected(item);
    }
}
