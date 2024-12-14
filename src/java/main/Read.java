package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Read {

    String address1;
    String address2;


    public static void main(String[] args) {
//        // 确保提供了一个有效的图片文件路径
//        String imagePath = "C:/Users/94359/IdeaProjects/bad_apple/ds/16x12 15fps/0051.jpg"; // 替换为你的JPG图片路径
//
//        // 使用Swing创建一个简单的GUI窗口来显示图片
//        JFrame frame = new JFrame("Image Viewer");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(800, 600); // 设置窗口大小，根据需要调整
//
//        try {
//            // 读取图片文件
//            BufferedImage image = ImageIO.read(new File(imagePath));
//
//            // 创建一个ImageIcon来包装BufferedImage
//            ImageIcon icon = new ImageIcon(image);
//
//            // 创建一个JLabel来显示图片，并将ImageIcon设置为其图标
//            JLabel label = new JLabel(icon);
//
//            // 将JLabel添加到JFrame的内容面板中
//            frame.getContentPane().add(label, BorderLayout.CENTER);
//
//            // 显示窗口
//            frame.setVisible(true);
//        } catch (IOException e) {
//            // 处理图片读取失败的情况
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Error loading image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//
//        // 像素坐标（例如，获取图像中心像素的颜色）
//        // int x = 100; // 替换为你想要的x坐标
//        // int y = 100; // 替换为你想要的y坐标
//        for (int x = 44; x < 1349; x = x + 90) {
//            for (int y = 44; y < 989; y = y + 90) {
//                printColorOnePage(x, y, imagePath);
//            }
//        }
        Read readPages = new Read("C:/Users/94359/IdeaProjects/bad_apple/ds/16x12 5fps/_", ".jpg");
        String[] imagePaths = readPages.getImagePaths();
        System.out.println(imagePaths.length);
        // 创建 JFrame 主窗口
        JFrame frame = new JFrame("Pixel Image Player");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(320, 240); // 窗口尺寸
        frame.setLocationRelativeTo(null);

        // 创建 JLabel 用于显示图片
        JLabel label = new JLabel();
        label.setHorizontalAlignment(JLabel.CENTER);
        frame.add(label);

        // 确保有图片可用
        if (imagePaths.length == 0) {
            System.out.println("No images found!");
            return;
        }

        // 创建 Timer 控制图片播放
        Timer timer = new Timer(200, null); // 每 500 毫秒切换图片

        // 使用数组索引跟踪当前图片位置
        final int[] currentIndex = {0};

        timer.addActionListener(e -> {
            // 显示当前图片
            ImageIcon icon = new ImageIcon(imagePaths[currentIndex[0]]);
            Image scaledImage = icon.getImage().getScaledInstance(320, 240, Image.SCALE_FAST); // 放大
            label.setIcon(new ImageIcon(scaledImage));

            // 更新索引
            currentIndex[0] = (currentIndex[0] + 1) % imagePaths.length; // 循环播放
        });

        // 添加键盘监听器
        frame.addKeyListener(new KeyListener() {
            private boolean isPlaying = false; // 播放状态标记

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) { // 空格键
                    if (isPlaying) {
                        timer.stop();
                        isPlaying = false;
                        System.out.println("Playback paused.");
                    } else {
                        timer.start();
                        isPlaying = true;
                        System.out.println("Playback started.");
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });

        // 设置焦点以接收键盘事件
        frame.setFocusable(true);
        frame.requestFocusInWindow();

        // 显示窗口
        frame.setVisible(true);
    }

    public Read(String address1, String address2) {
        this.address1 = address1;
        this.address2 = address2;
    }

    public static void printColorOnePage(int x, int y, String imagePath) {
        try {
            // 读取图像文件
            BufferedImage image = ImageIO.read(new File(imagePath));

            // 获取像素的RGB值
            int rgb = image.getRGB(x, y);

            // 解析RGB值
            int red = (rgb >> 16) & 0xff;
            int green = (rgb >> 8) & 0xff;
            int blue = rgb & 0xff;

            // 输出颜色分量
            System.out.println("Red: " + red);
            System.out.println("Green: " + green);
            System.out.println("Blue: " + blue);
        } catch (IOException e) {
            // 处理图像读取失败的情况
            e.printStackTrace();
            System.out.println("Error reading image file.");
        }
    }

    public static String[] printColorOnePixel(int x, int y, String[] imagePaths) {
        String[] onePixelChangeInColor = new String[imagePaths.length];
        int i = 0;
        for (String imagePath : imagePaths) {
            try {
                // 读取图像文件
                BufferedImage image = ImageIO.read(new File(imagePath));

                // 获取像素的RGB值
                int rgb = image.getRGB(x, y);

                // 解析RGB值
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;

                // 输出颜色分量
                if (red >= 128 && green >= 128 && blue >= 128) {
                    onePixelChangeInColor[i] = "White";
                }
                if (red < 128 && green < 128 && blue < 128) {
                    onePixelChangeInColor[i] = "Black";
                }
            } catch (IOException e) {
                // 处理图像读取失败的情况
                e.printStackTrace();
                System.out.println("Error reading image file.");
            }
            i++;
            //printColorOnePage(0,0, imagePath);
        }
        return onePixelChangeInColor;
    }

    public String[] getImagePaths() {
        String[] imagePaths = new String[492];
        int num2 = 0;
        for (int i = 491; i >= 0; i--) {
            String num1 = Integer.toString(i);
            if (num1.length() == 1) {
                num1 = "000" + num1;
            } else if (num1.length() == 2) {
                num1 = "00" + num1;
            } else if (num1.length() == 3) {
                num1 = "0" + num1;
            }
            num2++;
            // 确保提供了一个有效的图片文件路径
            String imagePath = address1 + num1 + "_图层 " + num2 + address2; // 替换为你的JPG图片路径
            imagePaths[i] = imagePath;
        }
        reverseArray(imagePaths);
        return imagePaths;
    }

    public static void reverseArray(String[] array) {
        int left = 0;
        int right = array.length - 1;

        while (left < right) {
            // 交换左侧和右侧的元素
            String temp = array[left];
            array[left] = array[right];
            array[right] = temp;

            left++;
            right--;
        }
    }

    public static Map<String, String> getOnePixelChangeInColorImageMap(String[] colors) {
        // 初始化结果 Map
        Map<String, String> colorMap = new LinkedHashMap<>();

        double currentStart = 0.0; // 起始值
        double interval = 1.0; // 单位区间长度
        int blackCount = 0; // Black 的计数器
        int whiteCount = 0; // White 的计数器

        String previousColor = ""; // 跟踪上一个颜色
        int consecutiveCount = 0; // 连续相同颜色的计数器

        for (int i = 0; i < colors.length; i++) {
            String color = colors[i];

            if (!color.equals(previousColor)) {
                // 如果颜色发生切换，保存上一个颜色的区间
                if (previousColor.equals("Black")) {
                    blackCount++;
                    colorMap.put(
                            "Black" + blackCount,
                            String.format("%.1f - %.1f (%d)", currentStart, currentStart + consecutiveCount * interval, consecutiveCount)
                    );
                } else if (previousColor.equals("White")) {
                    whiteCount++;
                    colorMap.put(
                            "White" + whiteCount,
                            String.format("%.1f - %.1f (%d)", currentStart, currentStart + consecutiveCount * interval, consecutiveCount)
                    );
                }
                // 重置连续计数
                currentStart += consecutiveCount * interval;
                consecutiveCount = 1;
            } else {
                // 如果颜色没有切换，连续计数加 1
                consecutiveCount++;
            }

            // 更新上一颜色
            previousColor = color;
        }

        // 处理最后一个区间
        if (previousColor.equals("Black")) {
            blackCount++;
            colorMap.put(
                    "Black" + blackCount,
                    String.format("%.1f - %.1f (%d)", currentStart, currentStart + consecutiveCount * interval, consecutiveCount)
            );
        } else if (previousColor.equals("White")) {
            whiteCount++;
            colorMap.put(
                    "White" + whiteCount,
                    String.format("%.1f - %.1f (%d)", currentStart, currentStart + consecutiveCount * interval, consecutiveCount)
            );
        }

        // 输出结果
        for (Map.Entry<String, String> entry : colorMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        return colorMap;
    }
}
