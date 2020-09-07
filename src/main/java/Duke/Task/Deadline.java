package Duke.Task;

import Duke.Duke;
import Duke.Task.Task;

public class Deadline extends Task {
    protected String time;
    public Deadline (String deadline, String timeDL) {
        description = deadline;
        time = timeDL;
        type = "[D]";
        isDone = false;
    }
    public Deadline() {
        description = "";
        time = "";
        type = "[D]";
        isDone = false;
    }
    @Override
    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }
    @Override
    public String getType() {
        return this.type;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public void markAsDone() {
        isDone = true;
    }
    @Override
    public String getTime() {
        return time;
    }
}
