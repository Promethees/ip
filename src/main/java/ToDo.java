public class ToDo extends Task {
    public ToDo(String taskTodo) {
        super.description = taskTodo;
        super.type = "[T]";
        super.isDone = false;
        super.time = "";
    }
    public ToDo() {
        super.description = "";
        super.type = "[T]";
        super.isDone = false;
    }
}
