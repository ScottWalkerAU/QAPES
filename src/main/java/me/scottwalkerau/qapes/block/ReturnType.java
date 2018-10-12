package me.scottwalkerau.qapes.block;

/**
 * The types any given block can return
 * @author Scott Walker
 */
public enum ReturnType {
    /** Java void */
    VOID,
    /** Java boolean */
    BOOLEAN,
    /** Java long */
    NUMBER,
    /** Custom type for a pair of facilities */
    FACILITY_PAIR
}
