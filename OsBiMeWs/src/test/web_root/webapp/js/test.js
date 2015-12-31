var BASE = "rest/";

function login(form) {
	$.ajax({
		url: BASE + "auth",
		method: "post",
		data: {
			usr: form.usr.value,
			pwd : form.pwd.value
		},
		success: function(data) {
			if (data == "") {
				var btn = $("#btn_login");
				btn.removeClass("red").addClass("green");
				window.setTimeout(function() {
					btn.removeClass("green").addClass("red");
				}, 600000);
			} else {
				check_data(data);
			}
			
		},
		error: show_http_error
	});
}

function check() {
	$.ajax({
		url: BASE + "auth",
		success: check_data,
		error: show_http_error
	});
}

function logout() {
	$.ajax({
		url: BASE + "logout",
		success: check_data,
		error: show_http_error
	});
}

function show_new_window(msg) {
	var win = window.open(null,'_blank',
		'toolbar=no,location=no,status=no,menubar=no,scrollbars=yes,' + 
							'status=no,resizable=yes,width=600,height=500');
	var doc = win.document;
	doc.write(msg);
}

function show_http_error(jqXHR, textStatus, errorThrown) {
	if (jqXHR.responseText) {
		show_new_window("Message: " + textStatus + "<br />Status: " + jqXHR.status + 
					"<br />Error:" + errorThrown + "<hr />" + jqXHR.responseText);
	} else {
		alert(textStatus + "\n" + jqXHR.status + ":" + errorThrown);
	}
}

function check_data(data) {
	if (data.error) {
		show_error(data.error);					
	} else {
		alert((data) ? data : "OK");
	}
}

function show_error(error) {	
	alert(get_data_error(error, false));
}

function show_data_error(error) {	
	alert(get_data_error(error, true));
}

function get_data_error(error, is_html) {
	var prefix = (is_html) ? "<b>" : "";
	var suffix = (is_html) ? "</b>" : "";
	var cr = (is_html) ? "<br />" : "\n";
	var tab = (is_html) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "\t";
	var err = prefix + "ERROR:" + suffix + " " + error.id;
	err += cr + prefix +"MESSAGE:" + suffix + " " + error.msg;
	if (error.info)
		err += cr + prefix +"INFO:" + suffix + " " + error.info;
	
	if (!(is_empty(error.details))) {
		err += cr + prefix +"DETAILS:" + suffix + " ";
		for (var msg in error.details)
			err += cr + tab + error.details[msg];
	}
	
	return err;
}

function is_empty(str) {
	return (str === undefined || str == "");
}

function test_config() {
	var list = $("#config").val();
	url = "cfg" + ((list) ? "?lst=" + list : "");
	
	$.ajax({
		url: BASE + url,
		dataType: "json",
		success: function (data) {
			show_config(data, url);
		},
		error: show_http_error
	});
}

function show_config(data, url) {
	var res = '<b>Config: </b><a href="' + url + 
			'" target="_blank" >' + url + "</a><br />";
	
	if (data.error)
		res += get_data_error(data.error);
	else {
		for (var idx in data)
			res += "&nbsp;&nbsp;&nbsp;" + idx + "=" + data[idx] + "<br />";
	}
	
	$("#test_res").html(res);
	
}

function test_ll(lang, labels) {
	var params = [];
	if (lang)
		params.push("lang=" + lang);
	if (labels)
		params.push("lbl=" + labels);
	
	url = "ll";
	if (params.length > 0)
		url += "?" + params.join("&");
	
	$.ajax({
		url: BASE + url,
		dataType: "json",
		success: function (data) {
			show_ll(data, url);
		},
		error: show_http_error
	});
}

function show_ll(data, url) {
	var res = '<b>Lang Labels: </b><a href="' + url + 
			'" target="_blank" >' + url + "</a><br />";
	
	if (data.error)
		res += get_data_error(data.error);
	else {
		for (var idx in data)
			res += "&nbsp;&nbsp;&nbsp;" + idx + "=" + data[idx] + "<br />";
	}
	
	$("#test_res").html(res);
	
}