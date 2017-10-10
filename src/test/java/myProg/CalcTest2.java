package myProg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalcTest2 {
    Calc calc = new Calc();

    @Test
    void testAdd() throws Exception {
        int result = calc.add(0, 0);
        assertEquals(0, result);
    }

    @Test
    void testSub() throws Exception {
        int result = calc.sub(0, 0);
        assertEquals(0, result);
    }

    @Test
    void testMult() throws Exception {
        int result = calc.mult(0, 0);
        assertEquals(0, result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme