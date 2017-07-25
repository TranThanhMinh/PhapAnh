package anhpha.clientfirst.crm.model;

/**
 * Created by mc975 on 2/7/17.
 */
public class MReport {
    private int  id ;
    private String  name ;
    private double  value ;
    private MColor color;
    public int getId() {
        return id;
    }

    public MColor getColor() {
        return color;
    }

    public void setColor(MColor color) {
        this.color = color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
