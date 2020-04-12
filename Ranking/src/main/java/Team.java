import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Team {

    public Team(String name) {
        this.name = name;
        this.teamStats = new HashMap<String, ProbabilityDistribution>();
    }

    public void addRecord(String opponentTeamName, int goalDifference) {
        ProbabilityDistribution pd = this.teamStats.get(opponentTeamName);
        if (pd == null) {
            this.teamStats.put(opponentTeamName, new ProbabilityDistribution());
        }
        this.teamStats.get(opponentTeamName).addRecord(goalDifference);
    }

    public double avgMean() {
        double mean = 0;
        int count = 0;
        for (ProbabilityDistribution pd : this.teamStats.values()) {
            double mean2 = pd.getMean();
            int count2 = pd.getCount();

            mean = ((mean * count) + (mean2 * count2)) / (count + count2);
            count = count + count2;
        }
        return mean;
    }

    public double calculateCombinedSD() {
        return 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, ProbabilityDistribution> getTeamStats() {
        return teamStats;
    }

    public void setTeamStats(Map<String, ProbabilityDistribution> teamStats) {
        this.teamStats = teamStats;
    }

    private String name;
    private Map<String, ProbabilityDistribution> teamStats;
}
