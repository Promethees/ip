package Duke;

import Duke.Exceptions.ipException;
import Duke.Exceptions.ipException2;
import Duke.Exceptions.ipException3;
import Duke.Task.Deadline;
import Duke.Task.Event;
import Duke.Task.Task;
import Duke.Task.ToDo;
import static Duke.Constants.constants.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Duke {
    private static  ArrayList<Task> tasks = new ArrayList<>();

    private static File previousList = new File(DIR);

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
        } else if (command.equalsIgnoreCase("find")) {
            String taskToBeFound = line.substring(line.indexOf("find ") + FIND_INDEX);
            try {
                find(taskToBeFound, countTasks);
            } catch (ipException e) {
                System.out.println(REJECT_FIND);
            }
        }
        else {
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
            LocalDate dateCoverted = LocalDate.parse(tasks.get(index).getTime());
            System.out.println("\t" + tasks.get(index).getType() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription() + " (by:" + dateCoverted.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")");
            //System.out.println("\t" + tasks.get(index).getType() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription() + " (by:" + tasks.get(index).getTime() + ")");
        }
        if (tasks.get(index).getType() == "[E]") {
            LocalDate dateCoverted = LocalDate.parse(tasks.get(index).getTime());
            System.out.println("\t" + tasks.get(index).getType() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription() + " (at:" + dateCoverted.format(DateTimeFormatter.ofPattern("MMM d yyyy")) + ")");
            //System.out.println("\t" + tasks.get(index).getType() + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription() + " (at:" + tasks.get(index).getTime() + ")");
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

    private static void find(String keyword, int countTasks) throws ipException {
        if (countTasks > 0) {
            int count = 0;
            for (int i = 0; i < countTasks; i++) {
                if (tasks.get(i).getDescription().contains(keyword)) {
                    System.out.print("\t" + (i + 1) + ".");
                    printTaskInfo(i);
                    count ++;
                }
            }
            if (count == 0) {
                System.out.println(NOTFOUND_ITEM);
            } else return;
        } else {
            //System.out.println(EMPTY_LIST);
            throw new ipException();
        }
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
                System.out.println("duke.txt aka the data file created!");
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
