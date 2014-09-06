var login={
            status:false,
	    data:{}
	};




$(document).ready(
    function() {
var url = window.location.toString();
        if(url.match(/maximintegrated.com\/en/) || url.match(/maximintegrated.com\/[^('jp'|'cn')]/)){document.getElementById('lang_en').style.display='none';document.getElementById('lang_en_mob').style.display='none';addthis_config =
              {
                "data_track_addressbar":false,
                services_compact: "twitter,facebook,google_plusone_share,linkedin,pinterest_share,gmail,email,print"
              };}
        else if(url.match(/china.maximintegrated.com/) || url.match(/maximintegrated.com\/cn/)){document.getElementById('lang_cn').style.display='none';document.getElementById('delimit').style.display='none';document.getElementById('lang_cn_mob').style.display='none';addthis_config =
              {
                "data_track_addressbar":false,
                services_exclude:"facebook,twitter,cleanprint,favorites",
                services_compact: "sinaweibo,qzone,gmail,email,print",
                      ui_language : "zh"
              };}
        else if(url.match(/maximintegrated.com\/jp/) || url.match(/japan.maximintegrated.com/)){document.getElementById('lang_jp').style.display='none';document.getElementById('lang_jp_mob').style.display='none';addthis_config =
             {
                "data_track_addressbar":false,
                services_exclude:"gmail,cleanprint,favorites",
                services_compact: "twitter,facebook,google_plusone_share,email,print",
                ui_language : "ja"
              };}

if(url.match(/print=enabled/)){
          window.focus();
          window.print();
        }


    /*Eloqua tracking click on tabs/pdfs*/
        if (typeof customUrl === 'undefined'){customUrl=url;}
        //var _pageUrl = customUrl || window.location.protocol + "//" + window.location.hostname + window.location.pathname;
        var _pageUrl = window.location.protocol + "//" + window.location.hostname + window.location.pathname;
	if (_pageUrl.substr(_pageUrl.length-1) === "/"){ _pageUrl = _pageUrl.substr(0, _pageUrl.length-1); }
        $("ul.nav-tabs li a").click(function(){
                if (($(this).attr("href").substr(0,1) === "#") && (typeof _elq !== "undefined") && (typeof _elq.trackEvent === "function")){
                        var _label = $(this).text();
                        if (typeof _label.trim === "function"){
                                _label = _label.trim();
                        }
                        _label = _label.replace(/\s/g,"_");
                        _elq.trackEvent( _pageUrl + "/" + _label );
                }
        });
        //tracking clicks on links to PDFs
        $("a[href$='.pdf'],a[href$='.PDF']").click(function(){
                if ((typeof _elq !== "undefined") && (typeof _elq.trackEvent === "function")){
                        _elq.trackOutboundLink(this);
                        return false;
                }
        });
    /*End of Eloqua tracking click on tabs/pdfs*/


    /*Fix by CKJ for data-toggle=tab*/ 
    	$("#myTab a").click(function () {
		var x = this.href;
		window.location.replace(x);
	});
    /*fix end*/
        $("#ib1").click(function() {
            $("#ib1info").slideToggle();
        });
        $("#ib2").click(function() {
            $("#ib2info").slideToggle();
        });
        $("#ib3").click(function() {
            $("#ib3info").slideToggle();
        });
        $("#parametricsearch").click(function() {
            $("#parametricsearchinfo").slideToggle();
        });
		$('.carousel').carousel({interval: 5000});
});

var Prev = '';
$(document).ready(function(){
	$('.accordion-toggle').click(function(){
		if(Prev == $(this).parent().next().attr('id')){
			$('#'+Prev).slideToggle('slow');
			Prev = '';
		}
		else{
		if(Prev != ''){
			$('#'+Prev).slideToggle('slow');
			Prev = $(this).parent().next().attr('id');
		}
		else{
			Prev = $(this).parent().next().attr('id');
		}
		$(this).parent().next().slideToggle('slow');
		}
	});
	$('.left-nav-section').hover(function() {
		$(this).parents('.accordion-body').attr('style','display:block');
		var p = $(this);
		var offset = p.offset();
		//$(this).find('.terlinks').attr('style', 'top:'+ p.offset().top + 'px; left:'+ (p.offset().left - 6) + 'px;');
		$(this).find('.terlinks').attr('style', 'top:'+ (p.offset().top - 76) + 'px; left:80%;');
		//$(this).find('.terlinks').attr('style', 'top:'+ p.offset().top + 'px; left:137px;');
		$(this).find('.terlinks').show().animate({
            'width': '263px'
        },260);
	  }, function() {
		$(this).find('.terlinks').css('width', '0');
		$(this).find('.terlinks').hide();
	  }
	);
});

