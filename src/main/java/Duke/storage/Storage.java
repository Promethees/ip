package duke.storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static duke.Duke.tasks;
public class Storage {
    /**This function reads data stored in data file and coverts it into ArrayList structure
     *
     * @param f data file storing the information of the tasks
     * @return total tasks detected in the File
     * @throws FileNotFoundException when the file is not found
     */
    private static int convertFile(File f) throws FileNotFoundException {
        int numLine = 0;
        Scanner s = new Scanner(f);
        try {
            while (s.hasNext()) {
                String currentLine = s.nextLine();
                String[] data = currentLine.split("\\|");
                for (int i = 0; i < data.length; i++) {
                    data[i] = data[i].trim();
                }
                if (data[0].equalsIgnoreCase("T")) {
                    tasks.add(new ToDo(data[2]));
                    if (data[1].equalsIgnoreCase("1")) {
                        tasks.get(numLine).markAsDone();
                    }
                } else if (data[0].equalsIgnoreCase("E")) {
                    tasks.add(new Event(data[2], data[3]));
                    if (data[1].equalsIgnoreCase("1")) {
                        tasks.get(numLine).markAsDone();
                    }
                } else if (data[0].equalsIgnoreCase("D")) {
                    tasks.add(new Deadline(data[2], data[3]));
                    if (data[1].equalsIgnoreCase("1")) {
                        tasks.get(numLine).markAsDone();
                    }
                }
                numLine++;
            }
        } catch (DateTimeParseException e) {
            System.out.println("\tThere is something wrong in the data/duke.txt!");
        }
        return numLine;
    }

    /**Check if the data file exists or not, creates "duke.txt" in "data" directory if not
     *
     * @param f file to be processed
     */
    public static int convertFromFile(File f) {
        File folder = new File("data");
        boolean bool = folder.mkdirs(); // If folder doesn't exist, then create it

        try {
            return convertFile(f);
        } catch (FileNotFoundException e) {
            //System.out.println("File not found");
            try {
                f.createNewFile();
                System.out.println("duke.txt aka the data file created in the data folder!");
                return 0;
            } catch (IOException err) {
                err.printStackTrace();
                return 0;
            }
        }
    }

    /**Write data from ArrayList structure to text form in directory "data/duke.txt"
     *
     * @param f file to be processed
     * @param countTasks total number of tasks/lines in the data
     * @throws IOException IO error
     */
    private static void recordFile(File f, int countTasks) throws IOException {
        FileWriter fw = new FileWriter(f);
        for (int i = 0; i < countTasks; i++) {
            fw.write(tasks.get(i).getTypeWOBrackets() + " | " + tasks.get(i).isDoneOneZero() + " | " + tasks.get(i).getDescription() + tasks.get(i).getTimeWithSlash() + "\n");
        }
        fw.close();
    }

    /**Record the file or print error message
     *
      * @param f data file to be processed
     * @param countTasks total number of tasks
     */
    public static void recordListToFile(File f, int countTasks) {
        try {
            recordFile(f, countTasks);
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
