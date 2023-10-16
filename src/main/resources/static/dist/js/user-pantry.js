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
function saveChanges() {
    var productPantryList = [];

    // Obtener los valores actualizados de cantidad y guardarlos en la lista
    var rows = document.querySelectorAll("[id^='quantity-']");
    rows.forEach(function(row) {
        var pantryId = row.closest("tr").querySelector(".pantryId").value;
        var productId = row.closest("tr").querySelector(".productId").value;
        var quantity = parseInt(row.value);
        productPantryList.push({ pantryId: pantryId, productId: productId, quantity: quantity });
    });

    // Obtener el token CSRF
    var csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");

    // Realizar una solicitud AJAX para guardar los cambios en el servidor
    $.ajax({
        type: "POST",
        url: "/updateProductPantry",
        data: JSON.stringify(productPantryList),
        contentType: "application/json",
        beforeSend: function(xhr) {
            xhr.setRequestHeader("X-CSRF-TOKEN", csrfToken); // Agregar el token CSRF en el encabezado de la solicitud
        },
        success: function(response) {
            if (response.success) {
                alert("Cambios guardados exitosamente");
            } else {
                alert("Error al guardar cambios");
            }
        },
        error: function() {
            alert("Error de comunicación con el servidor");
        }
    });
}


















