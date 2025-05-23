import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Project extends Applet implements Runnable {
    // Declaration the buttons 
    Button red, green, blue, Eraser, EraseAll;
    Button Oval, Rect, Line, Freehand;
	Button Undo ;
	Button Redo ;
    // Declaration CheckBox
    Checkbox dotted, filled;
    // Make the default color to be black
    Color selected_color = Color.BLACK;
    // Declare the Default drawing to be Line
    String selected = "Line";
    // Declare the Boolean isdotted, is filled to register it with the CheckBox
    boolean isDotted = false, isFilled = false;
    // Declare Flexible arrayList of ClassType shape
    ArrayList<Shape> shapes = new ArrayList<>();
	
	Stack <Shape> trash = new Stack<>();
    Shape tempShape;
    Image offScreen;
    Graphics offG;

    public void init() {
        Thread th = new Thread(this);

        red = new Button("RED");
        green = new Button("GREEN");
        blue = new Button("BLUE");
        Eraser = new Button("Eraser");
        EraseAll = new Button("Erase All");
		Undo = new Button("Undo");
		Redo = new Button("Redo");
		
        red.setBackground(Color.RED);
        green.setBackground(Color.GREEN);
        blue.setBackground(Color.BLUE);

        add(red); add(green); add(blue); add(Eraser); add(EraseAll); add(Undo); add(Redo);

        Oval = new Button("Oval");
        Rect = new Button("Rectangle");
        Line = new Button("Line");
        Freehand = new Button("Freehand");
        add(Oval); add(Rect); add(Line); add(Freehand);

        dotted = new Checkbox("Dotted");
        filled = new Checkbox("Filled");
        add(dotted); add(filled);

        // Add action listeners
	

		//undo 
        Undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if(shapes.size() > 0)
				{
                trash.push(shapes.get(shapes.size() -1 )); // add the last vvalue element of the ArrayList into the trash
                shapes.remove(shapes.size() -1 ); // remove the last vvalue element of the ArrayList 
				}
				repaint();
            }
        });	
		
		// Redo
        Redo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				if(trash.size() > 0)
				{
					// pop --> return the last element and remove it from the stack
					// shapes.add --> add in the arraylist
                shapes.add( trash.pop() ); // add the last vvalue element of the ArrayList into the trash
				}
				repaint();
            }
        });
		
        red.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected_color = Color.RED;
            }
        });

        green.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected_color = Color.GREEN;
            }
        });

        blue.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected_color = Color.BLUE;
            }
        });

        Eraser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected = "Eraser";
            }
        });

        EraseAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapes.clear();
                repaint();
            }
        });

        Line.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected = "Line";
            }
        });

        Rect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected = "Rectangle";
            }
        });

        Oval.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected = "Oval";
            }
        });

        Freehand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selected = "Freehand";
            }
        });

        dotted.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                isDotted = dotted.getState();
            }
        });

        filled.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                isFilled = filled.getState();
            }
        });

        addMouseListener(new MouseHandler());
        addMouseMotionListener(new MouseMotionHandler());

        th.start();
    }

    class Shape {
        int x1, y1, x2, y2;
        Color c;
        String type;
        boolean dotted, filled;
        // Linked List Implementation 
        ArrayList<Point> freehandPoints;

        Shape(String type, int x1, int y1, Color c, boolean dotted, boolean filled) {
            this.type = type;
            this.x1 = x1;
            this.y1 = y1;
            this.c = c;
            this.dotted = dotted;
            this.filled = filled;

            // Initialize freehandPoints for Freehand and Eraser
            if (type.equals("Freehand") || type.equals("Eraser")) {
                freehandPoints = new ArrayList<>();
            }
        }
    }

    class MouseHandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            // Determine the color based on the selected tool
            Color color;
            if (selected.equals("Eraser")) {
                color = getBackground(); // Use background color for the eraser
            } else {
                color = selected_color; // Use selected color for the other tools
            }

            // Create a new shape
            tempShape = new Shape(selected, e.getX(), e.getY(), color, isDotted, isFilled);

            // If Freehand tool is selected, add the starting point
            if (selected.equals("Freehand") || selected.equals("Eraser")) {
                tempShape.freehandPoints.add(new Point(e.getX(), e.getY()));
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (tempShape != null) { // Add null check
                if (!(selected.equals("Freehand") || selected.equals("Eraser"))) {
                    tempShape.x2 = e.getX();
                    tempShape.y2 = e.getY();
                }
                shapes.add(tempShape); // Add the shape to the list
                tempShape = null; // Clear the temporary shape
                repaint(); // Redraw the applet
            }
        }
    }

    class MouseMotionHandler extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            if (tempShape != null) { // Add null check to avoid NullException
                if (selected.equals("Freehand") || selected.equals("Eraser")) {
                    tempShape.freehandPoints.add(new Point(e.getX(), e.getY()));
                } else {
                    tempShape.x2 = e.getX();
                    tempShape.y2 = e.getY();
                }
                repaint();
            }
        }
    }

    public void update(Graphics g) {
		
        if (offScreen == null) {
            offScreen = createImage(getWidth(), getHeight());
            offG = offScreen.getGraphics();
        }
        offG.clearRect(0, 0, getWidth(), getHeight());
        paint(offG);
        g.drawImage(offScreen, 0, 0, this);
		
		//paint(g);
    }


    public void paint(Graphics g) {
        for (Shape s : shapes) {
            drawShape(g, s);
        }
        if (tempShape != null) {
            drawShape(g, tempShape);
        }
    }

    private void drawShape(Graphics g, Shape s) {
        g.setColor(s.c);
        Graphics2D g2 = (Graphics2D) g;

        if (s.dotted) {
            g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
        } else {
            g2.setStroke(new BasicStroke(1));
        }

        int x = Math.min(s.x1, s.x2);
        int y = Math.min(s.y1, s.y2);
        int w = Math.abs(s.x1 - s.x2);
        int h = Math.abs(s.y1 - s.y2);

        switch (s.type) {
            case "Line":
                g.drawLine(s.x1, s.y1, s.x2, s.y2);
                break;
            case "Rectangle":
                if (s.filled) {
                    g.fillRect(x, y, w, h);
                } else {
                    g.drawRect(x, y, w, h);
                }
                break;
            case "Oval":
                if (s.filled) {
                    g.fillOval(x, y, w, h);
                } else {
                    g.drawOval(x, y, w, h);
                }
                break;
            case "Freehand":
                for (int i = 1; i < s.freehandPoints.size(); i++) {
                    Point p1 = s.freehandPoints.get(i - 1);
                    Point p2 = s.freehandPoints.get(i);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
                break;
            case "Eraser":
                for (Point p : s.freehandPoints) {
                    g.fillRect(p.x - 10, p.y - 10, 20, 20); // Center the rectangle at the point
                }
                break;
        }
    }

    public void run() {}
}