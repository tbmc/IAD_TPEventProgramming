package iad.eventprog;
/*
 * Bean.java
 *
 * Created on 11 janvier 2003, 14:20
 */
import java.beans.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author  gilles
 */
public class ImageViewerBean extends JPanel implements java.io.Serializable {
    
    private Image images[];
    private String imagesFileName[];
    private int	current;
    private int maxImages;
    private boolean loop;
    private String pathName;
    
    public class JPEGFilenameFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return name.toLowerCase().endsWith(".jpg");

           // if (name.endsWith(".jpg") || name.endsWith(".JPG")) return true;
            // return false;
        }
    }
    
 
    
    public ImageViewerBean(String initPathName) {
		loop = true;
		maxImages = 20;
		pathName = initPathName;
		
		createImages();

    }
    
    private void createImages() {
    	File file = new File(pathName);
    	imagesFileName = file.list(new JPEGFilenameFilter());
        int i, l = imagesFileName.length;
	
        images = new Image[l];
        for (i=0; i < l; i++) {
        	String imagePath = pathName + "/" + imagesFileName[i];
        	System.out.println("Loading..."+ imagePath);
        	images[i] = new ImageIcon(imagePath).getImage();
        	System.out.println("Image create:" + imagePath + "->" + images[i]);
        }
        current = (l > 0) ? 0 : -1;

    }

    @Override
    protected void paintComponent(Graphics g) { 
    	super.paintComponent(g);
        if (images != null && hasImages()) {
            g.drawImage(images[current], 0,0, null);
        }     
        else {
            FontMetrics fm;
            Dimension dim;
            int strWidth, strAscent;
            int centerBoxX, centerBoxY;
            String str = "Aucun image disponible";
            
            dim = getSize();
            fm = g.getFontMetrics();
            
            strWidth = fm.stringWidth(str);
            strAscent = fm.getAscent();
            
            centerBoxX = dim.width/2 - strWidth/2;
            centerBoxY = dim.height/2 + strAscent/2;
            g.drawString(str, centerBoxX, centerBoxY);
        }

    }
     
    public void first() { 
    	if (hasImages()) {
    		current = 0; 
        	this.repaint();
            imageChanged();
    	}
    }
    public void last() { 
    	if (hasImages()) {
    		current = images.length-1; 
    		this.repaint();
            imageChanged();
    	}
    }
    public void next() {
    	if (hasImages()) {
    		if (current != images.length-1) { 
    			current++; 
    			this.repaint();
    			imageChanged();
    		} 
    		else {
    			if (loop) {
    				first();
    			}
    		}

    	}
    }

    public void previous() { 
    	if (hasImages()) {
    		if (current != 0) {
    			current--; 
    			this.repaint();
                imageChanged();
    		}
    		else { 
    			if (loop) {
    				last();
    			}
    		}

		}
    }

    private Dimension getCurrentImageDimension() {
        File f = new File(pathName + "\\" + imagesFileName[current]);
        BufferedImage bimg = null;
        try {
            bimg = ImageIO.read(f);
            return new Dimension(bimg.getWidth(), bimg.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void imageChanged() {

        ImageChangedEvent event = new ImageChangedEvent(this, imagesFileName[current], getCurrentImageDimension());

        for(ImageChangedListener l : listeners) {
            l.imageChanged(event);
        }
    }

    public boolean hasImages() {
    	return current != -1;
    }
    
    public String getPathName() {
        return pathName;
    }
    
    public void setPathName(String newPath) {
		pathName = newPath;
    	createImages();
		this.repaint();
    }
    
    public boolean getLoop() { return loop; }
    public void setLoop(boolean nval) { loop = nval; }
    
    public int getMaxImages() { return maxImages; }
    public void setMaxImages(int nval) { maxImages = nval; }


    private LinkedList<ImageChangedListener> listeners = new LinkedList<>();
    public void addImageChangedListener(ImageChangedListener listener) {
        listeners.add(listener);
    }

    public void removeImageChangedListener(ImageChangedListener listener) {
        listeners.remove(listener);
    }
	        
}
