package codewings.emily.synth;

import codewings.emily.util.MoreArrayUtils;
import com.google.common.base.Preconditions;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class SoundWave {
    /**
     * Sample rate of the waveform.
     */
    private double sampleRate;

    /**
     * Array of float that stores raw waveform
     */
    private double[] waveform;

    public SoundWave(double sampleRate, double[] waveform) {
        Preconditions.checkNotNull(waveform, "waveform is null");
        this.sampleRate = sampleRate;
        this.waveform = waveform;
    }

    public INDArray getWaveform(double sampleRate) {
        double[] waveform = this.waveform;
        if (this.sampleRate != sampleRate) {
            waveform = MoreArrayUtils.interpolate(this.waveform, sampleRate / this.sampleRate);
        }
        return Nd4j.create(waveform);
    }
}
