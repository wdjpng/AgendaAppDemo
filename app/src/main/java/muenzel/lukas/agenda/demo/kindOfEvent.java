package muenzel.lukas.agenda.demo;

import java.io.Serializable;

/**
 * Created by muenz on 21.03.2018.
 */

public enum kindOfEvent implements Serializable {
    homework(0),
    test(1),
    notification(2);

    int number;

    kindOfEvent(int number){
        this.number = number;
    }
}
