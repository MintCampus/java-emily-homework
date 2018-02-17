package codewings.emily.synth;

public enum Pitch implements Comparable<Pitch> {
    REST(-1),

    A_0(1),
    A_SHARP_0(2),
    B_FLAT_0(2),
    B_0(3),

    C_1(4),
    C_SHARP_1(5),
    D_FLAT_1(5),
    D_1(6),
    D_SHARP_1(7),
    E_FLAT_1(7),
    E_1(8),
    F_1(9),
    F_SHARP_1(10),
    G_FLAT_1(10),
    G_1(11),
    G_SHARP_1(12),
    A_FLAT_1(12),
    A_1(13),
    A_SHARP_1(14),
    B_FLAT_1(14),
    B_1(15),

    C_2(16),
    C_SHARP_2(17),
    D_FLAT_2(17),
    D_2(18),
    D_SHARP_2(19),
    E_FLAT_2(19),
    E_2(20),
    F_2(21),
    F_SHARP_2(22),
    G_FLAT_2(22),
    G_2(23),
    G_SHARP_2(24),
    A_FLAT_2(24),
    A_2(25),
    A_SHARP_2(26),
    B_FLAT_2(26),
    B_2(27),

    C_3(28),
    C_SHARP_3(29),
    D_FLAT_3(29),
    D_3(30),
    D_SHARP_3(31),
    E_FLAT_3(31),
    E_3(32),
    F_3(33),
    F_SHARP_3(34),
    G_FLAT_3(34),
    G_3(35),
    G_SHARP_3(36),
    A_FLAT_3(36),
    A_3(37),
    A_SHARP_3(38),
    B_FLAT_3(38),
    B_3(39),

    C_4(40),
    C_SHARP_4(41),
    D_FLAT_4(41),
    D_4(42),
    D_SHARP_4(43),
    E_FLAT_4(43),
    E_4(44),
    F_4(45),
    F_SHARP_4(46),
    G_FLAT_4(46),
    G_4(47),
    G_SHARP_4(48),
    A_FLAT_4(48),
    A_4(49),
    A_SHARP_4(50),
    B_FLAT_4(50),
    B_4(51),

    C_5(52),
    C_SHARP_5(53),
    D_FLAT_5(53),
    D_5(54),
    D_SHARP_5(55),
    E_FLAT_5(55),
    E_5(56),
    F_5(57),
    F_SHARP_5(58),
    G_FLAT_5(58),
    G_5(59),
    G_SHARP_5(60),
    A_FLAT_5(60),
    A_5(61),
    A_SHARP_5(62),
    B_FLAT_5(62),
    B_5(63),

    C_6(64),
    C_SHARP_6(65),
    D_FLAT_6(65),
    D_6(66),
    D_SHARP_6(67),
    E_FLAT_6(67),
    E_6(68),
    F_6(69),
    F_SHARP_6(70),
    G_FLAT_6(70),
    G_6(71),
    G_SHARP_6(72),
    A_FLAT_6(72),
    A_6(73),
    A_SHARP_6(74),
    B_FLAT_6(74),
    B_6(75),

    C_7(76),
    C_SHARP_7(77),
    D_FLAT_7(77),
    D_7(78),
    D_SHARP_7(79),
    E_FLAT_7(79),
    E_7(80),
    F_7(81),
    F_SHARP_7(82),
    G_FLAT_7(82),
    G_7(83),
    G_SHARP_7(84),
    A_FLAT_7(84),
    A_7(85),
    A_SHARP_7(86),
    B_FLAT_7(86),
    B_7(87),

    C_8(88);

    private int keyNumber;
    private double frequency;

    Pitch(int keyNumber) {
        this.keyNumber = keyNumber;
        if (keyNumber > 0)
            this.frequency = Math.pow(2.0, (keyNumber - 49) / 12.0) * 440.0;
    }

    /**
     * Parse abbreviation of the pitch.
     * Examples:
     *
     *     A#2 -> Pitch.A_SHARP_2
     *     Db4 -> Pitch.D_FLAT_4
     *     E7  -> Pitch.E_7
     */
    public static Pitch parse(String abbr) {
        if (abbr == null)
            throw new IllegalArgumentException("Cannot parse null string");

        if (abbr.equals("R"))
            return Pitch.REST;

        if (abbr.length() < 2 || abbr.length() > 3)
            throw new IllegalArgumentException(String.format("Pitch %s has invalid length", abbr));

        StringBuilder sb = new StringBuilder();

        char pitch = abbr.charAt(0);
        if (pitch < 'A' || pitch > 'G')
            throw new IllegalArgumentException("Invalid pitch: " + abbr);
        sb.append(pitch);
        sb.append('_');

        if (abbr.length() == 3) {
            char enharmonic = abbr.charAt(1);
            if (enharmonic != '#' && enharmonic != 'b')
                throw new IllegalArgumentException("Invalid enharmonic: " + abbr);
            sb.append(enharmonic == '#' ? "SHARP_" : "FLAT_");
        }

        char octaves = abbr.charAt(abbr.length() - 1);
        if (octaves < '0' || octaves > '9')
            throw new IllegalArgumentException("Invalid octave: " + abbr);
        sb.append(octaves);

        String parsed = sb.toString();
        return Pitch.valueOf(parsed);
    }

    public int getKeyNumber() {
        return keyNumber;
    }

    public double getFrequency() {
        return frequency;
    }
}
