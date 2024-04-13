package com.george;
import java.awt.Image;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;
import javax.swing.JToolTip;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.bbn.openmap.LayerHandler;
import com.bbn.openmap.MapBean;
import com.bbn.openmap.MouseDelegator;
import com.bbn.openmap.MultipleSoloMapComponentException;
import com.bbn.openmap.corba.CSpecialist.MouseEvent;
import com.bbn.openmap.event.OMMouseMode;
import com.bbn.openmap.gui.LayersPanel;
import com.bbn.openmap.gui.MapPanel;
import com.bbn.openmap.gui.OpenMapFrame;
import com.bbn.openmap.gui.OverlayMapPanel;
import com.bbn.openmap.gui.ToolPanel;
import com.bbn.openmap.layer.GraticuleLayer;
import com.bbn.openmap.layer.OMGraphicHandlerLayer;
import com.bbn.openmap.layer.learn.BasicLayer;
import com.bbn.openmap.layer.shape.BufferedShapeLayer;
import com.bbn.openmap.layer.shape.ShapeLayer;
import com.bbn.openmap.omGraphics.OMArrowHead;
import com.bbn.openmap.omGraphics.OMCircle;
import com.bbn.openmap.omGraphics.OMGraphic;
import com.bbn.openmap.omGraphics.OMGraphicList;
import com.bbn.openmap.omGraphics.OMLine;
import com.bbn.openmap.omGraphics.OMPoly;
import com.bbn.openmap.omGraphics.OMRaster;
import com.bbn.openmap.omGraphics.OMScalingIcon;
import com.bbn.openmap.omGraphics.OMText;
import com.bbn.openmap.proj.Projection;
import com.bbn.openmap.proj.coords.LatLonPoint;
import com.george.targets.SurfaceToSurfacemissile;

/**
 * This is a simple application that uses the OpenMap MapBean to show a map.
 * This is the model you should follow if you are starting to use OpenMap and
 * want to create your own application. Use a MapPanel! Use the MapHandler
 * inside it!
 * <p>
 * This example shows:
 * <ul>
 * <li>MapBean
 * <li>MapHandler
 * <li>LayerHandler
 * <li>LayersPanel
 * <li>ShapeLayer with political data
 * <li>GraticuleLayer
 * <li>BasicLayer with some random data
 * <li>Tools to navigate around on the map
 * </ul>
 */
