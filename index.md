## Welcome to Promethees - The Greek legendary version of Duke!

You can file and download JAR file of the project [here](https://github.com/Promethees/ip/releases/tag/v0.2) and type in the command line portal at the same repository as your downloaded JAR file `java -jar ip.jar` to start the program. 

Prerequisite of using: having Java11 installed on your computer.

### Available commands

These following commands can be used by the user to modify his/her scheudule with simple syntax.

List of commands and theirs effects (all commands can be called with unsensitive case, e.g LisT or lIst, ... are equivalent with list)

`list`
- Syntax:
  list
- Effect:
  - Display all of tasks existed in the ArrayList to the console and number of tasks currently stored in the data. 
  - If the list is empty, printout the announcement

`bye`
- Syntax: 
  bye
- Effect:
  - Terminate the program immediately

`todo`
- Syntax:
  todo `<name of task to be added>`
- Effect:
  - Add the Todo task with chosen name to the ArrayList of Tasks
- Example:
  `todo study`
  
`deadline`
- Syntax:
  deadline `<name of task to be added> /by <date to be done>`
- Effect:
  - Add the Deadline task with chosen name to the ArrayList of Tasks (<date to be done> has the form of YYYY-MM-DD)
- Example:
  `deadline do this /by 2020-10-02`
  
`event`
- Syntax:
  event `<name of task to be added> /at <date to be done>`
- Effect:
  - Add the Event task with chosen name to the ArrayList of Tasks (<date to be done> has the form of YYYY-MM-DD)
- Example:
  `event brother's marriage /at 2020-12-12`
  
`done`
- Syntax:
  done `<index of task to be done>`
- Effect:
  - Mark the task with chosen index in the database to be done
- Example:
  `done 3` (task 3 is then marked as done)

`find`
- Syntax:
  find `<keyword>`
- Effect:
  - Find the tasks with chosen keyword
- Example:
  `find swim`
  
`remove`
- Syntax:
  remove `<index of task to be removed>`
- Effect:
  - Mark the task with chosen index in the database to be done
- Example:
  `remove 3` (task 3 is then removed from the data file)

### Support or Contact
Having trouble with using the application? For more information and feedback, please contact [Promethees](https://github.com/Promethees)
