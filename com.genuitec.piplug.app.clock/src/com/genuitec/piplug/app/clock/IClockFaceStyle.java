package com.genuitec.piplug.app.clock;

public interface IClockFaceStyle {

    double handHourStopsAt();

    int handHourWidth();

    double handMinuteStopsAt();

    int handMinuteWidth();

    double handSecondStopsAt();

    int handSecondWidth();

    double largeTicksStartAt();

    int largeTicksWidth();

    double smallTicksStartAt();

    int smallTicksWidth();

    boolean smallTicksEnabled();

}
