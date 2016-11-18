package cat.matxos.ranking.pojo;


public class FilterRanking {

    private String bib;
    private String name;
    private String route;
    private Boolean male;

    public FilterRanking(){}

    public FilterRanking(String bib, String name, String route, String gender) {
        this.bib = bib;
        this.name = name;
        this.route = route;
        if (gender != null && !gender.isEmpty()) {
            this.male = gender.equals("M");
        }
    }

    public String getBib() {
        return bib;
    }

    public void setBib(String bib) {
        this.bib = bib;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
}
