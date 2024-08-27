import ItemModel from "../model/ItemModel.js";

// Initialize selected row index
var selectedRowIndex = -1;

// Validate item form
function validateItemForm(id, description, price, qty) {
    let isValid = true;

    // Reset input styles and error messages
    $('input').removeClass('invalid-input');
    $('.error-message').hide();

    // Validate ID
    const idPattern = /^[a-zA-Z0-9]+$/;
    if (!id || !idPattern.test(id)) {
        $('#itemId').addClass('invalid-input');
        $('#itemIdError').text('ID must be alphanumeric.').show();
        isValid = false;
    }

    // Validate description
    if (!description) {
        $('#itemDescription').addClass('invalid-input');
        $('#itemDescriptionError').text('Description cannot be empty.').show();
        isValid = false;
    }

    // Validate price
    const pricePattern = /^\d+(\.\d{2})?$/;
    if (!price || !pricePattern.test(price)) {
        $('#unitPrice').addClass('invalid-input');
        $('#itemPriceError').text('Price must be a valid number.').show();
        isValid = false;
    }

    // Validate quantity
    const qtyPattern = /^\d+$/;
    if (!qty || !qtyPattern.test(qty)) {
        $('#qty').addClass('invalid-input');
        $('#itemQtyError').text('Quantity must be a positive number.').show();
        isValid = false;
    }

    return isValid;
}

// Update item form validation
function validateUpdateItemForm(id, description, price, qty) {
    let isValid = true;

    $('input').removeClass('invalid-input');
    $('.error-message').hide();

    // Validate ID
    const idPattern = /^[a-zA-Z0-9]+$/;
    if (!id || !idPattern.test(id)) {
        $('#itemEditId').addClass('invalid-input');
        $('#itemEditIdError').text('ID must be alphanumeric.').show();
        isValid = false;
    }

    // Validate description
    if (!description) {
        $('#itemEditDes').addClass('invalid-input');
        $('#itemEditDescriptionError').text('Description cannot be empty.').show();
        isValid = false;
    }

    // Validate price
    const pricePattern = /^\d+(\.\d{2})?$/;
    if (!price || !pricePattern.test(price)) {
        $('#itemEditPrice').addClass('invalid-input');
        $('#itemEditPriceError').text('Price must be a valid number.').show();
        isValid = false;
    }

    // Validate quantity
    const qtyPattern = /^\d+$/;
    if (!qty || !qtyPattern.test(qty)) {
        $('#itemEditqty').addClass('invalid-input');
        $('#itemEditQtyError').text('Quantity must be a positive number.').show();
        isValid = false;
    }

    return isValid;
}

// Load item table from backend
function loadItemTable() {
    $.ajax({
        url: 'http://localhost:8080/possystem/item?all=true',
        method: 'GET',
        dataType: 'json',
        success: function (data) {
            $("#item-tbl-body").empty();
            data.forEach(item => {
                var record = `
                    <tr>
                        <td class="item-id-value">${item.itemId}</td>
                        <td class="item-description-value">${item.description}</td>
                        <td class="item-price-value">${item.unitPrice}</td>
                        <td class="item-qty-value">${item.qtyOnHand}</td>
                    </tr>
                `;
                $("#item-tbl-body").append(record);
            });
        },
        error: function (xhr, status, error) {
            console.error('Failed to load items:', error);
        }
    });
}

// Add item
$("#btn-add-item").on('click', () => {
    var itemId = $("#itemId").val();
    var itemDescription = $("#itemDescription").val();
    var itemPrice = $("#unitPrice").val();
    var itemQty = $("#qty").val();

    if (!validateItemForm(itemId, itemDescription, itemPrice, itemQty)) {
        return;
    }

    let item = {
        itemId: itemId,
        description: itemDescription,
        unitPrice: parseFloat(itemPrice),
        qtyOnHand: parseInt(itemQty)
    };

    $.ajax({
        url: 'http://localhost:8080/possystem/item',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(item),
        success: function () {
            alert('Item added successfully');
            loadItemTable();
        },
        error: function (xhr, status, error) {
            console.error('Failed to add item:', error);
        }
    });
});

// Select row
$("#item-tbl-body").on('click', 'tr', function () {
    $("#item-tbl-body tr").removeClass("selected-row");
    $(this).addClass("selected-row");
    selectedRowIndex = $(this).index();

    let itemId = $(this).find(".item-id-value").text();
    let itemDescription = $(this).find(".item-description-value").text();
    let itemPrice = $(this).find(".item-price-value").text();
    let itemQty = $(this).find(".item-qty-value").text();

    $("#itemEditId").val(itemId);
    $("#itemEditDes").val(itemDescription);
    $("#itemEditPrice").val(itemPrice);
    $("#itemEditqty").val(itemQty);
});

// Apply selected row style
function applySelectedRowStyle() {
    $("#item-tbl-body tr").removeClass("selected-row");
    $("#item-tbl-body tr:eq(" + selectedRowIndex + ")").addClass("selected-row");
}

// Update item
$("#btn-update-item").on('click', () => {
    if (selectedRowIndex === -1) {
        alert("No row selected.");
        return;
    }

    var updatedItemId = $("#itemEditId").val();
    var updatedItemDescription = $("#itemEditDes").val();
    var updatedItemPrice = $("#itemEditPrice").val();
    var updatedItemQty = $("#itemEditqty").val();

    if (!validateUpdateItemForm(updatedItemId, updatedItemDescription, updatedItemPrice, updatedItemQty)) {
        return;
    }

    let item = {
        itemId: updatedItemId,
        description: updatedItemDescription,
        unitPrice: parseFloat(updatedItemPrice),
        qtyOnHand: parseInt(updatedItemQty)
    };

    $.ajax({
        url: 'http://localhost:8080/possystem/item',
        method: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify(item),
        success: function () {
            alert('Item updated successfully');
            loadItemTable();
            applySelectedRowStyle();
            selectedRowIndex = -1;
        },
        error: function (xhr, status, error) {
            console.error('Failed to update item:', error);
        }
    });
});

// Delete item
$("#btn-dlt-item").on('click', () => {
    if (selectedRowIndex === -1) {
        alert("No row selected.");
        return;
    }

    let itemId = $("#item-tbl-body tr:eq(" + selectedRowIndex + ")").find(".item-id-value").text();

    if (!confirm("Are you sure you want to delete this item?")) {
        return;
    }

    $.ajax({
        url: 'http://localhost:8080/possystem/item?id=' + itemId,
        method: 'DELETE',
        success: function () {
            alert('Item deleted successfully');
            loadItemTable();
            selectedRowIndex = -1;
        },
        error: function (xhr, status, error) {
            console.error('Failed to delete item:', error);
        }
    });
});

// Search item
$("#item-search-form").on('submit', (event) => {
    event.preventDefault();
    var searchId = $("#itemSearchBar").val().trim();

    $.ajax({
        url: 'http://localhost:8080/possystem/item?id=' + searchId,
        method: 'GET',
        dataType: 'json',
        success: function (item) {
            if (item) {
                $("#modalItemId").text(item.itemId);
                $("#modalItemDescription").text(item.description);
                $("#modalItemPrice").text(item.unitPrice);
                $("#modalItemQty").text(item.qtyOnHand);
                $("#itemModal").modal('show');
            } else {
                alert("Item not found.");
            }
        },
        error: function (xhr, status, error) {
            console.error('Failed to search item:', error);
        }
    });
});

// Load the item table initially
loadItemTable();

