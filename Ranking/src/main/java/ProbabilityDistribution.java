import java.util.HashMap;
import java.util.Map;

public class ProbabilityDistribution {

    public ProbabilityDistribution(){
        this.count=0;
        this.probabilityOfEvent = new HashMap<>();
        this.noOfTimesEventOccurred =  new HashMap<>();
    }

    public void addRecord(int goalDifference){
        ++count;
        int value = this.noOfTimesEventOccurred.get(goalDifference)==null?0:this.noOfTimesEventOccurred.get(goalDifference);
        this.noOfTimesEventOccurred.put(goalDifference, value+1);
        this.updateProbability();
    }

    public double getMean(){
        double sum =0.0;
        for(double value : this.probabilityOfEvent.values()){
            sum+=value;
        }

        return sum/count;
    }

    private void updateProbability(){
        for(Integer key : this.noOfTimesEventOccurred.keySet()){
            this.probabilityOfEvent.put(key, (double)this.noOfTimesEventOccurred.get(key)/count);
        }
    }
    private Map<Integer, Double> probabilityOfEvent;
    private Map<Integer, Integer> noOfTimesEventOccurred;
    private int count;
}
