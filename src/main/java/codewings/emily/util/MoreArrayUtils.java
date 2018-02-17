package codewings.emily.util;

/**
 * Some mathematical array utils
 */
public final class MoreArrayUtils {
    private MoreArrayUtils() {}

    public static double[] interpolate(double[] src, double ratio) {
        if (ratio == 1.0) {
            return src;
        } if (ratio > 0.5)
            return linearInterpolate(src, ratio);
        else if (ratio > 0)
            return parzenInterpolate(src, ratio);
        else
            throw new IllegalArgumentException("Invalid ratio: " + ratio);
    }

    /**
     * Linearly interpolate a and b with ratio k. As k -> 0, return value
     * approaches to a, and vice versa.
     */
    private static double linear(double a, double b, double k) {
        return a * (1 - k) + b * k;
    }

    /**
     * Linear interpolate with regard to its adjacent neighbors.
     */
    private static double[] linearInterpolate(double[] src, double ratio) {
        int length = (int) Math.ceil(src.length * ratio);
        double[] dest = new double[length];

        for (int i = 0; i < length; i++) {
            double srcIndex = (i / ratio);
            int srcLeftIndex = (int) Math.floor(srcIndex);
            int srcRightIndex = (int) Math.ceil(srcIndex);
            double k = srcIndex - srcLeftIndex;
            dest[i] = linear(src[srcLeftIndex], src[srcRightIndex], k);
        }

        return dest;
    }

    private static double gaussianKernel(double dist, double sigma) {
        double partition =  Math.sqrt(2 * Math.PI) * sigma;
        return Math.exp(-(dist * dist) / (2 * sigma * sigma)) / partition;
    }

    /**
     * Shortening interpolation using parzen window with Gaussian kernel
     */
    private static double[] parzenInterpolate(double[] src, double ratio) {
        double sampleDist = 1 / ratio;
        double sigma = sampleDist / 2;

        int length = (int) Math.ceil(src.length * ratio);
        double[] dest = new double[length];

        for (int i = 0; i < length; i++) {
            double srcIndex = (i / ratio);
            double srcRangeBegin = srcIndex - 3.0 * sigma;
            double srcRangeEnd = srcIndex + 3.0 * sigma;
            float weightedSum = 0.0f;
            for (int j = (int) srcRangeBegin; j < srcRangeEnd; j++) {
                if (j < 0 || j >= src.length) continue;
                weightedSum += gaussianKernel(srcIndex - j, sigma) * src[j];
            }
            dest[i] = weightedSum;
        }

        return dest;
    }
}
