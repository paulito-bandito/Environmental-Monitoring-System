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

namespace GreenLifeLine_NonBlend
{
    /// <summary>
    /// Interaction logic for Window1.xaml
    /// </summary>
    public partial class TheWindow : Window
    {
        protected EmsSyndicator.EmsSyndicatorClient emsSyndicator = new EmsSyndicator.EmsSyndicatorClient();
        protected user currentUser = null;
        protected List<AreaComponent> listOfCustomComponents = new List<AreaComponent>();   
        
        
        public TheWindow()
        {
            InitializeComponent();

        }

        private void initPageTwo()
        {
            domain newDomain = new domain();
            newDomain.id = 1;
            environment[] listOfEnvs = emsSyndicator.getEnvironments(newDomain);

            // Place them on page 2
            for(int i=0; i<listOfEnvs.Length; i++)
            {
                environment thisEnv = listOfEnvs[i];               

                //
                // Create component
                //

                AreaComponent thisComp = new AreaComponent();
                thisComp.TitleText = thisEnv.title;
                thisComp.emsSyndicator = emsSyndicator;
                thisComp.Name = "Comp" + thisEnv.id;
                Canvas.SetLeft(thisComp, (double)thisEnv.x);
                Canvas.SetTop(thisComp, (double)thisEnv.y);
                //
                //  Set event listeners
                //
                thisComp.MouseEnter += this.CustomComponent_MouseEnter;
                thisComp.MouseLeave += this.CustomComponent_MouseLeave;
                thisComp.MouseDoubleClick += this.CustomComponent_MouseClick;
                //
                // Get status of this environment
                //
                sensorRange[] listOfSensorRanges = emsSyndicator.getCurrentSensorRanges(thisEnv);
                thisComp.SensorRanges = listOfSensorRanges;
                //
                // Get capture devices
                //
                aCaptureData[] listOfDataCapturers = emsSyndicator.getDataCapturers(thisEnv);
                thisComp.DataCapturerers = listOfDataCapturers;
                //
                //  Add it to the list so we can manipulate it later.
                //
                listOfCustomComponents.Add(thisComp);
                //
                // Place it on the stage.
                //
                page2.Children.Add(thisComp);
                
            }
            
        }

        private void CustomComponent_MouseClick(object sender, MouseEventArgs e)
        {
            forwardButton_MouseDown(sender, null);
        }
        private void CustomComponent_MouseEnter(object sender, MouseEventArgs e)
        {
            //
            // Set visibility
            //
            imageShroud.Visibility = Visibility.Visible;

            for(var i= 0; i< listOfCustomComponents.Count; i++)
            {
                AreaComponent currentComp = listOfCustomComponents[i];
                if( currentComp != sender)
                {
                    currentComp.Visibility = Visibility.Hidden;
                }
            }
        }

        private void CustomComponent_MouseLeave(object sender, MouseEventArgs e)
        {
            //
            // Set visibility
            //
            imageShroud.Visibility = Visibility.Collapsed;

            for(var i= 0; i< listOfCustomComponents.Count; i++)
            {
                AreaComponent currentComp = listOfCustomComponents[i];
                if( currentComp != sender)
                {
                    currentComp.Visibility = Visibility.Visible;
                }
            }
        }

        private void forwardButton_MouseDown(object sender, Object e)
        {
            // get the name of the sender, get the last digit
            FrameworkElement parentObj = (FrameworkElement)(sender as FrameworkElement).Parent;
            String nameOfSender = parentObj.Name;

            switch (nameOfSender)
            {
                case "page0":
                    page0.Visibility = Visibility.Collapsed;
                    page1.Visibility = Visibility.Visible;
                    break;
                case "page1":                    
                    page1.Visibility = Visibility.Collapsed;
                    page2.Visibility = Visibility.Visible;
                    initPageTwo();
                    break;
                case "page2":
                    page2.Visibility = Visibility.Collapsed;
                    page3.Visibility = Visibility.Visible;
                    break;
                case "page3":
                    page3.Visibility = Visibility.Collapsed;
                    page4.Visibility = Visibility.Visible;
                    break;
                case "page4":
                    page4.Visibility = Visibility.Collapsed;
                    page5.Visibility = Visibility.Visible;
                    break;
                case "page5":
                    // break.
                    break;
                default:
                    break;
            }

        }

        private void backButton_MouseDown(object sender, MouseButtonEventArgs e)
        {
            // get the name of the sender, get the last digit
            FrameworkElement parentObj = (FrameworkElement)(sender as FrameworkElement).Parent;
            String nameOfSender = parentObj.Name;
            
            switch (nameOfSender)
            {
                case "page0":
                    // do nothing.
                    break;
                case "page1":
                    page0.Visibility = Visibility.Visible;
                    page1.Visibility = Visibility.Collapsed;
                    break;
                case "page2":
                    page2.Visibility = Visibility.Collapsed;
                    page1.Visibility = Visibility.Visible;
                    break;
                case "page3":
                    page3.Visibility = Visibility.Collapsed;
                    page2.Visibility = Visibility.Visible;
                    break;
                case "page4":
                    page4.Visibility = Visibility.Collapsed;
                    page3.Visibility = Visibility.Visible;
                    break;
                case "page5":
                    page5.Visibility = Visibility.Collapsed;
                    page4.Visibility = Visibility.Visible;
                    break;
                default:
                    break;
            }

        }
    }
}
