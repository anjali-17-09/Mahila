package com.example.mahila.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CycleCalculator {

    public static class CycleInfo {
        public int cycleDay;
        public String currentPhase;
        public String nextPeriodDate;
        public String ovulationWindow;

        public CycleInfo(int cycleDay, String currentPhase, String nextPeriodDate, String ovulationWindow) {
            this.cycleDay = cycleDay;
            this.currentPhase = currentPhase;
            this.nextPeriodDate = nextPeriodDate;
            this.ovulationWindow = ovulationWindow;
        }
    }

    public static CycleInfo calculateCycleInfo(long lastPeriodMillis, int averageCycleLength) {
        if (averageCycleLength <= 0) averageCycleLength = 28;

        long nowMillis = System.currentTimeMillis();
        long diffDays = (nowMillis - lastPeriodMillis) / (1000 * 60 * 60 * 24);
        int cycleDay = (int) (diffDays % averageCycleLength) + 1;

        String phase;
        if (cycleDay <= 5) {
            phase = "Menstrual Phase";
        } else if (cycleDay <= 13) {
            phase = "Follicular Phase";
        } else if (cycleDay <= 16) {
            phase = "Ovulatory Phase";
        } else {
            phase = "Luteal Phase";
        }

        Calendar calNextPeriod = Calendar.getInstance();
        calNextPeriod.setTimeInMillis(lastPeriodMillis);
        calNextPeriod.add(Calendar.DAY_OF_YEAR, averageCycleLength);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String nextPeriodDateStr = sdf.format(calNextPeriod.getTime());

        Calendar calOvulation = Calendar.getInstance();
        calOvulation.setTimeInMillis(lastPeriodMillis);
        calOvulation.add(Calendar.DAY_OF_YEAR, averageCycleLength - 14);

        Calendar calFertileStart = (Calendar) calOvulation.clone();
        calFertileStart.add(Calendar.DAY_OF_YEAR, -5);

        Calendar calFertileEnd = (Calendar) calOvulation.clone();
        calFertileEnd.add(Calendar.DAY_OF_YEAR, 1);

        SimpleDateFormat sdfWindow = new SimpleDateFormat("MMM dd", Locale.getDefault());
        String ovulationWindowStr = sdfWindow.format(calFertileStart.getTime()) + " - " + sdfWindow.format(calFertileEnd.getTime());

        return new CycleInfo(cycleDay, phase, nextPeriodDateStr, ovulationWindowStr);
    }
}
