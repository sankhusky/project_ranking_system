import java.util.*;

public class TeamComparator {
    public static int compare(Team t1, Team t2) {
        Map<String, ProbabilityDistribution> pd1 = t1.getTeamStats();
        Map<String, ProbabilityDistribution> pd2 = t2.getTeamStats();
        Set<String> commonOpponents;
        if (pd1.containsKey(t2.getName()) && pd1.get(t2.getName()).getCount() > 4) { //// they have record with each other & played more than 3 matches
            return roundOff(pd1.get(t2.getName()).getMean());
        } else if ((commonOpponents = findCommonOpponents(pd1.keySet(), pd2.keySet())).size() > 0) { // they have played against common opponents
            List<ProbabilityDistribution> lst1 = new ArrayList<>();
            List<ProbabilityDistribution> lst2 = new ArrayList<>();
            for (String s : commonOpponents) {
                lst1.add(pd1.get(s));
                lst2.add(pd2.get(s));
            }
            return compareByMeanSD(t1.getAvgMean(lst1), t1.getAvgSD(lst1), t2.getAvgMean(lst2), t2.getAvgSD(lst2));
        } else { //general comparison
            return compareByMeanSD(t1.getAvgMean(), t1.getAvgSD(), t2.getAvgMean(), t2.getAvgSD());
        }
    }

    public static Set<String> findCommonOpponents(Set<String> team1Opponents, Set<String> team2Opponents) {
        //Finding common opponents
        if (team1Opponents.size() < team2Opponents.size()) {
            team1Opponents.retainAll(team2Opponents);
            return team1Opponents;
        } else {
            team2Opponents.retainAll(team1Opponents);
            return team2Opponents;
        }
    }

    /**
     * Calculates the mean-difference of the two teams or the Standard Deviation differences by comparing with a
     * threshold to find the team most likely to win
     * @param avgMean1
     * @param avgSD1
     * @param avgMean2
     * @param avgSD2
     * @return mean Goal-Difference between the two teams
     */
    public static int compareByMeanSD(double avgMean1, double avgSD1, double avgMean2, double avgSD2) {
        //compare using means
        double difference = 0.0;
        if ((avgMean1 <= 0 && avgMean2 <= 0) || (avgMean1 >= 0 && avgMean2 >= 0)) {
            difference = Math.abs(avgMean1 - avgMean2);
        } else { //opposite sign values
            difference = Math.abs(avgMean1) + Math.abs(avgMean2);
        }
        if (difference > 0.3) { //0.3 assumed as a threshold
            return roundOff(avgMean1 - avgMean2); //high mean high chances to win
        } else { //curves are very similar, need to consider SD for comparison
            return avgSD1 < avgSD2 ? roundOff(avgMean1) : roundOff(avgMean2);  //small sd high accuracy to win
        }
    }

    public static int roundOff(double val) {
        if (val <= 0) {
            return (int) Math.ceil(val);
        } else {
            return (int) Math.floor(val);
        }
    }
}
