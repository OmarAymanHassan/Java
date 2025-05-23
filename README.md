# Java Paint Brush Applet

## Overview  
This project is an interactive Java applet-based drawing application that allows users to draw and manipulate various shapes including lines, rectangles, ovals, and freehand sketches. It supports color selection, dotted and filled shape styles, an eraser tool, and undo/redo functionality.

## Features  
- Draw lines, rectangles, ovals, and freehand shapes  
- Choose from multiple colors (red, green, blue, black)  
- Toggle dotted or solid stroke styles  
- Fill shapes with color or leave them unfilled  
- Eraser tool to remove parts of the drawing  
- Undo and redo actions using stack-based management  
- Clear all drawings with "Erase All" button  
- Smooth rendering with double buffering to reduce flicker  

## How to Run  
1. Compile the Java source file using `javac Project.java`
```
javac Project.java
```

2. Run the applet viewer or an IDE that supports applets with `appletviewer Project.java`  
   (Note: Modern browsers no longer support Java applets directly; use an applet viewer or IDE with applet support)
```
appletviewer Project.java
```


## Controls  
- Color buttons to select drawing color (Red, Green, Blue)  
- Shape buttons to choose shape type (Line, Rectangle, Oval, Freehand)  
- Checkboxes to toggle dotted lines and filled shapes  
- Eraser button to switch to eraser mode  
- Undo and Redo buttons to revert or restore previous drawing actions  
- Erase All button to clear the entire canvas  

## Code Structure  
- The main `Project` class extends `Applet` and implements `Runnable`  
- An inner `Shape` class represents drawable objects with properties like position, color, type, and style  
- Mouse and mouse motion listeners handle drawing input  
- Shapes are stored in an ArrayList, while undo/redo is managed with a Stack  

## Notes  
- Designed as a learning project to demonstrate Java GUI programming, event handling, and basic graphics concepts  
- Uses double buffering to optimize drawing performance and minimize flickering  
