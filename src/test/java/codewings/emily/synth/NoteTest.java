package codewings.emily.synth;

import static org.junit.Assert.*;

import org.apache.commons.lang3.math.Fraction;
import org.junit.Test;

public class NoteTest {

    @Test
    public void parse_Succeeds() {
        Note n1 = Note.parse("A#4/16");
        assertEquals(n1.pitch, Pitch.A_SHARP_4);
        assertEquals(n1.length, Fraction.getFraction(1, 16));
    }

    @Test
    public void parse_InvalidFormat_Fails() {
        try {
            Note.parse("A#4");
            fail("Lacks length information");
        } catch (IllegalArgumentException ignored) {}

        try {
            Note.parse("A#4/");
            fail("No length after slash");
        } catch (IllegalArgumentException ignored) {}

        try {
            Note.parse("/A#4/16/");
            fail("Too many slashes");
        } catch (IllegalArgumentException ignored) {}

        try {
            Note.parse("A#4/hello");
            fail("Invalid length");
        } catch (IllegalArgumentException ignored) {}
    }

    @Test
    public void parse_InvalidLength_Fails() {
        try {
            Note.parse("A#4/0");
            fail("Invalid length");
        } catch (IllegalArgumentException ignored) {}

        try {
            Note.parse("A#4/-1.0");
            fail("Invalid length");
        } catch (IllegalArgumentException ignored) {}
    }
}
