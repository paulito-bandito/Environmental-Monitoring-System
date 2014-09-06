/*
 * vid2jpg.java 01.01b 17.12.2006
 *  
 * Copyright (c) 2003-2006 ExAct Futures. All Rights Reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 * and associated documentation files (the "Software"), to deal in the Software without 
 * restriction, including without limitation the rights to use, copy, modify, merge, publish, 
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or 
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING 
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * Except as contained in this notice, the name of the author shall not be used in 
 * advertising or otherwise to promote the sale, use or other dealings in this Software without 
 * prior written authorization from the author.
 * 
 */
package com.ems.datalogging.data.jmf;

//import DataObjects.DAOFactory;
import com.ems.datalogging.business.CameraTO;
import com.ems.datalogging.business.SnapshotTO;
import com.ems.datalogging.data.db.EmsDataLoggingDbDAOFactory;
import com.ems.datalogging.data.utils.DAOFactory;
import java.io.*;
import java.awt.*;
import javax.media.*;
import javax.media.control.*;
import javax.media.format.*;
import javax.media.protocol.*;
import java.awt.image.*;
import com.sun.media.codec.video.jpeg.NativeEncoder;

// If you prefer to amend the sun example FrameAccess.java then to get every frame include
import java.awt.font.TextAttribute;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
// in process(Buffer in, Buffer out). This avoids dropping frames whilst not removing likes
import javax.imageio.ImageIO;
// of end of media events.

/**
 * This class will take pictures for a given media locator string which will get
 * turned into a {@link javax.media.MediaLocator} object. For example you might
 * be inputting a string like "vfw://0" for a capture device on the Java Media Framework
 * <br/>
 * This object has been modified from the orginal GUI form to work as an invisible
 * component, and to conform to the Factory Pattern I am using for my Data Access
 * Objects of which this qualifies.
 * <br/>
 * This entails that I have moved the constructor information out into the 
 * initSnapshotterDAO () method. The constructor takes a {@link DAOFactory} which
 * will have a property to specficy to what directory it should write its images  
 * <br/>
 * More about the init () method: 
 * <br/>
 * This object has 2 modes: automatic, and manual. 
 * <ul>
 *  <li>
 *      When it is in automatic mode, 
 *      when you pass a true for the picMode in the constructor, it will write to disk
 *      every frame that comes its way from the push-processor that it is using.
 *  </li>
 *  <li>
 *      When it is in manual mode, you can call the takePicture() method to just
 *      write to disk a single frame in time.
 *  </li>
 * <ul>
 * <br/>
 * 
 * @author Unknown, but he has an email address and website (emsil: andyble@ntlworld.com)
 * @see http://www.exactfutures.com/index01.htm
 * @author Modified by Paul Walter
 */
public class NetworkCamera extends Frame implements ControllerListener
{
    
        // VARS ----------------------------------------------------------------
    
	Processor       p;
	Object          waitObj = new Object();
	boolean         stateOK = true;
	DataSourceHandler handler;
	imgPanel        currPanel;
        int             imgWidth;
        int             imgHeight;
	DirectColorModel dcm = new DirectColorModel(32, 0x00FF0000, 0x0000FF00, 0x000000FF);
	MemoryImageSource sourceImage;
        Image           outputImage;
	String          sep = System.getProperty("file.separator");
	NativeEncoder   e;
	int[]           outvid;
	int             startFr = 1;
        int             endFr = 1000;
        int             countFr = 0;
	boolean         sunjava=true;

	// PAUL, you are modifying this starting here
	boolean takeAPicture = true;    // WILL OR WONT ALLOW PICTURES TO BE TAKEN
	boolean pictureMode = false;	// TRUE MEANS IT IS IN AUTOMATIC MODE WHICH WILL GO EVERY TIME IT RECIEVES A FRAME
        private static final String DAO_FACTORY_INSTACE_NAME = "ems";
        private static final boolean DEBUG = false;
        private String snapshotDirectory = "";
        boolean debug = false;
        boolean isVisible = false;
        
        // BUFFER FOR INCOMING SNAPSHOT OBJECTS
        Vector <SnapshotTO> snapshotBuffer = new Vector<SnapshotTO>();
        
