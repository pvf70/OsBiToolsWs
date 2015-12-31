<%@ page import="com.osbitools.ws.core.daemons.DsFilesCheck" %>
<%@include file="html/header.jsp"%>

<%
  // String[] ds_list = WsInit.getDsMapList();
  DsFilesCheck dcheck = (DsFilesCheck) pageContext.
                    getServletContext().getAttribute("dcheck");
  String[] ds_list = dcheck.getDsMapList();
  Arrays.sort(ds_list);
  pageContext.setAttribute("ds_list", ds_list);
%>

  <title>OsBiWs Core</title>
  
  <script type="text/javascript" src="js/maps.js"></script>
<body>
  <%@include file="html/auth.html"%>
  
  <!-- Map List -->
  <hr />
  <div>
    <form>
      <label for="maps">Maps</label>
        <select id="maps" name="maps">
          <option value=""></option>
           <option value="xxx">xxx</option>
        <c:forEach var="map" items="${ds_list}">
          <option value="<c:out value="${map}"/>"
            <c:if test="${map == def_map}"> 
          selected</c:if>><c:out value="${map}" /></option>
        </c:forEach>
      </select>
      <button onclick="test_map(this.form.maps.value)" type="button">Test</button>
      <button onclick="test_columns(this.form.maps.value)" 
          type="button">Columns</button><br />
      
      <!-- Lang Labels -->
      <label for="maps">Lang </label> 
      <select id="lang" name="lang">
        <option value=""></option>
         <option value="en">English</option>
         <option value="fr">Français</option>
         <option value="ru">Русский</option>
       </select>
       
       <label for="maps">Labels </label> 
       <select id="llabels" name="llabels">
         <option value=""></option>
         <option value="ll_lets_go">ll_lets_go</option>
         <option value="ll_username,ll_password">ll_username,ll_password</option>
       </select>
       
       <button onclick="test_ll(this.form.lang.value, this.form.llabels.value)" 
                 type="button">Test</button>
    </form>
    <div id="test_map"></div>
  </div>
</body>
</html>
