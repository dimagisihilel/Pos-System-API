import CustomerModel from "../model/CustomerModel.js";

// Initialize selected row index
var selectedRowIndex = -1

// Show customer management section by default
$('#customer-mng-section').css({display:'block'});
$('#item-mng-section').css({display:'none'});
$('#order-mng-section').css({display:'none'});

// Navigation click handlers
$('#navCustomer').on('click',()=>{
    $('#customer-mng-section').css({display:'block'});
    $('#item-mng-section').css({display:'none'});
    $('#order-mng-section').css({display:'none'});
});

$('#navItem').on('click',()=>{
    $('#customer-mng-section').css({display:'none'});
    $('#order-mng-section').css({display:'none'});
    $('#item-mng-section').css({display:'block'});
});

$('#navOrder').on('click',()=>{
    $('#customer-mng-section').css({display:'none'});
    $('#item-mng-section').css({display:'none'});
    $('#order-mng-section').css({display:'block'});
});

// Validate customer form
function validateCustomerForm(id, name, address, contact) {
    let isValid = true;

    // Reset input styles and error messages
    $('input').removeClass('invalid-input');
    $('.error-message').hide();

    // Validate ID
    const idPattern = /^[a-zA-Z0-9]+$/;
    if (!id || !idPattern.test(id)) {
        $('#cusId').addClass('invalid-input');
        $('#cusIdError').text('ID must contain only letters and numbers.').show();
        isValid = false;
    }

    // Validate name
    const namePattern = /^[a-zA-Z\s]+$/;
    if (!name || !namePattern.test(name)) {
        $('#cusName').addClass('invalid-input');
        $('#cusNameError').text('Name must contain only letters and spaces.').show();
        isValid = false;
    }

    // Validate address
    if (!address) {
        $('#cusAddress').addClass('invalid-input');
        $('#cusAddressError').text('Address cannot be empty.').show();
        isValid = false;
    }

    // Validate contact
    const contactPattern = /^\d{10}$/;
    if (!contact || !contactPattern.test(contact)) {
        $('#cusContact').addClass('invalid-input');
        $('#cusContactError').text('Contact must be exactly 10 digits.').show();
        isValid = false;
    }

    return isValid;
}

// Update customer form validation
function validateUpdateForm(id, name, address, contact) {
    let isValid = true;

    $('input').removeClass('invalid-input');
    $('.error-message').hide();

    // Validate ID
    const idPattern = /^[a-zA-Z0-9]+$/;
    if (!id || !idPattern.test(id)) {
        $('#cusEditId').addClass('invalid-input');
        $('#cusEditIdError').text('ID must be alphanumeric.').show();
        isValid = false;
    }

    // Validate name
    const namePattern = /^[a-zA-Z\s]+$/;
    if (!name || !namePattern.test(name)) {
        $('#cusEditName').addClass('invalid-input');
        $('#cusEditNameError').text('Name must contain only letters and spaces.').show();
        isValid = false;
    }

    // Validate address
    if (!address) {
        $('#cusEditAddress').addClass('invalid-input');
        $('#cusEditAddressError').text('Address cannot be empty.').show();
        isValid = false;
    }

    // Validate contact
    const contactPattern = /^\d{10}$/;
    if (!contact || !contactPattern.test(contact)) {
        $('#cusEditContact').addClass('invalid-input');
        $('#cusEditContactError').text('Contact must be exactly 10 digits.').show();
        isValid = false;
    }

    return isValid;
}

// Load customer table from backend
function loadTable() {
    $.ajax({
        url: 'http://localhost:8080/possystem/customer?all=true',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $("#customer-tbl-body").empty();
            data.forEach(item => {
                var record = `
                    <tr>
                        <td class="customer-id-value">${item.customerid}</td>
                        <td class="customer-name-value">${item.name}</td>
                        <td class="customer-address-value">${item.address}</td>
                        <td class="customer-contact-value">${item.contact}</td>
                    </tr>
                `;
                $("#customer-tbl-body").append(record);
            });
        },
        error: function (xhr, status, error) {
            console.error('Failed to load customers:', error);
        }
    });
}

