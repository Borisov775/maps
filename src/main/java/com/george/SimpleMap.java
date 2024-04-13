package com.george;


import com.bbn.openmap.gui.BasicMapPanel;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.omGraphics.OMCircle;
import com.bbn.openmap.omGraphics.OMGraphicList;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Properties;
import javax.swing.JFrame;

/**
 * This is a simple application that uses the OpenMap MapBean to show a map.
 * This sample application is just provided to show the simplest way to put a
 * map in a java application. If you want a the best example to use for a simple
 * application to play with OpenMap components, use SimpleMap2! Use a MapPanel!
 * <p>
 * This example shows:
 * <ul>
 * <li>MapBean
 * <li>ShapeLayer with political data
 * </ul>
 */
public class SimpleMap {

	public static void main(String args[]) {

		BasicMapPanel mapPanel = new BasicMapPanel();

		// Create a ShapeLayer to show world political boundaries.
		// Set the properties of the layer. This assumes that the
		// "data" directory containing the files "dcwpo-browse.shp"
		// and "dcwpo-browse.ssx" are in a path specified in the
		// CLASSPATH variable. These files are distributed with
		// OpenMap and reside in the toplevel "share" subdirectory.
		ShapeLayer shapeLayer = new ShapeLayer();
		Properties shapeLayerProps = new Properties();
		shapeLayerProps.put("prettyName", "Political Solid");
		shapeLayerProps.put("lineColor", "000000");
		shapeLayerProps.put("fillColor", "BDDE83");
		shapeLayerProps.put("shapeFile", "data/shape/dcwpo-browse.shp");
		shapeLayerProps.put("spatialIndex", "data/shape/dcwpo-browse.ssx");
		shapeLayer.setProperties(shapeLayerProps);

		// Add the political layer to the map
		mapPanel.getMapBean().add(shapeLayer);

        // Create a Swing frame
        JFrame frame = new JFrame("Simple Map");
        // Size the frame appropriately
        frame.setSize(640, 480);
		// Add the map to the frame
		frame.getContentPane().add(mapPanel);
		OMGraphicHandlerLayer graphicsLayer = new OMGraphicHandlerLayer();
            double latitude = 50; // Replace with desired latitude
            double longitude = -130;; // Replace with desired longitude
            double radiusInMeters = 1000000; // Replace with desired radius

            OMCircle circle = new OMCircle(latitude, longitude, radiusInMeters);
            circle.setLinePaint(Color.RED);
            circle.setFillPaint(Color.RED);
            OMGraphicList list = new OMGraphicList();
            list.add(circle);

            OMGraphicHandlerLayer layer = new OMGraphicHandlerLayer();
            layer.setList(list);
            graphicsLayer.isVisible();

        
            mapPanel.addMapComponent(graphicsLayer);

        // If you close the frame, exit the app.
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Display the frame
		frame.setVisible(true);
	}
}