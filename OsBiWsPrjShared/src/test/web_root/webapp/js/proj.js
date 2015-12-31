function osbi_me() {
	window.open("osbi_me");
}

function download_ex_file() {
	var cname = $("#ex_file").val();
	window.location = BASE + "ex_file?name=" + 
								get_cur_proj_name() + cname + "&dname=dat";
	// http_action_ex("ex_file", get_cur_proj_name() + cname, "get", "&dname=dat");
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
		url: BASE + "git?name=" + get_entity_name() + "&rev=" + rev,
		dataType: "json",
		success: function(data) {
			if (data.error) {
				show_error(data.error);					
			} else {
				$("#entity_text").removeClass("bg-green").addClass("bg-yellow").
												val(JSON.stringify(data));
			}
		},
		error: show_http_error
	});
}

function git_commit() {
	http_action_ex("git", get_entity_name(), "post", 
			"&comment=" + encodeURIComponent($("#comment").val()));
}

function git_log() {
	$.ajax({
		url: BASE + "git?name=" + get_entity_name(),
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

function entity_create() {
	entity_upload("put");
}

function entity_update() {
	entity_upload("post");
}

function entity_upload(type) {
	var name = $("#entity_name").val();
	if (is_empty(name)) {
		alert("DataSet Map is empty");
		return;
	}
	
	$.ajax({
        url: BASE + "entity?name=" + get_cur_proj_name() + name,
        type: type,
        data: $("#entity_text").val(),
        // cache: false,
        // processData: false, // Don't process the files
        contentType: false, // Set content type to false as jQuery will tell the server its a query string request
        success: check_data,
		error: show_http_error
	}); 
}

function entity_read() {
	$.ajax({
		url: BASE + "entity?name=" + get_entity_name(),
		dataType: "json",
		success: function(data) {
			if (data.error) {
				show_error(data.error);					
			} else {
				$("#entity_text").removeClass("bg-yellow").
						addClass("bg-green").val(JSON.stringify(data));
			}
		},
		error: show_http_error
	});
}

function entity_rename() {
	http_rename_post("entity");
}

function entity_delete() {
	entity_utils("delete");
}

function entity_download() {
	window.location.href = BASE + "entity_utils?name=" + get_entity_name();
}

function proj_utils(htype) {
	http_action("project", htype);
}

function get_entity_name() {
	return get_cur_proj_name() + $("#entity_name").val();
}

function entity_utils(htype) {
	http_action_ex("entity", get_entity_name(), htype);
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
	$("#entity_name").val(val + "." + EXT);
	
	$("#entity_text").removeClass("bg-yellow").removeClass("bg-green");
	
	if (val == "bad") {
		$("#entity_text").val("");
	} else if (val == "test1") {
		$("#entity_text").val("Test 1");
	} else if (val == "test2") {
		$("#entity_text").val("Test 2");
	}
}

function set_proj(proj_input) {
	var val = $(proj_input).val();
	if (is_empty(val))
		$("#dot").hide();
	else
		$("#dot").show();
	
	$("#proj_name").html($(proj_input).val());
}

function get_cur_proj_name() {
	var val = $("#project_name").val();
	return (is_empty(val)) ? "" : val + ".";
}