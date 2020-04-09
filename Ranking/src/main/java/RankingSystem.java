import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.*;

public class RankingSystem {
    public static void main(String args[]){
        Teams teams = new Teams();
        Team teamA;
        Team teamB;
        try {
            Reader in = new FileReader("C:\\Users\\phapa\\Downloads\\EPL_Data\\datasets\\2019-2020.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            int i=1;
            for (CSVRecord record : records) {// 3 - Team A 4 Team B  5 - 6 =  GD
                if(i==1){i++;continue;}
                String teamName1 = record.get(3);
                if(teams.getByName(teamName1)==null){
                    teams.getLstTeam().add(new Team(teamName1));
                }
                String teamName2 = record.get(4);
                if(teams.getByName(teamName2)==null){
                    teams.getLstTeam().add(new Team(teamName2));
                }
                teamA = teams.getByName(teamName1);
                teamB = teams.getByName(teamName2);
                int goalDifference = Integer.parseInt(record.get(5))- Integer.parseInt(record.get(6));

                if(teamA.getTeamStats().get(teamB.getId())==null){
                    ProbabilityDistribution pd = new ProbabilityDistribution();
                    pd.addRecord(goalDifference);
                    teamA.getTeamStats().put(teamB.getId(),pd);
                }
                else {
                    teamA.getTeamStats().get(teamB.getId()).addRecord(goalDifference);
                }
                goalDifference = -1 * goalDifference;
                if(teamB.getTeamStats().get(teamA.getId())==null){
                    ProbabilityDistribution pd = new ProbabilityDistribution();
                    pd.addRecord(goalDifference);
                    teamB.getTeamStats().put(teamA.getId(),pd);
                }
                else {
                    teamB.getTeamStats().get(teamA.getId()).addRecord(goalDifference);
                }
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }

        for(Team t : teams.getLstTeam()){
            System.out.println("-----------------------------------------");
            System.out.println(t.getName());
            System.out.println("-----------------------------------------");

            for(Map.Entry<Integer, ProbabilityDistribution> entry : t.getTeamStats().entrySet()){
                System.out.println(teams.getById(entry.getKey())+" - "+ entry.getValue().getMean());
            }
        }
    }
}


