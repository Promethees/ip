package duke.parser;
import duke.task.*;
import static duke.Duke.tasks;
import static duke.Duke.ui;
import static duke.constants.Constants.*;

import duke.exceptions.UnableToHandleCommandException;
import duke.exceptions.SyntaxErrorException;
import duke.exceptions.CountTasksNotChangedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    /** Screening the input of user and execute
     *  appropriate tasks depends on the command detected
     *
     *
     * @return the detected command extracted from user input for further analysis
     * @param countTasks number of current tasks in the list
     * @param line user input
     * @throws SyntaxErrorException if no valid command is detected from user's input
     * @throws CountTasksNotChangedException Throw to this exception to avoid increment in countTasks
     * (when Todo, Deadline, or Event commands are not stated properly)
     */
    public static String parseCommand(String line, int countTasks) throws SyntaxErrorException, CountTasksNotChangedException {
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
            } catch (UnableToHandleCommandException e) {
                ui.printRejectTodo();
                throw new CountTasksNotChangedException(); //Throw to this exception to avoid increment in countTasks
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
                    throw new CountTasksNotChangedException();
                }
            } catch (UnableToHandleCommandException e) {
                ui.printRejectDeadline();
                throw new CountTasksNotChangedException(); //Throw to this exception to avoid increment in countTasks
            } catch (SyntaxErrorException e) {
                ui.printRejectDeadline();
                throw new CountTasksNotChangedException();
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
                    throw new CountTasksNotChangedException();
                }
            } catch (UnableToHandleCommandException e) {
                ui.printRejectEvent();
                throw new CountTasksNotChangedException(); //Throw to this exception to avoid increment in countTasks
            } catch (SyntaxErrorException e) {
                ui.printRejectEvent();
                throw new CountTasksNotChangedException();
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
                throw new CountTasksNotChangedException();
            }
            if (num <= countTasks && num > 0) {
                removeTask(num - 1, countTasks);
            } else {
                ui.printRejectDone();
                throw new CountTasksNotChangedException();
            }
        } else if (command.equalsIgnoreCase("find")) {
            String taskToBeFound = line.substring(line.indexOf("find ") + FIND_INDEX);
            try {
                find(taskToBeFound, countTasks);
            } catch (UnableToHandleCommandException e) {
                ui.printRejectFind();
            }
        }
        else {
            throw new SyntaxErrorException();
        }
        return command;
    }

    /**Set countTasks to pertinent value after parsing user's input
     * increase by 1 if a task is added
     * decrease by 1 if a task is deleted
     * remain if syntax errors occur or "list" is called
     * set to -1 if "bye" command is called
     *
     * @param line user's input
     * @param countTasks number of tasks available in the ArrayList
     * @return countTasks after the whole process of analysing
     */
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
        } catch (SyntaxErrorException e) {
            System.out.println(INSTRUCTION);
            return countTasks;
        } catch (CountTasksNotChangedException e) {
            return countTasks;
        }
        return countTasks;
    }

    /**Return the name of Todo task to be added to the list
     *
     * @param line User's input
     * @return expect name of the Todo task
     * @throws UnableToHandleCommandException detects syntax errors
     */
    private static String analyseTodo(String line) throws UnableToHandleCommandException {
        if (line.substring(TODO_INDEX).trim().length() == 0) {
            throw new UnableToHandleCommandException();
        }
        else return line.substring(TODO_INDEX);
    }

    /**Return the name of Deadline task to be added to the list
     *
     * @param line User's input
     * @return expect name of the Deadline task
     * @throws UnableToHandleCommandException detects syntax errors
     */
    private static String analyseDL(String line) throws UnableToHandleCommandException {
        if (line.indexOf("/by") == -1) {
            throw new UnableToHandleCommandException();
        }
        else return line.substring(DEADLINE_INDEX);
    }

    /**Return the name of Event task to be added to the list
     *
     * @param line User's input
     * @return expect name of the Event task
     * @throws UnableToHandleCommandException detects syntax errors
     */
    private static String analyseEV(String line) throws UnableToHandleCommandException {
        if (line.indexOf("/at") == -1) {
            throw new UnableToHandleCommandException();
        }
        else return line.substring(EVENT_INDEX);
    }

    /**Extract the Name of the tasks for Deadline and Todo with assigned delimiters
     *
     * @param line
     * @param extractor delimiter for Deadline ("/by") and Event ("/at")
     * @return the name of the task
     * @throws SyntaxErrorException if any syntax error is detected
     */
    private static String extractFrom(String line, String extractor) throws SyntaxErrorException {
        String newTask = line.substring(0, line.indexOf(extractor));
        if (newTask.trim().length() != 0) {
            return newTask.trim();
        }
        else {
            throw new SyntaxErrorException();
        }
    }

    /**Extract the Time of the tasks for Deadline and Todo with assigned delimiters
     *
     * @param line
     * @param extractor delimiter for Deadline ("/by") and Event ("/at")
     * @return the name of the task
     * @throws SyntaxErrorException if any syntax error is detected
     */
    private static String extractTimeFrom(String line, String extractor) throws SyntaxErrorException {
        String newTime = line.substring(line.indexOf(extractor) + extractor.length() + 1 );
        if (newTime.trim().length() != 0) {
            return newTime.trim();
        }
        else {
            throw new SyntaxErrorException();
        }
    }

    /**When command "done" is called, set status of named task to "done"
     *
     * @param index to index of Task of be mark as done
     */
    private static void markTaskDone(int index) {
        System.out.println("\t" + HORIZONTAL);
        System.out.println(DONE_TASK);
        tasks.get(index).markAsDone();
        System.out.println("\t\t" + tasks.get(index).getStatusIcon() + " " + tasks.get(index).getDescription());
        System.out.println("\t" + HORIZONTAL);
    }

    /**When command "remove" is called, remove the task with the named index from the ArrayList
     *
     * @param index index of Task to be removed from the ArrayList
     * @param countTasks number of current tasks in the list before deleting
     */
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

    /**Print the information of certain task to be called
     *
     * @param index index of Task to be printed out
     */
    public static void printTaskInfo(int index) {
        try {
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
        } catch (DateTimeParseException e) {
            System.out.println("\tThere is something wrong with the format in the data file!");
        }
    }

    /**Print all tasks' info existed in the ArrayList
     *
     * @param countTasks number of current task in the ArrayList
     */
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

    /**Add a Todo task to the ArrayList and print out its information
     *
     * @param countTasks number of current tasks exist in the ArrayList, for printing purpose only
     * @param line users' input
     */
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

    /**Add an Event task to the ArrayList and print out its information
     *
     * @param countTasks number of current tasks exist in the ArrayList, for printing purpose only
     * @param line users' input
     */
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

    /**Add a Deadline task to the ArrayList and print out its information
     *
     * @param countTasks number of current tasks exist in the ArrayList, for printing purpose only
     * @param line users' input
     */
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

    /**Find the tasks that contain desired keywords
     *
     * @param keyword keyword to be found in the tasks in the ArrayList
     * @param countTasks number of current tasks in the ArrayList
     * @throws UnableToHandleCommandException detects syntax errors or logical errors in the function
     */
    private static void find(String keyword, int countTasks) throws UnableToHandleCommandException {
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
            throw new UnableToHandleCommandException();
        }
    }

}
