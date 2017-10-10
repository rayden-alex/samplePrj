package myProg;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalcTest3 {
    Calc calc = new Calc();

    @Test
    @DisplayName("Multiplying")
    void mult() {
        int result = calc.mult(0, 0);
        assertEquals(0, result);
    }

}