public class SimpleMap2 {
    final MapPanel mapPanel =new OverlayMapPanel();
    final OMGraphicHandlerLayer graphicsLayer = new OMGraphicHandlerLayer();
    final OMGraphicHandlerLayer gHandlerLayer_polygon=new OMGraphicHandlerLayer();
    OMGraphicList list = new OMGraphicList();
    OMGraphicList list_of_polygons=new OMGraphicList();
    public SimpleMap2() {
    
        try {

            /*
             * The BasicMapPanel automatically creates many default components,
             * including the MapBean and the MapHandler. You can extend the
             * BasicMapPanel class if you like to add different functionality or
             * different types of objects.
             */
        

            /*
             * The MapHandler is central to this application, although you never
             * really see it. It's in the MapPanel. Calling addMapComponent(obj)
             * is the same as calling mapPanel.getMapHandler().add(obj).
             */

            /*
             * Create and add a LayerHandler to the MapHandler. The LayerHandler
             * manages Layers, whether they are part of the map or not.
             * layer.setVisible(true) will add it to the map. The LayerHandler
             * has methods to do this, too. The LayerHandler will find the
             * MapBean in the MapHandler.
             */
            mapPanel.addMapComponent(new LayerHandler());
            // Add MouseDelegator, which handles mouse modes (managing mouse
            // events via MouseModes)
            mapPanel.addMapComponent(new MouseDelegator());
            // Add OMMouseMode, which handles how the map reacts to mouse
            // movements
            mapPanel.addMapComponent(new OMMouseMode());
            // Add a ToolPanel for widgets on the north side of the map.
            mapPanel.addMapComponent(new ToolPanel());
            // Add a LayersPanel, which lets you control layers
            mapPanel.addMapComponent(new LayersPanel());


            /*
             * Create a ShapeLayer to show world political boundaries. Set the
             * properties of the layer. This assumes that the datafile
             * "cntry02.shp" is in a path specified in the CLASSPATH variable.
             * These files are distributed with OpenMap and reside in the top
             * level "share" sub-directory.
             */
            ShapeLayer shapeLayer = new BufferedShapeLayer();

            // Since this Properties object is being used just for
            // this layer, the properties do not have to be scoped
            // with marker name.
            Properties shapeLayerProps = new Properties();
            shapeLayerProps.put("prettyName", "Political Solid");
            shapeLayerProps.put("lineColor", "000000");
            shapeLayerProps.put("fillColor", "BDDE83");
            shapeLayerProps.put("shapeFile", "data\\shape\\cntry02\\cntry02.shp");
            shapeLayer.setProperties(shapeLayerProps);
            shapeLayer.setVisible(true);

            // Last on top.
            mapPanel.addMapComponent(shapeLayer);
            mapPanel.addMapComponent(new GraticuleLayer());

          
            
        
            double latitude = 47.6061; // Replace with desired latitude
            double longitude = -122.3328;; // Replace with desired longitude
            double radiusInMeters = 1.5; // Replace with desired radius

            OMCircle circle = new OMCircle(latitude, longitude, radiusInMeters);
            circle.setLinePaint(Color.RED);
            circle.setFillPaint(Color.RED);
            list.add(circle);

             
            File csvFile=new File("us_cities_coordinates.csv");
            try{
                Scanner scanner =new Scanner(csvFile);
                scanner.nextLine();
                while(scanner.hasNextLine()){
                    String line =scanner.nextLine();
                    System.err.println(line);;
                    String[] coordinates=line.split(",");

                    double latitudeFromCSV=Double.parseDouble(coordinates[1].trim());
                    double longitudeFromCSV=Double.parseDouble(coordinates[2].trim());

                    list.add(new SurfaceToSurfacemissile(latitudeFromCSV, longitudeFromCSV).createTheSilo());
                    
                }
                scanner.close();
            }
            catch(FileNotFoundException exception){
                exception.printStackTrace();
            }
            

            double latitude2 = 40.7128;
            double longitude2 = -90.0060;
            OMRaster iconRaster= new SurfaceToSurfacemissile(latitude2,longitude2).createTheSilo();
            OMText name=new OMText(latitude2,longitude2,"New York's silo",OMText.JUSTIFY_CENTER);

            
                
            
    
            
            graphicsLayer.setName("Missiles Silos");
            graphicsLayer.setList(list);
            graphicsLayer.isVisible();
            list.add(name);
            list.add(iconRaster);

            gHandlerLayer_polygon.setName("Polygons");
            gHandlerLayer_polygon.setList(list_of_polygons);
            gHandlerLayer_polygon.isVisible();
            
            mapPanel.addMapComponent(graphicsLayer);
            mapPanel.addMapComponent(gHandlerLayer_polygon);
            // Create a Swing frame. The OpenMapFrame knows how to use
            // the MapHandler to locate and place certain objects.
            OpenMapFrame frame = new OpenMapFrame("Simple Map 2");
            // Size the frame appropriately
            frame.setSize(640, 480);

            mapPanel.addMapComponent(frame);

            // If you close the frame, exit the app
            frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            });

            // Display the frame
            frame.setVisible(true);
            // Get the default MapBean that the BasicMapPanel created.
            MapBean mapBean = mapPanel.getMapBean();
            // Set the map's center
            mapBean.setCenter(new LatLonPoint.Double(43.0, -95.0));
            // Set the map's scale 1:120 million
            mapBean.setScale(120000000f);
            


