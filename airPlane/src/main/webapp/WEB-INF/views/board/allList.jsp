<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Air Line</title>
</head>
<style>
	div{
		display: flex;
		flex-direction: column;
    	align-items: baseline;
	}
	#container {
		width: 1200px;
		margin: 0 auto;
	}
	header {
		width: 1200px;
		margin: 0 auto;
		font-size: 15px;
		color: #0770e3;
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
	#all {
		padding: 20px;
		background-color: #02122c;
		border-radius: 9px;
	}
	th{
		color: white;
		padding: 10px;
	}
	td{
		padding: 10px;
		color: #02122c;
	}
	tbody{
		background-color: #f1f2f8;
	}
	select{
		margin: 0 10px 0 0;
	}
	label{
		color: white;
		margin: 5px;
	}
	.send{
		margin: 15px;
	}
	input[type="submit"] {
		height: 30px;
	} 
	form{
		height: 40px;
	}
	h3{
		color: white;
		margin: 10px 5px;
	}
	p{
		color: white;
	}
	table[id=tab_3]{
		margin: 30px 0 0 0;
	}
	table[id=tab_1], table[id=tab_2]{
		width: 900px;
		font-size: 15px;
	}

</style>
<body>
	<header>
		<h1>Airscanner</h1>
		<div>
			<nav id="top">
				<div class="header_tab2">
					<a href="/web/">항공권</a>
				</div>
				<div class="header_tab">
					<a href="#">전체</a>
				</div>
				<div class="header_tab2">
					<a href="#">호텔</a>
				</div>
			</nav>
		</div>
	</header>
<div id="container">
<div id="all">
<div id="minmax">
<div id = "tab">
<p name= "groupBy">[ 항공사 별 운항 비행기 수 ]</br> <c:forEach items="${list4}" var="list4">${list4.airline} : ${list4.des}  </c:forEach></p>
<table id = "tab_1">
	<h3>최고가 항공</h3>
	<tbody>
		<c:forEach items="${list1}" var="list1">
 			<tr>
  				<td>${list1.airline}</td>
  				<td>${list1.departure}</td>
  				<td>${list1.des}</td>
  				<td>${list1.de_time}</td>
  				<td>${list1.des_time}</td>
  				<td>${list1.day}</td>
  				<td>${list1.de_day}</td>
  				<td>${list1.des_day}</td>
  				<td>${list1.price}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
<div>
<table id = "tab_2">
	<h3>최저가 항공</h3>
	<tbody>
		<c:forEach items="${list3}" var="list3">
 			<tr>
  				<td>${list3.airline}</td>
  				<td>${list3.departure}</td>
  				<td>${list3.des}</td>
  				<td>${list3.de_time}</td>
  				<td>${list3.des_time}</td>
  				<td>${list3.day}</td>
  				<td>${list3.de_day}</td>
  				<td>${list3.des_day}</td>
  				<td>${list3.price}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</div>
<table id="tab_3">
	<thead>
		<tr>
			<th>*</th>
			<th>항공사</th>
			<th>편명</th>
			<th>출발공항</th>
			<th>도착공항</th>
			<th>출발시간</th>
			<th>도착시간</th>
			<th>운항요일</th>
			<th>시작일자</th>
			<th>종료일자</th>
			<th>기종</th>
			<th>가격</th>
		</tr>	
	</thead>
	<tbody>
		<c:forEach items="${list2}" var="list2">
 			<tr>
 				<td>${list2.num}</td>
  				<td>${list2.airline}</td>
  				<td>${list2.name}</td>
  				<td>${list2.departure}</td>
  				<td>${list2.des}</td>
  				<td>${list2.de_time}</td>
  				<td>${list2.des_time}</td>
  				<td>${list2.day}</td>
  				<td>${list2.de_day}</td>
  				<td>${list2.des_day}</td>
  				<td>${list2.part}</td>
  				<td>${list2.price}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</body>
</html>