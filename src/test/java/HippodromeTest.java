import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {
    @Test
    void constructor_NullParameterPassed_ThrowsIllegalArgumentException() {
        List<Horse> horses = null;
        String message = "Horses cannot be null.";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(exception.getMessage(), message);
    }
    @Test
    void constructor_EmptyListPassed_ThrowsIllegalArgumentException() {
        List<Horse> horses = Collections.emptyList();
        String message = "Horses cannot be empty.";

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals(exception.getMessage(), message);
    }

    @Test
    void getHorses_ReturnsCorrectHorsesList() {
        List<Horse> horses = new ArrayList<>();
        int numOfHorses = 30;
        for (int i = 0; i < numOfHorses; i++) {
            horses.add(new Horse("Test" + i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void getWinner_ReturnsHorseWithBiggestDistance() {
        List<Horse> horses = new ArrayList<>();
        int numOfHorses = 30;
        for (int i = 0; i < numOfHorses; i++) {
            horses.add(new Horse("Test" + i, i, i * 2));
        }
        Horse winner = horses.get(numOfHorses - 1);

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(winner, hippodrome.getWinner());
    }

    @Test
    void move_CallsMoveForAllHorses() {
        List<Horse> horses = new ArrayList<>();
        int numOfHorses = 50;
        for (int i = 0; i < numOfHorses; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

}