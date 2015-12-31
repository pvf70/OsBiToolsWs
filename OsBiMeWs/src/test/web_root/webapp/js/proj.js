function osbi_me() {
	window.open("osbi_me");
}

function ds_ver() {
	http_action("ds_ver", "get");
}

function ll_ver() {
	http_action("ll_ver", "get");
}

function get_sql_columns() {
	http_action_ex("ex_file", $("#sql_conn").val(), "get", 
			"&ext=sql&info=columns&param_sql=" + encodeURI($("#sql").val()));
}

function upload_csv_file() {
	document.fCsvFileUpload.action = BASE + "ex_file?ext=csv&name=" + name + 
			get_cur_proj_name() + "test.csv";
	document.fCsvFileUpload.submit();
}

function load_ex_file() {
	var cname = $("#ex_file").val();
	 http_action_ex("ex_file", get_cur_proj_name() + cname, "get", "&ext=csv");
}

function git_push() {
	git_remote_utils("push");
}

function git_pull() {
	git_remote_utils("pull");
}

function git_remote_utils(action) {
	$.ajax({
		url: BASE + "git?name=" + action,
		type: "put",
		success: check_data,
		error: show_http_error
	});
}

function show_rev(sel) {
	var rev = sel.value;
	if (rev == "") {
		alert("No Revision Selected");
		return;
	}
	
	$.ajax({
		url: BASE + "git?name=" + get_ds_map_name() + "&rev=" + rev,
		dataType: "json",
		success: function(data) {
			if (data.error) {
				show_error(data.error);					
			} else {
				$("#ds_map_text").removeClass("bg-green").addClass("bg-yellow").
												val(JSON.stringify(data));
			}
		},
		error: show_http_error
	});
}

function git_commit() {
	http_action_ex("git", get_ds_map_name(), "post", 
			"&comment=" + encodeURIComponent($("#comment").val()));
}

function git_log() {
	$.ajax({
		url: BASE + "git?name=" + get_ds_map_name(),
		dataType: "json",
		success: function(data) {
			if (data.error) {
				show_error(data.error);					
			} else {
				var logs = $("#git_log");
				logs.empty();
				for (var i in data) {
					var log = data[i];
					var id = log.id;
					logs.append('<option value="' + id + '">' + "ID:" + id +
						"; Comment: " + log.comment +
						"; Commited: " + new Date(log.commit_time * 1000) + 
						" by [" + log.committer + ']</option>');
				}
				logs.show();
			}
		},
		error: show_http_error
	});
}

function proj_read_all() {
	http_action_ex("project", "*", "get");
}

function proj_create() {
	proj_utils("put");
}
	
function proj_read() {
	proj_utils("get");
}

function proj_rename() {
	http_rename_post("project");	
}

function proj_delete() {
	proj_utils("delete");
}

function ds_map_create() {
	ds_map_upload("put");
}

function ds_map_update() {
	ds_map_upload("post");
}

function ds_map_upload(type) {
	var name = $("#ds_map_name").val();
	if (is_empty(name)) {
		alert("DataSet Map is empty");
		return;
	}
	
	$.ajax({
        url: BASE + "ds_map?name=" + get_cur_proj_name() + name,
        type: type,
        data: $("#ds_map_text").val(),
        // cache: false,
        // processData: false, // Don't process the files
        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
        success: check_data,
		error: show_http_error
	}); 
}

function ds_map_read() {
	// ds_map_utils("get");
	
	$.ajax({
		url: BASE + "ds_map?name=" + get_ds_map_name(),
		dataType: "json",
		success: function(data) {
			if (data.error) {
				show_error(data.error);					
			} else {
				$("#ds_map_text").removeClass("bg-yellow").
						addClass("bg-green").val(JSON.stringify(data));
			}
		},
		error: show_http_error
	});
}

function ds_map_rename() {
	http_rename_post("ds_map");
}

function ds_map_delete() {
	ds_map_utils("delete");
}

function proj_utils(htype) {
	http_action("project", htype);
}

function get_ds_map_name() {
	return get_cur_proj_name() + $("#ds_map_name").val();
}

function ds_map_utils(htype) {
	http_action_ex("ds_map", get_ds_map_name(), htype);
}

function http_rename_post(action) {
	$.ajax({
		url: BASE + action,
		method: "post",
		data: {
			name: get_cur_proj_name() + $("#" + action + "_name").val(),
			rename_to: get_cur_proj_name() + $("#" + action + 
								"_rename_to").val(),
		},
		success: check_data,
		error: show_http_error
	});
}

function http_action(action, htype) {
	http_action_ex(action, $("#" + action + "_name").val(), htype);
}

function http_action_ex(action, name, htype, params) {
	var pstr = (params === undefined) ? "" : params;
	
	$.ajax({
		url: BASE + action + "?name=" + name + pstr,
		method: htype,
		success: check_data,
		error: show_http_error
	});
}

