package muenzel.lukas.agenda.demo;

import android.content.Context;

import android.content.Intent;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuItem;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;

import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends AppCompatActivity {

    public static Date clickedDate;
    public static muenzel.lukas.agenda.demo.Event[] events;
    public static muenzel.lukas.agenda.demo.Event[] drawnEvents;

    private AlertDialogMain wantToSignOut = new AlertDialogMain();
    private SimpleDateFormat dateFormatMonth;
    private TextView textViewForDate;
    private CompactCalendarView compactCalendarView;
    private android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        declareVariables();
        setOnClickListener();

        /**
         * Deactivates Landscape mode
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (!User.isUserReadble(getApplicationContext())) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }


        if (readEvents() != null)
            events = readEvents();
        else
            events = new Event[0];

        drawEvents();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        writeEvents(events, getApplicationContext());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()){
            case R.id.myAccount:
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);

                break;

            case R.id.logOut:
                wantToSignOut.show(getFragmentManager(), "missiles");
                break;

            case R.id.reportIssue:
                Intent intent3 = new Intent(getApplicationContext(), ReportActivity.class);
                startActivity(intent3);
                break;

            default:
                Toast.makeText(getApplicationContext(), "Error 001 occured, bitte kontaktiere den System-Admin", Toast.LENGTH_SHORT);
                break;
        }


        return super.onOptionsItemSelected(menuItem);
    }
    private muenzel.lukas.agenda.demo.Event[] readEvents() {

        try {
            muenzel.lukas.agenda.demo.Event[] events;

            FileInputStream fis = getApplicationContext().openFileInput("events");
            ObjectInputStream is = new ObjectInputStream(fis);
            events = (Event[]) is.readObject();
            is.close();
            fis.close();
            return events;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    private void declareVariables(){
        this.toolbar = findViewById(R.id.toolbarMain);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        textViewForDate = this.findViewById(R.id.textView);
        compactCalendarView = findViewById(R.id.compactcalendar_view);

        wantToSignOut.setData("WARNUNG", "Willst du dich wirklich abmelden? Das würde" +
                " all deine Daten löschen.", "Ja", "Nein", getApplicationContext());

        dateFormatMonth = new SimpleDateFormat("MMMM- yyyy", Locale.getDefault());
        compactCalendarView.setLocale(TimeZone.getTimeZone("GERMAN"), Locale.GERMAN);
        compactCalendarView.setUseThreeLetterAbbreviation(true);
    }

    public static void writeEvents(Event[] event, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("events", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(event);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnClickListener() {
        compactCalendarView.setListener(new CompactCalendarView.CompactCalendarViewListener() {

            @Override

            public void onDayClick(Date dateClicked) {

                clickedDate = dateClicked;

                Intent intent = new Intent(getApplicationContext(), DailyTodoList.class);

                startActivity(intent);


            }


            @Override

            public void onMonthScroll(Date firstDayOfNewMonth) {

                textViewForDate.setText(dateFormatMonth.format(firstDayOfNewMonth));

            }

        });


        /**
         *
         reloadButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        writeEvents(MySql.loadEvents(getApplicationContext()), getApplicationContext());

        readEvents();

        drawEvents();
        }
        });
         **/
    }


    public void drawEvents() {
        try {
            compactCalendarView.removeAllEvents();

            drawnEvents = new Event[events.length];
            for (int i = 0; i < events.length; i++) {
                drawnEvents[i] = events[i].clone();
            }

            for (int i = 0; i < events.length; i++) {
                if (events[i] != null) {
                    Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
                    currentCalender.setTime(events[i].getDate());

                    switch (events[i].getKindOfEvent()) {
                        case test:
                            compactCalendarView.addEvent(new com.github.sundeepk.compactcalendarview.domain.Event(Color.BLUE, currentCalender.getTimeInMillis()), false);
                            break;

                        case homework:
                            if (events[i].isDone()) {
                                compactCalendarView.addEvent(new com.github.sundeepk.compactcalendarview.domain.Event(Color.GREEN, currentCalender.getTimeInMillis(), false));
                            } else {
                                compactCalendarView.addEvent(new com.github.sundeepk.compactcalendarview.domain.Event(Color.RED, currentCalender.getTimeInMillis(), false));
                            }
                            break;

                        case notification:
                            compactCalendarView.addEvent(new com.github.sundeepk.compactcalendarview.domain.Event(Color.YELLOW, currentCalender.getTimeInMillis(), false));
                    }

                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}