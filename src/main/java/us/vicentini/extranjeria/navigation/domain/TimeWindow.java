package us.vicentini.extranjeria.navigation.domain;

import lombok.Data;

@Data
public class TimeWindow {
    private final String week;
    private final String time;
    private final String status;
}
