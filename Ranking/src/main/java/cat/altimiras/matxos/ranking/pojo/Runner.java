package cat.altimiras.matxos.ranking.pojo;


public class Runner {

    private String name;
    private String bib;
    private String race;
    private String route;
    private Boolean male;

    public Runner(String name, String bib, String race, String route, Boolean male) {
        this.name = name;
        this.bib = bib;
        this.race = race;
        this.route = route;
        this.male = male;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBib() {
        return bib;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Boolean getMale() {
        return male;
    }

    public void setMale(Boolean male) {
        this.male = male;
    }
}
