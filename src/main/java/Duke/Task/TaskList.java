package duke.task;

import java.util.ArrayList;

public class TaskList {
    private int countTasks;
    private ArrayList<Task> tasks;

    public TaskList() {
        countTasks = 0;
        this.tasks = new ArrayList<>();
    }
    public void add(Task t) {
        countTasks ++;
        this.tasks.add(t);
    }
    public Task get(int i) {
        return this.tasks.get(i);
    }
    public void remove(Task t) {
        tasks.remove(t);
    }
    public int getCountTasks() {
        return this.countTasks;
    }
    public ArrayList<Task> getAllTasks() {
        return this.tasks;
    }
    public TaskList getAll() {
        return this;
    }
}
