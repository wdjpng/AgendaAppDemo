package muenzel.lukas.agenda.demo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DailyTodoList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_todo_list);

        final TextView t = this.findViewById(R.id.todoTextView);
        final ImageButton everythingDone = this.findViewById(R.id.everythingDone);
        final ImageButton backButton = this.findViewById(R.id.backButtonTodoList);

        String text = "\n\n\n";

        ArrayList<Event> notifications = new ArrayList<>();
        ArrayList<Event> tests = new ArrayList<>();
        ArrayList<Event> homeworks = new ArrayList<>();

        for (int i = 0; i < MainActivity.events.length; i++) {


            if (MainActivity.events[i].getDate().getDay() == MainActivity.clickedDate.getDay())

                switch (MainActivity.events[i].getKindOfEvent()) {
                    case notification:
                        if (MainActivity.clickedDate.getDate() == MainActivity.events[i].getDate().getDate())
                            notifications.add(MainActivity.events[i]);
                        break;

                    case test:
                        if (MainActivity.clickedDate.getDate() == MainActivity.events[i].getDate().getDate())
                            tests.add(MainActivity.events[i]);
                        break;

                    case homework:
                        if (MainActivity.clickedDate.getDate() == MainActivity.events[i].getDate().getDate())
                            homeworks.add(MainActivity.events[i]);
                        break;

                }

        }


        for (int i = 0; i < notifications.size(); i++) {
            text += notifications.get(i).getTitle() + " (Notitz)\n\n" + notifications.get(i).getMessage() + "\n\n";
        }

        for (int i = 0; i < tests.size(); i++) {
            text += tests.get(i).getTitle() + " (Test)\n\n" + tests.get(i).getMessage() + "\n\n";
        }

        for (int i = 0; i < homeworks.size(); i++) {
            text += homeworks.get(i).getTitle().toString() + " (Hausaufgabe)\n\n" + homeworks.get(i).getMessage() + "\n\n";

            if (homeworks.get(i).isDone()) {
                everythingDone.setImageResource(R.drawable.deny_symbol);
            } else {
                everythingDone.setImageResource(R.drawable.accept_symbol);
            }
        }

        if (text.equals("\n\n\n")) {
            text += "An diesem Tag hast du keine Aufgaben, Tests oder Notifikationen deiner Lehrer.";
        }
        t.setText(text);

        if(homeworks.size() <= 0){
            everythingDone.setVisibility(View.INVISIBLE);
        }

        everythingDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < MainActivity.events.length; i++) {
                    if (MainActivity.events[i].getKindOfEvent() == kindOfEvent.homework) {
                        if (MainActivity.clickedDate.getDate() == MainActivity.events[i].getDate().getDate()) {
                            if (MainActivity.events[i].isDone()) {
                                MainActivity.events[i].setDone(false);
                                everythingDone.setImageResource(R.drawable.accept_symbol);
                                Toast.makeText(getApplicationContext(), "Deine Hausaufgaben wurden als *nicht* gemacht markiert", Toast.LENGTH_SHORT).show();
                            } else {
                                MainActivity.events[i].setDone(true);
                                everythingDone.setImageResource(R.drawable.deny_symbol);
                                Toast.makeText(getApplicationContext(), "Deine Hausaufgaben wurden als gemacht markiert", Toast.LENGTH_SHORT).show();
                            }

                            break;
                        }
                    }
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });
    }
}
