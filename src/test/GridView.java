package test;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GridView extends JPanel {

    List<Grid.State> path;
    List<Grid.State> open;
    List<Grid.State> closed;
    private static final int cell = 16;

    public GridView(int width, int height, List<Grid.State> path, List<Grid.State> open, List<Grid.State> closed) {
        this.path = path;
        this.open = open;
        this.closed = closed;
        this.setPreferredSize(new Dimension(width*cell,height*cell));
        setBackground(Color.black);
        //System.out.println(open.size());
    }

    public GridView(int width, int height, List<Grid.State> path) {
        this.path = path;
        this.setPreferredSize(new Dimension(width*cell,height*cell));
        setBackground(Color.black);
        //System.out.println(open.size());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if (closed != null) {
            g2d.setColor(Color.GRAY);
            g2d.setStroke(new BasicStroke(4));
            for (int i = 1; i < closed.size(); i+=2) {
                int x1 = closed.get(i - 1).getX() * cell + cell / 2;
                int y1 = closed.get(i - 1).getY() * cell + cell / 2;
                int x2 = closed.get(i).getX() * cell + cell / 2;
                int y2 = closed.get(i).getY() * cell + cell / 2;
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        if (open != null) {
            g2d.setColor(Color.CYAN);
            g2d.setStroke(new BasicStroke(4));
            for (int i = 1; i < open.size(); i+=2) {
                int x1 = open.get(i - 1).getX() * cell + cell / 2;
                int y1 = open.get(i - 1).getY() * cell + cell / 2;
                int x2 = open.get(i).getX() * cell + cell / 2;
                int y2 = open.get(i).getY() * cell + cell / 2;
                g2d.drawLine(x1, y1, x2, y2);
            }
        }
        g2d.setColor(Color.GREEN);
        g2d.setStroke(new BasicStroke(4));
        for (int i = 1; i < path.size(); i++) {
            int x1 = path.get(i - 1).getX() * cell + cell / 2;
            int y1 = path.get(i - 1).getY() * cell + cell / 2;
            int x2 = path.get(i).getX() * cell + cell / 2;
            int y2 = path.get(i).getY() * cell + cell / 2;
            g2d.drawLine(x1, y1, x2, y2);
        }
    }
}