$(function (){
 $("#productStatus").tooltip();
});

$(".modal-wide").on("show.bs.modal", function() {
  var height = $(window).height() - 200;
  $(this).find(".modal-body").css("max-height", height);
});   







/**
* Customized Login, My Bookmarks and My Cart Header widget javascript starts from here.
*/

/**
Login Widget Javascript starts here
*/
    $(function(){
        $.ajax({
            url:"https://my.maximintegrated.com/wsi/auth/mywidget.mvp",
            dataType:"json",
            type: 'get',
            xhrFields: {
                withCredentials: true
            },
            success:function(data){
                if(data.success==1)
                {
		    if(data.mdata.is_logged_in=="1")
                    {
                        login.status=true;
                        login.data=data.mdata;
//						$('#myMaximSpan').text(data.mdata.member_fname+" "+data.mdata.member_lname).attr("style","text-transform:capitalize");
             //           $('#myMaximLink').attr("href","https://my.maximintegrated.com/");
                        if(document.getElementById('prodConfidentailLink') && data.mdata.max_conf_url!=null && data.mdata.max_conf_url!=undefined && data.mdata.max_conf_url!="")
                        {
							$('#prodConfidentailLink').attr("href",data.mdata.max_conf_url+qvid).show();
                            $('#prodConfidentailLink').click(function(){ 
                                 window.open(data.mdata.max_conf_url+qvid,'maximConfidential','menubar=1,scrollbars=1,resizable=1,width=930,height=500'); 
                                 return false;
                            });
                        }
						$('#myMaximLogoutDiv').show();
						$('#myMaximLoginDiv').hide();
                                                if(data.mdata.member_profile_pic!='default' && data.mdata.member_profile_pic){
                                                     $('#mymi-icon').attr("src",'https://my.maximintegrated.com/mymaxim/profile_pref/profile_img/thumb/'+data.mdata.member_profile_pic);                                    $('#mymi-icon1').attr("src",'https://my.maximintegrated.com/mymaxim/profile_pref/profile_img/thumb/'+data.mdata.member_profile_pic);
                                                }
                    }
                }
                else
                {
                    alert(data.msg);
                }
bookmark_handler('','',0);
            }

        });
    });
function setCookie(c_name, value, exdays) {

    var exdate = new Date();

    exdate.setDate(exdate.getDate() + exdays);

    var c_value = value + ((exdays == null) ? "" : "; expires=" + exdate.toUTCString())+ ";domain=.maximintegrated.com;path=/";

    document.cookie = c_name + "=" + c_value;

}



function addReturnUrlCookie()

{

    setCookie("MAX_GOAL",document.URL,365);

}

/**
Login Widget End Here
*/

/**
*My Bookmarks javascript starts here 
*/

function add_bmark()
{
     var params = "&title="+encodeURIComponent(document.title)+"&url="+encodeURIComponent(document.URL);
     bookmark_handler('add',params,0);
}
function del_bmark(id,ind)
{
   var params = "&mark_id="+id;
   bookmark_handler('delete',params,ind);
}

