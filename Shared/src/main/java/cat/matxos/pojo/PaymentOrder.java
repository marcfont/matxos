package cat.matxos.pojo;

import javax.persistence.*;

@Entity
@Table(name = "payment_order")
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double amount;

    private String status;

    private String response;

    private String auth_code;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getAuth_code() {
        return auth_code;
    }

    public void setAuth_code(String auth_code) {
        this.auth_code = auth_code;
    }

    @Override
    public String toString() {
        return "PaymentOrder{" +
                "id=" + id +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", response='" + response + '\'' +
                ", auth_code='" + auth_code + '\'' +
                '}';
    }
}
