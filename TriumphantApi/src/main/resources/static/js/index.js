
function toggleSidebar() {
    const sidebar = document.querySelector('.sideBar');

    sidebar.classList.toggle('active');
}


function openInsertModal() {
    document.getElementById("insertModal").style.display = "block";
}

function openCashModal(data) {
    document.getElementById("cashModal").style.display = "block";

    const itemId = data?.getAttribute('data-id');
    const genderVal = data?.getAttribute('data-gender');
    const birthdayVal = data?.getAttribute('data-birthday');
    const usernameVal = data?.getAttribute('data-username');
    const msisdnVal = data?.getAttribute('data-msisdn');
    const emailVal = data?.getAttribute('data-email');

    console.log("Open cash modal", itemId, genderVal, birthdayVal, usernameVal, msisdnVal, emailVal);


    $("#memberId").text(itemId);
    $("#genderVal").text(genderVal);
    $("#birthdayVal").text(birthdayVal);
    $("#usernameVal").text(usernameVal);
    $("#msisdnVal").text(msisdnVal);
    $("#emailVal").text(emailVal);


}

function closeCashModal() {
    document.getElementById("cashModal").style.display = "none";
}

function closeInsertModal() {
    document.getElementById("insertModal").style.display = "none";
}

function closeDetailsModal() {
    document.getElementById("detailsModal").style.display = "none";
}

function openUpdateModal(data) {

    document.getElementById("updateModal").style.display = "block";

    const itemId = data?.getAttribute('data-id');
    const genderVal = data?.getAttribute('data-gender');
    const birthdayVal = data?.getAttribute('data-birthday');

    // Your logic to open the update modal using the itemId
    console.log("Edit Item ID:", itemId);
    console.log("Edit Item Gender:", genderVal);
    console.log("Edit Item Birthday:", birthdayVal);

    document.getElementById("memberIdUpdate").value = itemId;
    document.getElementById("genderUpdate").value = genderVal;
    document.getElementById("birthdayUpdate").value = birthdayVal;


}

function closeUpdateModal() {
    document.getElementById("updateModal").style.display = "none";
}



