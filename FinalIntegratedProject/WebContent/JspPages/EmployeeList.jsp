<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login Page</title>

<link href="<c:url value="/resources/css/claro.css" />" rel="stylesheet">

 

<!-- <link rel="stylesheet"
	href="http://dojotoolkit.org/reference-guide/1.9/_static/js/dijit/themes/claro/claro.css"> -->
	<link rel="stylesheet" href="http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojox/grid/resources/claroGrid.css"/>
<script>
	dojoConfig = {
		async : true,
		parseOnLoad : true
	}
</script>
  <script
	src='http://ajax.googleapis.com/ajax/libs/dojo/1.9.2/dojo/dojo.js'></script>  


<script>
	 

	function showDialog() {

		dijit.byId("dialogOne").show();
	}
</script>


<script>



	require([ "dojo/parser", "dojo/_base/xhr", "dojo/ready","dijit/Dialog","dojox/grid/DataGrid","dojo/data/ItemFileReadStore" ], function(parser,
			ajax, ready) {
		//require([ "dojo/ready"], function( ready){
		ready(function() {

			alert("hey in ready fun");
			//OnLoad load the grid
				dojo.xhrPost({
				url : "${pageContext.request.contextPath}/employeeListGrid",
				handleAs : "json",
				content : {
					firstParam : ""
					 
				},
				
				
				 load : function(response, ioArgs) {
				 
					alert("success");
					var newData = {	identifier: "employeeId",items: response	};
						
					var dataStore = new dojo.data.ItemFileReadStore({data: newData, id:"dataStoreId"});

					
					var grid = dijit.byId("gridId");
					grid.setStore(dataStore);
					  
				},error : function(response, ioArgs) {
					alert("An error occurred while invoking the service.");
				}
			});//GRID
			

			});
			
	});
		
		 
</script>




</head>
<body class="claro">

   <img alt="RRR" src="<c:url value="/resources/image/untitled.png" />">

	
	
	
<table  data-dojo-type="dojox.grid.DataGrid"   data-dojo-props="rowSelector:'20px'" id="gridId"    style="width: 800px; height: 200px;">
	    <thead>
	        <tr>
	            <th width="300px" field="firstName">First Name</th>
	            <th width="150px" field="lastName">Last Name</th>
	            <th width="100px" field="kinId">Kin Id</th> 
	            <th width="100px" field="education">Education</th> 
<!-- 	            <th width="50px" field="status" formatter="getActionColumn">Action </th> -->
<!--  	            <th width="50px" field="status"  formatter="deleteRow">Delete </th>   -->
	        </tr>
	        
	    </thead>
    
	</table>

	
	
	
</body>
</html>