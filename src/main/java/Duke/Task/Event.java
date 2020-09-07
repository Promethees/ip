package Duke.Task;

public class Event extends Task {
    protected String time;
    public Event (String event, String timeEV) {
        description = event;
        time = timeEV;
        type = "[E]";
        isDone = false;
    }
    public Event() {
        description = "";
        time = "";
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
