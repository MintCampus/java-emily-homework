package codewings.emily.synth.track;

import codewings.emily.synth.Note;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.math.Fraction;

import java.util.ArrayList;
import java.util.List;

/**
 * Lists of notes that single instrument plays
 */
public class Track {
    public List<TrackComponent> components;

    /**
     * Default constructor
     */
    public Track(List<TrackComponent> components) {
        this.components = components;
    }

    /**
     * Parse custom format score
     */
    public static Track parse(String score) {
        Fraction timeSignature = null;
        Fraction globalProgress = Fraction.ZERO;
        Fraction measureProgress = Fraction.ZERO;
        int numMeasures = 0;  // for debugging

        List<TrackComponent> components = new ArrayList<>();

        int lineNumber = 0;  // for debugging
        for (String line : score.split("\n")) {
            lineNumber++;
            if (line.contains("#"))
                line = line.split("#")[0];
            line = line.trim();

            // Tempo defined
            if (line.startsWith("tempo:")) {
                int tempo = Integer.parseInt(line.substring(6).trim());
                components.add(new TempoComponent(globalProgress, tempo));
            }

            // Time signature defined
            else if (line.startsWith("timeSignature:")) {
                timeSignature = Fraction.getFraction(line.substring(14).trim());
                components.add(new TimeSignatureComponent(globalProgress, timeSignature));
            }

            // Measure changed
            else if (line.startsWith("---")) {
                Preconditions.checkNotNull(timeSignature, "Time signature not defined");

                if (measureProgress.equals(Fraction.ZERO) && numMeasures == 0) {
                    // Beginning of the score; o.k.
                } else if (measureProgress.compareTo(timeSignature) == 0) {
                    measureProgress = Fraction.ZERO;
                    numMeasures++;
                } else {
                    throw new IllegalArgumentException(
                            String.format("line %d: Measure #%d does not match the time signature %s",
                                    lineNumber, numMeasures + 1, timeSignature.toString()));
                }
            }

            // Notes
            else if (line.length() > 0) {
                List<Note> notes = new ArrayList<>();
                for (String noteStr : line.split(" ")) {
                    notes.add(Note.parse(noteStr));
                }
                NotesComponent notesComponent = new NotesComponent(globalProgress, notes);
                globalProgress = globalProgress.add(notesComponent.occupyingLength);
                measureProgress = measureProgress.add(notesComponent.occupyingLength);
                components.add(notesComponent);
            }
        }

        return new Track(components);
    }


}
