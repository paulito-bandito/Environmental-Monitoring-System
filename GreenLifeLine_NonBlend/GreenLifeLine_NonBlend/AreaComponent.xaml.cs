using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using GreenLifeLine_NonBlend.EmsSyndicator;
using System.Net;
using System.IO;

namespace GreenLifeLine_NonBlend
{
    /// <summary>
    /// Interaction logic for UserControl1.xaml
    /// </summary>
    public partial class AreaComponent : UserControl
    {
        private SolidColorBrush _RedColorConst = new SolidColorBrush(Colors.Red);
        private SolidColorBrush _BlueColorConst = new SolidColorBrush(Colors.Blue);
        //private String _StatusText = "";

        public static DependencyProperty TitleTextProperty = DependencyProperty.Register(
            "TitleText", typeof(String), typeof(AreaComponent));

        public static DependencyProperty StatusTextProperty = DependencyProperty.Register(
            "StatusText", typeof(String), typeof(AreaComponent));

        public static DependencyProperty SensorRangesProperty = DependencyProperty.Register(
            "SensorRanges", typeof(sensorRange[]), typeof(AreaComponent));

        public static DependencyProperty DataCapturerersProperty = DependencyProperty.Register(
            "DataCapturerers", typeof(aCaptureData[]), typeof(AreaComponent));

        public static DependencyProperty emsSyndicatorProperty = DependencyProperty.Register(
            "emsSyndicator", typeof(EmsSyndicator.EmsSyndicatorClient), typeof(AreaComponent));


        public EmsSyndicatorClient emsSyndicator
        {
            get
            {
                return (EmsSyndicatorClient)GetValue(emsSyndicatorProperty);
            }
            set
            {
                SetValue(emsSyndicatorProperty, value);
            }
        }

        public aCaptureData[] DataCapturerers
        {
            get
            {
                return (aCaptureData[])GetValue(DataCapturerersProperty);
            }
            set
            {
                SetValue(DataCapturerersProperty, value);
            }
        }

        public sensorRange[] SensorRanges
        {
            get
            {
                return (sensorRange[])GetValue(SensorRangesProperty);
            }
            set
            {
                SetValue(SensorRangesProperty, value);
            }
        }

        public String TitleText
        {
            get
            {
                return (String)GetValue(TitleTextProperty);
            }
            set
            {
                SetValue(TitleTextProperty, value);
            }
        }

        public String StatusText
        {
            get
            {
                return (String)GetValue(StatusTextProperty);
            }
            set
            {
                SetValue(StatusTextProperty, value);
            }
        }

        public SolidColorBrush IconFillColor
        {
            get
            {
                Boolean isSerious = false;
                for (int i = 0; i < SensorRanges.Length; i++ )
                {
                    if (SensorRanges[i].priority > 0)
                    {
                        isSerious = true;
                        break;
                    }
                }

                // Check to see if it was serious
                if (isSerious)
                {
                    return _RedColorConst;
                }
                    
                
                return _BlueColorConst;
                
            }
            set
            {
                //SetValue(IconFillColorProperty, value);
            }
        }
        
       

        public AreaComponent()
        {
            InitializeComponent();
            this.DataContext = this;
        }

        private void ParentContainer_MouseEnter(object sender, MouseEventArgs e)
        {
            //
            // Remove all children
            //
            DeviceListing.Children.Clear();

            //
            // Add new children
            //
            if (DataCapturerers != null)
            {
                String displayText = "";

                //
                // Get current data
                //
                for (int i = 0; i < DataCapturerers.Length; i++)
                {
                    aCaptureData thisDevice = DataCapturerers[i];
                    
                    aData thisData = emsSyndicator.getCurrentData(thisDevice);
                    displayText = buildText(thisData, thisDevice);

                    if (thisData is snapshot)
                    {
                        Image img = downloadImages(thisData as snapshot);


                        if (img != null)
                        {
                            DeviceListing.Children.Add(img);
                        }
                    }

                    if (displayText != "")
                    {
                        TextBlock newText = new TextBlock();
                        newText.Text = displayText;
                        DeviceListing.Children.Add(newText);
                    }
                    
                }
            }
            
            
            DeviceListing.Visibility = Visibility.Visible;
        }

        private Image downloadImages(snapshot aSnapshot)
        {
            Image img = new Image();  //Image control of wpf

            try
            {
                WebRequest request = WebRequest.Create(aSnapshot.uri);

                // If required by the server, set the credentials.
                //NetworkCredential myCred = new NetworkCredential("username", "password");
                //request.Credentials = myCred;
                request.Timeout = 60000;  // one minute

                // Get the response.
                //label1.Text = "Getting Response";
                //Application.DoEvents();
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                //label1.Text = "Got Response";

                // Get the stream containing content returned by the server.
                Stream dataStream = response.GetResponseStream();
                //label1.Text = "Got ResponseStream";
                //Application.DoEvents();

                // Open the stream using a StreamReader for easy access.
                StreamReader reader = new StreamReader(dataStream);

                // Read the content.
                BinaryReader myBinReader = new BinaryReader(dataStream);
                byte[] byteMe = myBinReader.ReadBytes((int)response.ContentLength);
                //label1.Text = "Got Bytes";
                //Application.DoEvents();

                // Create and return an image.
                BitmapImage bi = new BitmapImage();
                bi.BeginInit();
                bi.CreateOptions = BitmapCreateOptions.None;
                bi.CacheOption = BitmapCacheOption.Default;
                bi.StreamSource = new MemoryStream(byteMe);
                bi.EndInit();

                img.Source = bi;


            }
            catch (Exception exp)
            {
                TextBlock newText = new TextBlock();
                newText.Text = "Exception downloading image " + aSnapshot.uri + ":\n\n" + exp.Message;
                DeviceListing.Children.Add(newText);
            }
            
            

            
            return img;

        }

        private String buildText(aData a_data, aCaptureData a_captureDevice)
        {
            String returnString = "";

            if ((a_data != null) && (a_captureDevice != null))
            {
                if (a_captureDevice is camera)
                {
                    camera thisCam = (camera)a_captureDevice;
                    snapshot thisSnap = (snapshot)a_data;
                    returnString = thisCam.title + ", Camera: " + thisSnap.uri;
                }
                else if (a_captureDevice is sensorChannel)
                {
                    sensorChannel thisSenChan = (sensorChannel)a_captureDevice;
                    measurement thisMeasure = (measurement)a_data;
                    returnString = thisSenChan.title + ", " + thisSenChan.sensorTypeName + ", " + thisMeasure.measurement1 + " " + thisSenChan.sensorMetricTypeName;
                }
                else if (a_captureDevice is noteTaker)
                {
                    noteTaker thisNoteTaker = (noteTaker)a_captureDevice;
                    note thisNote = (note)a_data;
                }
            }

            return returnString;

        }

        private void ParentContainer_MouseLeave(object sender, MouseEventArgs e)
        {
            DeviceListing.Visibility = Visibility.Collapsed;
        }
    }
}
