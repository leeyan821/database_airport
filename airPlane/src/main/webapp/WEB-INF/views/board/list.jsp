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
	#container {
		width: 1100px;
		margin: 0 auto;
	}
	header {
		width: 1100px;
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
		padding: 5px 8px;
	}
	table{
		margin: 0 auto;		
	}
	td{
		padding: 5px 8px;
		color: #02122c;
	}
	tbody{
		background-color: #f1f2f8;
		border-radius: 8px;
	}
	form{
		display: flex;
    	width: 100%;
    	justify-content: flex-start;
    	margin: 5px 10px 15px 0;
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
	hidden{
		height: 0;
	}
	form{
		height: 40px;
	}
	p{
		color: white;
		font-size: 15px;
		margin: 0 0 20px 30px;
	}
</style>
<body>
	<header>
		<h1>Airscanner</h1>
		<div>
			<nav id="top">
				<div class="header_tab">
					<a href="/web/">항공권</a>
				</div>
				<div class="header_tab2">
					<a href="./board/allList">전체</a>
				</div>
				<div class="header_tab2">
					<a href="#">호텔</a>
				</div>
			</nav>
		</div>
	</header>
<div id="container">
<div id="all">
<form id="select" action="move.do" method="get">
	<li>
		<label for="path">상세 선택</label>
		<select id="path" name="val">
			<option value="up">가격 오름차순</option>
			<option value="down">가격 내림차순</option>
			<option value="sun">오전</option>
			<option value="moon">오후</option>
			<option value="time">현재 시간때</option>
		</select>
	</li>
	<input type="hidden" name="test1" value="${s1}"/><br/>
	<input type="hidden" name="test2" value="${s2}"/><br/>
	<input type="hidden" name="test3" value="${s3}"/><br/>
	<input type="hidden" name="test4" value="${s4}"/><br/>
	<input type="hidden" name="test5" value="${s5}"/><br/>
	<input type="submit" id="send" value="Go">
</form>
<table>
	<p name= "count">[ 총 : ${count} 개의 결과 ]</p>
	<p name= "avg">[평균 가격 : ${avg} 원 ]</p>
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
</div>
</body>
</html>