package codewings.emily.synth;

import codewings.emily.synth.instr.Instrument;
import codewings.emily.synth.instr.SineWaveGenerator;
import codewings.emily.synth.track.Track;
import com.google.common.io.Resources;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SynthesizerMain {
    public static void main(String[] args) throws IOException {
        Synthesizer synthesizer = new Synthesizer();
        Instrument instrument = new SineWaveGenerator();

        // Read track from
        URL url = Resources.getResource("track_example.txt");
        String trackStr = Resources.toString(url, StandardCharsets.UTF_8);
        Track track = Track.parse(trackStr);

        synthesizer.add(track, instrument);

        synthesizer.saveWav("result.wav");
    }
}
