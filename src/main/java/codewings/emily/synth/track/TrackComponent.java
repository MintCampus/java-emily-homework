package codewings.emily.synth.track;


import org.apache.commons.lang3.math.Fraction;

public abstract class TrackComponent {
    public final Fraction beginAt;

    protected TrackComponent(Fraction beginAt) {
        this.beginAt = beginAt;
    }
}