function fill_map(sel) {
	var val = $(sel).val();
	$("#ds_map_name").val(val + ".xml");
	
	$("#ds_map_text").removeClass("bg-yellow").removeClass("bg-green");
	
	if (val == "bad") {
		$("#ds_map_text").val("bad");
	} else if (val == "empty") {
		$("#ds_map_text").val(
'<?xml version="1.0" encoding="UTF-8"?>' +
'<ds xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"' +
'	xsi:noNamespaceSchemaLocation="http://www.osbitools.com/xsd/ds.xsd" ' +
'	descr="Empty DataSet" enabled="false" ver_max="1" ver_min="0">' +
'</ds>'
		);
	} else if (val == "full") {
		$("#ds_map_text").val(
		'<?xml version="1.0" encoding="UTF-8"?>' +
'<ds xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' +
	'xsi:noNamespaceSchemaLocation="http://www.osbitools.com/xsd/ds.xsd" ' +
	'descr="Test DataSet" enabled="true" ver_max="1" ver_min="0">' +
		'' +
	'<lang_map>' +
		'<column name="LANG_US" />' +
		'<column name="LANG_RU" />' +
	'</lang_map>' +
	'' +
	'<ex_columns>' +
		'<auto_inc>' +
			'<column name="A11" />' +
			'<column name="B22" />' +
		'</auto_inc>' +
		'' +
		'<calc>' +
			'<column java_type="Integer" name="CALC1">A + B</column>' +
			'<column java_type="String" name="CALC2">C + D</column>' +
		'</calc>' +
	'</ex_columns>' +
	'' +
	'<sort_by_grp>' +
		'<sort_by idx="1" column="COL1" sequence="asc" unique="false" />' +
		'<sort_by idx="2" column="COL2" sequence="desc" unique="false" />' +
	'</sort_by_grp>' +
	'' +
	'<filter><![CDATA[A < B]]></filter>' +
	'' +
	'<group_ds>' +
		'<group_ds>' +
			'<group_ds>' +
				'<sort_by_grp>' +
					'<sort_by idx="1" column="COL1" />' +
				'</sort_by_grp>' +
				'<static_ds>' +
					'<static_data>' +
						'<columns>' +
							'<column name="COL1" java_type="java.lang.String" on_error="" />' +
							'<column name="COL2" java_type="java.lang.String" on_error="" />' +
						'</columns>' +
						'<static_rows>' +
							'<row>' +
								'<cell name="COL1" value="UUu" />' +
								'<cell name="COL2" value="ЦцЦ" />' +
							'</row>' +
							'<row>' +
								'<cell name="COL1" value="dDd" />' +
								'<cell name="COL2" value="ЗЗз" />' +
							'</row>' +
						'</static_rows>' +
					'</static_data>' +
				'</static_ds>' +
				'' +
				'<static_ds>' +
					'<static_data>' +
						'<columns>' +
							'<column name="COL1" java_type="java.lang.String" on_error="" />' +
							'<column name="COL2" java_type="java.lang.String" on_error="" />' +
						'</columns>' +
						'<static_rows>' +
							'<row>' +
								'<cell name="COL1" value="uuU" />' +
								'<cell name="COL2" value="Ццц" />' +
							'</row>' +
							'<row>' +
								'<cell name="COL1" value="Ddd" />' +
								'<cell name="COL2" value="ЗЗз" />' +
							'</row>' +
						'</static_rows>' +
					'</static_data>' +
				'</static_ds>' +
			'</group_ds>' +
			'<static_ds>' +
				'<static_data>' +
					'<columns>' +
						'<column name="COL1" java_type="java.lang.String" on_error="" />' +
						'<column name="COL2" java_type="java.lang.String" on_error="" />' +
					'</columns>' +
					'<static_rows>' +
						'<row>' +
							'<cell name="COL1" value="uUu" />' +
							'<cell name="COL2" value="цЦц" />' +
						'</row>' +
						'<row>' +
							'<cell name="COL1" value="Ddd" />' +
							'<cell name="COL2" value="ЗзЗ" />' +
						'</row>' +
					'</static_rows>' +
				'</static_data>' +
			'</static_ds>' +
			'' +
			'<static_ds>' +
				'<static_data>' +
					'<columns>' +
						'<column name="COL1" java_type="java.lang.String" on_error="" />' +
						'<column name="COL2" java_type="java.lang.String" on_error="" />' +
					'</columns>' +
					'<static_rows>' +
						'<row>' +
							'<cell name="COL1" value="uUu" />' +
							'<cell name="COL2" value="цЦц" />' +
						'</row>' +
						'<row>' +
							'<cell name="COL1" value="Ddd" />' +
							'<cell name="COL2" value="ЗзЗ" />' +
						'</row>' +
					'</static_rows>' +
				'</static_data>' +
			'</static_ds>' +
		'</group_ds>' +
		'<static_ds>' +
			'<lang_map>' +
				'<column name="LANG_FR" />' +
				'<column name="LANG_DE" />' +
			'</lang_map>' +
			'' +
			'<static_data>' +
				'<columns>' +
					'<column name="COL1" java_type="java.lang.String" on_error="ERROR !!!" />' +
					'<column name="COL2" java_type="java.lang.String" on_error="ERROR !!!" />' +
				'</columns>' +
				'<static_rows>' +
					'<row>' +
						'<cell name="COL1" value="bBb" />' +
						'<cell name="COL2" value="УуУ" />' +
					'</row>' +
					'<row>' +
						'<cell name="COL1" value="AaA" />' +
						'<cell name="COL2" value="пПп" />' +
					'</row>' +
				'</static_rows>' +
			'</static_data>' +
		'</static_ds>' +
	'</group_ds>' +
'</ds>');
	}
}

function set_proj(proj_input) {
	var val = $(proj_input).val();
	if (is_empty(val))
		$("#dot").hide();
	else
		$("#dot").show();
	
	$("#ds_map_proj").html($(proj_input).val());
}

function get_cur_proj_name() {
	var val = $("#project_name").val();
	return (is_empty(val)) ? "" : val + ".";
}