import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.Objects;

final public class SkeletonKey implements Runnable {

    private final Timer timer = new Timer(60);

    private final String runtimeText = "Haxzorin' for %s [ %ds ]";
    private final JLabel runtimeLabel = new JLabel(String.format(runtimeText, "0", 0));

    private final String nextPressText = "Next hax in [ %ds ]";
    private final JLabel nextPressLabel = new JLabel(String.format(nextPressText, 0));

    private final String keyPressText = "[ %d ] total hax0rz";
    private final JLabel keyPressLabel = new JLabel(String.format(keyPressText, 0));

    private Robot angryKeyboardMasher;

    public static void main(String[] a)
    throws Exception {
        SkeletonKey sk = new SkeletonKey();
        javax.swing.SwingUtilities.invokeAndWait(sk);
        sk.loop();
    }

    public void run() {
        try { angryKeyboardMasher = new Robot(); } catch(Exception e) { return; }

        DefaultMetalTheme dmt = new DefaultMetalTheme(){
            @Override
            public ColorUIResource getWindowTitleForeground() {
                return new ColorUIResource(Color.RED);
            }

            @Override
            public ColorUIResource getWindowTitleInactiveForeground() {
                return new ColorUIResource(Color.DARK_GRAY);
            }

            @Override
            public ColorUIResource getWindowTitleInactiveBackground() {
                return new ColorUIResource(Color.BLACK);
            }

            @Override
            public ColorUIResource getWindowTitleBackground(){
                return new ColorUIResource(Color.BLACK);
            }

            @Override
            public ColorUIResource getPrimaryControlHighlight() {
                return new ColorUIResource(Color.BLACK);
            }

            @Override
            public ColorUIResource getPrimaryControlDarkShadow() {
                return new ColorUIResource(Color.BLACK);
            }

            @Override
            public ColorUIResource getControlHighlight() {
                return new ColorUIResource(Color.BLACK);
            }

            @Override
            public ColorUIResource getControlDarkShadow() {
                return new ColorUIResource(Color.BLACK);
            }

            @Override
            public ColorUIResource getControl() {
                return new ColorUIResource(Color.BLACK);
            }
        };

        MetalLookAndFeel.setCurrentTheme(dmt);
        try { UIManager.setLookAndFeel(new MetalLookAndFeel()); } catch(Exception e) {}

        JFrame f = new JFrame();
        JFrame.setDefaultLookAndFeelDecorated(true);
        f.setUndecorated(true);
        f.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);
        f.setTitle("Skeleton Key");
        f.setLocationRelativeTo(null);
        f.getContentPane().setBackground(Color.BLACK);
        f.getContentPane().setForeground(Color.BLUE);

        JPanel p = new JPanel();
        p.setBackground(Color.DARK_GRAY);
        p.setLayout(new GridBagLayout());
        p.setPreferredSize(new Dimension(250, 75));
        GridBagConstraints gbc = new GridBagConstraints();

        runtimeLabel.setForeground(Color.LIGHT_GRAY);
        nextPressLabel.setForeground(Color.LIGHT_GRAY);
        keyPressLabel.setForeground(Color.LIGHT_GRAY);

        p.add(runtimeLabel, gbc);
        gbc.gridy = 1;
        p.add(nextPressLabel, gbc);
        gbc.gridy = 2;
        p.add(keyPressLabel, gbc);

        // Weird hax to get image from jar - doesn't work on compile
        try(
            InputStream is = SkeletonKey.class.getClassLoader().getResourceAsStream("skeli2.png")
        ) {
            Objects.requireNonNull(is);
            Image icon = ImageIO.read(is);
            f.setIconImage(icon);
        } catch(Exception e){
            System.err.println(e.getMessage());
        }

        f.add(p);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    private void loop(){
        while(true) {
            try { Thread.sleep(1000); } catch (Exception e) { return; }
            timer.increment();
            runtimeLabel.setText(String.format(runtimeText, timer.toString(), timer.getRaw()));
            keyPressLabel.setText(String.format(keyPressText, timer.getTotalTicks()));
            nextPressLabel.setText(String.format(nextPressText, timer.getTimeToNextTick()));
            mashKey();
        }
    }

    private void mashKey(){
        if(!timer.matchesTick()) return;

        angryKeyboardMasher.keyPress(KeyEvent.VK_CONTROL);
        angryKeyboardMasher.keyRelease(KeyEvent.VK_CONTROL);
    }
}
