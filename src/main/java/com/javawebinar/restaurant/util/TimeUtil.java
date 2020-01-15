package com.javawebinar.restaurant.util;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

public class TimeUtil {
    private static Clock clock = Clock.systemDefaultZone();
    private static ZoneId zoneId = ZoneId.systemDefault();

    public static final LocalTime ELEVEN_AM = LocalTime.of(11, 0);

    public static LocalTime nowTime() {
        return LocalTime.now(getClock());
    }

    public static void useFixedClockAt(LocalDateTime date) {
        clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId);
    }

    private static Clock getClock() {
        return clock;
    }
}
