package net.k3nder.gl.graphic.text;

import net.k3nder.gl.graphic.visual.Texture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

public class Font {
    private java.awt.Font font;

    private HashMap<Character, Texture> glyphs;

    public Font(InputStream path, float size) {
        try {
            java.awt.Font font = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, path);
            this.font = font.deriveFont(size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Texture renderTextToImage(String text) {
        // Cargar la fuente desde un archivo

        // Crear una imagen que contendrá el texto
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        g2d.setFont(font);
        int width = g2d.getFontMetrics().stringWidth(text);
        int height = g2d.getFontMetrics().getHeight();
        g2d.dispose();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();

        // Configurar renderizado de alta calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(font);
        g2d.drawString(text, 0, g2d.getFontMetrics().getAscent());

        g2d.dispose();

        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = ByteBuffer.allocateDirect(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[(image.getHeight() - 1 - y) * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));  // Red
                buffer.put((byte) ((pixel >> 8) & 0xFF));   // Green
                buffer.put((byte) (pixel & 0xFF));          // Blue
                buffer.put((byte) ((pixel >> 24) & 0xFF));  // Alpha
            }
        }

        buffer.flip();

        return Texture.createTexture(width, height, buffer);
    }

    public HashMap<Character, Texture> getGlyphs() {
        if (!(glyphs == null)) return glyphs;

        glyphs = new HashMap<>();

        // load mayus
        for (int i = 32; i <= 126; i++) {
            glyphs.put(Character.valueOf((char) i),
                    renderTextToImage(String.valueOf((char) i)));
        }

        return glyphs;
    }
}
