import java.util.*;
import java.util.stream.Collectors;

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

    public  double getAvgMean(){
        return getAvgMean(this.getTeamStats().values().stream().collect(Collectors.toList()));
    }

    public double getAvgMean(List<ProbabilityDistribution> teamsPD) {
        double mean = 0;
        int count = 0;
        for (ProbabilityDistribution pd : teamsPD) {
            double mean2 = pd.getMean();
            int count2 = pd.getCount();

            mean = ((mean * count) + (mean2 * count2)) / (count + count2);
            count = count + count2;
        }
        return mean;
    }
    public double getAvgSD(){
        return getAvgSD(this.getTeamStats().values().stream().collect(Collectors.toList()));
    }

    public double getAvgSD(List<ProbabilityDistribution> teamsPD) {
        if (teamsPD.size() == 1) {
            ProbabilityDistribution pd = teamsPD.get(0);
            return pd.getSD(pd.getMean());
        }
        double sd = 0;
        int count = 0;
        for (ProbabilityDistribution pd: teamsPD) {
            double sd2= pd.getSD(pd.getMean());
            int count2= pd.getCount();
            sd = Math.sqrt(((count)*Math.pow(sd,2) + (count2)*Math.pow(pd.getSD(pd.getMean()),2))/(count+count2));
            count = count+count2;
        }
        return sd;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    private String name;
    private Map<String, ProbabilityDistribution> teamStats; //key = opponent, value = probability distribution of
    // goals against that team
    private int score;
}
