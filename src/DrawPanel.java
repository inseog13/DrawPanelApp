import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {

    private int startX, startY, endX, endY;

    private int prevX, prevY;

    private String currentTool = "FREE";

    private Color currentColor = Color.BLACK;

    private int currentStroke = 3;

    private JLabel statusLabel;

    private ArrayList<DrawAction> actions =
            new ArrayList<>();

    private DrawAction currentAction;

    public DrawPanel(JLabel statusLabel) {

        this.statusLabel = statusLabel;

        setBackground(Color.WHITE);

        MouseAdapter mouseAdapter =
                new MouseAdapter() {

                    @Override
                    public void mousePressed(MouseEvent e) {

                        startX = e.getX();
                        startY = e.getY();

                        prevX = startX;
                        prevY = startY;

                        currentAction =
                                new DrawAction();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                        if (
                                !currentTool.equals("FREE")
                                        &&
                                        !currentTool.equals("ERASER")
                        ) {

                            currentAction.addShape(
                                    new MyShape(
                                            startX,
                                            startY,
                                            endX,
                                            endY,
                                            currentTool,
                                            currentColor,
                                            currentStroke
                                    )
                            );
                        }

                        if (!currentAction.getShapes().isEmpty()) {

                            actions.add(currentAction);
                        }

                        currentAction = null;

                        repaint();
                    }
                };

        MouseMotionAdapter motionAdapter =
                new MouseMotionAdapter() {

                    @Override
                    public void mouseDragged(MouseEvent e) {

                        endX = e.getX();
                        endY = e.getY();

                        if (
                                currentTool.equals("FREE")
                        ) {

                            currentAction.addShape(
                                    new MyShape(
                                            prevX,
                                            prevY,
                                            endX,
                                            endY,
                                            "LINE",
                                            currentColor,
                                            currentStroke
                                    )
                            );

                            prevX = endX;
                            prevY = endY;
                        }

                        else if (
                                currentTool.equals("ERASER")
                        ) {

                            currentAction.addShape(
                                    new MyShape(
                                            prevX,
                                            prevY,
                                            endX,
                                            endY,
                                            "LINE",
                                            Color.WHITE,
                                            currentStroke + 10
                                    )
                            );

                            prevX = endX;
                            prevY = endY;
                        }

                        repaint();
                    }
                };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(motionAdapter);
    }

    public void setTool(String tool) {

        currentTool = tool;

        updateStatus();
    }

    public void setColor(Color color) {

        currentColor = color;

        updateStatus();
    }

    public void setStrokeSize(int size) {

        currentStroke = size;

        updateStatus();
    }

    public void undo() {

        if (!actions.isEmpty()) {

            actions.remove(
                    actions.size() - 1
            );

            repaint();
        }
    }

    public void clearAll() {

        actions.clear();

        repaint();
    }

    private void updateStatus() {

        String toolName = "";

        switch (currentTool) {

            case "FREE":
                toolName = "자유그리기";
                break;

            case "LINE":
                toolName = "선";
                break;

            case "RECT":
                toolName = "사각형";
                break;

            case "OVAL":
                toolName = "원";
                break;

            case "ERASER":
                toolName = "지우개";
                break;
        }

        statusLabel.setText(
                "도구 : "
                        + toolName
                        + " | 굵기 : "
                        + currentStroke
        );
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        for (DrawAction action : actions) {

            for (MyShape shape : action.getShapes()) {

                drawShape(g, shape);
            }
        }

        if (currentAction != null) {

            for (MyShape shape : currentAction.getShapes()) {

                drawShape(g, shape);
            }
        }

        if (
                !currentTool.equals("FREE")
                        &&
                        !currentTool.equals("ERASER")
        ) {

            drawShape(
                    g,
                    new MyShape(
                            startX,
                            startY,
                            endX,
                            endY,
                            currentTool,
                            currentColor,
                            currentStroke
                    )
            );
        }
    }

    private void drawShape(
            Graphics g,
            MyShape shape
    ) {

        Graphics2D g2 =
                (Graphics2D) g;

        g2.setColor(shape.color);

        g2.setStroke(
                new BasicStroke(
                        shape.strokeSize
                )
        );

        int x =
                Math.min(
                        shape.x1,
                        shape.x2
                );

        int y =
                Math.min(
                        shape.y1,
                        shape.y2
                );

        int width =
                Math.abs(
                        shape.x1
                                - shape.x2
                );

        int height =
                Math.abs(
                        shape.y1
                                - shape.y2
                );

        switch (shape.type) {

            case "LINE":

                g2.drawLine(
                        shape.x1,
                        shape.y1,
                        shape.x2,
                        shape.y2
                );

                break;

            case "RECT":

                g2.drawRect(
                        x,
                        y,
                        width,
                        height
                );

                break;

            case "OVAL":

                g2.drawOval(
                        x,
                        y,
                        width,
                        height
                );

                break;
        }
    }
}