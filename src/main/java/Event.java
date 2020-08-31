public class Event extends Task {
    //private final String time;
    public Event (String event, String time) {
        super.description = event;
        super.time = time;
        super.type = "[E]";
    }
    public Event() {
        super.description = "";
        super.time = "";
    }
    //public String getTime() {
    /*    return this.time;
    }*/
}
