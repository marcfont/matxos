package cat.matxos.control.pojo;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "time_reads")
public class Read implements Serializable, Persistable {

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

    @Column(name = "time")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Read read = (Read) o;

        return !(readKey != null ? !readKey.equals(read.readKey) : read.readKey != null);

    }

    @Override
    public int hashCode() {
        return readKey != null ? readKey.hashCode() : 0;
    }

    /*
    public String getRace() {
        return readKey.getRace();
    }

    public void setRace(String race){
        this.readKey.setRace(race);
    }

    public String getBib() {
        return readKey.getBib();
    }

    public void setBib(String bib) {
        this.readKey.setBib(bib);
    }

    public String getControl() {
        return this.readKey.getControl();
    }

    public void setControl(String control) {
        this.readKey.setControl(control);
    }

*/



    @Override
    public Serializable getId() {
        return readKey;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    @Embeddable
    @Access(AccessType.FIELD)
    public static class ReadKey implements Serializable {

        public ReadKey() {
        }

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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ReadKey readKey = (ReadKey) o;

            if (race != null ? !race.equals(readKey.race) : readKey.race != null) return false;
            if (bib != null ? !bib.equals(readKey.bib) : readKey.bib != null) return false;
            return true;

        }

        @Override
        public int hashCode() {
            int result = race != null ? race.hashCode() : 0;
            result = 31 * result + (bib != null ? bib.hashCode() : 0);
            return result;
        }
    }

}
