package cmput301.almokdad_fueltrack;

import java.util.ArrayList;

public class FillupLog {
    private ArrayList<Fillup> fillups= new ArrayList<Fillup>();

    public void add(Fillup fillup){
        fillups.add(fillup);
    }

    public Fillup getFillup(int index){
        return fillups.get(index);
    }

    public void delete(int index){
        fillups.remove(index);
    }

}
