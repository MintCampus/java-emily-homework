package codewings.emily.synth.instr;

import codewings.emily.synth.Note;
import codewings.emily.synth.Pitch;
import codewings.emily.synth.SoundWave;
import org.apache.commons.lang3.math.Fraction;

public interface Instrument {
    default SoundWave play(Note note, int tempo) {
        Fraction duration = note.length.multiplyBy(Fraction.getFraction(60, tempo));
        return play(note.pitch, duration.doubleValue());
    }

    SoundWave play(Pitch pitch, double duration);
}
