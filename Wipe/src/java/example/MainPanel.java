// -*- mode:java; encoding:utf-8 -*-
// vim:set fileencoding=utf-8:
// @homepage@

package example;

import java.awt.*;
import javax.swing.*;

public final class MainPanel extends JPanel {
  private Wipe mode = Wipe.IN;

  private MainPanel() {
    super(new BorderLayout());
    Timer animator = new Timer(5, null);
    ImageIcon icon = new ImageIcon(getClass().getResource("test.png"));
    Component wipe = new JComponent() {
      private int ww;
      @Override protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (getWipeMode() == Wipe.IN) {
          if (ww < icon.getIconWidth()) {
            ww += 10;
          } else {
            animator.stop();
          }
        } else { // Wipe.OUT:
          if (ww > 0) {
            ww -= 10;
          } else {
            animator.stop();
          }
        }
        int iw = icon.getIconWidth();
        int ih = icon.getIconHeight();
        g.drawImage(icon.getImage(), 0, 0, iw, ih, this);
        g.fillRect(ww, 0, iw, ih);
      }
    };
    wipe.setBackground(Color.BLACK);
    animator.addActionListener(e -> wipe.repaint());

    JButton button1 = new JButton("Wipe In");
    button1.addActionListener(e -> {
      setWipeMode(Wipe.IN);
      animator.start();
    });

    JButton button2 = new JButton("Wipe Out");
    button2.addActionListener(e -> {
      setWipeMode(Wipe.OUT);
      animator.start();
    });

    add(wipe);
    add(button1, BorderLayout.SOUTH);
    add(button2, BorderLayout.NORTH);
    setOpaque(false);
    setPreferredSize(new Dimension(320, 240));
    animator.start();
  }

  public void setWipeMode(Wipe wipeMode) {
    this.mode = wipeMode;
  }

  public Wipe getWipeMode() {
    return mode;
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(MainPanel::createAndShowGui);
  }

  private static void createAndShowGui() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
      ex.printStackTrace();
      Toolkit.getDefaultToolkit().beep();
    }
    JFrame frame = new JFrame("@title@");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.getContentPane().add(new MainPanel());
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }
}

enum Wipe {
  IN, OUT
}
