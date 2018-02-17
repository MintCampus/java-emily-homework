package codewings.emily.synth.track;

import codewings.emily.synth.Note;
import codewings.emily.synth.Pitch;
import org.apache.commons.lang3.math.Fraction;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TrackTest {
    private static Fraction frac(int num, int denom) {
        return Fraction.getFraction(num, denom);
    }

    private void verifyTempoComponent(TrackComponent component, Fraction beginAt, int tempo) {
        assertTrue(component instanceof TempoComponent);
        TempoComponent t = (TempoComponent) component;
        assertEquals(t.beginAt, beginAt);
        assertEquals(t.tempo, tempo);
    }

    private void verifyTimeSignatureComponent(TrackComponent component, Fraction beginAt, Fraction timeSig) {
        assertTrue(component instanceof TimeSignatureComponent);
        TimeSignatureComponent t = (TimeSignatureComponent) component;
        assertEquals(t.beginAt, beginAt);
        assertEquals(t.timeSignature, timeSig);
    }

    private void verifyNotesComponent(TrackComponent component, Fraction beginAt, Note... notes) {
        assertTrue(component instanceof NotesComponent);
        NotesComponent n = (NotesComponent) component;
        assertEquals(n.beginAt, beginAt);
        assertEquals(n.notes, Arrays.asList(notes));
    }

    @Test
    public void verify_exampleScore() {
        String trackStr = String.join("\n",
                "tempo: 30",
                "timeSignature: 4/4",
                "---",
                "E4/4",
                "^E4/8",
                "D4/8",
                "C4/4",
                "D4/4",
                "---"
        );

        Track track = Track.parse(trackStr);
        assertEquals(track.components.size(), 7);

        verifyTempoComponent(track.components.get(0),
                Fraction.ZERO,
                30);
        verifyTimeSignatureComponent(track.components.get(1),
                Fraction.ZERO,
                frac(4, 4));
        verifyNotesComponent(track.components.get(2),
                Fraction.ZERO,
                new Note(false, Pitch.E_4, frac(1, 4)));
        verifyNotesComponent(track.components.get(3),
                frac(1, 4),
                new Note(true, Pitch.E_4, frac(1, 8)));
        verifyNotesComponent(track.components.get(4),
                frac(3, 8),
                new Note(false, Pitch.D_4, frac(1, 8)));
        verifyNotesComponent(track.components.get(5),
                frac(1, 2),
                new Note(false, Pitch.C_4, frac(1, 4)));
        verifyNotesComponent(track.components.get(6),
                frac(3, 4),
                new Note(false, Pitch.D_4, frac(1, 4)));
    }

    @Test
    public void verify_chord_pitchOrder() {
        String trackStr = String.join("\n",
                "tempo: 30",
                "timeSignature: 1/4",
                "---",
                "G4/4 C4/4 E4/4",  // random order
                "---"
        );

        Track track = Track.parse(trackStr);
        assertEquals(track.components.size(), 3);

        verifyNotesComponent(track.components.get(2),
                Fraction.ZERO,
                new Note(false, Pitch.C_4, frac(1, 4)),
                new Note(false, Pitch.E_4, frac(1, 4)),
                new Note(false, Pitch.G_4, frac(1, 4)));
    }


    @Test
    public void verify_tiedChord() {
        String trackStr = String.join("\n",
                "tempo: 30",
                "timeSignature: 2/4",
                "---",
                "C4/4 E4/4 G4/4",
                "^E4/4 ^G4/4",
                "---"
        );

        Track track = Track.parse(trackStr);
        assertEquals(track.components.size(), 4);

        verifyNotesComponent(track.components.get(3),
                frac(1, 4),
                new Note(true, Pitch.E_4, frac(1, 4)),
                new Note(true, Pitch.G_4, frac(1, 4)));
    }

    @Test
    public void invalidTempo_failed() {
        // Invalid number
        try {
            String trackStr = String.join("\n",
                    "tempo: haha",
                    "---"
            );
            Track.parse(trackStr);
        } catch (IllegalArgumentException ignored) {}

        // Negative value
        try {
            String trackStr = String.join("\n",
                    "tempo: -120",
                    "---"
            );
            Track.parse(trackStr);
        } catch (IllegalArgumentException ignored) {}
    }
}
