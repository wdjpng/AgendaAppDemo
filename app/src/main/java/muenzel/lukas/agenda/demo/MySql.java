package muenzel.lukas.agenda.demo;

import android.content.Context;
import android.widget.Toast;

import java.sql.Connection;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.PreparedStatement;

import com.mysql.jdbc.Statement;



/*

 * Source: http://www.codejava.net/java-se/jdbc/jdbc-tutorial-sql-insert-select-update-and-delete-examples

 *

 * by Lukas Münzel ()

 *

 * you will need to have the JDBC-Driver (https://dev.mysql.com/downloads/file/?id=472651)

 * in the Server MySql Terminal on http://localhost/phpmyadmin (XAMPP must to be activated)

 *

 * CREATE DATABASE databasename;

   USE databasename;

   CREATE TABLE `databasename`.`tablename` ( `name` VARCHAR(100) NOT NULL , `age` INT(200) NOT NULL , `gender` INT(50) NOT NULL , `dayOfBirth` INT(49) NOT NULL ) ENGINE = InnoDB;

 *

 * You must to use the method createConnection

 *

 *

 *

 */

public class MySql {

    private static String db_host = "localhost";

    private static String db_port = "3306";

    private static String db_user = "root";

    private static String db_pass = "";

    private static String db_base = "agenda";

    private static String db_tablename = "tablename";

    private static String DRIVER = "com.mysql.jdbc.Driver";

    private static String mySqlUrl = "jdbc:mysql://" + db_host + ":" + db_port + "/" + db_base;

    private static Connection connection;

    public static MySql app = new MySql();


    public static Event[] loadEvents(Context context) {
        ArrayList<Event> events = new ArrayList<>();
        try {
            db_tablename = "events";

            createConnection();

            String sql = "SELECT * FROM " + db_tablename;

            Statement statement = (Statement) connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            int count = 0;

            while (result.next()) {

                events.add(new Event(result.getDate(1), result.getString(2), result.getString(3), Event.numberToKindOfEvent(result.getInt(4)), result.getString(5)));

                count++;

            }

            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context , "Die Daten konnten nicht geladen werden, bitte überprüfe deine Internetverbindung", Toast.LENGTH_SHORT).show();
        }

        return events.toArray(new Event[events.size()]);
    }

    public static User loadUser(String mail) {

        try {
            db_tablename = "user";

            createConnection();

            String sql = "SELECT * FROM " + db_tablename + " WHERE mail=" + mail;

            Statement statement = (Statement) connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            User user = new User(result.getString(1), result.getString(2), result.getString(3), result.getString(4));

            return user;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void createConnection() throws SQLException, ClassNotFoundException {

        connection = DriverManager.getConnection(mySqlUrl, db_user, db_pass);

    }

    public static void closeConnection() throws SQLException {

        connection.close();

    }

    public void updateDatabase() throws SQLException {

        // At he WHERE you must to give one ore more parameter, all Data with the right

        // WHERE will be updated

        String sql = "UPDATE " + db_tablename + " SET name=?, age=? WHERE name=?";

        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);

        statement.setString(1, "Bill Gates");

        statement.setInt(2, 123);

        statement.setString(3, "William Henry Bill Gates");

        statement.executeUpdate();

    }

    public void deleteDatabase() throws SQLException {

        // At he WHERE you must to give one ore more parameter, all Data with the right

        // WHERE will be deleted

        String sql = "DELETE FROM " + db_tablename + " WHERE name=?";

        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);

        statement.setString(1, "Bill Gates");

        statement.executeUpdate();

    }

    public void printDatabase() throws SQLException {

        String sql = "SELECT * FROM " + db_tablename;

        Statement statement = (Statement) connection.createStatement();

        ResultSet result = statement.executeQuery(sql);

        int count = 0;

        while (result.next()) {

            int age = result.getInt(2);

            int dayOfBirth = result.getInt(4);

            String name = result.getString("name");

            // String.format converts almost everything into a String

            System.out.println(String.format(name + " " + age + " " + dayOfBirth));

            count++;

        }

    }

    public void insertIntoDatabase() throws SQLException {

        // These ? must to be here !

        String sql = "INSERT INTO " + db_tablename + " (name, age, gender, dayOfBirth) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = (PreparedStatement) connection.prepareStatement(sql);

        statement.setString(1, "Bll Gates");

        statement.setInt(2, 69);

        statement.setInt(3, 23);

        statement.setInt(4, 255);

        statement.executeUpdate();

        // Close the Connection, this you must to do after you used Create Connection

        // and you will not use MySql any more

        closeConnection();

    }

}
