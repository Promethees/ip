public class Task {
    protected String type;
    protected String description;
    protected boolean isDone;
    protected String time;
    //Generator, in the main file must you "new" modifier to create the objects
    public Task(String description) {
        this.description = description;
        isDone = false;
        type = "";
    }
    public Task() {
        description = "";
        isDone = false;
        type = "";
    }

    public String getStatusIcon() {
        return (isDone ? "\u2713" : "\u2718"); //return tick or X symbols
    }
    public String getType() {
        return this.type;
    }
    public String getDescription() {
        return description;
    }

    public void markAsDone() {
        isDone = true;
    }

    public String getTime() {
        return time;
    }

}