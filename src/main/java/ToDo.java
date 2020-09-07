public class ToDo extends Task {
    protected String time;
    public ToDo(String taskTodo) {
        description = taskTodo;
        type = "[T]";
        isDone = false;
        time = "";
    }
    public ToDo() {
        description = "";
        type = "[T]";
        isDone = false;
        time = "";
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
