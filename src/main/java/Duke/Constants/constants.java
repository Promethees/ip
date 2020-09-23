package duke.constants;

public final class Constants {
     /*
        Notes for the Exceptions:
        +Duke.Exceptions.ipException: Check if after "todo, deadline, event" command is an empty string or not. More specifically, check whether
                                       "/by" or "/at" have been included in "deadline" or "event" yet
        +Duke.Exceptions.ipException2: Check if available commands "bye list todo deadline event done" have been stated correctly or not.
        Check if Duke.Task.Task and Time in "deadline" or "event" are empty strings or not
        +Duke.Exceptions.ipException3: avoid increment of countTasks when invalid syntax is called
     */
     public static final String DUKE =  " ____        _        \n"
             + "|  _ \\ _   _| | _____ \n"
             + "| | | | | | | |/ / _ \\\n"
             + "| |_| | |_| |   <  __/\n"
             + "|____/ \\__,_|_|\\_\\___|\n";
    public static final String PROMETHEES =    " ____  ____  ____  _      _____ _____  _     _____  _____  ____\n"
            +"/  __\\/  __\\/  _ \\/ \\__/|/  __//__ __\\/ \\ /|/  __/ /  __/ / ___\\\n"
            +"|  \\/||  \\/|| / \\|| |\\/|||  \\    / \\  | |_|||  \\   |  \\   |    \\\n"
            +"|  __/|    /| \\_/|| |  |||  /_   | |  | | |||  /_  |  /_  \\___ |\n"
            +"\\_/   \\_/ \\_\\____/\\_/  \\|\\____\\  \\_/  \\_/ \\|\\____\\ \\____\\ \\____/\n";
    public static final String HORIZONTAL = "--------------------------------------";
    public static final String HELLO_GREET =
            " My Child, I am Promethees, who had devoted his liver to liberate humanity from the Olympian's oppression\n"
                    +" Tell me, what can I do for you?";
    public static final String GOOD_BYE = " Mission accomplished!";
    public static final String INSTRUCTION = "Invalid Command! Available Commands: bye, list, todo, deadline, event, done.";
    public static final int TODO_INDEX = 4;
    public static final int DEADLINE_INDEX = 9;
    public static final int EVENT_INDEX = 6;
    public static final int DONE_INDEX = 5;
    public static final int REMOVE_INDEX = 7;
    public static final int FIND_INDEX = 5;
    public static final String REJECT_TODO = "Invalid command for todo. Must be \ntodo <nameOfTodo>; name";
    public static final String REJECT_DL = "Invalid command for deadline. Must be \ndeadline <nameOfEvent> /by <time>";
    public static final String REJECT_EV = "Invalid command for event. Must be \nevent <nameOfEvent> /at <time>";
    public static final String REJECT_DONE = "Invalid operation with done. Must be \ndone <(int)taskToBeDone>, taskToBeDone must be greater than 0 and  equal or lesser than ";
    public static final String REJECT_REMOVE = "Invalid operation with remove. Must be \nremove <(int)taskToBeRemoved>, taskToBeRemoved must be greater than 0 and equal or lesser than ";
    public static final String REJECT_FIND = "There is something wrong with your find method, please check again. The syntax is \nfind <name of item>";
    public static final String EMPTY_LIST = "This list is currently empty. You can use \"todo\", \"deadline\", \"event\" to add tasks";
    public static final String ADD_TASK = "\tGot it. I've added this task: ";
    public static final String DONE_TASK = "\t Nice! I've marked this task as done: ";
    public static final String REMOVE_TASK = "\tNoted! I've removed this task: ";
    public static final String NOTFOUND_ITEM = "\tSorry, but we can't find this in our list";
    public static final String DIR = "duke.txt";
    public static final String REJECT_DATE = "\tInvalid type for date, please follow the format YYYY-MM-DD";
}
