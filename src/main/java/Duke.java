import javax.sound.midi.SysexMessage;
import java.util.Scanner;
public class Duke {
    public static int MAX_TASK = 100;
    public static String logo = " ____        _        \n"
            + "|  _ \\ _   _| | _____ \n"
            + "| | | | | | | |/ / _ \\\n"
            + "| |_| | |_| |   <  __/\n"
            + "|____/ \\__,_|_|\\_\\___|\n";
    public static String PROMETHEES =    " ____  ____  ____  _      _____ _____  _     _____  _____  ____\n"
                                        +"/  __\\/  __\\/  _ \\/ \\__/|/  __//__ __\\/ \\ /|/  __/ /  __/ / ___\\\n"
                                        +"|  \\/||  \\/|| / \\|| |\\/|||  \\    / \\  | |_|||  \\   |  \\   |    \\\n"
                                        +"|  __/|    /| \\_/|| |  |||  /_   | |  | | |||  /_  |  /_  \\___ |\n"
                                        +"\\_/   \\_/ \\_\\____/\\_/  \\|\\____\\  \\_/  \\_/ \\|\\____\\ \\____\\ \\____/\n";
    public static String HORIZONTAL = "--------------------------------------";
    public static String HELLO_GREET =
            " My Son, I am Promethees, who had devoted his liver to liberate humanity from the Olympian's oppression\n"
                    +" Tell me, what can I do for you?";
    public static String GOOD_BYE = " Mission accomplished!";
    public static String INSTRUCTION = "Invalid Command! Available Commands: bye, list, todo, deadline, event, done.";
    public static int TODO_INDEX = 5;
    public static int DEADLINE_INDEX = 9;
    public static int EVENT_INDEX = 6;

    public static void printWelcomeGreet() {
        System.out.println(PROMETHEES);
        System.out.println(HORIZONTAL);
        System.out.println(HELLO_GREET);
        System.out.println(HORIZONTAL);
    }
    public static void printGoodbye() {
        System.out.println("\t" +HORIZONTAL);
        System.out.println("\t"+GOOD_BYE);
        System.out.println("\t" +HORIZONTAL);
    }
    public static void parseInput(int countTasks, Task[] tasks) {

        //Input and read inputs from the user
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        String[] words = line.split(" ");
        String command = words[0];

        while (true) {
            //System.out.println(command);
            if (command.equalsIgnoreCase("bye")) {
                return;
            } else if (command.equalsIgnoreCase("list")) {
                System.out.println(HORIZONTAL);
                printTasks(countTasks, tasks);
                System.out.println(HORIZONTAL);
            } else if (command.equalsIgnoreCase("todo")) {
                System.out.println(HORIZONTAL);
                String newTodo = line.substring(TODO_INDEX);
                countTasks = addTodos(countTasks, tasks, newTodo);
                System.out.println("\tGot it. I've added this task: ");
                System.out.println("\t"+tasks[countTasks - 1].getType() + tasks[countTasks - 1].getStatusIcon() + " " + tasks[countTasks - 1].getDescription());
                System.out.println("\tNow you have " + countTasks + " tasks in the list.");
                System.out.println(HORIZONTAL);
            } else if (command.equalsIgnoreCase("deadline")) {
                System.out.println(HORIZONTAL);
                if (line.indexOf("/by") == -1) {
                    System.out.println("Invalid command for deadline. Must be \n deadline <nameOfEvent> /at <time>");
                }
                else {
                    System.out.println(line.indexOf("/"));
                    String newDL = line.substring(DEADLINE_INDEX, line.indexOf("/by"));
                    String time = line.substring(line.indexOf("/by") + 3);
                    System.out.println(newDL);
                    System.out.println(time);
                    countTasks = addDeadlines(countTasks, tasks, newDL, time);
                    System.out.println("\tGot it. I've added this task: ");
                    System.out.println("\t" + tasks[countTasks - 1].getType() + tasks[countTasks - 1].getStatusIcon() + " " + tasks[countTasks - 1].getDescription() + " by: " + tasks[countTasks - 1].getTime());
                    System.out.println("\tNow you have " + countTasks + " tasks in the list.");
                    System.out.println(HORIZONTAL);
                }
            } else if (command.equalsIgnoreCase("event")) {
                System.out.println(HORIZONTAL);
                if (line.indexOf("/at") == -1) {
                    System.out.println("Invalid command for event. Must be \n event <nameOfEvent> /at <time>");
                }
                else {
                    String newEV = line.substring(EVENT_INDEX, line.indexOf("/at"));
                    String time = line.substring(line.indexOf("/at") + 3);
                    countTasks = addEvents(countTasks, tasks, newEV, time);
                    System.out.println("\tGot it. I've added this task: ");
                    System.out.println("\t" + tasks[countTasks - 1].getType() + tasks[countTasks - 1].getStatusIcon() + " " + tasks[countTasks - 1].getDescription() + " at:" + tasks[countTasks - 1].getTime());
                    System.out.println("\tNow you have " + countTasks + " tasks in the list.");
                    System.out.println(HORIZONTAL);
                }
            } else if (command.equalsIgnoreCase("done")) {
                String taskToBeDone = line.substring(line.indexOf("done ") + 5);
                boolean isInt = true;
                int num = countTasks + 1;
                try {
                    num = Integer.parseInt(taskToBeDone);
                } catch (NumberFormatException e) {
                    isInt = false;
                }
                if (isInt) {
                    if (num <= countTasks) {
                        System.out.println("\t" + HORIZONTAL);
                        System.out.println("\t Nice! I've marked this task as done: ");
                        tasks[num - 1].markAsDone();
                        System.out.println("\t\t" + tasks[num - 1].getStatusIcon() + " " + tasks[num - 1].getDescription());
                        System.out.println("\t" + HORIZONTAL);
                    } else {
                        System.out.println("Invalid operation with done. Must be \n done <(int)taskToBeDone>");
                    }
                }
            } else {
                System.out.println(INSTRUCTION);
            }
            line = in.nextLine();
            words = line.split(" ");
            command = words[0];
        }
    }

