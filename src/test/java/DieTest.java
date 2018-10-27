import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DieTest {

    Die die1 = new Die(1,6);

    @BeforeEach
    void setUp() {
        System.out.println("Starting Test");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test Ended\n");
    }

    @Test
    void roll() {
        System.out.println("roll test in progress");
        for (int i = 0, rollResult = die1.roll(); i < 12;i++, rollResult = die1.roll()){
            assertTrue(rollResult <= 6 && rollResult >= 1);
        }
    }
}