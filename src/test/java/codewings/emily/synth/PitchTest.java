package codewings.emily.synth;

import static org.junit.Assert.*;

import org.junit.Test;

public class PitchTest {
    @Test
    public void parse_Succeeds() {
        assertEquals(Pitch.parse("A#2"), Pitch.A_SHARP_2);
        assertEquals(Pitch.parse("Db4"), Pitch.D_FLAT_4);
        assertEquals(Pitch.parse("E7"), Pitch.E_7);
        assertEquals(Pitch.parse("R"), Pitch.REST);
    }

    @Test
    public void parse_InvalidFormat_Fails() {
        try {
            Pitch.parse(":-)");
            fail("Invalid character is not permitted");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("H5");
            fail("Note H doesn't exist");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("A");
            fail("Octave number should be included");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("C##");
            fail("Octave number should be an integer");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("C+3");
            fail("Enharmonic should be one of # (sharp) or b (flat)");
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void parse_NonExistingNote_Fails() {
        try {
            Pitch.parse("Cb5");
            fail("There's no flat for C");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("Fb5");
            fail("There's no flat for F");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("B#5");
            fail("There's no sharp for B");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("E#5");
            fail("There's no sharp for E");
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void parse_NotesOutOfBound_Fails() {
        try {
            Pitch.parse("G#0");
            fail("A0 is the lowest note");
        } catch (IllegalArgumentException ignored) {}

        try {
            Pitch.parse("C#8");
            fail("C0 is the highest note");
        } catch (IllegalArgumentException ignored) {}
    }
}
