package muenzel.lukas.agenda.demo;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


        /**
         * Deactivates Landscape mode
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ImageButton backButton = this.findViewById(R.id.backButtonAccount);
        TextView welcomeTextView = this.findViewById(R.id.welcomeTextView);
        TextView textView = this.findViewById(R.id.textViewAccount);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });

        User currentUser = User.readUser(getApplicationContext());

        String[] name = getNameFromMail(currentUser.getMail());

        welcomeTextView.setText(welcomeTextView.getText().toString().replace("[Vorname]", name[0]));
        welcomeTextView.setText(welcomeTextView.getText().toString().replace("[Nachname]", name[1]));

        textView.setText(textView.getText().toString().replace("[Klasse]", currentUser.getFirstCourse()));
        textView.setText(textView.getText().toString().replace("[erstesPflichtwahlfach]", currentUser.getSecondCourse()));
        textView.setText(textView.getText().toString().replace("[zweitesPlichtwahlfach]", currentUser.getThirdCourse()));
    }

    public String[] getNameFromMail(String mail){
        String[] name = new String[2];

        String firstNameDotLastName = User.readUser(getApplicationContext()).getMail().replace("@stud.edubs.ch", "");

        name[0] = firstNameDotLastName.substring(0, firstNameDotLastName.indexOf('.'));
        name[1] = firstNameDotLastName.substring(firstNameDotLastName.indexOf('.') + 1);

        name[0] = name[0].substring(0, 1).toUpperCase() + name[0].substring(1);
        name[1] = name[1].substring(0, 1).toUpperCase() + name[1].substring(1);

        for(int i = 0; i < name.length; i++){
            name[i] = name[i].replace("ae", "ä");
            name[i] = name[i].replace("ue", "ü");
            name[i] = name[i].replace("oe", "ö");
        }
        return name;
    }
}
