public class TableProperty implements Comparable<TableProperty>{
    private String teamName;
    //private int goalScored;
    //private int goalConceded;
    private int goalDifference;
    private int point;

    @Override
    public int compareTo(TableProperty o) {
        return o.getPoint()-this.getPoint();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getGoalDifference() {
        return goalDifference;
    }

    public void setGoalDifference(int goalDifference) {
        this.goalDifference = goalDifference;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
