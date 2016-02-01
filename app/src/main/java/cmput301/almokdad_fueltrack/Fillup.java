package cmput301.almokdad_fueltrack;

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

    public void setDate(String date) {
        this.date = date;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOdometer() {
        return odometer;
    }

    public void setOdometer(String odometer) {
        this.odometer = odometer;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
