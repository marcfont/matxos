package cat.altimiras.matxos.pojo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "time_reads")
public class Read implements Serializable{

    public Read(){}

    public Read(String race, String control, String bib, String time){
        this.time = time;
        ReadKey key = new ReadKey();
        key.setBib(bib);
        key.setControl(control);
        key.setRace(race);
        this.readKey = key;
    }

    @EmbeddedId
    private ReadKey readKey;

    private String time;


    public ReadKey getReadKey() {
        return readKey;
    }

    public void setReadKey(ReadKey readKey) {
        this.readKey = readKey;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Embeddable
    @Access(AccessType.FIELD)
    public class ReadKey implements Serializable {

        private String race;

        private String bib;

        private String control;

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
    }

}
