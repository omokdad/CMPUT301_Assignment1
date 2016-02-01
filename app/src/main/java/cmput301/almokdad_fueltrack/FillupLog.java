package cmput301.almokdad_fueltrack;

import java.util.ArrayList;

// a class with a static array of "Fillup" objects that can be only accessed and edited through the methods in this class.

public class FillupLog {
    private static ArrayList<Fillup> fillups = new ArrayList<Fillup>();

    public FillupLog() {
    }

    public static ArrayList<Fillup> getFillups() {
        return fillups;
    }

    public static void setFillups(ArrayList<Fillup> newfillups) {
        fillups = newfillups;
    }

    public Fillup getFillup(int index) {
        return fillups.get(index);
    }

    public void addFillup (Fillup fillup) {
        FillupLog.fillups.add(fillup);
    }

    public void addFillup (Fillup fillup, int index) {
        FillupLog.fillups.add(index,fillup);
    }

    public void removeFillup (int index){
        FillupLog.fillups.remove(index);
    }
}