        // TEXT ELEMENTS FOR THE FINAL IMAGE
        private String  FONT_TITLE = "";
        private Color   FONT_COLOR = Color.yellow; // THIS WILL BE THE COLOR OF THE WORDS ON THE PICTURE
        private String  FONT_NAME = "Arial";
        private int     FONT_STYLE = Font.BOLD;
        private int     FONT_SIZE  = 12;
        private int     FONT_X_POS = 20;
        private int     FONT_Y_POS = 20;
        private EmsDataLoggingDbDAOFactory daoFactory;
        
        // REFERENCE TO THE CAMERA OBJECT THAT THIS IS PART OF
        private CameraTO cameraTO;

        
        // CONSTRUCTORS --------------------------------------------------------
                
        /**
         * Default constructor so it can be tested from its own main method, in 
         * order to be tested as a discrete component
         */
        public NetworkCamera()
	{
            // FOR TESTING, PUT IT IN C
            snapshotDirectory = "C:"+sep;
        }
	/**
         * This constructor uses a DAOFactory object which will specify, upon
         * reading from the dao.properties file somewhere in your classpath, what
         * folder it should be writing the incoming frame data too.
         * @param thisFactory
         */
	public NetworkCamera(String a_snapshotDirectory )
	{
		snapshotDirectory = a_snapshotDirectory;
                
        } // END OF CONSTRUCTOR

        
        
        // GETTERS AND SETTERS -------------------------------------------------
       
       
        /**
         * This will init the snapshotter. 
         * <ul>
         *  <li>mediaLocationStr is where the video capture device is located</li>
         *  <li>isInAutomaticMode will dictate if every frame that comes out of the buffer is rendered into a file</li>
         *  <li>isDebugMode if true will verbosely indicate its internal dialog</li>
         *  <li>canBeSeen whether or not this should be visible to the end user, if so it will display as a form</li>
         * </ul>
         * @param mediaLocationStr
         * @param isInAutomaticMode
         * @param isDebugMode
         * @param canBeSeen
         */
        public void initSnapshotterDAO(CameraTO aCameraTO, boolean isDebugMode, boolean canBeSeen)
        {
                cameraTO = aCameraTO;
                FONT_TITLE = aCameraTO.getTheTitle();
                debug = isDebugMode;
                isVisible = canBeSeen;
                pictureMode = false;        // This will alays be false while this is a Data Access Object
		if(pictureMode == false)
		{
			// THEN SET takePicture equal to false, so it will wait for us to ask it to take a picture.
                        takeAPicture = false;
		}
		
		MediaLocator ml;
                String args = aCameraTO.getNetworkAddress();

		if((ml = new MediaLocator(args)) == null)
		{
			System.err.println("Cannot build media locator from: " + args);
			takeAPicture = false;
		}

		if(!open(ml))
		{
			System.err.println("Failed to open media source");
		}
        }
        
        // ACTIONS -------------------------------------------------------------
        
	/**
	* Given a MediaLocator, create a processor and start
	*/
	private boolean open(MediaLocator ml)
	{
		if(debug)System.out.println("Create processor for: " + ml);
		
		try
		{
			p = Manager.createProcessor(ml);
		}
		catch (Exception e)
		{
			System.err.println("Failed to create a processor from the given media source: " + e);
			return false;
		}
		
		p.addControllerListener(this);
		
		// Put the Processor into configured state.
		p.configure();
		if(!waitForState(p.Configured))
		{
			System.err.println("Failed to configure the processor.");
			return false;
		}
		
		// Get the raw output from the Processor.
		p.setContentDescriptor(new ContentDescriptor(ContentDescriptor.RAW));
		
		TrackControl tc[] = p.getTrackControls();
		if(tc == null)
		{
			System.err.println("Failed to obtain track controls from the processor.");
			return false;
		}
		
		TrackControl videoTrack = null;
		for(int i = 0; i < tc.length; i++)
		{
			if(tc[i].getFormat() instanceof VideoFormat)
			{
				tc[i].setFormat(new RGBFormat(null, -1, Format.byteArray, -1.0F, 24, 3, 2, 1));
				videoTrack = tc[i];
			}
			else
			tc[i].setEnabled(false);
		}		
		if(videoTrack == null)
		{
			System.err.println("The input media does not contain a video track.");
			return false;
		}		
		if(debug)System.out.println("Video format: " + videoTrack.getFormat());

		p.realize();
		if(!waitForState(p.Realized))
		{
			System.err.println("Failed to realize the processor.");
			return false;
		}
		
		// Get the output DataSource from the processor and set it to the DataSourceHandler.
		DataSource ods = p.getDataOutput();
		handler = new DataSourceHandler();
		try
		{
			handler.setSource(ods);	// also determines image size
		}
		catch(IncompatibleSourceException e)
		{
			System.err.println("Cannot handle the output DataSource from the processor: " + ods);
			return false;
		}
		
                // MAKE THE COMPONENT VISIBLE    
                setLayout(new FlowLayout(FlowLayout.LEFT));
                currPanel = new imgPanel(new Dimension(imgWidth,imgHeight));
                add(currPanel);
                pack();
                setLocation(100,100);
                
                if(isVisible)
                {                    
                    setVisible(true);
                }
		
		
		handler.start();
		
		// Prefetch the processor.
		p.prefetch();
		
		if(!waitForState(p.Prefetched))
		{
			System.err.println("Failed to prefetch the processor.");
			return false;
		}

		// Start the processor
		//p.setStopTime(new Time(20.00));
		p.start();
		
		return true;
	}

