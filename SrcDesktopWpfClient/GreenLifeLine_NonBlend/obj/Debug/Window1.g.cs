﻿#pragma checksum "..\..\Window1.xaml" "{406ea660-64cf-4c82-b6f0-42d48172a799}" "D9AB90021B28176EDD87B30A6ADCA296"
//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:2.0.50727.3082
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

using GreenLifeLine_NonBlend;
using System;
using System.Diagnostics;
using System.Windows;
using System.Windows.Automation;
using System.Windows.Controls;
using System.Windows.Controls.Primitives;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Ink;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Media.Animation;
using System.Windows.Media.Effects;
using System.Windows.Media.Imaging;
using System.Windows.Media.Media3D;
using System.Windows.Media.TextFormatting;
using System.Windows.Navigation;
using System.Windows.Shapes;


namespace GreenLifeLine_NonBlend {
    
    
    /// <summary>
    /// TheWindow
    /// </summary>
    public partial class TheWindow : System.Windows.Window, System.Windows.Markup.IComponentConnector {
        
        
        #line 8 "..\..\Window1.xaml"
        internal System.Windows.Controls.Grid LayoutRoot;
        
        #line default
        #line hidden
        
        
        #line 10 "..\..\Window1.xaml"
        internal System.Windows.Controls.Canvas page5;
        
        #line default
        #line hidden
        
        
        #line 27 "..\..\Window1.xaml"
        internal System.Windows.Controls.Canvas page4;
        
        #line default
        #line hidden
        
        
        #line 45 "..\..\Window1.xaml"
        internal System.Windows.Controls.Canvas page3;
        
        #line default
        #line hidden
        
        
        #line 64 "..\..\Window1.xaml"
        internal System.Windows.Controls.Canvas page2;
        
        #line default
        #line hidden
        
        
        #line 76 "..\..\Window1.xaml"
        internal System.Windows.Shapes.Rectangle imageShroud;
        
        #line default
        #line hidden
        
        
        #line 103 "..\..\Window1.xaml"
        internal System.Windows.Controls.Canvas page1;
        
        #line default
        #line hidden
        
        
        #line 133 "..\..\Window1.xaml"
        internal System.Windows.Controls.Canvas page0;
        
        #line default
        #line hidden
        
        
        #line 140 "..\..\Window1.xaml"
        internal System.Windows.Controls.TextBox usernameTxtBox;
        
        #line default
        #line hidden
        
        
        #line 148 "..\..\Window1.xaml"
        internal System.Windows.Controls.TextBox passwordTxtBox;
        
        #line default
        #line hidden
        
        
        #line 156 "..\..\Window1.xaml"
        internal System.Windows.Controls.Button loginBtn;
        
        #line default
        #line hidden
        
        private bool _contentLoaded;
        
        /// <summary>
        /// InitializeComponent
        /// </summary>
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        public void InitializeComponent() {
            if (_contentLoaded) {
                return;
            }
            _contentLoaded = true;
            System.Uri resourceLocater = new System.Uri("/GreenLifeLine_NonBlend;component/window1.xaml", System.UriKind.Relative);
            
            #line 1 "..\..\Window1.xaml"
            System.Windows.Application.LoadComponent(this, resourceLocater);
            
            #line default
            #line hidden
        }
        
        [System.Diagnostics.DebuggerNonUserCodeAttribute()]
        [System.ComponentModel.EditorBrowsableAttribute(System.ComponentModel.EditorBrowsableState.Never)]
        [System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Design", "CA1033:InterfaceMethodsShouldBeCallableByChildTypes")]
        void System.Windows.Markup.IComponentConnector.Connect(int connectionId, object target) {
            switch (connectionId)
            {
            case 1:
            this.LayoutRoot = ((System.Windows.Controls.Grid)(target));
            return;
            case 2:
            this.page5 = ((System.Windows.Controls.Canvas)(target));
            return;
            case 3:
            
            #line 24 "..\..\Window1.xaml"
            ((System.Windows.Controls.Canvas)(target)).MouseDown += new System.Windows.Input.MouseButtonEventHandler(this.backButton_MouseDown);
            
            #line default
            #line hidden
            return;
            case 4:
            this.page4 = ((System.Windows.Controls.Canvas)(target));
            return;
            case 5:
            
            #line 41 "..\..\Window1.xaml"
            ((System.Windows.Controls.Canvas)(target)).MouseDown += new System.Windows.Input.MouseButtonEventHandler(this.backButton_MouseDown);
            
            #line default
            #line hidden
            return;
            case 6:
            this.page3 = ((System.Windows.Controls.Canvas)(target));
            return;
            case 7:
            
            #line 59 "..\..\Window1.xaml"
            ((System.Windows.Controls.Canvas)(target)).MouseDown += new System.Windows.Input.MouseButtonEventHandler(this.backButton_MouseDown);
            
            #line default
            #line hidden
            return;
            case 8:
            this.page2 = ((System.Windows.Controls.Canvas)(target));
            return;
            case 9:
            this.imageShroud = ((System.Windows.Shapes.Rectangle)(target));
            return;
            case 10:
            
            #line 98 "..\..\Window1.xaml"
            ((System.Windows.Controls.Canvas)(target)).MouseDown += new System.Windows.Input.MouseButtonEventHandler(this.backButton_MouseDown);
            
            #line default
            #line hidden
            return;
            case 11:
            this.page1 = ((System.Windows.Controls.Canvas)(target));
            return;
            case 12:
            
            #line 116 "..\..\Window1.xaml"
            ((System.Windows.Controls.Canvas)(target)).MouseDown += new System.Windows.Input.MouseButtonEventHandler(this.forwardButton_MouseDown);
            
            #line default
            #line hidden
            return;
            case 13:
            
            #line 128 "..\..\Window1.xaml"
            ((System.Windows.Controls.Canvas)(target)).MouseDown += new System.Windows.Input.MouseButtonEventHandler(this.backButton_MouseDown);
            
            #line default
            #line hidden
            return;
            case 14:
            this.page0 = ((System.Windows.Controls.Canvas)(target));
            return;
            case 15:
            this.usernameTxtBox = ((System.Windows.Controls.TextBox)(target));
            return;
            case 16:
            this.passwordTxtBox = ((System.Windows.Controls.TextBox)(target));
            return;
            case 17:
            this.loginBtn = ((System.Windows.Controls.Button)(target));
            
            #line 160 "..\..\Window1.xaml"
            this.loginBtn.Click += new System.Windows.RoutedEventHandler(this.forwardButton_MouseDown);
            
            #line default
            #line hidden
            return;
            }
            this._contentLoaded = true;
        }
    }
}
