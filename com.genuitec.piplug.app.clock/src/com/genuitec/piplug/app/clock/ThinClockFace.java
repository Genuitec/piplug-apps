package com.genuitec.piplug.app.clock;

public class ThinClockFace implements IClockFaceStyle {

    @Override
    public double handHourStopsAt() {
	return 0.25;
    }

    @Override
    public int handHourWidth() {
	return 4;
    }

    @Override
    public double handMinuteStopsAt() {
	return 0.33;
    }

    @Override
    public int handMinuteWidth() {
	return 2;
    }

    @Override
    public double handSecondStopsAt() {
	return 0.44;
    }

    @Override
    public int handSecondWidth() {
	return 1;
    }

    @Override
    public double largeTicksStartAt() {
	return 0.83;
    }

    @Override
    public int largeTicksWidth() {
	return 6;
    }

    @Override
    public double smallTicksStartAt() {
	return 0.94;
    }

    @Override
    public int smallTicksWidth() {
	return 3;
    }

    @Override
    public boolean smallTicksEnabled() {
	return true;
    }
}