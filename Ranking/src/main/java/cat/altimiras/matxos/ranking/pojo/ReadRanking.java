package cat.altimiras.matxos.ranking.pojo;

public class ReadRanking {

    private String name;

    private String race;

    private String bib;

    private String control;

    private String time;

    private String route;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getBib() {
        return bib;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    @Override
    public String toString() {
        return "ReadRanking{" +
                "name='" + name + '\'' +
                ", race='" + race + '\'' +
                ", bib='" + bib + '\'' +
                ", control='" + control + '\'' +
                ", time='" + time + '\'' +
                ", route='" + route + '\'' +
                '}';
    }
}
