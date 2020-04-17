import java.util.HashMap;
import java.util.Map;

/**
 * Details related to a particular opponent of a team. All the details here are about games against that
 * opponent this instance is associated to
 */
public class ProbabilityDistribution {

    public ProbabilityDistribution() {
        this.count = 0;
        this.probabilityOfEvent = new HashMap<>();
        this.noOfTimesEventOccurred = new HashMap<>();
    }

    public void addRecord(int goalDifference) {
        ++count;
        int value = this.noOfTimesEventOccurred.get(goalDifference) == null ? 0 : this.noOfTimesEventOccurred.get(goalDifference);
        this.noOfTimesEventOccurred.put(goalDifference, value + 1);
        this.updateProbability();
        this.mean = (this.mean*(count-1)+goalDifference)/count;
    }

    public double getSD(double mean){
        double difference = 0;
        for (Map.Entry<Integer, Integer> entry : this.noOfTimesEventOccurred.entrySet()) {
            difference += Math.pow(entry.getKey()-mean,2)*entry.getValue();
        }
        difference = difference/(count-1);
        return Math.sqrt(difference);
    }

    private void updateProbability() {
        for (Integer key : this.noOfTimesEventOccurred.keySet()) {
            this.probabilityOfEvent.put(key, (double) this.noOfTimesEventOccurred.get(key) / count);
        }
    }
    public Map<Integer, Double> getProbabilityOfEvent() {
        return probabilityOfEvent;
    }

    public void setProbabilityOfEvent(Map<Integer, Double> probabilityOfEvent) {
        this.probabilityOfEvent = probabilityOfEvent;
    }

    public Map<Integer, Integer> getNoOfTimesEventOccurred() {
        return noOfTimesEventOccurred;
    }

    public void setNoOfTimesEventOccurred(Map<Integer, Integer> noOfTimesEventOccurred) {
        this.noOfTimesEventOccurred = noOfTimesEventOccurred;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }
    private Map<Integer, Double> probabilityOfEvent; //key = GD, value = probability of getting that GD among all
    // matches played
    private Map<Integer, Integer> noOfTimesEventOccurred; //key = GD, value = count of matches resulting in this GD
    private int count; //count of matches played
    private double mean; //mean of GD of all matches played against this team, i.e. Team A vs Team B
}
