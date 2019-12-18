package trendelenburg.de.Models;

public class PointEvent {

    private int points;
    private String reason;

    public PointEvent(int points, String reason) {
        this.points = points;
        this.reason = reason;
    }

    @Override
    public String toString() {
        if(points>=0){
            return "§a+"+points+": "+reason;
        }else{
            return "§c"+points+": "+reason;
        }

    }
}

