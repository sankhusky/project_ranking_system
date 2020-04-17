import com.opencsv.CSVWriter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.*;

public class RankingSystem {
    //Sanket's PC
//    private static final String BASE_PATH = "D:\\abhyas\\sem2\\psa\\project_ranking_system\\Ranking\\datasets\\";
        private static final String BASE_PATH = "datasets/";
    //Akshay's PC
//    private static final String BASE_PATH = "C:\\Users\\phapa\\Downloads\\EPL_Data\\datasets\\";
    private static final Scanner scanner = new Scanner(System.in);

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

                Reader in = new FileReader(BASE_PATH + path);
                System.out.println(path);

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
                if (!record.get(2).trim().equals("") && !record.get(3).trim().equals("")) {
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

        //Computing results for remaining matches
        try {
            Reader in = new FileReader(BASE_PATH + "remaining.csv");
            Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
            for (CSVRecord record : records) {

                if (!record.get(0).trim().equals("") && !record.get(1).trim().equals("")) {
                    teamA = teams.getByName(record.get(0));
                    teamB = teams.getByName(record.get(1));
                    Integer goalDifference = teams.compareTeams(record.get(0), record.get(1));
                    if (goalDifference != null) {
                        TableProperty tbl1 = table.stream().filter(x -> x.getTeamName().equalsIgnoreCase(record.get(0))).findFirst().orElse(null);
                        if (tbl1 == null) {
                            tbl1 = new TableProperty();
                            tbl1.setTeamName(teamA.getName());
                            tbl1.setGoalDifference(goalDifference);
                            if (goalDifference > 0) {
                                tbl1.setPoint(3);
                            }
                            if (goalDifference == 0) {
                                tbl1.setPoint(tbl1.getPoint() + 1);
                            }
                            table.add(tbl1);
                        } else {
                            tbl1.setGoalDifference(tbl1.getGoalDifference() + goalDifference);
                            if (goalDifference > 0) {
                                tbl1.setPoint(tbl1.getPoint() + 3);
                            }
                            if (goalDifference == 0) {
                                tbl1.setPoint(tbl1.getPoint() + 1);
                            }
                        }
                        goalDifference = goalDifference * -1;
                        TableProperty tbl2 = table.stream().filter(x -> x.getTeamName().equalsIgnoreCase(record.get(1))).findFirst().orElse(null);
                        if (tbl2 == null) {
                            tbl2 = new TableProperty();
                            tbl2.setTeamName(teamB.getName());
                            tbl2.setGoalDifference(goalDifference);
                            if (goalDifference > 0) {
                                tbl2.setPoint(3);
                            }
                            if (goalDifference == 0) {
                                tbl2.setPoint(1);
                            }
                            table.add(tbl2);
                        } else {
                            tbl2.setGoalDifference(tbl2.getGoalDifference() + goalDifference);
                            if (goalDifference > 0) {
                                tbl2.setPoint(tbl2.getPoint() + 3);
                            }
                            if (goalDifference == 0) {
                                tbl2.setPoint(tbl2.getPoint() + 1);
                            }
                        }
                    }
                    else{
                        System.out.println("Error in calculation.");
                        break;
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Collections.sort(table);

        File file = new File(BASE_PATH + "result.csv");
        try {
            // create FileWriter object with file as parameter
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv
            String[] header = { "Team Name", "Goal Difference", "Points" };
            writer.writeNext(header);

            for(TableProperty tbl : table){
                String[] data = { tbl.getTeamName(), String.valueOf(tbl.getGoalDifference()), String.valueOf(tbl.getPoint())};
                writer.writeNext(data);
            }
            // closing writer connection
            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("-------------------------Get probability of a team winning or " +
                "losing:--------------------------");
        System.out.println("Please enter the full names of 2 teams of your choice, comma separated :");
        String input = scanner.nextLine();

        try {
            if(input.contains(",")){
                String[] teamPair = input.split(",");
                teamPair[0] = teamPair[0].trim();
                teamPair[1] = teamPair[1].trim();
                Team t1 = teams.getByName(teamPair[0]);
                Team t2 = teams.getByName(teamPair[1]);
                double[] matchProbabilities = t1.getTeamStats().get(t2.getName()).getGameProbabilities();
                String ftResult;
                int i = getMaxIndex(matchProbabilities);
                if(i==0){
                    ftResult = "win";
                }else if(i==1){
                    ftResult = "lose";
                }else{
                    ftResult = "draw";
                }
                int predictedGD = Math.abs(teams.compareTeams(teamPair[0],teamPair[1]));
                System.out.println(teamPair[0] + " has following probabilities of full-time result against "+teamPair[1] + ":");
                System.out.println("Winning:"+matchProbabilities[0]);
                System.out.println("Losing:"+matchProbabilities[1]);
                System.out.println("Draw:"+matchProbabilities[2]);
                if(!ftResult.equals("draw")){
                    System.out.println(teamPair[0] + " is most likely to "+ftResult+ " against "+ teamPair[1] + " with an" +
                            " average goal-difference of approximately "+predictedGD);
                }else{
                    System.out.println(teamPair[0] + " is most likely to "+ftResult+ " against "+ teamPair[1]);
                }
            }else{
                System.out.println("Please enter the names in correct format.");
            }
        } catch (Exception e) {
            System.out.println("Team is not present. Please check the input.");
        }

        scanner.close();
    }

    private static int getMaxIndex(double[] matchProbabilities) {
        double max = Double.MIN_VALUE;
        int index=-1;
        for (int i = 0; i < matchProbabilities.length; i++) {
            if(matchProbabilities[i]>max){
                max = matchProbabilities[i];
                index = i;
            }
        }
        return index;
    }
}


