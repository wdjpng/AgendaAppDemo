package muenzel.lukas.agenda.demo;

import android.support.annotation.NonNull;

import com.github.sundeepk.compactcalendarview.CompactCalendarView;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * Created by muenz on 21.03.2018.
 */

public class Event implements Serializable, Comparable<Event> {
    private Date date;
    private String title;
    private String message;
    private String group;
    private boolean canBeDone = false;
    private boolean isDone = false;
    private kindOfEvent kindOfEvent;


    public Event(Date date, String title, String message, kindOfEvent kindOfEvent, String group) {
        this.date = date;
        this.title = title;
        this.message = message;
        this.kindOfEvent = kindOfEvent;
        this.group = group;

        if (kindOfEvent == muenzel.lukas.agenda.demo.kindOfEvent.homework) {
            canBeDone = true;
        }
    }

    @Override
    public int compareTo(Event comparedEvent) {
        Calendar currentCalender = Calendar.getInstance(Locale.getDefault());
        Calendar currentCalender2 = Calendar.getInstance(Locale.getDefault());

        currentCalender.setTime(this.date);
        currentCalender2.setTime(comparedEvent.getDate());
        return (int) (currentCalender.getTimeInMillis() - currentCalender2.getTimeInMillis());
    }

    public boolean deepEquals(Event secondEvent) {
        if (this.title.equals(secondEvent.getTitle()) && this.message.equals(secondEvent.getTitle())
                && (this.canBeDone == secondEvent.canBeDone) && this.date == secondEvent.getDate()) {
            return true;
        }

        return false;
    }

    public static kindOfEvent numberToKindOfEvent(int number) {
        switch (number) {
            case 0:
                return muenzel.lukas.agenda.demo.kindOfEvent.homework;
            case 1:
                return muenzel.lukas.agenda.demo.kindOfEvent.test;
            case 2:
                return muenzel.lukas.agenda.demo.kindOfEvent.notification;
            default:
                return null;
        }
    }

    public Event clone() {
        Event clonedEvent = new Event(this.getDate(), this.getTitle(), this.getMessage(), this.getKindOfEvent(), this.getGroup());
        
        return  clonedEvent;
    }

    public Date getDate() {
        return date;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCanBeDone() {
        return canBeDone;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean don) {
        isDone = don;
    }

    public muenzel.lukas.agenda.demo.kindOfEvent getKindOfEvent() {
        return kindOfEvent;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }
}
