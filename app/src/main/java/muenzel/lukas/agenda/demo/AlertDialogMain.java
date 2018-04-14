package muenzel.lukas.agenda.demo;


import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

public class AlertDialogMain extends DialogFragment {
    String title = "";
    String message = "";
    String positiveButton = "";
    String negativeButton = "";
    Context context;

    public void setData(String title, String message, String positiveButton, String negativeButton, Context context) {
        this.title = title;
        this.message = message;
        this.positiveButton = positiveButton;
        this.negativeButton = negativeButton;
        this.context = context;
    }

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(title);
        builder.setIcon(R.drawable.warning_symbol);
        builder.setMessage(message)
                .setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        User.writeUser(null, context);
                        MainActivity.writeEvents(null, context);

                        Intent intent2 = new Intent(context, LoginActivity.class);
                        startActivity(intent2);
                    }
                })
                .setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}