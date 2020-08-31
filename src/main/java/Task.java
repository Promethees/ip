public class Task {
    protected String type;
    protected String description;
    protected boolean isDone;
    protected String time;
    //Generator, in the main file must you "new" modifier to create the objects
    public Task(String description) {
        this.description = description;
        this.isDone = false;
        this.type = "";
    }
    public Task() {
        this.description = "";
        this.isDone = false;
        this.type = "";
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

    public void markAsDone(){
        this.isDone = true;
    }

    public String getTime() {return this.time;}

}