import java.util.Scanner;
public class Duke {
    /*
        Notes for the Exceptions:
        +ipException: Check if after "todo, deadline, event" command is an empty string or not. More specifically, check whether
                                       "/by" or "/at" have been included in "deadline" or "event" yet
        +ipException2: Check if available commands "bye list todo deadline event done" have been stated correctly or not.
        Check if Task and Time in "deadline" or "event" are empty strings or not
        +ipException3: avoid increment of countTasks when invalid syntax is called
     */
    public static int MAX_TASK = 100;
    public static String DUKE =  " ____        _        \n"
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
            " My Child, I am Promethees, who had devoted his liver to liberate humanity from the Olympian's oppression\n"
                    +" Tell me, what can I do for you?";
    public static String GOOD_BYE = " Mission accomplished!";
    public static String INSTRUCTION = "Invalid Command! Available Commands: bye, list, todo, deadline, event, done.";
    public static int TODO_INDEX = 5;
    public static int DEADLINE_INDEX = 9;
    public static int EVENT_INDEX = 8;
    public static String REJECT_TODO = "Invalid command for todo. Must be \ntodo <nameOfTodo>; name";
    public static String REJECT_DL = "Invalid command for deadline. Must be \ndeadline <nameOfEvent> /by <time>";
    public static String REJECT_EV = "Invalid command for event. Must be \nevent <nameOfEvent> /at <time>";
    public static String REJECT_DONE = "Invalid operation with done. Must be \ndone <(int)taskToBeDone>, taskToBeDone must equal or lesser than ";
    public static String EMPTY_LIST = "This list is currently empty. You can use \"todo\", \"deadline\", \"event\" to add tasks";

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

    public static String parseCommand(String line, int countTasks, Task[] tasks) throws ipException3, ipException2 {
        String[] words = line.split(" ");
        String command = words[0];
        if (command.equalsIgnoreCase("bye")) {
            return command;
        } else if (command.equalsIgnoreCase("list")) {
            return command;
        } else if (command.equalsIgnoreCase("todo")) {
            try {
                String newTodo = analyseTodo(line);
                addTodos(countTasks, tasks, newTodo);
            } catch (ipException e) {
                System.out.println(REJECT_TODO);
                throw new ipException3(); //Throw to this exception to avoid increment in countTasks
            }
        } else if (command.equalsIgnoreCase("deadline")) {
            try {
                String DL = analyseDL(line);
                String newDL = extractFrom(DL,"/by");
                String time = extractTimeFrom(DL,"/by");
                addDeadlines(countTasks, tasks, newDL, time);
            } catch (ipException e) {
                System.out.println(REJECT_DL);
                throw new ipException3(); //Throw to this exception to avoid increment in countTasks
            } catch (ipException2 e) {
                System.out.println(REJECT_DL);
                throw new ipException3();
            }
        } else if (command.equalsIgnoreCase("event")) {
            try {
                String EV = analyseEV(line);
                String newEV = extractFrom(EV,"/at");
                String time = extractTimeFrom(EV,"/at");
                addEvents(countTasks, tasks, newEV, time);
            } catch (ipException e) {
                System.out.println(REJECT_EV);
                throw new ipException3(); //Throw to this exception to avoid increment in countTasks
            } catch (ipException2 e) {
                System.out.println(REJECT_EV);
                throw new ipException3();
            }
        } else if (command.equalsIgnoreCase("done")) {
            String taskToBeDone = line.substring(line.indexOf("done ") + 5);
            boolean isInt = true;
            int num = countTasks + 1;
            try {
                num = Integer.parseInt(taskToBeDone);
            } catch (NumberFormatException e) {
                isInt = false;
                System.out.println(REJECT_DONE + countTasks);
            }
            if (isInt) {
                if (num <= countTasks) {
                    markTaskDone(tasks[num - 1]);
                } else {
                    System.out.println(REJECT_DONE + countTasks);
                }
            }
        } else {
            throw new ipException2();
        }
        return command;
    }

    private static int resolveCommand(String line, int countTasks, Task[] tasks) {
        try {
            String command = parseCommand(line, countTasks, tasks);
            if (command.equalsIgnoreCase("bye")) {
                return -1;
            }
            else if (command.equalsIgnoreCase("list")) {
                printTasks(countTasks, tasks);
            }
            else if (command.equalsIgnoreCase("todo")) {
                countTasks ++;
            }
            else if (command.equalsIgnoreCase("deadline")) {
                countTasks ++;
            }
            else if (command.equalsIgnoreCase("done")) {
                return countTasks;
            }
        } catch (ipException2 e) {
            System.out.println(INSTRUCTION);
            return countTasks;
        } catch (ipException3 e) {
            return countTasks;
        }
        return countTasks;
    }

