document.getElementById("recipe-search-input").addEventListener("input", function () {
    const query = this.value;
    if (query.length >= 2) {  // Solo hacer la búsqueda si hay al menos 2 caracteres
        fetch(`/autocomplete?keyword=${query}`)
            .then(response => response.json())
            .then(data => {
                const resultsDiv = document.getElementById("search-results");
                resultsDiv.innerHTML = '';  // Limpiar resultados anteriores

                if (data.length > 0) {
                    data.forEach(recipe => {
                        const a = document.createElement("a");
                        a.href = `/recipes/${recipe.id}`;
                        a.textContent = recipe.name;
                        resultsDiv.appendChild(a);
                    });
                } else {
                    resultsDiv.innerHTML = '<p>No se encontraron resultados</p>';
                }
            });
    } else {
        document.getElementById("search-results").innerHTML = '';  // Limpiar si no hay texto
    }
});