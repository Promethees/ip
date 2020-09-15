package Duke;

import Duke.Exceptions.ipException;
import Duke.Exceptions.ipException2;
import Duke.Exceptions.ipException3;
import Duke.Task.Deadline;
import Duke.Task.Event;
import Duke.Task.Task;
import Duke.Task.ToDo;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Duke {
    /*
        Notes for the Exceptions:
        +Duke.Exceptions.ipException: Check if after "todo, deadline, event" command is an empty string or not. More specifically, check whether
                                       "/by" or "/at" have been included in "deadline" or "event" yet
        +Duke.Exceptions.ipException2: Check if available commands "bye list todo deadline event done" have been stated correctly or not.
        Check if Duke.Task.Task and Time in "deadline" or "event" are empty strings or not
        +Duke.Exceptions.ipException3: avoid increment of countTasks when invalid syntax is called
     */
    private static  ArrayList<Task> tasks = new ArrayList<>();
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
    public static int TODO_INDEX = 4;
    public static int DEADLINE_INDEX = 9;
    public static int EVENT_INDEX = 6;
    public static int DONE_INDEX = 5;
    public static int REMOVE_INDEX = 7;
    public static String REJECT_TODO = "Invalid command for todo. Must be \ntodo <nameOfTodo>; name";
    public static String REJECT_DL = "Invalid command for deadline. Must be \ndeadline <nameOfEvent> /by <time>";
    public static String REJECT_EV = "Invalid command for event. Must be \nevent <nameOfEvent> /at <time>";
    public static String REJECT_DONE = "Invalid operation with done. Must be \ndone <(int)taskToBeDone>, taskToBeDone must be greater than 0 and  equal or lesser than ";
    public static String REJECT_REMOVE = "Invalid operation with remove. Must be \nremove <(int)taskToBeRemoved>, taskToBeRemoved must be greater than 0 and equal or lesser than ";
    public static String EMPTY_LIST = "This list is currently empty. You can use \"todo\", \"deadline\", \"event\" to add tasks";
    public static String ADD_TASK = "\tGot it. I've added this task: ";
    public static String DONE_TASK = "\t Nice! I've marked this task as done: ";
    public static String REMOVE_TASK = "\tNoted! I've removed this task: ";
    private static File previousList = new File("./data/duke.txt");

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

    public static String parseCommand(String line, int countTasks) throws ipException3, ipException2 {
        String[] words = line.split(" ");
        String command = words[0];
        if (command.equalsIgnoreCase("bye")) {
            return command;
        } else if (command.equalsIgnoreCase("list")) {
            return command;
        } else if (command.equalsIgnoreCase("todo")) {
            try {
                String newTodo = analyseTodo(line);
                newTodo = newTodo.trim();
                addTodos(countTasks, newTodo);
            } catch (ipException e) {
                System.out.println(REJECT_TODO);
                throw new ipException3(); //Throw to this exception to avoid increment in countTasks
            }
        } else if (command.equalsIgnoreCase("deadline")) {
            try {
                String DL = analyseDL(line);
                String newDL = extractFrom(DL,"/by");
                String time = extractTimeFrom(DL,"/by");
                addDeadlines(countTasks, newDL, time);
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
                addEvents(countTasks, newEV, time);
            } catch (ipException e) {
                System.out.println(REJECT_EV);
                throw new ipException3(); //Throw to this exception to avoid increment in countTasks
            } catch (ipException2 e) {
                System.out.println(REJECT_EV);
                throw new ipException3();
            }
        } else if (command.equalsIgnoreCase("done")) {
            String taskToBeDone = line.substring(line.indexOf("done ") + DONE_INDEX);
            boolean isInt = true;
            int num = countTasks + 1;
            try {
                num = Integer.parseInt(taskToBeDone);
            } catch (NumberFormatException e) {
                isInt = false;
                System.out.println(REJECT_DONE + countTasks);
            }
            if (isInt) {
                if (num <= countTasks && num > 0) {
                    markTaskDone(num - 1);
                } else {
                    System.out.println(REJECT_DONE + countTasks);
                }
            }
        } else if (command.equalsIgnoreCase("remove")) {
            String taskToBeDone = line.substring(line.indexOf("remove ") + REMOVE_INDEX);
            int num;
            try {
                num = Integer.parseInt(taskToBeDone);
            } catch (NumberFormatException e) {
                System.out.println(REJECT_REMOVE + countTasks);
                throw new ipException3();
            }
            if (num <= countTasks && num > 0) {
                removeTask(num - 1, countTasks);
            } else {
                System.out.println(REJECT_REMOVE + countTasks);
                throw new ipException3();
            }
        } else {
            throw new ipException2();
        }
        return command;
    }

    private static int resolveCommand(String line, int countTasks) {
        try {
            String command = parseCommand(line, countTasks);
            if (command.equalsIgnoreCase("bye")) {
                return -1;
            }
            else if (command.equalsIgnoreCase("list")) {
                printTasks(countTasks);
            }
            else if (command.equalsIgnoreCase("todo")) {
                countTasks ++;
            }
            else if (command.equalsIgnoreCase("deadline")) {
                countTasks ++;
            }
            else if (command.equalsIgnoreCase("event")) {
                countTasks ++;
            }
            else if (command.equalsIgnoreCase("remove")) {
                countTasks --;
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
        if (newTask.trim().length() != 0) {
            return newTask.trim();
        }
        else {
            throw new ipException2();
        }
    }

    public static String extractTimeFrom(String line, String extractor) throws ipException2 {
        String newTime = line.substring(line.indexOf(extractor) + extractor.length() + 1 );
        if (newTime.trim().length() != 0) {
            return newTime.trim();
        }
        else {
            throw new ipException2();
        }
    }

    private static void markTaskDone(int index) {
        System.out.println("\t" + HORIZONTAL);
        System.out.println(DONE_TASK);
        tasks.get(index).markAsDone();
        System.out.println("\t\t" + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription());
        System.out.println("\t" + HORIZONTAL);
    }

    private static void removeTask(int index, int countTasks) {
        System.out.println("\t" + HORIZONTAL);
        System.out.println(REMOVE_TASK);
        printTaskInfo(index);
        tasks.remove(tasks.get(index));
        System.out.print("\tNow you have " + (countTasks - 1) + " task");
        if (countTasks > 1) {
            System.out.println("s in the list.");
        } else {
            System.out.println(" in the list.");
        }
        System.out.println("\t" + HORIZONTAL);
    }

    private static void printTaskInfo(int index) {
        if (tasks.get(index).getType() == "[D]") {
            System.out.println("\t" + tasks.get(index).getType() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription() + " (by:" + tasks.get(index).getTime() + ")");
        }
        if (tasks.get(index).getType() == "[E]") {
            System.out.println("\t" + tasks.get(index).getType() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription() + " (at:" + tasks.get(index).getTime() + ")");
        } else if (tasks.get(index).getType() == "[T]") {
            System.out.println("\t" + tasks.get(index).getType() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription() + tasks.get(index).getTime());
        }
    }

    private static void printTasks(int countTasks) {
        if (countTasks > 0) {
            System.out.println("\t" + HORIZONTAL);
            for (int i = 1; i <= countTasks; i++) {
                System.out.print("\t" + i + ".");
                printTaskInfo(i-1);
            }
            System.out.println("\t" + HORIZONTAL);
        } else {
            System.out.println(EMPTY_LIST);
        }
    }

    private static void addTodos(int countTasks, String line) {
        tasks.add(new ToDo(line));
        countTasks++;
        System.out.println(ADD_TASK);
        System.out.println("\t" + tasks.get(countTasks - 1).getType() + tasks.get(countTasks - 1).getStatusIcon() + " " + tasks.get(countTasks - 1).getDescription());
        System.out.print("\tNow you have " + countTasks + " task");
        if (countTasks > 1)
            System.out.print("s");
        else System.out.print("");
        System.out.println(" in the list.");
    }

    private static void addEvents(int countTasks, String line, String time) {
        tasks.add(new Event(line,time));
        countTasks++;
        System.out.println(ADD_TASK);
        System.out.println("\t" + tasks.get(countTasks - 1).getType() + tasks.get(countTasks - 1).getStatusIcon() + " " + tasks.get(countTasks - 1).getDescription() + " at:" + tasks.get(countTasks - 1).getTime());
        System.out.print("\tNow you have " + countTasks + " task");
        if (countTasks > 1)
            System.out.print("s");
        else System.out.print("");
        System.out.println(" in the list.");
    }

    private static void addDeadlines(int countTasks, String line, String time) {
        tasks.add(new Deadline(line,time));
        countTasks++;
        System.out.println(ADD_TASK);
        System.out.println("\t" + tasks.get(countTasks - 1).getType() + tasks.get(countTasks - 1).getStatusIcon() + " " + tasks.get(countTasks - 1).getDescription() + " by:" + tasks.get(countTasks - 1).getTime());
        System.out.print("\tNow you have " + countTasks + " task");
        if (countTasks > 1)
            System.out.print("s");
        else System.out.print("");
        System.out.println(" in the list.");
    }

    private static int convertFile(File f) throws FileNotFoundException{
        int numLine = 0;
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String currentLine = s.nextLine();
            String[] data = currentLine.split("\\|");
            //System.out.println(data.length);
            for (int i = 0; i < data.length; i++) {
                data[i] = data[i].trim();
            }
            if (data[0].equalsIgnoreCase("T")) {
                tasks.add(new ToDo(data[2]));
                if (data[1].equalsIgnoreCase("1")) {
                    tasks.get(numLine).markAsDone();
                }
            } else if (data[0].equalsIgnoreCase("E")) {
                tasks.add(new Event(data[2],data[3]));
                if (data[1].equalsIgnoreCase("1")) {
                    tasks.get(numLine).markAsDone();
                }
            } else if (data[0].equalsIgnoreCase("D")) {
                tasks.add(new Deadline(data[2],data[3]));
                if (data[1].equalsIgnoreCase("1")) {
                    tasks.get(numLine).markAsDone();
                }
            }
            numLine++;
        }
        return numLine;
    }

    public static int convertFromFile(File f) {
        try {
            return convertFile(f);
        } catch (FileNotFoundException e) {
            //System.out.println("File not found");
            try {
                f.createNewFile();
                return 0;
            } catch (IOException err) {
                err.printStackTrace();
                return 0;
            }
        }
    }

    private static void recordFile(File f, int countTasks) throws IOException {
        FileWriter fw = new FileWriter(f);
        for (int i = 0; i < countTasks; i++) {
            fw.write(tasks.get(i).getTypeWOBrackets() + " | " + tasks.get(i).isDoneOneZero() + " | " + tasks.get(i).getDescription() + tasks.get(i).getTimeWithSlash() + "\n");
        }
        fw.close();
    }

    public static void recordListToFile(File f, int countTasks) {
        try {
            recordFile(f, countTasks);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        printWelcomeGreet();
        int countTasks = convertFromFile(previousList);
        while(countTasks != -1) {
            //System.out.println(countTasks);
            Scanner in = new Scanner(System.in);
            String line = in.nextLine();
            int preCountTasks = countTasks;
            countTasks = resolveCommand(line, countTasks);
            if (countTasks == -1) {
                recordListToFile(previousList, preCountTasks);
            } else recordListToFile(previousList, countTasks);
        }
        printGoodbye();
    }
}
