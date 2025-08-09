package se.lexicon.dao.idSequencer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ToDoItemTaskIdSequencerTest {

    private ToDoItemTaskIdSequencer sequencer;

    @BeforeAll
    public void beforeAll() {
        sequencer = ToDoItemTaskIdSequencer.getInstance();
        sequencer.reset();
    }

    @Test
    @DisplayName("nextId increments and returns the next ID")
    public void nextIdIncrements() {
        int first = sequencer.nextId();
        int second = sequencer.nextId();
        assertEquals(first + 1, second);
    }

    @Test
    @DisplayName("getCurrentId returns the current ID")
    public void getCurrentIdWorks() {
        sequencer.reset();
        assertEquals(0, sequencer.getCurrentId());
        sequencer.nextId();
        assertEquals(1, sequencer.getCurrentId());
    }

    @Test
    @DisplayName("setCurrentId accepts valid non-negative IDs")
    public void setCurrentIdValid() {
        sequencer.setCurrentId(10);
        assertEquals(10, sequencer.getCurrentId());
    }

    @Test
    @DisplayName("setCurrentId throws on negative IDs")
    public void setCurrentIdThrowsOnNegative() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            sequencer.setCurrentId(-1);
        });
        assertEquals("ID must be non-negative", exception.getMessage());
    }

    @Test
    @DisplayName("reset sets current ID to zero")
    public void resetSetsZero() {
        sequencer.setCurrentId(5);
        sequencer.reset();
        assertEquals(0, sequencer.getCurrentId());
    }
}
