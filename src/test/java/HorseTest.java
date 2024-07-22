import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void constructor_when_nullFirstParameter() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1.0));
        assertEquals(exception.getMessage(), "Name cannot be null.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n", "\r"})
    void constructor_when_emptyFirstParameter(String emptyParameter) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(emptyParameter, 1.0));
        assertEquals(exception.getMessage(), "Name cannot be blank.");
    }

    @Test
    void constructor_when_negativeSecondParameter() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Karl", -1.0));
        assertEquals(exception.getMessage(), "Speed cannot be negative.");
    }

    @Test
    void constructor_when_negativeThirdParameter() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Karl", 1.0, -1.0));
        assertEquals(exception.getMessage(), "Distance cannot be negative.");
    }

    @Test
    void getNameTest() {
        String name = "Jake";
        assertEquals(name, new Horse(name, 1.0).getName());
    }

    @Test
    void getSpeedTest() {
        Double speed = 1.0;
        assertEquals(speed, new Horse("Test", speed).getSpeed());
    }

    @Test
    void getDistanceTest() {
        Double distance = 2.0;
        assertEquals(distance, new Horse("Test", 1.0, distance).getDistance());
    }

    @Test
    void move_CallsGetRandomDoubleCorrectly() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            String name = "Test";
            double speed = 1.0;
            double distance = 2.0;
            Horse horse = new Horse(name, speed, distance);

            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.22, 0.3, 0.89, 0.45})
    void move_Calculates(Double randomDouble) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            String name = "Test";
            double speed = 1.0;
            double distance = 2.0;
            double nextDistance = distance + speed * randomDouble;
            Horse horse = new Horse(name, speed, distance);
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(randomDouble);

            horse.move();

            assertEquals(nextDistance, horse.getDistance());
        }
    }

}