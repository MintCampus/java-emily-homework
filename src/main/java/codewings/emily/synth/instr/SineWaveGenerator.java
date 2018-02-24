package codewings.emily.synth.instr;

import codewings.emily.synth.Pitch;
import codewings.emily.synth.SoundWave;

public class SineWaveGenerator implements Instrument {
    /**
     * Hard-coded sample rate to use
     */
    private static final double SAMPLE_RATE = 44100.0;

    /**
     * Hard-coded amplitude decaying rate per second
     */
    private static final double DECAY_RATE = 0.62;

    @Override
    public SoundWave play(Pitch pitch, double duration) {
        double freq = pitch.getFrequency();
        int numSamples = (int) (SAMPLE_RATE * duration);
        double[] waveform = new double[numSamples];

        for (int t = 0; t < waveform.length; t++) {
            double phase = t * freq * 2 * Math.PI / SAMPLE_RATE;
            double amplitude = Math.pow(DECAY_RATE, t / SAMPLE_RATE);
            waveform[t] = amplitude * Math.sin(phase);
        }

        return new SoundWave(SAMPLE_RATE, waveform);
    }
}
