import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class EventsStatisticTest {

    private static final double E = 1e-6;

    SetableClock clock;
    EventsStatistic statistic;

    @BeforeEach
    public void setUp() {
        clock = new SetableClock(Instant.now());
        statistic = new EventsStatisticImpl(clock);
    }

    @Test
    public void testEmptyEvent() {
        assertEquals(0, statistic.getEventStatisticByName("no-test"), E);
    }

    @Test
    public void testSingleEvent() {
        statistic.incEvent("test");
        statistic.incEvent("test");
        statistic.incEvent("test");
        statistic.incEvent("test");

        assertEquals(4.0 / 60, statistic.getEventStatisticByName("test"), E);
    }

    @Test
    public void testSingleEvent_oldCleared() {
        statistic.incEvent("test");
        tick(Duration.ofHours(1));

        assertEquals(0, statistic.getEventStatisticByName("test"), E);
    }

    @Test
    public void testSingleEvent_oldReplaced() {
        statistic.incEvent("test");
        tick(Duration.ofMinutes(20));
        statistic.incEvent("test");
        tick(Duration.ofMinutes(20));
        statistic.incEvent("test");
        tick(Duration.ofMinutes(20));
        statistic.incEvent("test");

        assertEquals(3.0 / 60, statistic.getEventStatisticByName("test"), E);
    }

    @Test
    public void testMultipleEvents() {
        statistic.incEvent("test1");
        tick(Duration.ofMinutes(20));
        statistic.incEvent("test1");
        statistic.incEvent("test2");
        tick(Duration.ofMinutes(20));
        statistic.incEvent("test1");
        statistic.incEvent("test2");
        statistic.incEvent("test3");
        tick(Duration.ofMinutes(20));
        statistic.incEvent("test1");

        Map<String, Double> stats = statistic.getAllEventStatistic();
        assertEquals(3.0 / 60, stats.get("test1"), E);
        assertEquals(2.0 / 60, stats.get("test2"), E);
        assertEquals(1.0 / 60, stats.get("test3"), E);
    }

    private void tick(Duration duration) {
        clock.setNow(clock.now().plus(duration));
    }

}