	/**
	* Sets image size, and title placement
	*/
	private void imageProfile(VideoFormat vidFormat)
	{
		if(debug)System.out.println("Push Format "+vidFormat);
		Dimension d = (vidFormat).getSize();
		if(debug)System.out.println("Video frame size: "+ d.width+"x"+d.height);
		imgWidth=d.width;
		imgHeight=d.height;
                
                // SET TITLE POSITION, WHICH IS DEPENDENT ON THE IMAGE SIZE
                //FONT_X_POS = 10;
                //FONT_Y_POS = imgHeight - 20;
                
	}

	/**
	* Called on each new frame buffer
	*/
	private void useFrameData(Buffer inBuffer)
	{
            // MAKE SURE THAT WE SHOULD TAKE A PICTURE
            if(takeAPicture){
                
		countFr++;
		if(countFr<startFr || countFr>endFr)return;

		try
		{
                     //PULL A SNAPSHOT FROM THE BUFFER
                    SnapshotTO thisShot = snapshotBuffer.firstElement();

                    if(thisShot != null)
                    {
                        // REMOVE THAT REFERENCE, BECAUSE THIS IS A FIRST IN LAST OUT COLLECTION.
                        snapshotBuffer.remove(thisShot);
                        
			printDataInfo(inBuffer);

                        
			if(inBuffer.getData()!=null)	// vfw://0 can deliver nulls
			{
				if(outvid==null)outvid = new int[imgWidth*imgHeight];
				outdataBuffer(outvid,(byte[])inBuffer.getData());
				setImageAndText(outvid, thisShot);

				// name so OS more likely to sort correctly if doing jpg2vid
				//String paddedname = "00000000000000000000"+inBuffer.getTimeStamp();
				//String sizedname = paddedname.substring(paddedname.length()-20);
                                
                               
                                    
                                   
                                    // CREATE A NAME TO SAVE THE IMAGE ASelse{
                                        
                                    String theFileName = thisShot.getFileName();

                                    if(sunjava)
                                    {
                                        //System.err.println("writing file with Java");
                                        saveJpeg(outputImage,theFileName);		
                                    }
                                    else
                                    {
                                        //System.err.println("writing file...");
                                        if(e==null)initJpeg((RGBFormat)inBuffer.getFormat());
                                        byte[] b = fetchJpeg(inBuffer);
                                        String filename = theFileName;
                                        makeFile(filename, b);
                                    }
                               
			} // END OF IF STATMENT FOR CHECKING THE BUFFER
                     }else{
                                // THERE ISN"T A SNAPSHOT REFERENCE IN THE BUFFER, WE CAN"T TAKE A PHYSICAL PICTURE
                                System.err.println("There isn't anything in the buffer to make a file with");
                     } // END OF IF STATEMENT CHECKING TO SEE THAT THERE IS A SNAPSHOT IN THE SNAPSHOT BUFFER
		} // END OF TRY CATCH
		catch(Exception e){System.err.println("IO exception writing image to file: "+ e);}
            }
            
            // CHECK TO SEE IF WE ARE IN AUTOMATIC MODE, IF NOT THEN SET THE takeAPicture BOOLEAN TO FALSE.
            if(!pictureMode){
                takeAPicture = false;
            }
	} // END OF IF STATEMENT (takeAPicture)
        
//        private String createFileName(SnapshotTO aSnapshot)
//        {
//            // CONVERT TIMESTAMP TO "Time in Millis" BECAUSE IT IS FILE NAME SAFE (It doesn't have any specirfal characters like ':')
//            long millis = aSnapshot.getTimestamp().getTime();                                    
//            return aSnapshot.getCameraId() + "_" + aSnapshot .getId()+"_" + millis;
//        }
        /**
         * Allows incoming frame data to be written to disk. If this object's 
         * "pictureMode" is false, meaning that it shouldn't write all incoming
         * frame data to disk automatically, it will only take one frame and write
         * it to disk.
         */
        public void takeAPicture(SnapshotTO thisSnapshot) throws Exception
        {   
            if(cameraTO!=null)
            {
                snapshotBuffer.add(thisSnapshot);
                takeAPicture = true;
            }else{
                throw new Exception("This object has not been initialized. You must call the init method before you take a picture.");
            }
           
        }
	/**
	* Tidy on finish
	*/
	public void tidyClose()
	{
           
		handler.close();
		p.close();
		if(e!=null)e.close();
		if(isVisible)dispose();	// close the frame
		if(debug)System.out.println("Sources closed");
	}

        
	/**
	* Draw image to AWT frame, and apply some text.
	*/
	private void setImageAndText(int[] outpix, SnapshotTO aSnapshot)
	{
                
		if(sourceImage==null)sourceImage = new MemoryImageSource(imgWidth, imgHeight, dcm, outpix, 0, imgWidth);
		 
                Image resultImage = this.createImage(imgWidth,imgHeight);
                Graphics resultGraphics = resultImage.getGraphics();
		resultGraphics.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
		
                
                outputImage = createImage(sourceImage);

                // Draw frame and text and any other graphics 
                
                resultGraphics.drawImage(outputImage,0,0,this);
                
                // SET TITLE (at the top of the image)
                resultGraphics.setColor(Color.BLACK);               
                resultGraphics.drawString(FONT_TITLE,FONT_X_POS, FONT_Y_POS); // CREATE DROP SHADOW
                resultGraphics.setColor(FONT_COLOR);
                resultGraphics.drawString(FONT_TITLE,FONT_X_POS -1, FONT_Y_POS -1); // CREATE TOP COLOR
                
                Map<TextAttribute, Object> map = new Hashtable<TextAttribute, Object>();
                map.put(TextAttribute.BACKGROUND, Color.DARK_GRAY);
                
                // SET DATE (below the title)
                String formattedDate = aSnapshot.getFormattedDateString();
                resultGraphics.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE -2));                
                resultGraphics.setColor(Color.BLACK);
                resultGraphics.drawString(formattedDate,FONT_X_POS, FONT_Y_POS + 9); // CREATE DROP SHADOW                
                resultGraphics.setColor(FONT_COLOR);
                resultGraphics.drawString(formattedDate,FONT_X_POS -1, FONT_Y_POS +10); // CREATE TOP COLOR
                outputImage = (Image)resultImage;
                
                // SET HOURS (below the date)
                String formattedTime = aSnapshot.getFormattedHourString();
                resultGraphics.setColor(Color.BLACK);
                resultGraphics.drawString(formattedTime,FONT_X_POS, FONT_Y_POS + 19); // CREATE DROP SHADOW                
                resultGraphics.setColor(FONT_COLOR);
                resultGraphics.drawString(formattedTime,FONT_X_POS -1, FONT_Y_POS +20); // CREATE TOP COLOR
                outputImage = (Image)resultImage;
                
                //System.out.println("setting text");
                
                
		// MAKE THE COMPONENT VISIBLE                
                if(isVisible)
                {
                    //currPanel.setImage(resultImage);                    
                    currPanel.setImage(outputImage);	
                }
                
	}
        
	/**
	* Block until the processor has transitioned to the given state
	*/
	private boolean waitForState(int state)
	{
		synchronized(waitObj)
		{
			try
			{
				while(p.getState() < state && stateOK)
				waitObj.wait();
			}
			catch (Exception e)
			{
			}
		}
		return stateOK;
	}

	/**
	* Controller Listener.
	*/
	public void controllerUpdate(ControllerEvent evt)
	{
		if(evt instanceof ConfigureCompleteEvent ||	evt instanceof RealizeCompleteEvent || evt instanceof PrefetchCompleteEvent)
		{
			synchronized(waitObj)
			{
				stateOK = true;
				waitObj.notifyAll();
			}
		}
		else
		if(evt instanceof ResourceUnavailableEvent)
		{
			synchronized(waitObj)
			{
				stateOK = false;
				waitObj.notifyAll();
			}
		}
		else
		if(evt instanceof EndOfMediaEvent || evt instanceof StopAtTimeEvent)
		{
			tidyClose();
		}
	}

	/**
	* Prints frame info
	*/
	private void printDataInfo(Buffer buffer)
    {
            if(debug){
                System.out.println(" Time stamp: " + buffer.getTimeStamp());
		System.out.println(" Time: " + (buffer.getTimeStamp()/10000000)/100f+"secs");
		System.out.println(" Sequence #: " + buffer.getSequenceNumber());
		System.out.println(" Data length: " + buffer.getLength());
		System.out.println(" Key Frame: " + (buffer.getFlags()==Buffer.FLAG_KEY_FRAME)+" "+buffer.getFlags());
            }
		
    }

	/**
	* Converts buffer data to pixel data for display
	*/
	public void outdataBuffer(int[] outpix, byte[] inData)	// could use JavaRGBConverter
	{
		boolean flip=false;
		{
			int srcPtr = 0;
			int dstPtr = 0;
			int dstInc = 0;
			if(flip)
			{
				dstPtr = imgWidth * (imgHeight - 1);
				dstInc = -2 * imgWidth;
			}
			
			for(int y = 0; y < imgHeight; y++)
			{
				for(int x = 0; x < imgWidth; x++)
				{
					byte red = inData[srcPtr + 2];
					byte green = inData[srcPtr + 1];
					byte blue = inData[srcPtr];
					
					int pixel = (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff) << 0;
					outpix[dstPtr] = pixel;
					srcPtr += 3;
					dstPtr += 1;
				}
				dstPtr += dstInc;
			}
		}
		Thread.yield();
	}
	

	/**
	* Jpeg encoder and file writer
	*/
	public void saveJpeg(Image img, String filename)
	{
		BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
		//
                // put the image into the buffered image.
                //
                Graphics g = bi.getGraphics();
		g.drawImage(img, 0, 0, this); 
		
		//
                // Try to write the file.
                //		
		try 
		{ 
                    File outputfile = new File(snapshotDirectory+filename);
                    ImageIO.write(bi, "jpg", outputfile);
		}
		catch (java.io.IOException io) 
		{
			System.err.println("IOException" + io.getMessage()); 
		}
	}

	/**
	* Inits a jpeg encoder - if using MS JVM - but frame sizes have to be multiples of 8
	*/
	private void initJpeg(RGBFormat vfin) throws Exception
	{
		float val=0.6F;

		int widpx=imgWidth;
		int hgtpx=imgHeight;

		// This encoder need multiples of 8 - use another if a problem
		if(widpx % 8 != 0 || hgtpx % 8 != 0)
		{
			if(debug)System.out.println("Width = "+imgWidth+" "+"Height = "+imgHeight);
			throw new Exception("Image sizes not /8");
		}

		VideoFormat vfout = new VideoFormat("jpeg", new Dimension(widpx,hgtpx), widpx * hgtpx * 3, Format.byteArray, -1F);
		
		e = new NativeEncoder();
		e.setInputFormat(vfin);
		e.setOutputFormat(vfout);
		e.open();

		Control cs[] = (Control[])e.getControls();
		for (int i = 0; i < cs.length; i++)
		{
			if (cs[i] instanceof QualityControl)
			{			
				QualityControl qc = (QualityControl)cs[i];
				qc.setQuality(val);
				break;			
			}
		}
	}

	/**
	* Fetches a jpeg from buffer data - if using MS JVM
	*/
	private byte[] fetchJpeg(Buffer inBuffer) throws Exception
	{
		Buffer outBuffer=new Buffer();	// may need new to keep threadsafe if extended
		int result = e.process(inBuffer, outBuffer);
		int lengthF = outBuffer.getLength();
		byte[] b = new byte[lengthF];
		System.arraycopy(outBuffer.getData(), 0, b, 0, lengthF);
		return b;
	}

	/**
	* Saves jpeg to file
	*/
	public void makeFile(String filename, byte[] b)
	{
		BufferedOutputStream fw=null;
		try
		{
			fw = new BufferedOutputStream(new FileOutputStream("images"+sep+filename));
			fw.write(b, 0, b.length);fw.close();
			
			if(debug)System.out.println(" *** Created file "+filename);
		}
		catch(IOException e ){System.err.println("makeFile "+e);}
	}

	/***************************************************
	* Inner classes
	***************************************************/

	/**
	* A DataSourceHandler class to read from a DataSource and displays
	* information of each frame of data received.
	*/
	class DataSourceHandler implements BufferTransferHandler
	{
		DataSource source;
		PullBufferStream pullStrms[] = null;
		PushBufferStream pushStrms[] = null;
		Buffer readBuffer;

		/**
		* Sets the media source this MediaHandler should use to obtain content.
		*/
		private void setSource(DataSource source) throws IncompatibleSourceException
		{
			// Different types of DataSources need to handled differently.
			if(source instanceof PushBufferDataSource) 
			{
				pushStrms = ((PushBufferDataSource) source).getStreams();
				
				// Set the transfer handler to receive pushed data from the push DataSource.
				pushStrms[0].setTransferHandler(this);

				// Set image size
				imageProfile((VideoFormat)pushStrms[0].getFormat());
			}
			else
			if(source instanceof PullBufferDataSource)
			{
				System.err.println("PullBufferDataSource!");
			
				// This handler only handles push buffer datasource.
				throw new IncompatibleSourceException();
			}
			
			this.source = source;
			readBuffer = new Buffer();
		}
		
		/**
		* This will get called when there's data pushed from the PushBufferDataSource.
		*/
		public void transferData(PushBufferStream stream)
		{
                    
			try
			{
				stream.read(readBuffer);
			}
			catch(Exception e)
			{
				System.err.println("Error transfering data in the DataHandler inner class of SnapshotterDAO" + e);
				return;
			}

			// Just in case contents of data object changed by some other thread
			Buffer inBuffer = (Buffer)(readBuffer.clone());

			// Check for end of stream
			if(readBuffer.isEOM())
			{
				if(debug)System.out.println("End of stream");
				return;
			}

			// Do useful stuff or wait
			useFrameData(inBuffer);
		}

		public void start()
		{
			try{source.start();}catch(Exception e){System.err.println("Error starting: " + e);}
		}
		
		public void stop()
		{
			try{source.stop();}catch(Exception e){System.err.println("Error stopping: " +e);}
		}	
		
		public void close(){stop();}
		
		public Object[] getControls()
		{
			return new Object[0];
		}
		
		public Object getControl(String name)
		{
			return null;
		}
	}
	
	/**
	* Panel extension
	*/
	class imgPanel extends Panel
	{
		Dimension size;
		public Image myimg = null;

		public imgPanel(Dimension size)
		{
			super();
			this.size = size;
		}

		public Dimension getPreferredSize()
		{
			return size;
		}

		public void update(Graphics g)
		{
			paint(g);
		}

		public void paint(Graphics g)
		{
			if (myimg != null)
			{
				g.drawImage(myimg, 0, 0, this);
			}
		}

		public void setImage(Image img)
		{
			if(img!=null)
			{
				this.myimg = img;
				update(getGraphics());
			}
		}
	}
       
        /**
	* Static main method
	*/
