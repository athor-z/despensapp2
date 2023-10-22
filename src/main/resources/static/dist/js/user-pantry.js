function openEditModal() {
	// Obtiene el nombre actual de la despensa
	var userPantryName = document.getElementById("userPantryName").textContent;

	// Prepara el modal para la edición
	var editedPantryName = document.getElementById("editedPantryName");
	editedPantryName.value = userPantryName;

	// Abre el modal
	$('#editModal').modal('show');
}

/* Función para actualizar el nombre de la despensa desde el modal */
function updatePantryName() {
	var newName = document.getElementById("editedPantryName").value;

	// Obtén el token CSRF del elemento meta en tu plantilla Thymeleaf
	var csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");

	$.ajax({
		type: "POST",
		url: "/updatePantryName",
		data: { newName: newName, _csrf: csrfToken },
		dataType: "json", // Especifica que se espera una respuesta JSON
		success: function(response) {
			if (response.success) {
				document.getElementById("userPantryName").textContent = newName;
				$('#editModal').modal('hide');
			} else {
				alert(response.error || "Error al actualizar el nombre de la despensa");
			}
		},
		error: function() {
			alert("Error de comunicación con el servidor");
		}
	});
}
















