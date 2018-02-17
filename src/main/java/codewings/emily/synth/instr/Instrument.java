package codewings.emily.synth.instr;

import codewings.emily.synth.Note;
import codewings.emily.synth.SoundWave;

public interface Instrument {
    SoundWave play(Note note);
}
