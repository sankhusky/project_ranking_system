import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class EPLTests {

    private static final String BASE_PATH = "datasets/";
    private Teams teams;
    
    @Test
    public void testTeamCompare(){
        assertEquals( "ApproxGD"
                ,java.util.Optional.of(3)
                , java.util.Optional.ofNullable(teams.compareTeams("Man City", "Brighton", 1))
        );
    }

    @Test
    public void reverseTeamTest(){
        assertEquals( "SameTeamsReverse"
                ,java.util.Optional.ofNullable(teams.compareTeams("Southampton", "Chelsea", 1))
                , java.util.Optional.of(-1*teams.compareTeams("Chelsea", "Southampton", 1))
        );
    }

    @Test
    public void testLiverpool(){
        assertEquals("YNWA"
                ,java.util.Optional.of(1)
                , java.util.Optional.ofNullable(teams.compareTeams("Liverpool", "Tottenham", 1))
        );
    }



    @Before
    public void setUpData(){
        teams = new Teams();
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

                Reader in = new FileReader(BASE_PATH + path);
//                System.out.println(path);

                Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
                int i = 1;
                //iterate row-wise
                for (CSVRecord record : records) {// 3 - Team A 4 Team B  5 - 6 =  GD
                    if (i == 1) {
                        i++;
                        continue; //omit row 1
                    }
                    if (!record.get(2).trim().equals("") && !record.get(3).trim().equals("")) {

                        teamA = teams.getByName(record.get(2)); //team A object
                        teamB = teams.getByName(record.get(3)); //team B object
                        int goalDifference = Integer.parseInt(record.get(4)) - Integer.parseInt(record.get(5));//full time home goal - full time away goal

                        teamA.addRecord(teamB.getName(), goalDifference);
                        teamB.addRecord(teamA.getName(), goalDifference * -1);
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        //Reading record of 2019-2020 Filling table properties
        List<TableProperty> table = new ArrayList<>();
        try {
            Reader in = new FileReader(BASE_PATH + "2019-2020.csv");
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

                    TableProperty tbl1 = table.stream().filter(x -> x.getTeamName().equalsIgnoreCase(record.get(2))).findFirst().orElse(null);
                    if (tbl1 == null) {
                        tbl1 = new TableProperty();
                        tbl1.setTeamName(teamA.getName());
                        tbl1.setGoalDifference(goalDifference);
                        if (goalDifference > 0) {
                            tbl1.setPoint(3);
                        }
                        if(goalDifference==0){
                            tbl1.setPoint(tbl1.getPoint() + 1);
                        }
                        table.add(tbl1);
                    } else {
                        tbl1.setGoalDifference(tbl1.getGoalDifference() + goalDifference);
                        if (goalDifference > 0) {
                            tbl1.setPoint(tbl1.getPoint() + 3);
                        }
                        if(goalDifference==0){
                            tbl1.setPoint(tbl1.getPoint() + 1);
                        }
                    }
                    teamA.addRecord(teamB.getName(), goalDifference);
                    goalDifference = goalDifference * -1;
                    TableProperty tbl2 = table.stream().filter(x -> x.getTeamName().equalsIgnoreCase(record.get(3))).findFirst().orElse(null);
                    if (tbl2 == null) {
                        tbl2 = new TableProperty();
                        tbl2.setTeamName(teamB.getName());
                        tbl2.setGoalDifference(goalDifference);
                        if (goalDifference > 0) {
                            tbl2.setPoint(3);
                        }
                        if(goalDifference==0){
                            tbl2.setPoint(1);
                        }
                        table.add(tbl2);
                    } else {
                        tbl2.setGoalDifference(tbl2.getGoalDifference() + goalDifference);
                        if (goalDifference > 0) {
                            tbl2.setPoint(tbl2.getPoint() + 3);
                        }
                        if(goalDifference==0){
                            tbl2.setPoint(tbl2.getPoint() + 1);
                        }
                    }
                    teamB.addRecord(teamA.getName(), goalDifference);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
