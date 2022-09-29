$(async function () {
    await thisUser();
});

async function thisUser() {
    fetch("http://localhost:8080/api/viewUserPage")
        .then(res => res.json())
        .then(data => {

            $('#headerUsername').append(data.name);
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#headerRoles').append(roles);

            let user = `$(
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.lastname}</td>
                <td>${data.age}</td>
                <td>${data.mail}</td>
                <td>${roles}</td>)`;
            $('#userPanelBody').append(user);
        })
}