function bookmark_handler(action,params,ind)
{
   if(login.status)
   {
     $('#bookmarkNoLoginDiv').hide();
     $('#bookmarkDiv').show();
     
     uri = "https://my.maximintegrated.com/mymaxim/wsi/bookmarks/bm.mvp";
     if(action != '') { uri += '?action='+action+params; }
     http_request = new XMLHttpRequest();
     http_request.onreadystatechange=function()
     {
             if(http_request.readyState==4)
             {
                     if(action=='delete')
                     {
			 alert('Bookmark has been deleted successfully.');	
                         $(function() { $("#bmdel"+ind).delay(3000).fadeOut(0); });
                         document.getElementById('bm'+ind).content = "";
                         $('#bm'+ind+' tr').fadeOut('1000');
                         document.getElementById('bm'+ind).style.display='none';
                     }
                     var wrapper=0;
		     eval(http_request.responseText);
		     if(!wrapper)
		     {
		     	this.set_error("Bad wrapper");return(0);
		     }

                     if(action == 'add')
                     {
                           if(!wrapper.success) { document.getElementById('bmarksuc').innerHTML = wrapper.msg; alert(document.getElementById('bmarksuc').innerHTML);return 0; }
			   alert('Bookmark has been added successfully.');
                     }
                     var obj;
                     if(navigator.appName != 'Microsoft Internet Explorer')
                     {
                         obj = document.getElementsByClassName('col-sm-12 myBookmarks');
                     }
                     else
                     {
                         obj = document.getElementById('bookmarksModal').querySelectorAll('div.col-sm-12');
                     }
                     var page_img = '<!--[if lte IE 8]><img src="/maxkit/images/page_icon.png" /><![endif]--><!--[if gt IE 8]><img src="/maxkit/images/page_icon.svg" /><![endif]--><!--[if !IE]>--><img src="/maxkit/images/page_icon.svg"></img><!-- <![endif]-->';
                     obj[0].innerHTML = '';//document.getElementsByClassName('col-sm-12 myBookmarks')[0].innerHTML = '';
                     var max = (wrapper.mdata.bookmarks.length > 7) ? 7 : wrapper.mdata.bookmarks.length;
                     if(action == 'all') { max = wrapper.mdata.bookmarks.length; }
                     for(var i = 0; i < max; i++)
                     {
                                var url = wrapper.mdata.bookmarks[i][2];
                                var title = wrapper.mdata.bookmarks[i][1];
                                obj[0].innerHTML += '<p class="clear">'+page_img+'<a href="'+url+'">'+title+'</a></p>';//document.getElementsByClassName('col-sm-12 myBookmarks')[0].innerHTML += '<p class="clear">'+page_img+'<a href="'+url+'">'+title+'</a></p>';
                     }
             }
     }

     http_request.open("GET", uri);
     http_request.withCredentials = true;
     http_request.send();
  }
}


function printExternal(url) {
var W = window.open(url);
setTimeout(function(){W.window.print();}, 5000);
}

/**
* My Bookmarks javascript ends here
*/

