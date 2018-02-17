package codewings.emily.synth.track;


import org.apache.commons.lang3.math.Fraction;

public class TempoComponent extends TrackComponent {
    public final int tempo;

    protected TempoComponent(Fraction beginAt, int tempo) {
        super(beginAt);
        this.tempo = tempo;
    }
}
