package myProg;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalcTest {
    Calc calc = new Calc();

    @Test
    void testAdd() {
        int res = calc.add(3, 5);
        assertEquals(8, res);
    }



    @Test
    void testSub() {
        int res = calc.sub(3, 5);
        assertEquals(-2 , res);
    }


    @BeforeAll
    void setUp() throws Exception {
        System.out.println("Begin my tests...");
    }

    @AfterAll
    void tearDown() throws Exception {
        System.out.println("Finish my Tests...");
    }


}