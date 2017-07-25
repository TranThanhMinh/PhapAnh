package anhpha.clientfirst.crm.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;

import java.util.ArrayList;
import java.util.List;

import anhpha.clientfirst.crm.model.MCalendar;

/**
 * Created by Window7 on 3/7/2017.
 */
public class CalendarContentResolver {
    public static final String[] FIELDS = {
            CalendarContract.Reminders.TITLE,
            CalendarContract.Reminders.DESCRIPTION,
            CalendarContract.Reminders.DTSTART,
            CalendarContract.Reminders.CALENDAR_ID,
            CalendarContract.Reminders.ORGANIZER,
            "_id"//CalendarContract.Reminders.EVENT_ID
    };

    public static final Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/events");

    ContentResolver contentResolver;
    List<MCalendar> calendars = new ArrayList<>();

    public  CalendarContentResolver(Context ctx) {
        contentResolver = ctx.getContentResolver();
    }

    public List<MCalendar> getCalendars() {
        // Fetch a list of all calendars sync'd with the device and their display names
        try {
            Cursor cursor = contentResolver.query(CALENDAR_URI, FIELDS, null, null, null);

            try {
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String title = cursor.getString(0);
                        String description = cursor.getString(1);
                        String date = cursor.getString(2);
                        String location = cursor.getString(4);
                        int id = cursor.getInt(3);
                        int _id = cursor.getInt(5);
                        // This is actually a better pattern:
//                    String color = cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR));
//                    Boolean selected = !cursor.getString(3).equals("0");
//
                        MCalendar mCalendar = new MCalendar();
                        mCalendar.setCalendar_title(title);
                        mCalendar.setCalendar_content(description);
                        mCalendar.setCalendar_date(date);
                        mCalendar.setEvent_id(_id);
                        mCalendar.setCalendar_id(id);
                        mCalendar.setLocation(location);
                        calendars.add(mCalendar);
                    }
                }
            } catch (AssertionError ex) { /*TODO: log exception and bail*/ }

        }catch (Exception e){

        }
        return calendars;
    }
}