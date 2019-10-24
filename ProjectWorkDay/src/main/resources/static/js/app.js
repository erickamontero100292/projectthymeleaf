function open_delete(id, tipo) {

	var url = "";
	var doNothing = false;

	if (tipo === "E") {
		url = '/create/employee/delete/show/' + id;
	} else if (tipo === "W") {
		url = '/create/workday/delete/show/' + id;
	} else {
		doNothing = true;
	}

	if (!doNothing) {
		$.ajax({
			url : url,
			success : function(data) {
				$('#paraelmodal').html(data);
				$('#delete-modal').modal('show');
			}
		});
	}

}
