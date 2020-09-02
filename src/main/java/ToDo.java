public class ToDo extends Task {
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
    }
}