            URL imageURL=getClass().getResource("/ssn2.png");
            URL imageURL2=getClass().getResource("/sub_attack_nuclear_powered.png");
            //---- Object1---- setting the heading for the navy objects
            OMLine heading=addHeadingLine(32.6286, -54.1552, 48.6970 , -32.2704);
            heading.setLinePaint(Color.RED);
            list.add(heading);
            //  ----Object1---- adding the icon for the navy object 
            OMScalingIcon icon = new OMScalingIcon(32.6286, -54.1552, new ImageIcon(ImageIO.read(imageURL2).getScaledInstance(25, 25, Image.SCALE_SMOOTH)));
            list.add(icon);

            //----Object2---- creation of polygons with their own layer
            double[] llPoints = {33.955632, -162.5671, 30.143781, -145.0958, 9.112570, -142.847076, 10.603884, -167.04622};
            String name1= "Danger Zone";
            list_of_polygons.add(addPolygons(llPoints, name1));
            OMText name2=new OMText(llPoints[0],llPoints[1],name1,OMText.JUSTIFY_CENTER);
            list_of_polygons.add(name2);

           



            

            //adding the button for adding Surface-To-Surface 
            ImageIcon iconSSM=new ImageIcon(ImageIO.read(imageURL).getScaledInstance(15, 15, Image.SCALE_SMOOTH));
            JButton addSSMButton =new JButton("Locate the Surface-To-surface Missile",iconSSM);
            frame.getContentPane().add(addSSMButton,BorderLayout.SOUTH);

            //add action listener to the button
            addSSMButton.addActionListener(new ActionListener() {
            
                @Override
                public void actionPerformed(ActionEvent e) {
                    String latitude = JOptionPane.showInputDialog("Enter latitude:");
                    String longitude = JOptionPane.showInputDialog("Enter longitude:");
                    String name = JOptionPane.showInputDialog("Code name:");

                    // Create and add the OMRaster object to the map using the provided latitude, longitude, and name
                    double lat = Double.parseDouble(latitude);
                    double lon = Double.parseDouble(longitude);
                    OMRaster raster;
                    try {
                        raster = new SurfaceToSurfacemissile(lat, lon).createTheSilo();
                        graphicsLayer.getList().add(raster);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    OMText nameText = new OMText(lat, lon, name, OMText.JUSTIFY_CENTER);

                    graphicsLayer.getList().add(nameText);
                    
                }
            });

            
            
                    
                
            
            
            

        } catch (MultipleSoloMapComponentException | IOException msmce) {
            msmce.printStackTrace();
            // The MapHandler is only allowed to have one of certain
            // items. These items implement the SoloMapComponent
            // interface. The MapHandler can have a policy that
            // determines what to do when duplicate instances of the
            // same type of object are added - replace or ignore.

            // In this example, this will never happen, since we are
            // controlling that one MapBean, LayerHandler,
            // MouseDelegator, etc is being added to the MapHandler.
        } 

        
    }

    public OMLine addHeadingLine(double lat1,double lon1, double lat2, double lon2){
        OMLine line =new OMLine(lat1,lon1,lat2,lon2,OMGraphic.LINETYPE_STRAIGHT);
        // Create an arrow head and set it to the line
    
        // Add the line to your map
        // Assuming you have a LayerHandler instance named layerHandler
        return line;
    }
    public OMPoly addPolygons(double[] coordinates, String polygonName){
        //default value for the units is -1 
        int units=OMGraphic.DECIMAL_DEGREES;
        int ltype = OMGraphic.LINETYPE_RHUMB;
        OMPoly polygon = new OMPoly(coordinates, units, ltype);
        polygon.setLinePaint(Color.RED); // Set the color of the line
        //polygon.setFillPaint(Color.RED); // Set the line ends to use an arrowhead
        polygon.setIsPolygon(true);
        //polygon.addPoint(coordinates[0], coordinates[1], OMGraphic.DECIMAL_DEGREES);
        //OMGraphicList list = new OMGraphicList();
        //list.add(polygon);
        
        OMText name=new OMText(coordinates[0],coordinates[1],polygonName,1);
        //list.add(name);
        return polygon;
    }

    

    public static void main(String[] args) {


        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SimpleMap2();
            }
        });
    }
}