package Duke.Task;

import Duke.Duke;

public abstract class Task {
    //Generator, in the main file must you "new" modifier to create the objects
    protected String type;
    protected String description;
    protected boolean isDone;

    public abstract String getStatusIcon();
    public abstract String getType();
    public abstract String getDescription();

    public abstract void markAsDone();

    public abstract String getTime();

    public abstract boolean isDone();

    public abstract String getTypeWOBrackets();

    public abstract int isDoneOneZero();

    public abstract String getTimeWithSlash();

}