import java.util.ArrayList;
import java.util.List;

public class Teams {
    public Teams() {
        this.lstTeam = new ArrayList<>();
    }

    public Team getByName(String name) {
        Team team = this.lstTeam.stream().filter(x -> x.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if (team == null) {
            team = new Team(name);
            this.lstTeam.add(team);
        }
        return team;
    }

    /**
     * This method compares the two teams by using the probability data stored in the respective Team instances of
     * the given teams, forwards the objects to a detailed comparator method which checks mean GD and Standard
     * deviation of the mean GD and probabilities to return a predicted GD value
     * @param teamNameA name of team A
     * @param teamNameB name of team B
     * @param roundStyle Type of rounding needed for final value of predicted GD :
     *                   If 0 => Floor-Ceiling rounding
     *                      1=> Basic rounding to nearest integer
     * @return Approximate predicted goal difference between two given teams
     */
    public Integer compareTeams(String teamNameA, String teamNameB, int roundStyle) {
        Team teamA = this.lstTeam.stream().filter(x -> x.getName().equalsIgnoreCase(teamNameA)).findFirst().orElse(null);
        Team teamB = this.lstTeam.stream().filter(x -> x.getName().equalsIgnoreCase(teamNameB)).findFirst().orElse(null);
        if (teamA != null && teamB != null) {
            int gd = TeamComparator.compare(teamA, teamB, roundStyle); // Team A win or loose by what margin
            return gd;
        }
        return null;
    }

    public List<Team> getLstTeam() {
        return lstTeam;
    }

    public void setLstTeam(List<Team> lstTeam) {
        this.lstTeam = lstTeam;
    }

    private List<Team> lstTeam;
}
