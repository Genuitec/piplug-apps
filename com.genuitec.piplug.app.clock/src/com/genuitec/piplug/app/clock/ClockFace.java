package com.genuitec.piplug.app.clock;

import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class ClockFace extends Canvas implements PaintListener, Runnable {

    // Colors of Clock
    private static final int COLOR_TICKS = SWT.COLOR_WHITE;
    private static final int COLOR_FACE = SWT.COLOR_BLACK;
    private static final int COLOR_HAND_SECOND = SWT.COLOR_RED;
    private static final int COLOR_HAND_MINUTE = SWT.COLOR_WHITE;
    private static final int COLOR_HAND_HOUR = SWT.COLOR_WHITE;

    // Style of Clock
    private static IClockFaceStyle style = new ThinClockFace();

    private static final double TWO_PI = 2.0 * Math.PI;
    private Color background, ticks, hoursHand, minutesHand, secondsHand, face;
    private int _diameter;
    private int _centerX;
    private int _centerY;
    private Calendar _now;

    public ClockFace(Composite parent) {
	super(parent, SWT.DOUBLE_BUFFERED);
	addPaintListener(this);
	background = Display.getCurrent().getSystemColor(COLOR_FACE);
	face = Display.getCurrent().getSystemColor(COLOR_FACE);
	ticks = Display.getCurrent().getSystemColor(COLOR_TICKS);
	hoursHand = Display.getCurrent().getSystemColor(COLOR_HAND_HOUR);
	minutesHand = Display.getCurrent().getSystemColor(COLOR_HAND_MINUTE);
	secondsHand = Display.getCurrent().getSystemColor(COLOR_HAND_SECOND);
	_now = Calendar.getInstance();
    }

    @Override
    public void paintControl(PaintEvent e) {

	// prepare the clock face
	e.gc.setBackground(background);
	e.gc.setForeground(ticks);
	e.gc.setAdvanced(true);
	e.gc.setAntialias(SWT.ON);
	Rectangle client = getClientArea();
	e.gc.fillRectangle(client);
	int size = Math.min(client.width, client.height) - 60;
	Rectangle clock = new Rectangle(0, 0, size, size);
	clock.x = Math.max(0, client.width - size) / 2;
	clock.y = Math.max(0, client.height - size) / 2;

	_diameter = size;
	_centerX = clock.x + (clock.width / 2);
	_centerY = clock.y + (clock.height / 2);

	drawClockFace(e.gc);
	drawClockHands(e.gc);
	e.gc.setBackground(ticks);
	int circle = (int) (size * 0.02);
	e.gc.fillOval(_centerX - circle, _centerY - circle, circle * 2,
		circle * 2);
    }

    private void drawClockHands(GC gc) {
	// ... Get the various time elements from the Calendar object.
	int hours = _now.get(Calendar.HOUR);
	int minutes = _now.get(Calendar.MINUTE);
	int seconds = _now.get(Calendar.SECOND);
	int millis = _now.get(Calendar.MILLISECOND);

	// ... second hand
	int handMax = (int) (_diameter * style.handSecondStopsAt());
	double fseconds = (seconds + (double) millis / 1000) / 60.0;
	gc.setForeground(secondsHand);
	gc.setLineWidth(style.handSecondWidth());
	drawRadius(gc, fseconds, 0, handMax);

	// ... minute hand
	handMax = (int) (_diameter * style.handMinuteStopsAt());
	double fminutes = (minutes + fseconds) / 60.0;
	gc.setForeground(minutesHand);
	gc.setLineWidth(style.handMinuteWidth());
	drawRadius(gc, fminutes, 0, handMax);

	// ... hour hand
	handMax = (int) (_diameter * style.handHourStopsAt());
	gc.setForeground(hoursHand);
	gc.setLineWidth(style.handHourWidth());
	drawRadius(gc, (hours + fminutes) / 12.0, 0, handMax);
    }

    private void drawClockFace(GC gc) {
	int radius = _diameter / 2;

	gc.setBackground(face);
	int dxmin = _centerX - radius - 15;
	int dymin = _centerY - radius - 15;
	gc.fillOval(dxmin, dymin, _diameter + 30, _diameter + 30);

	// ... Draw the tick marks around the circumference.
	for (int sec = 0; sec < 60; sec++) {
	    int tickStart = -1;
	    if (sec % 5 == 0) {
		gc.setLineWidth(style.largeTicksWidth());
		tickStart = (int) (radius * style.largeTicksStartAt());
	    } else if (style.smallTicksEnabled()) {
		gc.setLineWidth(style.smallTicksWidth());
		tickStart = (int) (radius * style.smallTicksStartAt());
	    }
	    if (tickStart != -1)
		drawRadius(gc, sec / 60.0, tickStart, radius);
	}
    }

    private void drawRadius(GC gc, double percent, int minRadius, int maxRadius) {
	// ... percent parameter is the fraction (0.0 - 1.0) of the way
	// clockwise from 12. Because the Graphics2D methods use radians
	// counterclockwise from 3, a little conversion is necessary.
	// It took a little experimentation to get this right.
	double radians = (0.5 - percent) * TWO_PI;
	double sine = Math.sin(radians);
	double cosine = Math.cos(radians);

	int dxmin = _centerX + (int) (minRadius * sine);
	int dymin = _centerY + (int) (minRadius * cosine);

	int dxmax = _centerX + (int) (maxRadius * sine);
	int dymax = _centerY + (int) (maxRadius * cosine);
	gc.drawLine(dxmin, dymin, dxmax, dymax);
    }

    public void updateTime() {
	_now.setTimeInMillis(System.currentTimeMillis());
	getDisplay().syncExec(this);
    }

    public void run() {
	redraw();
    }
}
