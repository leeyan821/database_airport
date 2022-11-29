<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Air Line</title>
</head>
<style>
	#container {
		width: 1020px;
		margin: 0 auto;
	}
	header {
		width: 1020px;
		margin: 0 auto;
		font-size: 15px;
		color: #0770e3;
	}
	footer {
		width: 1020px;
		margin: 40px auto;
		font-size: 12px;
	}
	#all {
		padding: 20px;
		background-color: #02122c;
		border-radius: 9px;
	}
	#first_tab {
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(50px, 1fr));
	}
	label {
		color: #fff;
		font-size: 12px;
		font-weight: bold;
	}
	form{
		width: 900px;
		margin: 25px auto;
	}
	#where{
		width: 450px;
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(50px, 1fr));
	}
	#when{
		width: 450px;
		display: grid;
		grid-template-columns: repeat(auto-fit, minmax(50px, 1fr));
	}
	input {
		border: 1px solid #222;
		padding:8px;
		width: 207px;
		height: 42px;
		font-size: 17px;
	}
	.t1{
		border-top-left-radius: 8px;
		border-bottom-left-radius: 8px;
	}
	.t2{
		border-top-right-radius: 8px;
		border-bottom-right-radius: 8px;
	}
	#top{
		display: flex;
    	width: 100%;
    	justify-content: flex-start;
	}
	.header_tab{
		box-sizing: inherit;
		background-color: #02122c;
		margin: 0 0 0 5px;
		width: 80px;
		height: 30px;
		line-height: 30px;
		text-align: center;
		border-top-left-radius: 8px;
		border-top-right-radius: 8px;
	}
	 .header_tab2{
	 	box-sizing: inherit;
		background-color: #0770e3;
		margin: 0 0 0 5px;
		width: 80px;
		height: 30px;
		line-height: 30px;
		text-align: center;
		border-top-left-radius: 8px;
		border-top-right-radius: 8px;
	 }
	.header_tab a, .header_tab2 a{
		color: white;
		height: 30px;
		width: 80px;
		text-decoration: none;
	}
	#send{
		display: flex;
		background-color: #02122c;
		border-bottom-left-radius: 8px;
		border-bottom-right-radius: 9px;
	}
	#send p{
		color: white;
		font-size: 13px;
		margin: 0;
	}
	#Start5{
		margin: 20px 0 0 0;
		width: 700px;
	}
	button{
		margin: 55px 0 15px 0;
		width: 200px;
		height: 50px;
		border-radius: 8px;
		background-color: #00a698;
		font-size: 20px;
		color: white;
	}
	@media screen and (max-width: 1020px){
		#container {
			width: 700px;
			margin: 0 auto;
			border : 1px solid #222;
		}
		header {
			width: 700px;
			margin: 0 auto;
			font-size: 10px;
			color: #0770e3;
		}
	}
	
</style>
<body>
	<header>
		<h1>Airscanner</h1>
		<div>
			<nav id="top">
				<div class="header_tab">
					<a href="#">항공권</a>
				</div>
				<div class="header_tab2">
					<a href="board/allList">전체</a>
				</div>
				<div class="header_tab2">
					<a href="#">호텔</a>
				</div>
			</nav>
		</div>
	</header>
	<div id="container">
		<div id="all">
			<form action="move.do" method="get">
				<div id="first_tab">
				<div id="where">
					<div id="start1">
						<label>출발지</label>
						<div id="s_enter1">
							<input type="text" class="t1" name="s1" placeholder="공항 입력">
						</div>
					</div>
					<div id="start2">
						<label>도착지</label>
						<div id="s_enter2">
							<input type="text" name="s2" placeholder="공항 입력">
						</div>
					</div>
				</div>
				<div id="when">
					<div id="start3">
						<label>가는날</label>
						<div id="s_enter3">
							<input type="date" name="s3">
						</div>
					</div>
					<div id="start4">
						<label>오는날</label>
						<div id="s_enter4">
							<input type="date" class="t2" name="s4">
						</div>
					</div>
				</div>
				</div>
				<div id="send">
						<div id="Start5">
							<label>가격 선택</label>
							<div id="s_enter5">
                        <input type="range" list="short" name="s5" class="price" min="0" max="100000" step="1000" id="priceRange">
                        <p><span id="value"></span>원 이하</p>
                     </div>
						</div>
				<button type="submit" aria-label="항공권 검색"> 항공권 검색 ▷</button>
				</div>
			</form>
		</div>
	</div>
	<footer>
		<h3>[ 데이터 베이스 ]</h3>
		<p>202033011 김채림</p>
		<p>202033016 신아영</p>
	</footer>
	<script type="text/javascript">
      var slider = document.getElementById("priceRange");
      var output = document.getElementById("value");
      output.innerHTML = slider.value;
      
      slider.oninput = function() {
          output.innerHTML = this.value;
      }
   </script>
</html>