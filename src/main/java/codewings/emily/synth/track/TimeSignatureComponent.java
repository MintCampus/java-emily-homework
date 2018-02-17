package codewings.emily.synth.track;

import org.apache.commons.lang3.math.Fraction;

/**
 * Time signature indicates how much notes a single measure can contain;
 * for example time signature of "4/4" means four quarter notes can be
 * placed in a single measure.
 */
public class TimeSignatureComponent extends TrackComponent {
    public final Fraction timeSignature;

    public TimeSignatureComponent(Fraction beginAt, Fraction timeSignature) {
        super(beginAt);
        this.timeSignature = timeSignature;
    }
}
