$(document).ready(
		function() {

			$('#form-employee :submit').click(function(event) {
						// Evitamos que se envíe el formulario
						event.preventDefault();

						// Tomamos el número de workdays
						// y lo asignamos al campo oculto para enviarlo
						var selectedAsText = $('#workday-datalist').val();
						var valueAsLong = $(
								'#workday-list [value="' + selectedAsText
										+ '"]').data('value');
						$('#workday').val(valueAsLong);

						$('#form-employee').submit();
					});
		});
