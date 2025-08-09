package se.lexicon.dao.idSequencer;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PersonIdSequencerTest {

    private PersonIdSequencer sequencer;

    @BeforeAll
    public void setup() {
        sequencer = PersonIdSequencer.getInstance();
        sequencer.reset();
    }

    @Test
    @DisplayName("Next ID starts at 1 after reset")
    public void nextIdStartsAtOneAfterReset() {
        sequencer.reset();
        int firstId = sequencer.nextId();
        assertEquals(1, firstId);
    }

    @Test
    @DisplayName("Next ID increments correctly")
    public void nextIdIncrementsCorrectly() {
        sequencer.reset();
        int firstId = sequencer.nextId();
        int secondId = sequencer.nextId();
        assertEquals(firstId + 1, secondId);
    }

    @Test
    @DisplayName("Reset sets the ID back to zero")
    public void resetWorksCorrectly() {
        sequencer.nextId();
        sequencer.reset();
        int resetCurrent = sequencer.getCurrentId();
        assertEquals(0, resetCurrent);
    }

    @Test
    @DisplayName("getCurrentId returns the current ID")
    public void getCurrentIdReturnsCorrectValue() {
        sequencer.reset();
        sequencer.nextId();
        assertEquals(1, sequencer.getCurrentId());
    }

    @Test
    @DisplayName("setCurrentId sets the current ID correctly")
    public void setCurrentIdUpdatesValue() {
        sequencer.reset();
        sequencer.setCurrentId(10);
        assertEquals(10, sequencer.getCurrentId());
        assertEquals(11, sequencer.nextId());
    }

    @Test
    @DisplayName("setCurrentId throws IllegalArgumentException if negative value")
    public void setCurrentIdThrowsOnNegative() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            sequencer.setCurrentId(-1);
        });
        assertEquals("ID must be non-negative", ex.getMessage());
    }
}