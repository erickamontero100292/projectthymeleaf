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

$(document).ready(
		function() {
				console.log("prueba");
			$('#form-employee :submit').click(function(event) {
						// Evitamos que se envíe el formulario
						/*event.preventDefault();

						// Tomamos el número de workdays
						// y lo asignamos al campo oculto para enviarlo
						var selectedAsText = $('#workday-datalist').val();
						var valueAsLong = $(
								'#workday-list [value="' + selectedAsText
										+ '"]').data('value');
						$('#workday').val(valueAsLong);

						$('#form-employee').submit();*/
						console.log("prueba2");
					});
			
		});
