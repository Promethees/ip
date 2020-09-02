public class Event extends Task {
    public Event (String event, String time) {
        super.description = event;
        super.time = time;
        super.type = "[E]";
    }
    public Event() {
        super.description = "";
        super.time = "";
    }
}
