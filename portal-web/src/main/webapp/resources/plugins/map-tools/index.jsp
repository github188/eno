<!DOCTYPE html>
<html>
<head>
	<title>html image map creator</title>
	<link href="main.css" type="text/css" rel="stylesheet" />
	<meta charset="UTF-8" />
	<!--
		html image map creator
		Last Modified 20140410 by PING.CHEN
	-->
</head>
<body>
<noscript>
	<div id="noscript">
		Please, enable javascript in your browser
	</div>
</noscript>
  
<div id="wrapper">
	<header id="header">
		<nav id="nav" class="clearfix">
			<ul>
				<li id="save"><a href="#">确定</a></li>
				<li id="save_db" style="display:none"><a href="#">保存</a></li>				<!-- 隐藏按钮功能，当单击【确定】按钮后，会自动执行改按钮的功能 [ ChengKang 2014-07-23 ] -->
				<li id="load"><a href="#">重载</a></li>
				<li id="from_html" style="display:none"><a href="#">from html</a></li>	<!-- 隐藏按钮功能 [ ChengKang 2014-07-23 ] -->
				<li id="rect"><a href="#">矩形</a></li>
				<li id="circle"><a href="#">圆形</a></li>
				<li id="polygon"><a href="#">多边形</a></li>
				<li id="edit"><a href="#">编辑</a></li>
				<li id="to_html" style="display:none"><a href="#">to html</a></li>			<!-- 隐藏按钮功能 [ ChengKang 2014-07-23 ] -->
				<li id="preview" style="display:none"><a href="#">预览</a></li>				<!-- 隐藏按钮功能 [ ChengKang 2014-07-23 ] -->
				<li id="clear"><a href="#">清除</a></li>
				<li id="new_image" style="display:none"><a href="#">新图形</a></li>		<!-- 隐藏按钮功能，图形只在配置页面自动加载系统平面图的底图 [ ChengKang 2014-07-23 ] -->
				<li id="show_help" style="display:none"><a href="#">帮助</a></li>			<!-- 隐藏按钮功能 [ ChengKang 2014-07-23 ] -->
			</ul>
		</nav>
		<div id="coords"></div>
		<div id="debug"></div>
	</header>	
	<div id="image_wrapper">
		<div id="image">
			<img src="" alt="#" id="img" />
			<svg xmlns="http://www.w3.org/2000/svg" version="1.2" baseProfile="tiny" id="svg"></svg>
		</div>
	</div>
</div>

<!-- For html image map code -->
<div id="code">
	<span class="close_button" title="close"></span>
	<div id="code_content"></div>
</div>

<!-- Edit details block -->
<form id="edit_details">
	<h5>Attrubutes</h5>
	<span class="close_button" title="close"></span>
	<p>
		<label for="state_attr">TagID</label>		<!-- 对原本Demo中的state改名TagID，用来记录每个热区的TagID [ ChengKang 2014-07-22 ] -->
		<input type="text" id="state_attr" />
	</p>
	<p>
		<label for="href_attr">TagName</label>	<!-- 对原本Demo中的href改名TagName，用来记录每个热区的TagName [ ChengKang 2014-07-22 ] -->
		<input type="text" id="href_attr" />
	</p>
	<p>
		<label for="alt_attr">alt</label>					<!-- 保留原有属性字段，实际不使用 [ ChengKang 2014-07-22 ] -->
		<input type="text" id="alt_attr" />
	</p>
	<p>
		<label for="title_attr">title</label>				<!-- 保留原有属性字段，实际不使用 [ ChengKang 2014-07-22 ] -->
		<input type="text" id="title_attr" />
	</p>
	<button id="save_details">Save</button>
</form>

<!-- From html block -->
<div id="from_html_wrapper">
	<form id="from_html_form">
		<h5>Loading areas</h5>
		<span class="close_button" title="close"></span>
		<p>
			<label for="code_input">Enter your html code:</label>
			<textarea id="code_input"></textarea>
		</p>
		<button id="load_code_button">Load</button>
	</form>
</div>
  
<!-- Get image form -->
<div id="get_image_wrapper">
	<div id="get_image">
		
		<div id="loading">Loading</div>
		<div id="file_reader_support">
			<label>拖入图片</label>
			<div id="dropzone">
				<span class="clear_button" title="clear">x</span> 
				<img src="" alt="preview" id="sm_img" />
			</div>
			<b>或者</b>
		</div>
		<label for="url">输入图片地址</label>
		<span id="url_wrapper">
			<span class="clear_button" title="clear">x</span>
			<input type="text" id="url"/>
		</span>
		<button id="button">OK</button>
	</div>
</div>

<!-- Help block -->
<div id="overlay"></div>
<div id="help">
	<span class="close_button" title="close"></span>
	<div class="txt">
		<section>
			<h2>Main</h2>
			<p><span class="key">F5</span> &mdash; reload the page and display the form for loading image again</p>
			<p><span class="key">s</span> &mdash; save map params in localStorage</p>
		</section>
		<section>
			<h2>Drawing mode (rectangle / circle / polygon)</h2>
			<p><span class="key">ENTER</span> &mdash; stop polygon drawing (or click on first helper)</p>
			<p><span class="key">ESC</span> &mdash; cancel drawing of a new area</p>
			<p><span class="key">SHIFT</span> &mdash; square drawing in case of a rectangle and right angle drawing in case of a polygon</p>
		</section>
		<section>
		<h2>Editing mode</h2>
			<p><span class="key">DELETE</span> &mdash; remove a selected area</p>
			<p><span class="key">ESC</span> &mdash; cancel editing of a selected area</p>
			<p><span class="key">SHIFT</span> &mdash; edit and save proportions for rectangle</p>
			<p><span class="key">i</span> &mdash; edit attributes of a selected area (or dblclick on an area)</p>
			<p><span class="key">&uarr;</span> &mdash; move a selected area up</p>
			<p><span class="key">&darr;</span> &mdash; move a selected area down</p>
			<p><span class="key">&larr;</span> &mdash; move a selected area to the left</p>
			<p><span class="key">&rarr;</span> &mdash; move a selected area to the right</p>
		</section>
	</div>
	<footer>
		
	</footer>
</div>

<script type="text/javascript" src="scripts.js"></script>
</body>
</html>