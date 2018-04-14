package muenzel.lukas.agenda.demo;

import android.app.Application;
import android.content.Context;
import android.provider.ContactsContract;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by muenz on 21.03.2018.
 */

public class User implements Serializable {
    String firstCourse = "";
    String secondCourse = "";
    String thirdCourse = "";
    String mail = "";

    public User(String mail, String firstCourse, String secondCourse, String thirdCourse){
        this.mail = mail;
        this.firstCourse = firstCourse;
        this.secondCourse = secondCourse;
        this.thirdCourse = thirdCourse;
    }

    public static void writeUser(User user, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput("user", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(user);
            os.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isUserReadble(Context context) {
        try {
            User user;

            FileInputStream fis = context.openFileInput("user");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }

        return true;
    }

    public static User readUser(Context context) {
        User user;
        try {
            FileInputStream fis = context.openFileInput("user");
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (User) is.readObject();
            is.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

        return user;
    }

    public String getFirstCourse() {
        return firstCourse;
    }

    public String getSecondCourse() {
        return secondCourse;
    }

    public String getThirdCourse() {
        return thirdCourse;
    }

    public String getMail() {
        return mail;
    }
}