    public static String analyseTodo(String line) throws ipException {
        if (line.substring(TODO_INDEX).trim().length() == 0) {
            throw new ipException();
        }
        else return line.substring(TODO_INDEX);
    }

    public static String analyseDL(String line) throws ipException {
        if (line.indexOf("/by") == -1) {
            throw new ipException();
        }
        else return line.substring(DEADLINE_INDEX);
    }

    public static String analyseEV(String line) throws ipException {
        if (line.indexOf("/at") == -1) {
            throw new ipException();
        }
        else return line.substring(EVENT_INDEX);
    }

    public static String extractFrom(String line, String extractor) throws ipException2 {
        String newTask = line.substring(0, line.indexOf(extractor));
        System.out.println(newTask);
        if (newTask.trim().length() != 0) {
            return newTask;
        }
        else {
            throw new ipException2();
        }
    }

    public static String extractTimeFrom(String line, String extractor) throws ipException2 {
        String newTime = line.substring(line.indexOf(extractor)+extractor.length() + 1 );
        if (newTime.trim().length() != 0) {
            return newTime;
        }
        else {
            throw new ipException2();
        }
    }

    private static void markTaskDone(Task task) {
        System.out.println("\t" + HORIZONTAL);
        System.out.println("\t Nice! I've marked this task as done: ");
        task.markAsDone();
        System.out.println("\t\t" + task.getStatusIcon() + " " + task.getDescription());
        System.out.println("\t" + HORIZONTAL);
    }

    private static void printTasks(int countTasks, Task[] tasks) {
        if (countTasks > 0) {
            System.out.println("\t" + HORIZONTAL);
            for (int i = 1; i <= countTasks; i++) {
                if (tasks[i - 1].getType() == "[D]") {
                    System.out.println("\t" + i + "." + tasks[i - 1].getType() + tasks[i - 1].getStatusIcon() + " " + tasks[i - 1].getDescription() + "(by:" + tasks[i - 1].getTime() + ")");
                }
                if (tasks[i - 1].getType() == "[E]") {
                    System.out.println("\t" + i + "." + tasks[i - 1].getType() + tasks[i - 1].getStatusIcon() + " " + tasks[i - 1].getDescription() + "(at:" + tasks[i - 1].getTime() + ")");
                } else if (tasks[i - 1].getType() == "[T]") {
                    System.out.println("\t" + i + "." + tasks[i - 1].getType() + tasks[i - 1].getStatusIcon() + " " + tasks[i - 1].getDescription() + tasks[i - 1].getTime());
                }
            }
            System.out.println("\t" + HORIZONTAL);
        } else {
            System.out.println(EMPTY_LIST);
        }
    }

    private static void addTodos(int countTasks, Task[] tasks, String line) {
        tasks[countTasks] = new ToDo(line);
        countTasks++;
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t"+tasks[countTasks - 1].getType() + tasks[countTasks - 1].getStatusIcon() + " " + tasks[countTasks - 1].getDescription());
        System.out.print("\tNow you have " + countTasks + " task");
        if (countTasks > 1)
            System.out.print("s");
        else System.out.print("");
        System.out.println(" in the list.");
    }

    private static void addEvents(int countTasks, Task[] tasks, String line, String time) {
        tasks[countTasks] = new Event(line, time);
        countTasks++;
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t" + tasks[countTasks - 1].getType() + tasks[countTasks - 1].getStatusIcon() + " " + tasks[countTasks - 1].getDescription() + " at:" + tasks[countTasks - 1].getTime());
        System.out.print("\tNow you have " + countTasks + " task");
        if (countTasks > 1)
            System.out.print("s");
        else System.out.print("");
        System.out.println(" in the list.");
    }

    private static void addDeadlines(int countTasks, Task[] tasks, String line, String time) {
        tasks[countTasks] = new Deadline(line, time);
        countTasks++;
        System.out.println("\tGot it. I've added this task: ");
        System.out.println("\t" + tasks[countTasks - 1].getType() + tasks[countTasks - 1].getStatusIcon() + " " + tasks[countTasks - 1].getDescription() + " by:" + tasks[countTasks - 1].getTime());
        System.out.print("\tNow you have " + countTasks + " task");
        if (countTasks > 1)
            System.out.print("s");
        else System.out.print("");
        System.out.println(" in the list.");
    }

    public static void main(String[] args) {
        printWelcomeGreet();
        Task[] tasks = new Task[MAX_TASK];
        int countTasks = 0;
        while(countTasks != -1) {
            Scanner in = new Scanner(System.in);
            String line = in.nextLine();
            countTasks = resolveCommand(line, countTasks, tasks);
        }
        printGoodbye();
    }
}
