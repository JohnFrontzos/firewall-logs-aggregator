package john.frontzos.earlywarningsystem.events;

/**
 * @author Ioannis Frontzos
 * @version 1.0.0
 * @since 18/04/2015
 */
public abstract class Event {
    public String data;


    public Event(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
