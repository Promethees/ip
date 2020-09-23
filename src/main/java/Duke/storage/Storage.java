package duke.storage;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static duke.Duke.tasks;
public class Storage {

    private static int convertFile(File f) throws FileNotFoundException {
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
}