/**
* My Cart javascript starts here
*/
function mycart_widget()
{
        var cart_json = '{ "data" : [{"name" : "MAX_SHOP_SHOP", "url" : "https://shop.maximintegrated.com/storefront/shoppingcart.do?event=ShowShoppingCart&menuitem=ShoppingCart", "headers" : ["Maxim Part", "Quantity", "Price"],"divid" : "myCartOrder", "cook" : [0, 3, 8], "tableid" : "OrderTable", "emptymsg" : "No item(s) in <b>Buy</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabSample\') href=#myCartSample>Sample</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabQuote\') href=#myCartQuote>Quote</a></b> Carts?"}, {"name" : "MAX_SHOP_SAMPLE", "url" : "https://shop.maximintegrated.com/storefront/samplecart.do?event=ShowSampleCart&menuitem=SampleCart", "headers" : ["Maxim Part", "Quantity", "Customer Reference Number"], "divid" : "myCartSample", "cook" : [0, 2, 1], "tableid" : "SampleTable", "emptymsg" : "No item(s) in <b>Sample</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabOrder\') href=#myCartOrder>Buy</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabQuote\') href=#myCartSample>Quote</a></b> Carts?"}, {"name" : "MAX_SHOP_QUOTE","url" : "https://shop.maximintegrated.com/storefront/quotecart.do?event=showQuoteCart&menuitem=QuoteCart", "headers" : ["Maxim Part", "Quantity", "Competition Part", "Target Price (USD$)"], "divid" : "myCartQuote","cook" : [0, 2, 3,4], "tableid" : "QuoteTable", "emptymsg" : "No item(s) in <b>Quote</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabOrder\') href=#myCartOrder>Buy</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabSample\') href=#myCartSample>Sample</a></b> Carts?"}] }';
	var empty_msg = { MAX_SHOP_SHOP  : {
			 en : "No item(s) in <b>Buy</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabSample\') href=#myCartSample>Sample</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabQuote\') href=#myCartQuote>Quote</a></b> Carts?",
			 cn : '&#26242;&#26102;&#27809;&#26377;<b>&#35746;&#36141;</b>&#30340;&#20135;&#21697;&#12290;&#28857;&#20987;<b><a href="#myCartSample" onclick="tabchange(\'myTabSample\')" data-toggle="tab">&#26679;&#29255;</a></b>&#25110;&#32773;<b><a href="#myCartQuote" onclick="tabchange(\'myTabQuote\')" data-toggle="tab">&#35810;&#20215;</a></b>&#26597;&#35810;&#23545;&#24212;&#39033;&#30446;&#12290;',
			 jp : '<b>&#36092;&#20837;</b>&#12459;&#12540;&#12488;&#12395;&#12399;&#20309;&#12418;&#20837;&#12387;&#12390;&#12356;&#12414;&#12379;&#12435;&#12290;<b><a href="#myCartSample" onclick="tabchange(\'myTabSample\')" data-toggle="tab">&#12469;&#12531;&#12503;&#12523;</a></b>/<b><a href="#myCartQuote" onclick="tabchange(\'myTabQuote\')" data-toggle="tab">&#35211;&#31309;&#12418;&#12426;</a></b>&#12459;&#12540;&#12488;&#12434;&#12481;&#12455;&#12483;&#12463;&#12375;&#12390;&#12367;&#12384;&#12373;&#12356;&#12290;'
		    },
	MAX_SHOP_QUOTE : {
			 en : "No item(s) in <b>Quote</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabOrder\') href=#myCartOrder>Buy</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabSample\') href=#myCartSample>Sample</a></b> Carts?",
			 cn : '&#26242;&#26102;&#27809;&#26377;<b>&#35810;&#20215;</b>&#21333;&#12290;&#28857;&#20987;<b><a href="#myCartOrder" onclick="tabchange(\'myTabOrder\')" data-toggle="tab">&#35746;&#36141;</a></b>&#25110;&#32773;<b><a href="#myCartSample" onclick="tabchange(\'myTabSample\')" data-toggle="tab">&#26679;&#29255;</a></b>&#26631;&#31614;&#39029;&#26597;&#35810;&#23545;&#24212;&#39033;&#30446;&#12290;',
			 jp: '<b>&#35211;&#31309;&#12418;&#12426;</b>&#12459;&#12540;&#12488;&#12395;&#12399;&#20309;&#12418;&#20837;&#12387;&#12390;&#12356;&#12414;&#12379;&#12435;&#12290;<b><a href="#myCartOrder" onclick="tabchange(\'myTabOrder\')" data-toggle="tab">&#36092;&#20837;</a></b>/<b><a href="#myCartSample" onclick="tabchange(\'myTabSample\')" data-toggle="tab">&#12469;&#12531;&#12503;&#12523;</a></b>&#12459;&#12540;&#12488;&#12434;&#12481;&#12455;&#12483;&#12463;&#12375;&#12390;&#12367;&#12384;&#12373;&#12356;&#12290;'
			}, 
	MAX_SHOP_SAMPLE : {
			 en : "No item(s) in <b>Sample</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabOrder\') href=#myCartOrder>Buy</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabQuote\') href=#myCartSample>Quote</a></b> Carts?",
			 cn : '&#30003;&#35831;&#26679;&#29255;&#35831;&#25552;&#20132;<a href="https://support.maximintegrated.com/cn/sales/samples/">&#26679;&#29255;&#30003;&#35831;&#21333;</a>&#12290;&#20102;&#35299;&#26679;&#29255;&#30003;&#35831;&#36827;&#24230;&#65292;&#35831;&#32852;&#31995;&#23458;&#25143;&#26381;&#21153;&#12290;',
		         jp : '<b>&#12469;&#12531;&#12503;&#12523;</b>&#12459;&#12540;&#12488;&#12395;&#12399;&#20309;&#12418;&#20837;&#12387;&#12390;&#12356;&#12414;&#12379;&#12435;&#12290;<b><a href="#myCartOrder" onclick="tabchange(\'myTabOrder\')" data-toggle="tab">&#36092;&#20837;</a></b>/<b><a href="#myCartSample" onclick="tabchange(\'myTabQuote\')" data-toggle="tab">&#35211;&#31309;&#12418;&#12426;</a></b>&#12459;&#12540;&#12488;&#12434;&#12481;&#12455;&#12483;&#12463;&#12375;&#12390;&#12367;&#12384;&#12373;&#12356;&#12290;'
		      }, 
	go_to	     : {
			en : 'GO TO CART',
			cn : '&#21435;&#36141;&#29289;&#36710;',
			jp : '&#12459;&#12540;&#12488;&#12434;&#35211;&#12427;'
		      
		      }
		      };

        var cook = document.cookie;
	var lang = Lang.code;
        var cookieJson = JSON.parse(cart_json);
        for(var c =0; c < cookieJson.data.length; c++)
        {
                var cReg = new RegExp('(^|;)\\s*'+cookieJson.data[c].name+'=([^;]+)');
                var f=cook.match(cReg);
                var val = '';
                val=unescape(RegExp.$2);
		var url = '';
		url = cookieJson.data[c].url;
                if(! val.match(/:/g))
                {
                        document.getElementById(cookieJson.data[c].divid).innerHTML = '<div class="row"> <div class="col-sm-12" id=""><br />'+empty_msg[cookieJson.data[c].name][lang]+'</div></div>';
                }
                else
                {
                        val  = val.replace(/\~$/, '');
                        var cart_arr = val.split("~");
                        var head = '<div class="row"> <div class="col-sm-12" id=""><br /><table class="table table-bordered table-striped table-hover table-condensed"><thead><tr>';
                        for(var h =0; h < cookieJson.data[c].headers.length; h++)
                        {
                                var right_td = '<th>';
                                if(h == cookieJson.data[c].headers.length -1) { right_td = '<th class="table-right-head">'; }
                                head += right_td+cookieJson.data[c].headers[h]+'</th>';
                        }

                        var table_html = head+'</tr></thead>';
                        var table_rec = '<tbody>';
                        var total = 0;
                        for(var i=0; i<cart_arr.length; i++)
                        {
                                table_rec += '<tr>';
                                var cart = cart_arr[i].split(":");

                                for(var j=0;j< cookieJson.data[c].cook.length; j++)
                                {
                                        var ind = cookieJson.data[c].cook[j];
                                        var data = (cart[ind]) ? cart[ind] : 'N/A';
                                        if(cookieJson.data[c].name == 'MAX_SHOP_SHOP' && ind == 8) { if(data)data = (data) ? '$'+data : '$0.0'; }
                                        if(cookieJson.data[c].name == 'MAX_SHOP_QUOTE' && ind == 4) { if(data)data = (data) ? '$'+data : '$0.0'; }
                                        table_rec += '<td>'+data+'</td>';
                                        if(j == 2 && cookieJson.data[c].name == 'MAX_SHOP_SHOP') {
                                                total = parseFloat(total)+parseFloat(cart[cookieJson.data[c].cook[j]]);
                                        }
                                }
                                table_rec += '</tr>';
                        }
			url = cookieJson.data[c].url;
                        if(cookieJson.data[c].name == 'MAX_SHOP_SHOP') { table_rec += '<tr><td colspan=2>Subtotal</td><td>$'+(Math.round(total * 100) / 100).toFixed(2)+'</td>'; }
                        document.getElementById(cookieJson.data[c].divid).innerHTML = table_html+table_rec+'</tbody></table>';
                }
		document.getElementById(cookieJson.data[c].divid).innerHTML = document.getElementById(cookieJson.data[c].divid).innerHTML+'<div class="right-float"><button type="button" class="btn btn-danger btn-sm uppercase" onclick="javascript:gotocart(\''+url+'\');"">'+empty_msg['go_to'][lang]+'</button></div>';
        }
}

