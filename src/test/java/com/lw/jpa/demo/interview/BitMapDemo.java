package com.lw.jpa.demo.interview;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;

/**
 * This is for use of bitMap by Java, and this will be useful for you to know how {@java.util.BitSet} will be worked.
 * The BitSet use long array to store data, that means, the bitSet[0] will store about 2^4 records, the index is from
 * 0 to 63.
 *
 * @author wander
 * @version 1.0
 * @date 2025/6/17-18:05
 */
public class BitMapDemo {

    private static final Logger log = LoggerFactory.getLogger(BitMapDemo.class);

    @Test
    void testSimpleCase() {
        // you can add init size for this BitSet, do not worry, the length will increase once need
        BitSet bitSet = new BitSet(1024);
        // push 4 to bitSet, then the index of 2 will be marked as 1, to binary this should be 100
        bitSet.set(4);
        log.info("Current result for bitSet is: {}, and the check for 4 result is: {}, for 5 is: {}", bitSet.length(), bitSet.get(4), bitSet.get(5));

    }
}
