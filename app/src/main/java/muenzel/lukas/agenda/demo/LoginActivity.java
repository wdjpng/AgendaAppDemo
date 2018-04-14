package muenzel.lukas.agenda.demo;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /**
         * Deactivates Landscape mode
         */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /**
         * For not instant keyboard oppening
         */
        getWindow().setBackgroundDrawableResource(R.drawable.background);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        final AutoCompleteTextView userEmail = this.findViewById(R.id.userEmail);
        Button confirmButton = this.findViewById(R.id.confirmButton);
        final RadioButton acceptAGB = this.findViewById(R.id.acceptAgb);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (acceptAGB.isChecked()) {

                    if (userEmail.getText().toString().contains("@stud.edubs.ch")) {

                        if(MySql.loadUser(userEmail.getText().toString()) != null){
                        User.writeUser(MySql.loadUser(userEmail.getText().toString()), getApplicationContext());

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                        startActivity(intent);
                        }

                        else {
                            Toast.makeText(getApplicationContext() , "Dieser Benutzer konnte nicht gefunden werden", Toast.LENGTH_SHORT).show();
                        }
                    }


                    else{
                        Toast.makeText(getApplicationContext(), "Gib bitte eine @edubs.stud.ch Adresse ein", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Bitte akzeptiere die AGBs", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