$(document).ready(function() {
    const loadItems = () => {
        $.ajax({
            url: "/api/v1/Member/getAll",
            type: "GET",
            contentType: "application/json",
            success: function(response) {
                // Check if the data array has elements
                if (response && response.length >= 0) {
                    $(".id").text(response.id);
                    $(".username").text(response.username);
                    $(".msisdn").text(response.msisdn);
                    $(".gender").text(response.gender);
                    $(".birthday").text(response.birthday);
                    $(".email").text(response.email);

                    const itemCardFunc = (data) => {
                        let cardContainer = $(".mainCards");
                        cardContainer.empty();

                        if (data.length !== 0) {
                            data.forEach(items => {
                                const { username, msisdn, id, gender,birthday,email, } = items;
                                const card = `
                                    <div class="card">
                                    <div class="image">
                                        <img src="/img/userImage.jpg" alt="Item Image">
                                        </div>
                                        <div class="card-content">
                                            <input type="text" hidden id="${id}">
                                            <h2 class="header">Username</h2>
                                            <p class="username">${username}</p>
                                            <h2 class="header">MSISDN</h2>
                                            <p class="msisdn">${msisdn}</p>
                                             <h2 class="header">Gender</h2>
                                            <p class="price">${gender}</p>
                                             <h2 class="header">Date of Birth</h2>
                                            <p class="birthday">${birthday}</p>
                                             <h2 class="header">E-mail</h2>
                                            <p class="email">${email}</p>
                                            
                                            <button class="editBtn" data-id="${id}" data-gender="${gender}" data-birthday="${birthday}" onclick="openUpdateModal(this)">Edit Item</button>
                                            <button class="deleteBtn" data-id="${id}" onclick="deleteItem(this)">Delete Item</button>
                                            <button id="detailsBtn" data-id="${id}" data-email="${email}" data-msisdn="${msisdn}" data-username="${username}" data-birthday="${birthday}" data-gender="${gender}" onclick="getItemDetails(this)">Item Details</button>
                                            <button class="btn" data-id="${id}" data-email="${email}" data-msisdn="${msisdn}" data-username="${username}" data-birthday="${birthday}" data-gender="${gender}" onclick="openCashModal(this)">Add Cash</button>
                                        </div>
                                    </div>
                                `;

                                // Append each card to the container
                                cardContainer.append(card);
                            });
                        } else {
                            cardContainer.html('<p>NO ITEMS AVAILABLE FOR THIS PAGE</p>');
                        }
                    };

                    itemCardFunc(response);
                } else {
                    // Handle the case when there are no items in the response
                    console.error("No items found in the response.");
                }
            },
            error: function(error) {
                // Handle the error more gracefully
                console.error("AJAX request failed:", error);
            }
        });
    };

    // Call the Main Page function after it's defined
    loadItems();

    const loadTable = () => {
        $.ajax({
            url: "/api/v1/Member/getAll",
            type: "GET",
            contentType: "application/json",
            success: function(response) {
                // Check if the data array has elements
                if (response && response.length >= 0) {
                    $(".id").text(response.id);
                    $(".username").text(response.username);
                    $(".msisdn").text(response.msisdn);
                    $(".gender").text(response.gender);
                    $(".birthday").text(response.birthday);
                    $(".email").text(response.email);

                    const itemCardFunc = (data) => {
                        let tableContainer = $(".mainTables");
                        tableContainer.empty();

                        if (data.length !== 0) {
                            // Define the table head outside of the loop
                            const tableHead = `
            <table class="table table-bordered">
                <thead  class="table-dark">
                    <tr class="bg-primary">
                        <th scope="col">Username</th>
                        <th scope="col">MSISDN</th>
                        <th scope="col">Gender</th>
                        <th scope="col">Date of Birth</th>
                        <th scope="col">E-mail</th>
                        <th scope="col" style="padding-right:0 ">Actions</th>
                    </tr>
                </thead>
                <tbody>
        `;

                            // Append table head to the container
                            tableContainer.append(tableHead);

                            data.forEach(tables => {
                                const { username, msisdn, id, gender, birthday, email } = tables;
                                const tableRow = `
                <tr class="bg-primary">
                    <td>${username}</td>
                    <td>${msisdn}</td>
                    <td>${gender}</td>
                    <td>${birthday}</td>
                    <td>${email}</td>
                    <td colspan="2">
                        <button class="btn" data-id="${id}" data-gender="${gender}" data-birthday="${birthday}" onclick="openUpdateModal(this)">Edit Item</button>
                        <button class="btn" data-id="${id}" data-username="${username}" onclick="deleteItem(this)">Delete Item</button>
                        <button class="btn" id="detailsBtn" data-id="${id}" data-email="${email}" data-msisdn="${msisdn}" data-username="${username}" data-birthday="${birthday}" data-gender="${gender}" onclick="getItemDetails(this)">Item Details</button>
                        <button class="btn" data-id="${id}" data-email="${email}" data-msisdn="${msisdn}" data-username="${username}" data-birthday="${birthday}" data-gender="${gender}" onclick="openCashModal(this)">Add Cash</button>
                    </td>
                </tr>
            `;

                                // Append each row to the container
                                tableContainer.append(tableRow);
                            });

                            // Close the table body and table tag
                            tableContainer.append('</tbody></table>');
                        } else {
                            tableContainer.html('<p>NO ITEMS AVAILABLE FOR THIS PAGE</p>');
                        }
                    };

                    itemCardFunc(response);
                } else {
                    // Handle the case when there are no items in the response
                    console.error("No items found in the response.");
                }
            },
            error: function(error) {
                // Handle the error more gracefully
                console.error("AJAX request failed:", error);
            }
        });
    };
    loadTable()

});