//	public static void main(String[] args)
//	{
//		if(args.length == 0)
//		{
//			System.out.println("No media address.");
//			//new vid2jpg("file:testcam04.avi");	// or alternative "vfw://0" if webcam
//			SnapshotterDAO thisSnapper = new SnapshotterDAO();
//                        thisSnapper.initSnapshotterDAO("vfw://0", true, false, true);
//                        try {
//                            // WAIT 3 SECONDS TO SEE IF THE TAKE A PICTURE MECHANISM WORKS
//                            Thread.sleep(3000);
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(SnapshotterDAO.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        thisSnapper.takeAPicture();
//                        //thisSnapper.dispose();
//                        //System.exit(-1);
//                        try {
//                            // WAIT 3 SECONDS TO SEE IF THE TAKE A PICTURE MECHANISM WORKS
//                            Thread.sleep(2000);
//                            
//                            // THIS IS THE ONLY WAY TO END THIS CLASS, IT IS TENACIOUS OTHERWISE
//                            //thisSnapper.tidyClose();
//                        }  catch (InterruptedException ex) {
//                            Logger.getLogger(SnapshotterDAO.class.getName()).log(Level.SEVERE, null, ex);
//                        } catch (Throwable ex) {
//                            Logger.getLogger(SnapshotterDAO.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                        
//		}
//		else
//		{
////			String path = args[0].trim();
////			System.out.println(path);
////			new vid2jpg(path, false);
//		}
//	} // END OF MAIN METHOD

}
