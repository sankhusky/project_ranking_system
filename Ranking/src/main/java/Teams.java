import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teams {
    public Teams(){
        this.lstTeam = new ArrayList<>();
    }
    public Team getByName(String name){
        Team team = this.lstTeam.stream().filter(x->x.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if(team==null){
            team = new Team(name);
            this.lstTeam.add(team);
        }
        return team;
    }

    public List<Team> getLstTeam() {
        return lstTeam;
    }

    public void setLstTeam(List<Team> lstTeam) {
        this.lstTeam = lstTeam;
    }

    private List<Team> lstTeam;
}
