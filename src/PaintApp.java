import javax.swing.*;
import java.awt.*;

public class PaintApp extends JFrame {

    public PaintApp() {

        setTitle("Simple Paint Program");

        setSize(800, 600);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawPanel drawPanel = new DrawPanel();

        //버튼 패널
        JPanel buttonPanel = new JPanel();

        JButton freeButton = new JButton("Free");
        JButton lineButton = new JButton("Line");
        JButton rectButton = new JButton("Rectangle");
        JButton ovalButton = new JButton("Oval");

        //버튼 이벤트
        freeButton.addActionListener(
                e -> drawPanel.setShape("FREE")
        );
        lineButton.addActionListener(
                e -> drawPanel.setShape("LINE")
        );

        rectButton.addActionListener(
                e -> drawPanel.setShape("RECT")
        );

        ovalButton.addActionListener(
                e -> drawPanel.setShape("OVAL")
        );

        //버튼 추가
        buttonPanel.add(freeButton);
        buttonPanel.add(lineButton);
        buttonPanel.add(rectButton);
        buttonPanel.add(ovalButton);

        //프레임 추가
        add(buttonPanel, BorderLayout.NORTH);

        add(drawPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {

        new PaintApp();
    }
}