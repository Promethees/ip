public class Deadline extends Task {
    public Deadline (String deadline, String timeDL) {
        description = deadline;
        time = timeDL;
        type = "[D]";
    }
    public Deadline() {
        description = "";
        time = "";
    }
}
