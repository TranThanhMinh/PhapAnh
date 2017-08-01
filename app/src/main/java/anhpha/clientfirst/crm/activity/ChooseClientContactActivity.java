package anhpha.clientfirst.crm.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.R;
import anhpha.clientfirst.crm.adapter.ChooseClientContactAdapter;
import anhpha.clientfirst.crm.configs.Constants;
import anhpha.clientfirst.crm.configs.Preferences;
import anhpha.clientfirst.crm.customs.DividerItemDecoration;
import anhpha.clientfirst.crm.customs.RecyclerTouchListener;
import anhpha.clientfirst.crm.model.MAPIResponse;
import anhpha.clientfirst.crm.model.MClientContact;
import anhpha.clientfirst.crm.service_api.ServiceAPI;
import anhpha.clientfirst.crm.utils.DynamicBox;
import anhpha.clientfirst.crm.utils.LogUtils;
import anhpha.clientfirst.crm.utils.Utils;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChooseClientContactActivity extends BaseAppCompatActivity implements  RecyclerTouchListener.ClickListener, View.OnClickListener  {

    @Bind(R.id.rvActivities)
    RecyclerView rvActivities;
    @Bind(R.id.include)
    Toolbar toolbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    ChooseClientContactAdapter activityAdapter;
    List<MClientContact> mClientContacts = new ArrayList<>();
    Preferences preferences;
    Handler updateBarHandler;
    Cursor cursor;
    int counter = 0;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        box = new DynamicBox(this, R.layout.activity_choose_clients);
        ButterKnife.bind(this);
        preferences = new Preferences(mContext);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.add_client);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        checkPermission(Manifest.permission.READ_CONTACTS);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvActivities.setLayoutManager(mLayoutManager);
        rvActivities.setItemAnimator(new DefaultItemAnimator());
        rvActivities.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        rvActivities.addOnItemTouchListener(new RecyclerTouchListener(mContext, rvActivities, this));

        mClientContacts = new ArrayList<>();
        updateBarHandler =new Handler();
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Reading contacts...");
        pDialog.setCancelable(false);
        pDialog.show();

        // Since reading contacts takes more time, let's run it on a separate thread.
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    getContacts();
                }catch (Exception e){
                    pDialog.cancel();
                };
            }
        }).start();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_done, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done:
                int size = mClientContacts.size() -1 ;
                List<MClientContact> contacts= new ArrayList<>();
                for(MClientContact c : mClientContacts){
                    if(c.is_select){
                        contacts.add(c);
                    }
                }

                GetRetrofit().create(ServiceAPI.class)
                        .setClientContact(preferences.getStringValue(Constants.TOKEN, "")
                                , preferences.getIntValue(Constants.USER_ID, 0)
                                , preferences.getIntValue(Constants.PARTNER_ID, 0)
                                ,0
                                ,contacts
                        )
                        .enqueue(new Callback<MAPIResponse<List<MClientContact>>>() {
                            @Override
                            public void onResponse(Call<MAPIResponse<List<MClientContact>>> call, Response<MAPIResponse<List<MClientContact>>> response) {
                                LogUtils.api(TAG,call,response.body());

                                if(response.body().getResult().size()>0){
                                    Utils.showDialogSuccess(mContext, R.string.add_client_done);
                                }else {
                                    Utils.showError(coordinatorLayout, R.string.add_client_fail);
                                }
                            }

                            @Override
                            public void onFailure(Call<MAPIResponse<List<MClientContact>>> call, Throwable t) {
                                LogUtils.api(TAG,call,t.toString());

                                Utils.showError(coordinatorLayout, R.string.add_client_fail);

                            }
                        });
                return true;

            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getContacts() {

        String phoneNumber = null;
        String email = null;
        String address = null;
        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        Uri postal_uri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        String STREET = ContactsContract.CommonDataKinds.StructuredPostal.STREET;
        String STREET_ID = ContactsContract.Data.CONTACT_ID;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;
        ContentResolver contentResolver = getContentResolver();
        cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {
            counter = 0;
            try {
                while (cursor.moveToNext()) {
                    try {
                        // Update the progress message
                        updateBarHandler.post(new Runnable() {
                            public void run() {

                                counter++;
                                pDialog.setMessage("Reading contacts : " + (counter) + "/" + cursor.getCount());
                            }
                        });
                        MClientContact mClientContact = new MClientContact();

                        String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                        String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));
                        int hasPhoneNumber = Utils.tryParse(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));
                        if (hasPhoneNumber > 0) {
                            mClientContact.setClient_name(name);
                            //This is to read multiple phone numbers associated with the same contact
                            Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);
                            while (phoneCursor.moveToNext()) {
                                phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                                mClientContact.setPhone(phoneNumber);
                            }
                            phoneCursor.close();
                            // Read every email id associated with the contact
                            Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);
                            while (emailCursor.moveToNext()) {
                                email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                                mClientContact.setEmail(email);
                            }
                            emailCursor.close();
                            Cursor addressCursor = contentResolver.query(postal_uri, null, STREET_ID + " = ?", new String[]{contact_id}, null);
                            while (addressCursor.moveToNext()) {
                                address = addressCursor.getString(emailCursor.getColumnIndex(STREET));
                                mClientContact.setAddress(address);
                            }
                            addressCursor.close();

                            // Add the contact to the ArrayList
                            mClientContacts.add(mClientContact);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            // ListView has to be updated using a ui thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    activityAdapter = new ChooseClientContactAdapter(mContext,mClientContacts);
                    rvActivities.setAdapter(activityAdapter);
                }
            });
            // Dismiss the progressbar after 500 millisecondds
            updateBarHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDialog.cancel();
                }
            }, 500);
        }else{
            // Dismiss the progressbar after 500 millisecondds
            updateBarHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    pDialog.cancel();
                }
            }, 500);
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onClick(View view, int position) {
        mClientContacts.get(position).setIs_select(!mClientContacts.get(position).is_select());
        activityAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLongClick(View view, int position) {

    }


}
