/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oop.im2020;

import java.awt.image.BufferedImage;

/**
 *
 * @author childm
 */
public class OperationUtilities {

    public static int extract(int rgb, ColourComponent band) {
        int[] rgbA = getRGB(rgb);
        for (int i = 0; i < rgbA.length; i++) {
            if (i != band.ordinal()) {
                rgbA[i] = 0;
            }
        }
        return toRGB(rgbA);
    }

    public static int grayscale(int rgb) {
        int[] rgbA = getRGB(rgb);
        int sum = 0;
        for (int i = 0; i < rgbA.length; i++) {
            sum += rgbA[i];
        }
        sum = sum / 3;
        for (int i = 0; i < rgbA.length; i++) {
            rgbA[i] = sum;
        }
        return toRGB(rgbA);
    }

    public static int threshold(int rgb, int threshold) {
        int[] rgbA = getRGB(rgb);
        int sum = 0;
        for (int i = 0; i < rgbA.length; i++) {
            sum += rgbA[i];
        }
        sum = sum / 3;
        if (sum < threshold) {
            return 0; // black
        } else {
            return 0xffffff; // white           
        }
    }

    public static int negative(int rgb) {
        int[] rgbA = getRGB(rgb);
        for (int i = 0; i < rgbA.length; i++) {
            rgbA[i] = 255 - rgbA[i];
        }
        return toRGB(rgbA);
    }

    public static int tint(int rgb, double strength, ColourComponent band) {
        int[] rgbA = getRGB(rgb);
        for (int i = 0; i < rgbA.length; i++) {
            if (i == band.ordinal()) {
                rgbA[i] *= (1 + strength);
                if (rgbA[i] > 255) {
                    rgbA[i] = 255;
                }
            } else {
                rgbA[i] *= (1 - strength);
            }
        }
        return toRGB(rgbA);
    }

    public static int blend(int rgb1, int rgb2, double alpha) {
        int[] rgb1a = getRGB(rgb1);
        int[] rgb2a = getRGB(rgb2);
        for (int i = 0; i < rgb2a.length; i++) {
            rgb1a[i] = (int) ((rgb1a[i] * alpha) + (rgb2a[i] * (1 - alpha)));
        }
        return toRGB(rgb1a);
    }

    public static int difference(int rgb1, int rgb2) {
        int[] rgb1a = getRGB(rgb1);
        int[] rgb2a = getRGB(rgb2);
        for (int i = 0; i < rgb2a.length; i++) {
            rgb1a[i] = Math.abs(rgb1a[i] - rgb2a[i]);
        }
        return toRGB(rgb1a);
    }

    public static int chromaKey(int rgb1, int rgb2, int replaceRGB, double sensitivity) {
        int[] rgb1a = getRGB(rgb1);
        int[] target = getRGB(replaceRGB);
        int[] diffs = new int[]{Math.abs(target[0] - rgb1a[0]),
            Math.abs(target[1] - rgb1a[1]),
            Math.abs(target[1] - rgb1a[1])};
        boolean replace = true;
        for (int i = 0; i < diffs.length; i++) {
            if (diffs[i] > sensitivity * 255) {
                replace = false;
            }
        }
        if (replace) {
            return rgb2;
        } else {
            return rgb1;
        }
    }

    public static int[] getRGB(int rgb) {
        int[] output = new int[3];
        output[0] = 0xff & (rgb >> 16);
        output[1] = 0xff & (rgb >> 8);
        output[2] = 0xff & rgb;
        return output;
    }

    public static int toRGB(int[] rgb) {
        return rgb[2] | (rgb[1] << 8) | (rgb[0] << 16);
    }

    public static void setRGB(int x, int y, int rgb, BufferedImage image) {
        if ((x >= 0) && (x < image.getWidth()) && (y >= 0) && (y < image.getHeight())) {
            image.setRGB(x, y, rgb);
        }
    }

    public static int getRGB(int x, int y, BufferedImage image) {
        if ((x >= 0) && (x < image.getWidth()) && (y >= 0) && (y < image.getHeight())) {
            return image.getRGB(x, y);
        } else {
            return 0;
        }
    }

}
