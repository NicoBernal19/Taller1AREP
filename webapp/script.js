document.getElementById("getForm").addEventListener("submit", function(e) {
    e.preventDefault();

    const name = document.getElementById("name").value;
    fetch(`/app/hello?name=${encodeURIComponent(name)}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById("response").innerHTML = data.message;
        })
        .catch(error => console.error("Error al obtener los datos:", error));
});

document.getElementById("postForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const name = document.getElementById("postname").value;
    fetch(`/app/hello`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: `name=${name}`
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById("postResponse").innerHTML = data.message;
        })
        .catch(error => console.error("Error al obtener los datos:", error));
});