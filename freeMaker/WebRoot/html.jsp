<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">
body {
	font-family: Arial Unicode MS;
}
</style>
		<title>书皮</title>
		<style type="text/css">
		@page {
	size: a4 portrait;
	margin: 20mm 10mm;
	padding: 1em;
	@top-left {content: element(header);}
	@bottom-left{content: element(footer);}
	@bottom-center{content: counter(page) " / " counter(pages);}
	@bottom-right{content: "www.quidos.co.uk";}           
}

@page blank {
	size: a4 portrait;
	margin: 20mm 10mm;
	padding: 1em;
	@top-left { content: normal } 
	@bottom-left { content: normal } 
	@top-right { content: normal }   
	@bottom-right {content: normal }
	@bottom-center{content: normal }
} 

/*header as footer will automatically icluded in each pdf printed page*/
#header { position: running(header);}
#footer { display: block; position: running(footer);}

#pagenumber:before {content: counter(page);}
#pagecount:before {content: counter(pages);}

/* Used for generating Table of content */
#toc a::after { content: leader('.') target-counter(attr(href), page); }

/* Use this class for first level titles */
.page_break_before{ page-break-before: always; }

/* Use this class for forcing page break inside pdf */
.page_breaker{page-break-after:always;}
.blank { page : blank; }
</style>

<bookmarks>
	<bookmark name="test1" href="#test1" />
	<bookmark name="test2" href="#test2">
		
		<bookmark name="test3" href="#test3">
		</bookmark>

		
		<bookmark name="test4" href="#test4">
		</bookmark>
		
	</bookmark>
</bookmarks>
		
	</head>
	<body>
		<div style="font-family: Arial Unicode MS;" id="test1">
			<input name="testInput" type="hidden" value="test value" />
			<p>
				项目编号：
				<span style="border-bottom: black solid 1px;">{xiangmubianhao}</span>
			</p>
			<p>
				<span style=""><br/> </span>
			</p>
			





		</div>

	</body>
</html>