function tabchange(a_i)
{
        var deac = (a_i == 'myTabSample') ? ['myTabOrder', 'myTabQuote'] : (a_i == 'myTabOrder') ? ['myTabQuote', 'myTabSample'] : ['myTabSample', 'myTabOrder'];
        document.getElementById(a_i).className = 'uppercase active';
        document.getElementById(deac[1]).className = 'uppercase';
        document.getElementById(deac[0]).className = 'uppercase';
}

function gotocart(url)
{
	window.location = url;
}
	 
/**
* My Cart javascript ends here
*/

/**
* My Cart javascript starts here

function mycart_widget()
{
        var cart_json = '{ "data" : [{"name" : "MAX_SHOP_SHOP", "url" : "https://shop.maximintegrated.com/storefront/shoppingcart.do?event=ShowShoppingCart&menuitem=ShoppingCart", "headers" : ["Maxim Part", "Quantity", "Price"],"divid" : "myCartOrder", "cook" : [0, 3, 8], "tableid" : "OrderTable", "emptymsg" : "No item(s) in <b>Buy</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabSample\') href=#myCartSample>Sample</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabQuote\') href=#myCartQuote>Quote</a></b> Carts?"}, {"name" : "MAX_SHOP_SAMPLE", "url" : "https://shop.maximintegrated.com/storefront/samplecart.do?event=ShowSampleCart&menuitem=SampleCart", "headers" : ["Maxim Part", "Quantity", "Customer Reference Number"], "divid" : "myCartSample", "cook" : [0, 2, 1], "tableid" : "SampleTable", "emptymsg" : "No item(s) in <b>Sample</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabOrder\') href=#myCartOrder>Buy</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabQuote\') href=#myCartSample>Quote</a></b> Carts?"}, {"name" : "MAX_SHOP_QUOTE","url" : "https://shop.maximintegrated.com/storefront/quotecart.do?event=showQuoteCart&menuitem=QuoteCart", "headers" : ["Maxim Part", "Quantity", "Competition Part", "Target Price (USD$)"], "divid" : "myCartQuote","cook" : [0, 2, 3,4], "tableid" : "QuoteTable", "emptymsg" : "No item(s) in <b>Quote</b> Cart.Have you checked your <b><a data-toggle=tab onclick=tabchange(\'myTabOrder\') href=#myCartOrder>Buy</a></b> or <b><a data-toggle=tab onclick=tabchange(\'myTabSample\') href=#myCartSample>Sample</a></b> Carts?"}] }';

        var cook = document.cookie;
        var cookieJson = JSON.parse(cart_json);
        for(var c =0; c < cookieJson.data.length; c++)
        {
                var cReg = new RegExp('(^|;)\\s*'+cookieJson.data[c].name+'=([^;]+)');
                var f=cook.match(cReg);
                var val = '';
                val=unescape(RegExp.$2);
		var url = '';
		url = cookieJson.data[c].url;
                if(! val.match(/:/g))
                {

                        document.getElementById(cookieJson.data[c].divid).innerHTML = '<div class="row"> <div class="col-sm-12" id=""><br />'+cookieJson.data[c].emptymsg+'</div></div>';
                }
                else
                {
                        val  = val.replace(/\~$/, '');
                        var cart_arr = val.split("~");
                        var head = '<div class="row"> <div class="col-sm-12" id=""><br /><table class="table table-bordered table-striped table-hover table-condensed"><thead><tr>';
                        for(var h =0; h < cookieJson.data[c].headers.length; h++)
                        {
                                var right_td = '<th>';
                                if(h == cookieJson.data[c].headers.length -1) { right_td = '<th class="table-right-head">'; }
                                head += right_td+cookieJson.data[c].headers[h]+'</th>';
                        }

                        var table_html = head+'</tr></thead>';
                        var table_rec = '<tbody>';
                        var total = 0;
                        for(var i=0; i<cart_arr.length; i++)
                        {
                                table_rec += '<tr>';
                                var cart = cart_arr[i].split(":");

                                for(var j=0;j< cookieJson.data[c].cook.length; j++)
                                {
                                        var ind = cookieJson.data[c].cook[j];
                                        var data = (cart[ind]) ? cart[ind] : 'N/A';
                                        if(cookieJson.data[c].name == 'MAX_SHOP_SHOP' && ind == 8) { if(data)data = (data) ? '$'+data : '$0.0'; }
                                        if(cookieJson.data[c].name == 'MAX_SHOP_QUOTE' && ind == 4) { if(data)data = (data) ? '$'+data : '$0.0'; }
                                        table_rec += '<td>'+data+'</td>';
                                        if(j == 2 && cookieJson.data[c].name == 'MAX_SHOP_SHOP') {
                                                total = parseFloat(total)+parseFloat(cart[cookieJson.data[c].cook[j]]);
                                        }
                                }
                                table_rec += '</tr>';
                        }
			url = cookieJson.data[c].url;
                        if(cookieJson.data[c].name == 'MAX_SHOP_SHOP') { table_rec += '<tr><td colspan=2>Subtotal</td><td>$'+total+'</td>'; }
                        document.getElementById(cookieJson.data[c].divid).innerHTML = table_html+table_rec+'</tbody></table>';
                }
		document.getElementById(cookieJson.data[c].divid).innerHTML = document.getElementById(cookieJson.data[c].divid).innerHTML+'<div class="right-float"><button type="button" class="btn btn-danger btn-sm uppercase" onclick="javascript:gotocart(\''+url+'\');"">Go To Cart</button></div>';
        }
}

function tabchange(a_i)
{
        var deac = (a_i == 'myTabSample') ? ['myTabOrder', 'myTabQuote'] : (a_i == 'myTabOrder') ? ['myTabQuote', 'myTabSample'] : ['myTabSample', 'myTabOrder'];
        document.getElementById(a_i).className = 'uppercase active';
        document.getElementById(deac[1]).className = 'uppercase';
        document.getElementById(deac[0]).className = 'uppercase';
}

function gotocart(url)
{
	window.location = url;
}
	 
/**
* My Cart javascript ends here
*/
