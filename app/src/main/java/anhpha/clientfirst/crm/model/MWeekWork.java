package anhpha.clientfirst.crm.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Window7 on 3/30/2017.
 */
public class MWeekWork {

    public List<MWeek> weeks ;
    public List<MClient> clients ;

    public MWeekWork() {
        clients = new ArrayList<>();
        weeks = new ArrayList<>();

    }

    public List<MWeek> getWeeks() {
        return weeks;
    }

    public void setWeeks(List<MWeek> weeks) {
        this.weeks = weeks;
    }

    public List<MClient> getClients() {
        return clients;
    }

    public void setClients(List<MClient> clients) {
        this.clients = clients;
    }
}
