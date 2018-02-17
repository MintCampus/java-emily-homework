package codewings.emily.synth.track;

import codewings.emily.synth.Note;
import org.apache.commons.lang3.math.Fraction;

import java.util.List;

/**
 * NotesComponent contains a chord, a set of notes to be played
 * together, whose duration might be different. NotesComponent
 * is denoted by series of notes in one line, separated by a space.
 * Example:
 *
 *     A4/4 C#4/4 E4/4 A5/4
 */
public class NotesComponent extends TrackComponent {
    /**
     * A chord, or the notes to be played together
     */
    public final List<Note> notes;

    /**
     * How long the notes should be played
     * (relative to the duration of a whole note)
     */
    public Fraction occupyingLength;

    public NotesComponent(Fraction beginAt, List<Note> notes) {
        super(beginAt);
        this.notes = notes;
        this.notes.sort(Note::compareTo);

        occupyingLength = notes.get(0).length;
        for (Note note : notes) {
            if (note.length.compareTo(occupyingLength) < 0)
                occupyingLength = note.length;
        }
    }
}
