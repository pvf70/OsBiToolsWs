<%@page import="com.osbitools.ws.shared.TestConstants"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>

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
								      value="<%= TestConstants.TEST_USER %>" />
								</td>
							</tr>
							<tr>
								<td>Password:</td>
								<td>
								  <input type="password" name="pwd" 
								      value="<%= TestConstants.TEST_PASSWORD %>" />
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
	DsMap Version: <button onclick="ds_ver()">Get</button>
	                                     <span id="ds_ver"></span>
	                                     <hr>
  Language Label Version: <button onclick="ll_ver()">Get</button>
                                       <span id="ll_ver"></span>
  <hr>
  Connection: <select id="sql_conn"><option value="hsql">hsql</option></select> 
  SQL: <input id="sql" value="select * from test_data">&nbsp;
                     <button onclick="get_sql_columns()">Get Columns</button>
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
              <label for="ds_map_list">Predefined: </label>
              <select id="map_list" onchange="fill_map(this)">
                <option></option>
                <option value="bad">Bad Map</option>
                <option value="empty">Empty Map</option>
                <option value="full">Full Map</option>
              </select>
            </td>
          </tr>
					<tr>
						<td><label for="ds_map_name">Map Name: </label></td>
					</tr>
					<tr>
						<td>
						  <b id="ds_map_proj">test</b>
						  <b id="dot">.</b>
						  <input type="text" id="ds_map_name" />
							<button onclick="ds_map_read()">Read</button>
							<button onclick="ds_map_delete()">Delete</button></td>
					</tr>
					<tr>
						<td><input type="text" id="ds_map_rename_to" />
							<button onclick="ds_map_rename()">Rename To</button></td>
					</tr>
					<tr>
						<td><label for="ds_map_text">Map Text: </label></td>
					</tr>
					<tr>
						<td><textarea id="ds_map_text" rows="15" cols="100"></textarea></td>
					</tr>
					<tr>
						<td><label for="ds_map_file">Map File: </label> <input
							type="file" id="ds_map_file" /></td>
					</tr>
					<tr>
						<td>
							<button onclick="ds_map_create()">Create</button>
							<button onclick="ds_map_update()">Update</button>
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
          <option value="sort1.csv">sort1.csv</option>
          <option value="sort2.csv">sort2.csv</option>
          <option value="filter_complex.csv">filter_complex.csv</option>
          <option value="test.csv">test.csv</option>
        </select>
      </td>
      <td><button onclick="load_ex_file()">Load</button></td>
    </tr>
  </table>
  
  <hr />
  <form method="post" name="fCsvFileUpload" id="fCsvFileUpload"
    action="rest/ex_file?ext=csv&name=test.test.csv" 
                                        enctype="multipart/form-data" >
    CSV File Upload:
	  <input type="file" name="csv_file" />
	  <!-- <button type="button" onclick="upload_csv_file()">Upload</button> -->
	  <button type="submit">Upload</button>
  </form>
  
  <hr />
  <form method="post" name="fXmlFileUpload" id="fXmlFileUpload"
    action="rest/ds_map_utils?name=test.test.xml" 
                                        enctype="multipart/form-data" >
    XML File Upload:
    <input type="file" name="file" />
    <!-- <button type="button" onclick="upload_csv_file()">Upload</button> -->
    <button type="submit">Upload</button>
    <a href="rest/ds_map_utils?name=test.test.xml&check">Check</a>
  </form>
</body>
</html>