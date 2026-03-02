package net.novaproject.novauhc.utils;

import com.google.common.base.Strings;
import org.bukkit.ChatColor;

public class ProgressBar {

    public static String getProgressBar(int current, int max, int totalBars, String symbol, ChatColor completedColor, ChatColor notCompletedColor) {
        if (max <= 0) max = 1; 
        float percent = (float) current / max;
        int progressBars = Math.round(totalBars * percent);
        return Strings.repeat(completedColor + symbol, progressBars)
                + Strings.repeat(notCompletedColor + symbol, totalBars - progressBars);
    }
}