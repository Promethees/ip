package duke.parser;
import duke.task.*;
import static duke.Duke.tasks;
import static duke.Duke.ui;
import static duke.constants.Constants.*;

import duke.exceptions.IpException;
import duke.exceptions.IpException2;
import duke.exceptions.IpException3;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    public static String parseCommand(String line, int countTasks) throws IpException3, IpException2 {
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
            } catch (IpException e) {
                ui.printRejectTodo();
                throw new IpException3(); //Throw to this exception to avoid increment in countTasks
            }
        } else if (command.equalsIgnoreCase("deadline")) {
            try {
                String DL = analyseDL(line);
                String newDL = extractFrom(DL,"/by");
                String time = extractTimeFrom(DL,"/by");
                try {
                    LocalDate dateCoverted = LocalDate.parse(time);
                    addDeadlines(countTasks, newDL, time);
                    //System.out.println(dateCoverted);
                } catch (DateTimeParseException e) {
                    ui.printRejectDate();
                    throw new IpException3();
                }
            } catch (IpException e) {
                ui.printRejectDeadline();
                throw new IpException3(); //Throw to this exception to avoid increment in countTasks
            } catch (IpException2 e) {
                ui.printRejectDeadline();
                throw new IpException3();
            }
        } else if (command.equalsIgnoreCase("event")) {
            try {
                String EV = analyseEV(line);
                String newEV = extractFrom(EV,"/at");
                String time = extractTimeFrom(EV,"/at");
                try {
                    LocalDate dateCoverted = LocalDate.parse(time);
                    addEvents(countTasks, newEV, time);
                } catch (DateTimeParseException e) {
                    ui.printRejectDate();
                    throw new IpException3();
                }
            } catch (IpException e) {
                ui.printRejectEvent();
                throw new IpException3(); //Throw to this exception to avoid increment in countTasks
            } catch (IpException2 e) {
                ui.printRejectEvent();
                throw new IpException3();
            }
        } else if (command.equalsIgnoreCase("done")) {
            String taskToBeDone = line.substring(line.indexOf("done ") + DONE_INDEX);
            boolean isInt = true;
            int num = countTasks + 1;
            try {
                num = Integer.parseInt(taskToBeDone);
            } catch (NumberFormatException e) {
                isInt = false;
                ui.printRejectDone();
            }
            if (isInt) {
                if (num <= countTasks && num > 0) {
                    markTaskDone(num - 1);
                } else {
                    ui.printRejectDone();
                }
            }
        } else if (command.equalsIgnoreCase("remove")) {
            String taskToBeDone = line.substring(line.indexOf("remove ") + REMOVE_INDEX);
            int num;
            try {
                num = Integer.parseInt(taskToBeDone);
            } catch (NumberFormatException e) {
                ui.printRejectRemove();
                throw new IpException3();
            }
            if (num <= countTasks && num > 0) {
                removeTask(num - 1, countTasks);
            } else {
                ui.printRejectDone();
                throw new IpException3();
            }
        } else if (command.equalsIgnoreCase("find")) {
            String taskToBeFound = line.substring(line.indexOf("find ") + FIND_INDEX);
            try {
                find(taskToBeFound, countTasks);
            } catch (IpException e) {
                ui.printRejectFind();
            }
        }
        else {
            throw new IpException2();
        }
        return command;
    }
    public static int resolveCommand(String line, int countTasks) {
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
        } catch (IpException2 e) {
            System.out.println(INSTRUCTION);
            return countTasks;
        } catch (IpException3 e) {
            return countTasks;
        }
        return countTasks;
    }

    private static String analyseTodo(String line) throws IpException {
        if (line.substring(TODO_INDEX).trim().length() == 0) {
            throw new IpException();
        }
        else return line.substring(TODO_INDEX);
    }

    private static String analyseDL(String line) throws IpException {
        if (line.indexOf("/by") == -1) {
            throw new IpException();
        }
        else return line.substring(DEADLINE_INDEX);
    }

    private static String analyseEV(String line) throws IpException {
        if (line.indexOf("/at") == -1) {
            throw new IpException();
        }
        else return line.substring(EVENT_INDEX);
    }

    private static String extractFrom(String line, String extractor) throws IpException2 {
        String newTask = line.substring(0, line.indexOf(extractor));
        if (newTask.trim().length() != 0) {
            return newTask.trim();
        }
        else {
            throw new IpException2();
        }
    }

    private static String extractTimeFrom(String line, String extractor) throws IpException2 {
        String newTime = line.substring(line.indexOf(extractor) + extractor.length() + 1 );
        if (newTime.trim().length() != 0) {
            return newTime.trim();
        }
        else {
            throw new IpException2();
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

    public static void printTaskInfo(int index) {
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

    public static void printTasks(int countTasks) {
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

    private static void find(String keyword, int countTasks) throws IpException {
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
                ui.printNotFound();
            } else return;
        } else {
            //System.out.println(EMPTY_LIST);
            throw new IpException();
        }
    }

}