// Add customer
$("#btn-add-customer").on('click', () => {
    var customerId = $("#cusId").val();
    var customerName = $("#cusName").val();
    var customerAddress = $("#cusAddress").val();
    var customerContact = $("#cusContact").val();

    if (!validateCustomerForm(customerId, customerName, customerAddress, customerContact)) {
        return;
    }

    let customer = {
        customerid: customerId,
        name: customerName,
        address: customerAddress,
        contact: customerContact
    };

    $.ajax({
        url: 'http://localhost:8080/possystem/customer',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(customer),
        success: function () {
            alert('Customer added successfully');
            loadTable();
        },
        error: function (xhr, status, error) {
            console.error('Failed to add customer:', error);
        }
    });
});

// Select row
$("#customer-tbl-body").on('click', 'tr', function () {
    $("#customer-tbl-body tr").removeClass("selected-row");
    $(this).addClass("selected-row");
    selectedRowIndex = $(this).index();

    let cusId = $(this).find(".customer-id-value").text();
    let cusName = $(this).find(".customer-name-value").text();
    let cusAddress = $(this).find(".customer-address-value").text();
    let cusContact = $(this).find(".customer-contact-value").text();

    $("#cusEditId").val(cusId);
    $("#cusEditName").val(cusName);
    $("#cusEditAddress").val(cusAddress);
    $("#cusEditContact").val(cusContact);
});

// Apply selected row style
function applySelectedRowStyle() {
    $("#customer-tbl-body tr").removeClass("selected-row");
    $("#customer-tbl-body tr:eq(" + selectedRowIndex + ")").addClass("selected-row");
}

// Update customer
$("#btn-update-customer").on('click', () => {
    if (selectedRowIndex === -1) {
        alert("No row selected.");
        return;
    }

    var updatedCustomerId = $("#cusEditId").val();
    var updatedCustomerName = $("#cusEditName").val();
    var updatedCustomerAddress = $("#cusEditAddress").val();
    var updatedCustomerContact = $("#cusEditContact").val();

    if (!validateUpdateForm(updatedCustomerId, updatedCustomerName, updatedCustomerAddress, updatedCustomerContact)) {
        return;
    }

    let customer = {
        customerid: updatedCustomerId,
        name: updatedCustomerName,
        address: updatedCustomerAddress,
        contact: updatedCustomerContact
    };

    $.ajax({
        url: 'http://localhost:8080/possystem/customer',
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(customer),
        success: function () {
            alert('Customer updated successfully');
            loadTable();
            applySelectedRowStyle();
            selectedRowIndex = -1;
        },
        error: function (xhr, status, error) {
            console.error('Failed to update customer:', error);
        }
    });
});

// Delete customer
$("#btn-dlt-customer").on('click', () => {
    if (selectedRowIndex === -1) {
        alert("No row selected.");
        return;
    }

    let customerId = $("#customer-tbl-body tr:eq(" + selectedRowIndex + ")").find(".customer-id-value").text();

    if (!confirm("Are you sure you want to delete this customer?")) {
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/possystem/customer?id=' + customerId,
        method: 'DELETE',
        success: function () {
            alert('Customer deleted successfully');
            loadTable();
            selectedRowIndex = -1;
        },
        error: function (xhr, status, error) {
            console.error('Failed to delete customer:', error);
        }
    });
});

// Search customer
$("#search-form").on('submit', (event) => {
    event.preventDefault();
    var searchId = $("#searchBar").val().trim();

    $.ajax({
        url: 'http://localhost:8080/possystem/customer?id=' + searchId,
        method: 'GET',
        dataType: 'json',
        success: function (customer) {
            if (customer) {
                $("#modalCustomerId").text(customer.customerid);
                $("#modalCustomerName").text(customer.name);
                $("#modalCustomerAddress").text(customer.address);
                $("#modalCustomerContact").text(customer.contact);
                $("#customerModal").modal('show');
            } else {
                alert("Customer not found.");
            }
        },
        error: function (xhr, status, error) {
            console.error('Failed to search customer:', error);
        }
    });
});

// Load table on page load
$(document).ready(() => {
    loadTable();
});
