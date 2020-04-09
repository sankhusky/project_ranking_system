import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Team {

    public Team(String name){
        this.id = counter++;
        this.name = name;
        this.teamStats = new HashMap<Integer, ProbabilityDistribution>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getCounter() {
        return counter;
    }

    public static void setCounter(int counter) {
        Team.counter = counter;
    }

    public Map<Integer, ProbabilityDistribution> getTeamStats() {
        return teamStats;
    }

    public void setTeamStats(Map<Integer, ProbabilityDistribution> teamStats) {
        this.teamStats = teamStats;
    }

    private int id;
    private String name;
    private static int counter=1;
    private Map<Integer, ProbabilityDistribution> teamStats;
}
