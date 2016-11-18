package cat.matxos.matxoclock.api.form;

public class ReadForm {

    private String race;

    private String bib;

    private String control;

    private String time;

    private String checksum;

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

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    @Override
    public String toString() {
        return "ReadForm{" +
                "race='" + race + '\'' +
                ", bib='" + bib + '\'' +
                ", control='" + control + '\'' +
                ", time='" + time + '\'' +
                ", checksum='" + checksum + '\'' +
                '}';
    }
}
