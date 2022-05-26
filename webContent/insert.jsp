<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.javaex.vo.PersonVo"%>
<%@ page import="com.javaex.dao.PhoneDao"%>
<%@ page import="java.util.List"%>

<%
	//파라미터에서 값 꺼내오기
	String name = request.getParameter("name");
	String hp = request.getParameter("hp");
	String company = request.getParameter("company");
	/*
	System.out.println(name);
	System.out.println(hp);
	System.out.println(company);
	*/
	
	//Person 객체만들기
	PersonVo personVo = new PersonVo(name, hp, company);
	
	//PhoneDao 객체만들기
	PhoneDao phoneDao = new PhoneDao();
	
	//PhoneDao의 personInsert()메소드 이용해서 내용저장
	int count = phoneDao.personInsert(personVo);
	System.out.println(count);
	
	///////////////////////////////
	//리스트 가져와서 뿌리기
	///////////////////////////////
	List<PersonVo> personList = phoneDao.personSelect();
	System.out.println(personList);
	
	response.sendRedirect("./list.jsp"); //등록 후 list화면으로 돌아감
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>	
</body>
</html>