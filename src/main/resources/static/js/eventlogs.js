function reloadLogs() {
    const date = document.getElementById("datePicker").value;

    let logsContainer = document.getElementById("logsContainer");

    let link = "http://localhost:8080/logs/" + date;

    fetch(link)
        .then(res => res.json())
        .then((resArray) => {
        if (Array.isArray(resArray)) {
            if (resArray.length == 0) {
                logsContainer.innerHTML = "\n<h2> BRAK ZDARZEŃ DNIA: " + date + "</h2>";
                return;
            }

            logsContainer.innerHTML = "";

            logsContainer.innerHTML +=
                        `<div class="download-buttons">
                            <a href="/logs/download/csv/2024-12-26">Pobierz jako CSV</a>
                            <a href="/logs/download/txt/2024-12-26">Pobierz jako TXT</a>
                        </div>`;

            logsContainer.innerHTML += `\n<textarea disabled>${resArray.join("\n")}</textarea>`;
        } else {
            logsContainer.innerHTMl = "\n<p>INVALID RESPONSE</p>";
        }
    })
        .catch(err => {
        logsContainer.innerHTMl = "\n<p>ERROR</p>";
        console.error("Error fetching data: ", err);
    });
}