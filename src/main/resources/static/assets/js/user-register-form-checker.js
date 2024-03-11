$(document).ready(function() {
    var debounceTimer;
    $('#username').on('keyup', function() {
        var username = $(this).val();

        // Verifica la longitud del nombre de usuario y caracteres permitidos
        var isValidLength = username.length >= 4 && username.length <= 16;
        // Asegura que solo contenga caracteres válidos y no tenga caracteres especiales consecutivos
        var hasValidChars = /^[a-zA-Z0-9]+([._-]?[a-zA-Z0-9]+)*$/.test(username);

        if(!isValidLength || !hasValidChars) {
            $('#loadingIcon').hide(); // Oculta el icono de carga
            if(!isValidLength) {
                $('#usernameFeedback').text('Tu nombre de usuario debe tener entre 4 y 16 caracteres').css('color', 'red').show();
            } else if(!hasValidChars) {
                $('#usernameFeedback').text('Nombre de usuario no permitido: Solo puede contener letras, números, y no más de un punto (.), guión (-) o guión bajo (_) consecutivos entre caracteres.').css('color', 'red').show();
            }
            $('#registerButton').prop('disabled', true);
            return; // Detiene la ejecución adicional de la función
        }

        $('#usernameFeedback').hide(); // Oculta el feedback mientras se realiza la verificación
        $('#loadingIcon').show(); // Muestra el spinner de carga
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(function() {
            $.get('/users/check-username', {username: username}, function(data) {
                $('#loadingIcon').hide(); // Oculta el spinner de carga
                var feedbackElement = $('#usernameFeedback');
                if(data === 'Disponible') {
                    feedbackElement.text('El nombre de usuario está disponible').css('color', 'green');
                    $('#registerButton').prop('disabled', false); //Habilita botón de registro
                } else {
                    feedbackElement.text('El nombre de usuario no está disponible').css('color', 'red');
                    $('#registerButton').prop('disabled', true); //Deshabilita botón de registro
                }
                feedbackElement.show();
            });
        }, 1000);
    });
});