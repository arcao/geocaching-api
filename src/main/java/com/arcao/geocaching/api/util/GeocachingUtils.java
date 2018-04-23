package com.arcao.geocaching.api.util;

import java.util.Locale;

/**
 * Set of Geocaching helper methods.
 *
 * @author arcao
 * @since 1.5
 */
public final class GeocachingUtils {
    private static final String BASE_31_CHARS = "0123456789ABCDEFGHJKMNPQRTVWXYZ";

    // = (16 * 31 * 31 * 31) - (16 * 16 * 16 * 16)
    private static final long CACHE_CODE_BASE31_MAGIC_NUMBER = 411120;
    private static final String CACHE_PREFIX = "GC";
    private static final long CACHE_CODE_BASE16_MAX = 0xFFFF;
    private static final int BASE_16 = 16;

    private GeocachingUtils() {
    }

    /**
     * Convert a base 31 number containing chars 0123456789ABCDEFGHJKMNPQRTVWXYZ
     * to numeric value.
     *
     * @param input base 31 number
     * @return numeric value
     * @throws IllegalArgumentException If input contains illegal chars
     */
    public static long base31Decode(final String input) {
        final int base = BASE_31_CHARS.length();
        long ret = 0;

        for (char ch : input.toCharArray()) {
            ret *= base;

            int index = BASE_31_CHARS.indexOf(ch);
            if (index == -1) {
                throw new IllegalArgumentException("Only chars " + BASE_31_CHARS + " are supported.");
            }

            ret += index;
        }
        return ret;
    }

    /**
     * Convert a numeric value to base 31 number using chars
     * 0123456789ABCDEFGHJKMNPQRTVWXYZ.
     *
     * @param input numeric value
     * @return base 31 number
     */
    public static String base31Encode(long input) {
        final int base = BASE_31_CHARS.length();
        StringBuilder sb = new StringBuilder();

        while (input != 0) {
            sb.append(BASE_31_CHARS.charAt((int) (input % base)));
            input /= base;
        }

        return sb.reverse().toString();
    }

    /**
     * Convert cache code GCxxx to numeric cache id.<br>
     * <br>
     * The algorithm respects following rules used for cache code:<br>
     * <ul>
     * <li>GC0 - GCFFFF = value after GC prefix is a hexadecimal number
     * <li>GCG000 - ... = value after GC is a base 31 number minus magic constant
     * 411120 (16 * 31 * 31 * 31 - 16 * 16 * 16 * 16)
     * </ul>
     *
     * @param cacheCode cache code including GC prefix
     * @return cache id
     * @throws IllegalArgumentException Cache code does not contains GC prefix or contains invalid characters
     * @see #base31Decode(String)
     */
    public static long cacheCodeToCacheId(final String cacheCode) {
        String cacheCodeNorm = cacheCode.toUpperCase(Locale.US);

        if (cacheCodeNorm.length() < 3 || !cacheCodeNorm.startsWith(CACHE_PREFIX)) {
            throw new IllegalArgumentException("Cache code is too short or does not contains GC prefix.");
        }

        // remove GC prefix
        String code = cacheCodeNorm.substring(2);

        // 0 - FFFF = base16; G000 - ... = base 31
        if (code.length() <= 4 && code.charAt(0) < 'G') {
            try {
                return Long.parseLong(code, BASE_16);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Only chars " + BASE_31_CHARS + " are supported.");
            }
        } else {
            return base31Decode(code) - CACHE_CODE_BASE31_MAGIC_NUMBER;
        }
    }

    /**
     * Convert a numeric cache id to cache code GCxxx. The algorithm respects
     * rules for generating cache code.
     *
     * @param cacheId cache id
     * @return cache code including GC prefix
     * @see #cacheCodeToCacheId(String)
     * @see #base31Encode(long)
     */
    public static String cacheIdToCacheCode(long cacheId) {
        StringBuilder sb = new StringBuilder();

        // append GC prefix
        sb.append(CACHE_PREFIX);

        if (cacheId <= CACHE_CODE_BASE16_MAX) { // 0 - FFFF
            sb.append(Long.toString(cacheId, BASE_16).toUpperCase(Locale.US));
        } else { // G000 - ...
            sb.append(base31Encode(cacheId + CACHE_CODE_BASE31_MAGIC_NUMBER));
        }

        return sb.toString();
    }

    /**
     * Returns true if the cache code is valid. The algorithm respects
     * rules for cache code.
     *
     * @param cacheCode cache code
     * @return true if cache code is valid, otherwise false
     * @see #cacheCodeToCacheId(String)
     */
    public static boolean isCacheCodeValid(String cacheCode) {
        try {
            return cacheCodeToCacheId(cacheCode) >= 0;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
