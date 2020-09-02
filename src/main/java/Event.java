public class Event extends Task {
    public Event (String event, String timeEV) {
        description = event;
        time = timeEV;
        type = "[E]";
    }
    public Event() {
        description = "";
        time = "";
    }
}
