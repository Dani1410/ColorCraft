package com.example.main;

public class FormatosColor {
    private final String hexColor;
    private final String rgbColor;
    private final String argbColor;

    public FormatosColor(String hexColor, String rgbColor, String argbColor) {
        this.hexColor = hexColor;
        this.rgbColor = rgbColor;
        this.argbColor = argbColor;
    }

    public String getHexColor() {
        return hexColor;
    }

    public String getRgbColor() {
        return rgbColor;
    }

    public String getArgbColor() {
        return argbColor;
    }
}
