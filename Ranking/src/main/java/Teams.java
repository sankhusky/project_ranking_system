import java.util.ArrayList;
import java.util.List;

public class Teams {
    public Teams(){
        this.lstTeam = new ArrayList<>();
    }
    public Team getByName(String name){
        return this.lstTeam.stream().filter(x->x.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public String getById(int id){
        return this.lstTeam.stream().filter(x->x.getId()==id).findFirst().orElse(null).getName();
    }

    public List<Team> getLstTeam() {
        return lstTeam;
    }

    public void setLstTeam(List<Team> lstTeam) {
        this.lstTeam = lstTeam;
    }

    private List<Team> lstTeam;
}
