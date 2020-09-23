package duke;

import duke.task.TaskList;
import duke.ui.Ui;
import duke.storage.Storage;
import duke.parser.Parser;
import static duke.constants.Constants.*;
import java.io.File;
import java.util.Scanner;


public class Duke {
    public static final Storage storage = new Storage();
    public static final TaskList tasks = new TaskList();
    public static final Ui ui = new Ui();
    public static final Parser parser = new Parser();

    private static File previousList = new File(DIR);

    public static void main(String[] args) {
        ui.printWelcomeGreet();
        int countTasks = storage.convertFromFile(previousList);
        while(countTasks != -1) {
            Scanner in = new Scanner(System.in);
            String line = in.nextLine();
            int preCountTasks = countTasks;
            countTasks = parser.resolveCommand(line, countTasks);
            if (countTasks == -1) {
                storage.recordListToFile(previousList, preCountTasks);
            } else storage.recordListToFile(previousList, countTasks);
        }
        ui.printGoodbye();
    }
}