    private static void printTasks(int countTasks, Task[] tasks) {
        System.out.println("\t" + HORIZONTAL);
        for (int i = 1; i <= countTasks; i++) {
            if (tasks[i-1].getType() == "[D]") {
                System.out.println("\t" + i + "." + tasks[i - 1].getType() + tasks[i - 1].getStatusIcon() + " " + tasks[i - 1].getDescription() + "(by:" +tasks[i - 1].getTime() +")");
            }
            if (tasks[i-1].getType() == "[E]") {
                System.out.println("\t" + i + "." + tasks[i - 1].getType() + tasks[i - 1].getStatusIcon() + " " + tasks[i - 1].getDescription() + "(at:" +tasks[i - 1].getTime() +")");
            }
            else{
                System.out.println("\t" + i + "." + tasks[i - 1].getType() + tasks[i - 1].getStatusIcon() + " " + tasks[i - 1].getDescription() + tasks[i - 1].getTime());
            }
        }
        System.out.println("\t" + HORIZONTAL);
    }

    private static void printTask(Task task) {
        System.out.println("\t" + HORIZONTAL);
        System.out.println("\t Nice! I've marked this task as done: ");
        task.markAsDone();
        System.out.println("\t\t" + task.getStatusIcon() + " " + task.getDescription());
        System.out.println("\t" + HORIZONTAL);
    }

    private static int addTasks(int countTasks, Task[] tasks, String line) {
        System.out.println("\t" + HORIZONTAL);
        System.out.println("\t added: " + line);
        System.out.println("\t" + HORIZONTAL);
        tasks[countTasks] = new Task(line);
        countTasks++;
        return countTasks;
    }
    private static int addTodos(int countTasks, Task[] tasks, String line) {
        //System.out.println("\t" + HORIZONTAL);
        //System.out.println("\t added: " + line);
        //System.out.println("\t" + HORIZONTAL);
        tasks[countTasks] = new ToDo(line);
        countTasks++;
        return countTasks;
    }
    private static int addEvents(int countTasks, Task[] tasks, String line, String time) {
/*        System.out.println("\t" + HORIZONTAL);
        System.out.println("\t added: " + line);
        System.out.println("\t" + HORIZONTAL);*/
        tasks[countTasks] = new Event(line,time);
        countTasks++;
        return countTasks;
    }
    private static int addDeadlines(int countTasks, Task[] tasks, String line, String time) {
/*        System.out.println("\t" + HORIZONTAL);
        System.out.println("\t added: " + line);
        System.out.println("\t" + HORIZONTAL);*/
        tasks[countTasks] = new Deadline(line,time);
        countTasks++;
        return countTasks;
    }

    public static void main(String[] args) {
        printWelcomeGreet();
        //String line;

        //Input and read inputs from the user
        //Scanner in = new Scanner(System.in);
        //line = in.nextLine();

        Task[] tasks = new Task[MAX_TASK];
        int countTasks = 0;
        parseInput(countTasks,tasks);
        printGoodbye();
    }
}