function addMember() {
    let id = $("#insertId").val()
    let username = $('#username').val()
    let msisdn = $('#msisdn').val()
    let gender = $('#gender').val()
    let birthday = $('#birthday').val()
    let email = $('#email').val()

    let insertData = {
        "id": id,
        "username": username,
        "msisdn": msisdn,
        "gender": gender,
        "birthday": birthday,
        "email": email
    }

    console.log("Not YET success", insertData);


    $.ajax({
        url: "/api/v1/Member/addMember",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(insertData),
        success: function (insertData) {
            console.log("ggg success", insertData);

            alert("Item Added successfully");
            location.reload();
        },
        error: function (error) {
            console.log("Error updating item: ", error);
            // Additional error handling if needed
            console.log("ggg error", insertData);

        }
    });
}

$("#addMember").on("click", addMember);

function sendCash() {
    let cash = $('#cash').val()
    let month = $('#month').val()
    let memberId = $("#memberId").text()

    let cashId = $('#cashId').val()



    let cashData = {
        "id": cashId,
        "memberId": memberId,
        "cash": cash,
        "month": month,
    }

    console.log("Not YET successfully added cash", cashData);


    $.ajax({
        url: `/api/v1/Cash/sendCash`,
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(cashData),
        success: function (cashData) {
            console.log("ggg success", cashData);

            alert("cashData Added successfully");
            location.reload();
        },
        error: function (error) {
            console.log("Error adding cash: ", error);
            // Additional error handling if needed
            console.log("ggg error", cashData);

        }
    });
}

$("#sendCash").on("click", sendCash);


function updateItem() {


    // Get form data
    let formData = {
        id: $("#memberIdUpdate").val(),
        birthday: $("#birthdayUpdate").val(),
        gender: $("#genderUpdate").val(),
        username: $("#usernameUpdate").val(),
        msisdn: $("#msisdnUpdate").val(),
        email: $("#emailUpdate").val(),
    };



    // Make AJAX call
    $.ajax({
        type: "PUT",
        url: `/api/v1/Member/update`,
        contentType: "application/json",
        data: JSON.stringify(formData),
        success: function (formData) {
            console.log("ggg success", formData);

            alert("Update successful");
            location.reload();
        },
        error: function (error) {
            console.log("Error updating item: ", error);
            // Additional error handling if needed
            console.log("ggg error", formData);

        }
    });
}


function getItemDetails(detailsData) {

//get

    document.getElementById("detailsModal").style.display = "block";

    const itemId = detailsData?.getAttribute('data-id');
    const genderVal = detailsData?.getAttribute('data-gender');
    const birthdayVal = detailsData?.getAttribute('data-birthday');
    const usernameVal = detailsData?.getAttribute('data-username');
    const msisdnVal = detailsData?.getAttribute('data-msisdn');
    const emailVal = detailsData?.getAttribute('data-email');

    console.log("Edit Member Details:", itemId, genderVal, birthdayVal, usernameVal, msisdnVal, emailVal);

    document.getElementById("memberIdDetails").textContent = itemId;
    document.getElementById("genderDetails").textContent = genderVal;
    document.getElementById("birthdayDetails").textContent = birthdayVal;
    document.getElementById("usernameDetails").textContent = usernameVal;
    document.getElementById("msisdnDetails").textContent = msisdnVal;
    document.getElementById("emailDetails").textContent = emailVal;


}



function deleteItem(deleteData){

    console.log("Delete Not Yet successfull");
    const itemId = deleteData.getAttribute('data-id');
    const userName = deleteData.getAttribute('data-username');

    // Your logic to delete the item using the itemId
    console.log("Delete Item ID:", itemId, "and username", userName);


    $.ajax({
        url: `/api/v1/Member/delete/${itemId}`,
        type: "DELETE",
        contentType: "application/json",
        success: function (response) {
            console.log("Delete successful", response);

            alert("Delete successful");
            location.reload();
        },
        error: function (error) {
            console.log("Error deleting item: ", error);

        }
    });}
