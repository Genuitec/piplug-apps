package com.genuitec.piplug.app.clock;

public class ThickClockFace implements IClockFaceStyle {

    @Override
    public double handHourStopsAt() {
	return 0.25;
    }

    @Override
    public int handHourWidth() {
	return 12;
    }

    @Override
    public double handMinuteStopsAt() {
	return 0.33;
    }

    @Override
    public int handMinuteWidth() {
	return 8;
    }

    @Override
    public double handSecondStopsAt() {
	return 0.44;
    }

    @Override
    public int handSecondWidth() {
	return 3;
    }

    @Override
    public double largeTicksStartAt() {
	return 0.83;
    }

    @Override
    public int largeTicksWidth() {
	return 14;
    }

    @Override
    public double smallTicksStartAt() {
	return 0.94;
    }

    @Override
    public int smallTicksWidth() {
	return 4;
    }

    @Override
    public boolean smallTicksEnabled() {
	return false;
    }
}