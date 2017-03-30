package cat.matxos.pojo;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "control")
public class Control {

    @EmbeddedId
    private ControlKey key;

    private String name;

    private int orderC;

    public String getId() {
        return key.getId();
    }

    public String getName() {
        return name;
    }

    public int getOrder() {
        return orderC;
    }

    public void setOrder(int order) {
        this.orderC = order;
    }

    public ControlKey getKey() {
        return key;
    }

    public void setKey(ControlKey key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Embeddable
    @Access(AccessType.FIELD)
    public static class ControlKey implements Serializable {
        public ControlKey() {
        }

        public ControlKey(String race, String id) {
            this.race = race;
            this.id = id;
        }

        private String id;

        private String race;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getRace() {
            return race;
        }

        public void setRace(String race) {
            this.race = race;
        }
    }
}
