function test_map(map) {
	var trace = ($("#trace").prop("checked")) ? "&trace=on" : "";
	var url = "ds?map=" + map + trace;
	
	// Check if extra parameters required
	if (MAP_PARAMS[map])
		for (var idx in MAP_PARAMS[map])
			url += "&" + idx + "=" + MAP_PARAMS[map][idx];
			
	$.ajax({
		url: BASE + url,
		dataType: "json",
		success: function(data) {
			test_map_ok(data, url);
		},
		error: show_http_error
	});
}

function test_columns(map) {
	var trace = ($("#trace").prop("checked")) ? "&trace=on" : "";
	var url = "map_info?map=" + map + trace;
	
	$.ajax({
		url: BASE + url,
		dataType: "json",
		success: function(data) {
			show_new_window(JSON.stringify(data.columns));
		},
		error: show_http_error
	});
}

function test_map_ok(data, url) {
	var res = "<b>Map: <font color=\"blue\">" + $("#maps").val() + 
		'</font></b><br/><b>URL:</b> <a href="' + url + 
			'" target="_blank" >' + url + "</a><br />" + "<b>Data:</b><br/>";
	
	if (data.error)
		res += get_data_error(data.error);
	else {
		
		
		var columns = "";
		for (var idx in data.columns)
			columns += "," + data.columns[idx];
		
		var dstr = "";
		for (var i in data.data) {
			var rstr = "";
			var row = data.data[i];
			for (var j in row)
				rstr += "," + row[j];
			dstr += "<p>[" + rstr.substr(1) + "]</p>";
		}
		
		res += "columns: [" + columns.substr(1) + "]<br />" + 
											"data: [" + dstr + "]";
		
		if (data.trace) {
			var trace = "";
			for (var idx in data.trace) {
				var tr = data.trace[idx];
				trace += "<p>" + tr.key + ": " + tr.duration + "</p>";
			}
			res += "</br>trace:" + trace;
			
		}
	}
	
	$("#test_res").html(res);
}