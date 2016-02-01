package cmput301.almokdad_fueltrack;

//Class to create objects to represent each entry, these objects are called "Fillup", and contain information entered and calculated about the entry.

public class Fillup {
    protected String date;
    protected String station;
    protected String odometer;
    protected String grade;
    protected String amount;
    protected String unit;
    protected float cost;

    public Fillup(String date, String station, String odometer, String grade, String amount, String unit, float cost) {
        this.date = date;
        this.station = station;
        this.odometer = odometer;
        this.grade = grade;
        this.unit = unit;
        this.amount = amount;
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public String getStation() {
        return station;
    }

    public String getOdometer() {
        return odometer;
    }

    public String getGrade() {
        return grade;
    }

    public String getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public float getCost() {
        return cost;
    }
}
