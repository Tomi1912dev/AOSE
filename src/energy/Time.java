package energy;

public class Time {
    private int hour;
    private int minute;

    public Time(int hour, int minute) {
        try {
            if (hour < 60 && minute < 60) {
                this.hour = hour;
                this.minute = minute;
            } else { throw new Exception(); }
        } catch (Exception e) { System.err.println("Hour or minute exceeds the maximum number"); }
    }

    @Override
    public String toString() {
        return hour + "h" + minute;
    }
}
