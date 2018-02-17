package codewings.emily.synth;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.math.Fraction;

import java.util.Arrays;

public class Note implements Comparable<Note> {
    /**
     * Whether this note is tied to previous note or not.
     */
    public boolean tied;

    /**
     * Pitch of then note.
     */
    public final Pitch pitch;

    /**
     * Length of the note; 1 = whole note.
     */
    public final Fraction length;

    public Note(boolean tied, Pitch pitch, Fraction length) {
        this.tied = tied;
        this.pitch = pitch;
        this.length = length;
    }

    /**
     * Parse pitch and its length.
     * Example:
     *
     *     A#3/8   -> pitch = A_SHARP_3, length = 1/8
     *     A#3/24  -> pitch = A_SHARP_3, length = 1/24
     */
    public static Note parse(String note) {
        Preconditions.checkNotNull(note);

        boolean tied = false;
        if (note.startsWith("^")) {
            note = note.substring(1);
            tied = true;
        }

        String[] splits = note.split("/");
        if (splits.length != 2)
            throw new IllegalArgumentException("Illegal slashes: " + Arrays.toString(splits));

        Pitch pitch = Pitch.parse(splits[0]);
        int durationDenominator = Integer.parseInt(splits[1]);
        if (durationDenominator <= 0.0)
            throw new IllegalArgumentException("Invalid note length: " + durationDenominator);

        return new Note(tied, pitch, Fraction.getFraction(1, durationDenominator));
    }

    @Override
    public int compareTo(Note other) {
        return pitch.compareTo(other.pitch);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Note) {
            Note other = (Note) obj;
            return other.pitch.equals(pitch)
                    && other.length.equals(length)
                    && other.tied == tied;
        }
        return false;
    }

    @Override
    public String toString() {
        String pitchStr = pitch.name()
                .replace("_SHARP", "♯")
                .replace("_FLAT", "♭")
                .replace("_", "");
        return String.format("%s[%s]", pitchStr, length.toString());
    }
}
