package gent.timdemey.cards.base.icons.cards;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import gent.timdemey.cards.base.beans.B_Card;
import gent.timdemey.cards.base.beans.B_Suit;

public class CardIcon {

    private static final Map<B_Suit, String> suit2string = new HashMap<>();
    public static final int IMG_WIDTH = 100;
    public static final int IMG_HEIGHT = 150;

    static {
        suit2string.put(B_Suit.CLUBS, "Clubs");
        suit2string.put(B_Suit.HEARTS, "Hearts");
        suit2string.put(B_Suit.SPADES, "Spades");
        suit2string.put(B_Suit.DIAMONDS, "Diamonds");
    }

    public static Icon getIcon(B_Card card) {
        String path = suit2string.get(card.getSuit()) + "/" + card.getKind().getName() + ".png";
        return getIcon(path);
    }

    public static Icon getBackCover() {
        return getIcon("Back Covers/Emerald.png");
    }

    private static Icon getIcon(String path) {
        try {
            BufferedImage orig = ImageIO.read(CardIcon.class.getResourceAsStream(path));
            BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, orig.getType());
            Graphics2D g = resizedImage.createGraphics();
            g.setComposite(AlphaComposite.Src);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(orig, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
            g.dispose();
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
