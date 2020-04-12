import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Path;
import java.util.*;

public class RankingSystem {
    public static void main(String args[]) {
        Teams teams = new Teams();
        Team teamA;
        Team teamB;
        List<String> paths = new ArrayList<>();
        paths.add("2011-2012.csv");
        paths.add("2012-2013.csv");
        paths.add("2013-2014.csv");
        paths.add("2014-2015.csv");
        paths.add("2015-2016.csv");
        paths.add("2016-2017.csv");
        paths.add("2017-2018.csv");
        paths.add("2018-2019.csv");
        //paths.add("2019-2020.csv");
        try {
            for (String path : paths) {
                System.out.println(path);
                Reader in = new FileReader("C:\\Users\\phapa\\Downloads\\EPL_Data\\datasets\\" + path);
                Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
                int i = 1;
                for (CSVRecord record : records) {// 3 - Team A 4 Team B  5 - 6 =  GD
                    if (i == 1) {
                        i++;
                        continue;
                    }
                    if (record.get(2).trim() != "" && record.get(3).trim() != "") {

                        teamA = teams.getByName(record.get(2));
                        teamB = teams.getByName(record.get(3));
                        int goalDifference = Integer.parseInt(record.get(4)) - Integer.parseInt(record.get(5));

                        teamA.addRecord(teamB.getName(),goalDifference);
                        teamB.addRecord(teamA.getName(),goalDifference*-1);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        for (Team t : teams.getLstTeam()) {
            if(t.getName().equalsIgnoreCase("liverpool")) {
                System.out.println("-----------------------------------------");
                System.out.println(t.getName());
                System.out.println("-----------------------------------------");

                System.out.println("Average Mean:" + t.avgMean());
                System.out.println("-----------------------------------------");

                for (Map.Entry<String, ProbabilityDistribution> entry : t.getTeamStats().entrySet()) {
                    System.out.println(entry.getKey() + "  Mean:" + entry.getValue().getMean() + " SD:" + entry.getValue().getSD(entry.getValue().getMean()));
                }
            }
        }
    }
}


