public class Deadline extends Task {
    public Deadline (String deadline, String time) {
        super.description = deadline;
        super.time = time;
        super.type = "[D]";
    }
    public Deadline() {
        super.description = "";
        super.time = "";
    }
}
