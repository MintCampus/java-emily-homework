package codewings.emily.synth;

import codewings.emily.synth.instr.Instrument;
import codewings.emily.synth.track.Track;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.util.ArrayUtil;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Synthesizer {
    private static final double DEFAULT_SAMPLE_RATE = 44100;
    private static final double DEFAULT_MAX_DURATION = 60;
    private static final int SAMPLE_SIZE_BITS = 16;

    /**
     * Sample rate of the waveform.
     */
    private double sampleRate;

    /**
     * Array of float that stores raw waveform
     */
    private INDArray waveform;

    public Synthesizer(double sampleRate, double maxDuration) {
        this.sampleRate = sampleRate;
        this.waveform = Nd4j.zeros((int) (sampleRate * maxDuration));
    }

    public Synthesizer(double maxDuration) {
        this(DEFAULT_SAMPLE_RATE, maxDuration);
    }

    public Synthesizer() {
        this(DEFAULT_MAX_DURATION);
    }

    public void add(Track track, Instrument instrument) {
        // TODO
    }

    /**
     * Make waveform value range within [-1.0, 1.0]
     */
    private void normalizeWaveform() {
        double maxMagnitude = (double) waveform.amaxNumber();
        if (maxMagnitude > 1.0)
            waveform = waveform.div(maxMagnitude);
    }

    /**
     * Save waveform as a .wav file.
     * https://stackoverflow.com/questions/3297749
     */
    public void saveWav(String filename) throws IOException {
        if (!filename.endsWith(".wav"))
            filename = filename.concat(".wav");

        normalizeWaveform();
        int sampleMag = (1 << (SAMPLE_SIZE_BITS - 1)) - 1;

        File outputFile = new File(filename);
        AudioFormat audioFormat = new AudioFormat((float) sampleRate, SAMPLE_SIZE_BITS, 1, true, false);

        short[] discreteWaveform = ArrayUtil.toHalfs(waveform.mul(sampleMag).data().asDouble());
        byte[] binary = new byte[discreteWaveform.length * 2];
        ByteBuffer writer = ByteBuffer.wrap(binary);
        for (short waveVal : discreteWaveform) {
            writer.putShort(waveVal);
        }

        try (ByteArrayInputStream bais = new ByteArrayInputStream(binary);
             AudioInputStream ais = new AudioInputStream(bais, audioFormat, binary.length)) {
            AudioSystem.write(ais, AudioFileFormat.Type.WAVE, outputFile);
        }

        System.out.println("Sound saved to " + outputFile.getAbsolutePath());
    }
}
