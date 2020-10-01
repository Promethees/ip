# Welcome to Promethees - The Greek legendary version of Duke!

# User Guide

## Table of Contents

[1. Introduction](#1-introduction) <br>
[2. Quick start](#2-quick-start) <br>
[3. Features](#3-features) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.1 Adding a todo](#31-adding-a-todo-todo) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.2 Adding an event](#32-adding-an-event-event) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.3 Adding a deadline](#33-adding-a-deadline-deadline) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.4 Listing tasks](#34-listing-all-the-tasks-list) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.5 Marking a task as done](#35-done-a-task-done) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.6 Finding tasks](#36-finding-a-task-find) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.7 Removing a task](#37-removing-a-task-remove) <br>
&nbsp;&nbsp;&nbsp;&nbsp;[3.8 Exiting](#38-existing-the-program-bye) <br>
[4. Command summary](#4-command-summary)

## 1. Introduction
This is my individual project for CS2113T - Software engineering and OOP at NUS. It is a command line application to help user manage everyday tasks. Given below are instructions on how to use it. This project is made from Duke Project template and its name is inspired by Prometheus, a legendary hero in ancient Greek culture.

## 2. Quick Start
Prerequisites: JRE 11
1. Download the latest jar file from Github.
1. Open a shell, for example  *cmd, git bash*
1. Change the directory to the location of the jar file
1. Run the program by `java -jar ip.jar`
1. If the setup is correct, you should see the following greetings:
```
 ____  ____  ____  _      _____ _____  _     _____  _____  ____
/  __\/  __\/  _ \/ \__/|/  __//__ __\/ \ /|/  __/ /  __/ / ___\
|  \/||  \/|| / \|| |\/|||  \    / \  | |_|||  \   |  \   |    \
|  __/|    /| \_/|| |  |||  /_   | |  | | |||  /_  |  /_  \___ |
\_/   \_/ \_\____/\_/  \|\____\  \_/  \_/ \|\____\ \____\ \____/

--------------------------------------
 My Child, I am Promethees, who had devoted his liver to liberate humanity from the Olympian's oppression
 Tell me, what can I do for you?
--------------------------------------
duke.txt aka the data file created in the data folder!
```
Note: the last line appears when you run the program for the first time, when the data file is then created!

## 3. Features
**Notes about the command format**

Words in <UPPER_CASE> are the parameters to be supplied by the user

### 3.1. Adding a todo: `todo`
Adds a todo task to the list of tasks and updates the records.
The new task added is assumed to be not done.
The commands can be called with unsensitive cases: e.g `Todo` should be treated as same as `todo` or `toDo`

Format: `todo <TODO_DESCRIPTION>`

>Example

```
>>> todo wash clothes
	Got it. I've added this task: 
	[T]✘ wash clothes
	Now you have 1 task in the list.
```

### 3.2. Adding an event `event`
Adds an event with happening time to the list of tasks and updates the records. 
The new task added is assumed to be not done.

Format: `event <EVENT_DESCRIPTION> /at <EVENT_DATE>`
* The <EVENT_DATE> must be in the format yyyy-mm-dd.
* If the <EVENT_DATE> does not follow the standard format above, the system should return an error and reject the event to be added

>Example 

```
>>> event team meeting /at 2020-12-03           
	Got it. I've added this task: 
	[E]✘ team meeting at:2020-12-03
	Now you have 2 tasks in the list.
```

### 3.3. Adding a deadline `deadline`
Adds a task with deadline to the list of tasks and updates the records. 
The new task added is assumed to be not done.

Format: `deadline <DEADLINE_DESCRIPTION> /by <DEADLINE_DATE>`
* The <DEADLINE_DATE> must be in the format yyyy-mm-dd.
* If the <DEADLINE_DATE> does not follow the standard format above, the system should return an error and reject the deadline to be added

>Example

```
>>> deadline finalising JAR /by 2020-10-02
	Got it. I've added this task: 
	[D]✘ finalising JAR by:2020-10-02
	Now you have 3 tasks in the list.
```

### 3.4. Listing all the tasks `list`
Lists out all the existing tasks.

Format: `list`

>Example
```
>>> list
	--------------------------------------
	1.	[T]✘ wash clothes
	2.	[E]✘ team meeting (at:Dec 3 2020)
	3.	[D]✘ finalising JAR (by:Oct 2 2020)
	--------------------------------------
```

### 3.5. Mark a task as done `done`
Marks a task as done and updates the record.

Format: `done <INDEX>`
* The index of the task can be read from using `list` command.
* The index must be a **positive integer** 1, 2, 3, ... and smaller or equal **number of current taks stored**, otherwise an error will occur

>Example
```
>>> done 3
	--------------------------------------
	 Nice! I've marked this task as done: 
		✓ finalising JAR
	--------------------------------------
```

### 3.6. Finding a task `find`
Lists out all the tasks that contain a keyword

Format: `find <KEYWORD>`

>Example
```
>>> find team
	2.	[E]✘ team meeting (at:Dec 3 2020)
```

### 3.7. Deleting a task `remove`
Deletes a task from the list and updates the record.

Format: `remove <INDEX>`
* The index of the task can be read from using `list` command.
* The index must be a **positive integer** 1, 2, 3, ... and smaller or equal **number of current taks stored**, otherwise an error will occur

>Example
```
>>> remove 1
	--------------------------------------
	Noted! I've removed this task: 
	[T]✘ wash clothes
	Now you have 2 tasks in the list.
	--------------------------------------
```


### 3.9. Exiting the program: `bye`
Terminates the program and saves the recorded information to *~/data/duke.txt*

Format: `bye`

>Example
```
>>> bye
	--------------------------------------
	 Mission accomplished!
	--------------------------------------
```

## 4. Command summary

Command |Syntax| Command description and example 
------------| ------ | ------------- 
todo |`todo <DESCRIPTION>` |adds a new todo task
event |`event <DESCRIPTION> /at <DATE>` |adds a new event task
deadline |`deadline <DESCRIPTION> /by <DATE>`|adds a new deadline task
list |`list`|lists out the existing tasks
done |`done <INDEX>` |marks a task as done
find |`find <KEYWORD>`|lists out the tasks that contain a keyword
remove |`remove <INDEX>`|removes a task from the current list
bye |`bye`|terminates the program
