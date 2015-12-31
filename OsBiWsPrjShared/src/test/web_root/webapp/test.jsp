<%@page import="com.osbitools.ws.shared.TestConstants"%>
<%@page import="com.osbitools.ws.shared.prj.utils.TestEntityUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Prj Login</title>

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/test.js"></script>
<script type="text/javascript" src="js/proj.js"></script>

<style type="text/css">
p {
	margin: 0;
	padding-left: 10px;
}

.green {
  color: green;
  font-weight: bold;
}

.red {
  color: red;
  font-weight: bold;
}

.bg-yellow {
  background-color: rgb(247, 247, 202);
}

.bg-green {
  background-color: rgb(181, 213, 181);
}

.hidden {
  display: none;
}

</style>

<script type="text/javascript">
  var EXT="<%=TestEntityUtils.BASE_EXT%>"
</script>
</head>
<body>
	<div>
		<form action="auth" method="post">
			<table>
				<tr>
					<td>
						<table
							style="border: 1px solid gray; margin: auto; margin-bottom: 10px">
							<tr>
								<td>User Name:</td>
								<td>
								    <input type="text" name="usr" 
								      value="<%=TestConstants.TEST_USER%>" />
								</td>
							</tr>
							<tr>
								<td>Password:</td>
								<td>
								  <input type="password" name="pwd" 
								      value="<%=TestConstants.TEST_PASSWORD%>" />
								</td>
							</tr>
							<tr>
							<td align="left">
                  <input type="button" id="btn_osbi_me"
                       value="OsBi Me" onclick="osbi_me()" />
								<td align="right" colspan="2">
								  <input type="button" id="btn_login"
									     value="Submit" onclick="login(this.form)" />
							  </td>
							</tr>
						</table>
					</td>
					<td style="padding-left: 10px;" valign="top">
            <input type="button" value="Check" 
                                        onclick="check()" /><br />
						<input type="button" value="Logout" 
						                            onclick="logout()" />
					</td>
				</tr>
			</table>
		</form>
    Config: <input id="config" value="conn">&nbsp;
                          <button onclick="test_config()">Get</button>
		<div id="test_res"></div>
	</div>
	<hr>
	<button onclick="proj_read_all()">Read All Projects</button>
	<label for="project_name">Project: </label>
	<input type="text" id="project_name" value="test" 
	                     onchange="set_proj(this)" />
	<button onclick="proj_create()">Create</button>
	<button onclick="proj_read()">Read</button>
	<button onclick="proj_delete()">Delete</button>
	<button onclick="proj_rename()">Rename To</button>
	<input type="text" id="project_rename_to" />

	<hr>
	<table border="0">
		<tr>
			<td>
				<table>
				  <tr>
            <td>
              <label for="entity_list">Predefined: </label>
              <select id="map_list" onchange="fill_map(this)">
                <option></option>
                <option value="empty">Empty File</option>
                <option value="test1">Test1 File</option>
                <option value="test2">Test2 File</option>
              </select>
            </td>
          </tr>
					<tr>
						<td><label for="entity_name">Map Name: </label></td>
					</tr>
					<tr>
						<td>
						  <b id="proj_name">test</b>
						  <b id="dot">.</b>
						  <input type="text" id="entity_name" />
							<button onclick="entity_read()">Read</button>
							<button onclick="entity_download()">Download</button>
							<button onclick="entity_delete()">Delete</button></td>
					</tr>
					<tr>
						<td><input type="text" id="entity_rename_to" />
							<button onclick="entity_rename()">Rename To</button></td>
					</tr>
					<tr>
						<td><label for="entity_text">Map Text: </label></td>
					</tr>
					<tr>
						<td><textarea id="entity_text" rows="15" cols="100"></textarea></td>
					</tr>
					<tr>
						<td><label for="entity_file">Map File: </label> <input
							type="file" id="entity_file" /></td>
					</tr>
					<tr>
						<td>
							<button onclick="entity_create()">Create</button>
							<button onclick="entity_update()">Update</button>
						</td>
					</tr>
					<tr>
            <td>
              <label for="comment">Comment: </label>
              <input type="text" id="comment" />
              <button onclick="git_commit()"><b>COMMIT</b></button>
              <button onclick="git_log()">Git Log</button><br /><br />
              <select id="git_log" class="hidden" size="5"
                multiple="multiple" ondblclick="show_rev(this)"></select>
            </td>
          </tr>
          <tr>
            <td>
              <button onclick="git_push()">Push to Remote</button>
              <button onclick="git_pull()">Pull from Remote</button>
            </td>
          </tr>
				</table>
			</td>
		</tr>
	</table>
	
	<hr />
  <table border="0">
    <tr>
      <td>CSV</td>
      <td>
        <select id="ex_file">
          <option value="f1.int">f1.int</option>
          <option value="f1.num">f1.num</option>
          <option value="f5.int">f5.int</option>
          <option value="f9.num">f9.num</option>
          <option value="t1_eng.txt">t1_eng.txt</option>
          <option value="t1_ru.txt">t1_ru.txt</option>
        </select>
      </td>
      <td><button onclick="download_ex_file()">Download</button></td>
    </tr>
  </table>
    
  <hr />
  <form method="post" name="fXmlFileUpload" id="fXmlFileUpload"
    action="rest/entity_utils?name=test.demo.<%=TestEntityUtils.BASE_EXT%>" 
                                            enctype="multipart/form-data" >
    <%=TestEntityUtils.BASE_EXT.toUpperCase()%> File Upload:
    <input type="file" name="file" />
    <button type="submit">Upload</button>
    <a href="rest/entity_utils?name=test.txt&check">Check</a>
  </form>
</body>
</html>