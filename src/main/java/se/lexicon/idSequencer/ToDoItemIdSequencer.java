package se.lexicon.idSequencer;

public class ToDoItemIdSequencer {

    private int currentId = 0;

    private static final ToDoItemIdSequencer INSTANCE = new ToDoItemIdSequencer();

    private ToDoItemIdSequencer() {}

    public static ToDoItemIdSequencer getInstance() {
        return INSTANCE;
    }

    public int nextId() {
        return ++currentId;
    }

    public int getCurrentId() {
        return currentId;
    }

    public void setCurrentId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID must be non-negative");
        }
        this.currentId = id;
    }


    public void reset() {
        currentId = 0;
    }
}
