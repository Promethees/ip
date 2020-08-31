public class Deadline extends Task{
    //private final String deadline;
    public Deadline() {
        super.description = "";
        //this.deadline = "";
        super.time = "";
    }
    public Deadline(String task, String deadline) {
        super.description = task;
        super.time = deadline;
        super.type = "[D]";
    }
    /*public String getDeadline() {
        return this.deadline;
    }*/
}
