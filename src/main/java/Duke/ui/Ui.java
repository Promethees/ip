package duke.ui;
import static duke.constants.Constants.*;
import static duke.Duke.tasks;

public class Ui {
    //public Ui();
    public void printWelcomeGreet() {
        System.out.println(PROMETHEES);
        System.out.println(HORIZONTAL);
        System.out.println(HELLO_GREET);
        System.out.println(HORIZONTAL);
    }
    public void printGoodbye() {
        System.out.println("\t" +HORIZONTAL);
        System.out.println("\t"+GOOD_BYE);
        System.out.println("\t" +HORIZONTAL);
    }
    public void printNotFound() {
        System.out.println(NOTFOUND_ITEM);
    }
    public void printRejectTodo() {
        System.out.println(REJECT_TODO);
    }
    public void printRejectEvent() {
        System.out.println(REJECT_EV);
    }
    public void printRejectDeadline() {
        System.out.println(REJECT_DL);
    }
    public void printRejectDone() {
        System.out.println(REJECT_DONE + tasks.getCountTasks());
    }
    public void printRejectRemove() {
        System.out.println(REJECT_REMOVE + tasks.getCountTasks());
    }
    public void printRejectFind() {
        System.out.println(REJECT_FIND);
    }
    public void printRejectDate() {
        System.out.println(REJECT_DATE);
    }

}
