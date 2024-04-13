package com.george.targets;
import com.bbn.openmap.omGraphics.OMRaster;
import javax.swing.JToolTip;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
public class SurfaceToSurfacemissile extends  JToolTip {
    protected double latitude;
    protected double longitude;

    public SurfaceToSurfacemissile(double latitude,double longitude){
        this.latitude=latitude;
        this.longitude=longitude;
    }
    public OMRaster createTheSilo() throws IOException{ 
        URL imageURL=getClass().getResource("/ssn2.png");
            
            if (imageURL == null){
                throw new IllegalArgumentException("Resource not found: missile_silo.png");
            }
            try {
                Image image = ImageIO.read(imageURL);
            } catch (IOException e) {
                throw new RuntimeException("Failed to read image", e);
            }

            OMRaster missile= new OMRaster(this.latitude,this.longitude,new ImageIcon(ImageIO.read(imageURL).getScaledInstance(15, 15, Image.SCALE_SMOOTH)));
            

        return missile;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    
    

}
