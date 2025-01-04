function getEmployees(drugstoreId) {
    const url = `http://localhost:8080/adminpanel/employees/${drugstoreId}`;

    fetch(url)
        .then(response => {
        if (!response.ok) {
            throw new Error('Błąd w pobieraniu danych');
        }
        return response.json();
    })
        .then(employees => {

        const tableBody = document.querySelector('#employeeTable tbody');
        tableBody.innerHTML = '';

        employees.forEach(employee => {
            const row = document.createElement('tr');

            const cellId = document.createElement('td');
            cellId.textContent = employee.id;

            const cellUsername = document.createElement('td');
            cellUsername.textContent = employee.username;

            const cellDrugstoreId = document.createElement('td');
            cellDrugstoreId.textContent = employee.drugstoreId;

            const cellRoles = document.createElement('td');
            cellRoles.textContent = employee.roles.map(role => role.name).join(', ');

            row.appendChild(cellId);
            row.appendChild(cellUsername);
            row.appendChild(cellDrugstoreId);
            row.appendChild(cellRoles);

            tableBody.appendChild(row);
        });
    })
        .catch(error => {
        console.error('Wystąpił błąd:', error);
    });
}