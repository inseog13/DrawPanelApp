import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DrawPanel extends JPanel {

    int startX, startY, endX, endY;

    // Free Drawing용 이전 좌표
    int prevX, prevY;

    // 현재 선택된 도형
    String currentShape = "FREE";

    //도형 저장 리스트
    ArrayList<MyShape> shapes = new ArrayList<>();

    public DrawPanel() {

        //클릭 이벤트 처리
        MouseAdapter mouseAdapter = new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                startX = e.getX();
                startY = e.getY();

                prevX = startX;
                prevY = startY;
            }

            @Override
            public void mouseReleased(MouseEvent e) {

                //FREE 모드가 아닐 떄만 저장
                if(!currentShape.equals("FREE")) {
                    shapes.add(
                        new MyShape(startX, startY, endX, endY, currentShape)
                    );
                }

                repaint();
            }
        };

        //드래그 이벤트 처리
        MouseMotionAdapter motionAdapter = new MouseMotionAdapter() {

            @Override
            public void mouseDragged(MouseEvent e) {

                endX = e.getX();
                endY = e.getY();

                //자유 그리기 모드
                if(currentShape.equals("FREE")) {
                    shapes.add(
                        new MyShape(prevX, prevY, endX, endY, "LINE")
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

    //도형 선택
    public void setShape(String shape) {

        currentShape = shape;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        //저장된 도형 출력
        for (MyShape s : shapes) {

            drawShape(g, s);
        }

        //FREE 모드는 미리보기 제외
        if(!currentShape.equals("FREE")) {
            drawShape(
                g,
                new MyShape(startX, startY, endX, endY, currentShape)
            );
        }
        
    }

    //도형 그리기
    private void drawShape(Graphics g, MyShape s) {

        int x = Math.min(s.x1, s.x2);
        int y = Math.min(s.y1, s.y2);

        int width = Math.abs(s.x1 - s.x2);
        int height = Math.abs(s.y1 - s.y2);

        switch (s.type) {

            case "LINE":

                g.drawLine(s.x1, s.y1, s.x2, s.y2);
                break;

            case "RECT":

                g.drawRect(x, y, width, height);
                break;

            case "OVAL":

                g.drawOval(x, y, width, height);
                break;
        }
    }
}