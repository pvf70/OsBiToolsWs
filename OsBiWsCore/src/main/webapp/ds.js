(function($, osbi) {

  /**
   * Get Data Set from external resource
   *
   * @param {String} url base url of osbiws core web service 
   * @param {String} map name of dataset map
   * @param {Function} on_success ajax success handler
   * @param {Object} on_errors set of errors handlers as next:
   *    on_empty      - function to process empty data
   *    on_data_error - function to process data error
   *    on ajax_error - funtion to process ajax error request
   * @param {Object} params set of parameter to attach to ajax request
   *
   */
  osbi.get_ds = function(url, map, on_success, on_errors, params) {
    params = (params === undefined) ? {data:{}} : params;
    params.data["map"] = map + ".xml";
    
    var data = {
	    url: url + "/rest/ds",
	    data: params,
	    success: function(data) {
	      // Empty result is always error
	      if (data == undefined || data == null || data == "") {
	        if (on_errors !== undefined && 
	      		  typeof on_errors["on_empty"] == "function")
	          on_errors["on_empty"]();
	      } else if (data.error !== undefined) {
	        if (on_errors !== undefined && 
	      		  typeof on_errors["on_data_error"] == "function")
	          on_errors["on_data_error"](data.error);
	      } else {
	  if (typeof on_success == "function")
	          on_success(data);
	      }
	    },
	    error: function (jqXHR, msg, error) {
	      if (on_errors !== undefined && 
	      			typeof on_errors["on_ajax_error"] == "function")
	        on_errors["on_ajax_error"](jqXHR, msg, error);
	    }
    }
    
    for (pname in params)
    	data[pname] = params[pname];
    
    $.ajax(data);
  }

})(jQuery, jOsBiTools)
