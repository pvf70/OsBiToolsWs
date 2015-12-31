var BASE = "rest/";

function login(form) {
  $.ajax({
    url: BASE + "auth",
    method: "post",
    data: {
      usr: form.usr.value,
      pwd : form.pwd.value
    },
    success: check_data,
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
  // Add datestamp to prevent caching
  $.ajax({
    url: BASE + "logout"  + "?" + new Date().getTime(),
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

function show_http_error(jqXHR, msg, error) {
  if (jqXHR.responseText) {
    alert("Message: " + msg + "\nStatus: " + jqXHR.status + 
          "\nError:" + error + "\n----------\n" + jqXHR.responseText);
  } else {
    alert(msg + "\n" + jqXHR.status + ":" + error);
  }
}

function check_data(data) {
  if (data.error) {
    if (data.error.id == 16) {
      // SAML Redirect
      var msg = data.error.info.split("?");
      var form = $('<form action="' + msg[0] + 
                    '" method="post"></form>');
      $("body").append(form);
      
      var input = $('<input type="hidden" name="SAMLRequest" />');
      input.val(decodeURIComponent(msg[1]));
      form.append(input);
      form.submit();
    } else {
      show_error(data.error);
    }
  } else {
    alert((data) ? data : "OK");
  }
}

function show_error(error) {  
  alert(get_data_error(error, false));
}

function show_data_error(error) { 
  show_test_res(get_data_error(error, true));
}

function get_data_error(error, is_html) {
  var prefix = (is_html) ? "<b>" : "";
  var suffix = (is_html) ? "</b>" : "";
  var cr = (is_html) ? "<br />" : "\n";
  var tab = (is_html) ? "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" : "\t";
  
  var err = (is_html) ? '<font color="red"><b>' : "";
  
  err += prefix + "ERROR:" + suffix + " " + error.id;
  err += cr + prefix +"MESSAGE:" + suffix + " " + error.msg;
  if (error.info)
    err += cr + prefix +"INFO:" + suffix + " " + error.info;
  
  if (!(is_empty(error.details))) {
    err += cr + prefix +"DETAILS:" + suffix + " ";
    for (var msg in error.details)
      err += cr + tab + error.details[msg];
  }
  
  return err + (is_html ? '</b></font>' : "");
;
}

function is_empty(str) {
  return (str === undefined || str == "");
}

function test_congig(list) {
  url = "cfg" + ((list) ? "?lst=" + list : "");
  
  $.ajax({
    url: BASE + url,
    dataType: "json",
    success: function (data) {
      if (data.error)
        show_data_error(data);
      else
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
  
  show_test_res(res);
}

function show_test_res(msg) {
  $("#test_res").html(msg).show();
}

function hide_test_res() {
  $("#test_res").hide();
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
