document.getElementById("apiForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    fetch(`/app/hello?name=${encodeURIComponent(name)}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("response").innerHTML = data.message;
        })
        .catch(error => console.error("Error al obtener los datos:", error));
});