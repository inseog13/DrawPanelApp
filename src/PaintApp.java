import javax.swing.*;
import java.awt.*;

public class PaintApp extends JFrame {

    public PaintApp() {

        setTitle("간단 그림판");

        setSize(1200, 800);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        JLabel statusLabel =
                new JLabel(
                        "도구 : 자유그리기 | 굵기 : 3"
                );

        DrawPanel drawPanel =
                new DrawPanel(statusLabel);

        //---------------------------------
        // 툴바
        //---------------------------------

        JToolBar toolBar =
                new JToolBar();

        toolBar.setFloatable(false);

        //---------------------------------
        // 도구 선택
        //---------------------------------

        JToggleButton freeButton =
                new JToggleButton("자유그리기");

        JToggleButton lineButton =
                new JToggleButton("선");

        JToggleButton rectButton =
                new JToggleButton("사각형");

        JToggleButton ovalButton =
                new JToggleButton("원");

        JToggleButton eraserButton =
                new JToggleButton("지우개");

        ButtonGroup group =
                new ButtonGroup();

        group.add(freeButton);
        group.add(lineButton);
        group.add(rectButton);
        group.add(ovalButton);
        group.add(eraserButton);

        freeButton.setSelected(true);

        //---------------------------------
        // ToolTip
        //---------------------------------

        freeButton.setToolTipText(
                "마우스로 자유롭게 그립니다."
        );

        lineButton.setToolTipText(
                "직선을 그립니다."
        );

        rectButton.setToolTipText(
                "사각형을 그립니다."
        );

        ovalButton.setToolTipText(
                "원을 그립니다."
        );

        eraserButton.setToolTipText(
                "지우개 기능입니다."
        );

        //---------------------------------
        // 이벤트
        //---------------------------------

        freeButton.addActionListener(
                e -> drawPanel.setTool("FREE")
        );

        lineButton.addActionListener(
                e -> drawPanel.setTool("LINE")
        );

        rectButton.addActionListener(
                e -> drawPanel.setTool("RECT")
        );

        ovalButton.addActionListener(
                e -> drawPanel.setTool("OVAL")
        );

        eraserButton.addActionListener(
                e -> drawPanel.setTool("ERASER")
        );

        //---------------------------------
        // 색상 선택
        //---------------------------------

        JButton colorButton =
                new JButton("색상 선택");

        colorButton.setToolTipText(
                "그리기 색상을 선택합니다."
        );

        colorButton.addActionListener(
                e -> {

                    Color selectedColor =
                            JColorChooser.showDialog(
                                    this,
                                    "색상 선택",
                                    Color.BLACK
                            );

                    if (selectedColor != null) {

                        drawPanel.setColor(
                                selectedColor
                        );
                    }
                }
        );

        //---------------------------------
        // 굵기 슬라이더
        //---------------------------------

        JLabel sliderLabel =
                new JLabel("굵기");

        JSlider strokeSlider =
                new JSlider(
                        1,
                        10,
                        3
                );

        strokeSlider.setMajorTickSpacing(1);

        strokeSlider.setPaintTicks(true);

        strokeSlider.setPaintLabels(true);

        strokeSlider.setToolTipText(
                "선 굵기 조절"
        );

        strokeSlider.addChangeListener(
                e -> {

                    drawPanel.setStrokeSize(
                            strokeSlider.getValue()
                    );
                }
        );

        //---------------------------------
        // Undo
        //---------------------------------

        JButton undoButton =
                new JButton("실행취소");

        undoButton.setToolTipText(
                "마지막 작업을 취소합니다."
        );

        undoButton.addActionListener(
                e -> drawPanel.undo()
        );

        //---------------------------------
        // Clear
        //---------------------------------

        JButton clearButton =
                new JButton("전체삭제");

        clearButton.setToolTipText(
                "모든 그림을 삭제합니다."
        );

        clearButton.addActionListener(
                e -> {

                    int result =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "모든 그림을 삭제하시겠습니까?",
                                    "확인",
                                    JOptionPane.YES_NO_OPTION
                            );

                    if (
                            result ==
                                    JOptionPane.YES_OPTION
                    ) {

                        drawPanel.clearAll();
                    }
                }
        );

        //---------------------------------
        // 툴바 구성
        //---------------------------------

        toolBar.add(freeButton);

        toolBar.add(lineButton);

        toolBar.add(rectButton);

        toolBar.add(ovalButton);

        toolBar.add(eraserButton);

        toolBar.addSeparator();

        toolBar.add(colorButton);

        toolBar.addSeparator();

        toolBar.add(sliderLabel);

        toolBar.add(strokeSlider);

        toolBar.addSeparator();

        toolBar.add(undoButton);

        toolBar.add(clearButton);

        //---------------------------------
        // 배치
        //---------------------------------

        add(
                toolBar,
                BorderLayout.NORTH
        );

        add(
                drawPanel,
                BorderLayout.CENTER
        );

        add(
                statusLabel,
                BorderLayout.SOUTH
        );

        setVisible(true);
    }

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                PaintApp::new
        );
    }
}