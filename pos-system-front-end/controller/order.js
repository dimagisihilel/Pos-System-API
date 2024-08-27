$(document).ready(function() {
    loadNextOrderId();
    loadCustomers();
    loadItems();


    // Load customer details when a customer ID is selected
    $("#customerIdDropdown").on('change', function () {
        let selectedCustomerId = $(this).val();
        console.log("Selected Customer ID:", selectedCustomerId);

        if (selectedCustomerId) {
            $.ajax({
                url: `http://localhost:8080/possystem/customer?id=${selectedCustomerId}`,
                method: 'GET',
                success: function (data) {
                    console.log("Selected Customer:", data);
                    if (data && data.name) {
                        $("#customerName").val(data.name);
                    } else {
                        $("#customerName").val(''); // Clear the field if no data
                    }
                },
                error: function (error) {
                    console.error('Error fetching customer details:', error);
                    $("#customerName").val(''); // Clear the field on error
                }
            });
        } else {
            $("#customerName").val(''); // Clear the field if no ID is selected
        }
    });



    //  Load item details when an item ID is selected
    $("#itemIdDropdown").change(function () {
        let itemId = $(this).val();
        console.log("Selected Item ID:", itemId);

        if (itemId) {
            $.ajax({
                url: `http://localhost:8080/possystem/item?id=${itemId}`,
                method: 'GET',
                success: function (data) {
                    console.log("Selected Item:", data);
                    if (data) {
                        $("#itemOdDescription").val(data.description || '');
                        $("#itemOdUnitPrice").val(data.unitPrice || '');
                        $("#itemOdQty").val(data.qtyOnHand || '');
                    } else {
                        $("#itemOdDescription").val('');
                        $("#itemOdUnitPrice").val('');
                        $("#itemOdQty").val('');
                    }
                },
                error: function (err) {
                    console.error('Error fetching item details:', err);
                    // Clear fields if there's an error
                    $("#itemOdDescription").val('');
                    $("#itemOdUnitPrice").val('');
                    $("#itemOdQty").val('');
                }
            });
        } else {
            $("#itemOdDescription").val('');
            $("#itemOdUnitPrice").val('');
            $("#itemOdQty").val('');
        }
    });
});

//Load next order Id
function loadNextOrderId() {
    $.ajax({
        url: "http://localhost:8080/possystem/order?nextId=true&date=true",
        method: "GET",
        data: {
            nextId: true,
            date:true
        },
        success: function(response) {
            // Assuming the response is just the next order ID as a plain string
            $("#orderId").val(response);
        },
        error: function(error) {
            console.error("Error loading next order ID:", error);
        }
    });
}


//Load customers into dropdown
function loadCustomers() {
    $.ajax({
        url: 'http://localhost:8080/possystem/customer?all=true',
        method: 'GET',
        success: function(data) {
            $("#customerIdDropdown").empty();
            $("#customerIdDropdown").append('<option value="" selected disabled>Select Customer Id</option>');
            data.forEach(customer => {
                $("#customerIdDropdown").append(`<option value="${customer.customerid}">${customer.customerid}</option>`);
            });
        },
        error: function(err) {
            console.error('Error fetching customers:', err);
        }
    });
}

//Load items into dropdown
function loadItems() {
    $.ajax({
        url: 'http://localhost:8080/possystem/item?all=true',
        method: 'GET',
        success: function (data) {
            $("#itemIdDropdown").empty();
            $("#itemIdDropdown").append('<option value="" selected disabled>Select Item Id</option>');
            data.forEach(item => {
                $("#itemIdDropdown").append(`<option value="${item.itemId}">${item.itemId}</option>`);
            });
        },
        error: function (err) {
            console.error('Error fetching items:', err);
        }
    });
}


// Calculate total price when order quantity is entered
$("#itemOdOrderQty").on('input', function () {
    let unitPrice = parseFloat($("#itemOdUnitPrice").val());
    let orderQty = parseInt($("#itemOdOrderQty").val());
    if (!isNaN(unitPrice) && !isNaN(orderQty)) {
        let totalPrice = unitPrice * orderQty;
        $("#itemOdTotPrice").val(totalPrice.toFixed(2));
    }
});

// Add item to the table and reduce quantity on hand
$("#btnAddItem").on('click', function () {
    let selectedItemId = $("#itemIdDropdown").val();
    let orderQty = parseInt($("#itemOdOrderQty").val());
    let totalPrice = parseFloat($("#itemOdTotPrice").val());

    if (selectedItemId && orderQty > 0 && totalPrice > 0) {
        $.ajax({
            url: `http://localhost:8080/possystem/item?id=${selectedItemId}`,
            method: 'GET',
            success: function(item) {
                if (item.qtyOnHand >= orderQty) {
                    // Reduce quantity on hand
                    item.qtyOnHand -= orderQty;

                    // Add item details to the table
                    let newRow = `
                        <tr>
                            <th scope="row">${item.itemId}</th>
                            <td>${item.description}</td>
                            <td>Rs.${item.unitPrice}</td>
                            <td>${orderQty}</td>
                            <td>${totalPrice.toFixed(2)}</td>
                            <td><button type="button" class="btn btn-danger btnDelete">Delete</button></td>
                        </tr>
                    `;
                    $(".PurchaseTbl tbody").append(newRow);

                    // Update total
                    updateTotal();

                    // Update item quantity on the server
                    $.ajax({
                        url: `http://localhost:8080/possystem/item`,
                        method: 'PUT',
                        contentType: 'application/json',
                        data: JSON.stringify({
                            itemId: item.itemId,
                            description: item.description,
                            unitPrice: item.unitPrice,
                            qtyOnHand: item.qtyOnHand
                        }),
                        success: function() {
                            console.log('Item quantity updated successfully.');
                        },
                        error: function(err) {
                            console.error('Error updating item quantity:', err);
                        }
                    });
                } else {
                    alert('Not enough quantity on hand.');
                }
            },
            error: function(err) {
                console.error('Error fetching item details:', err);
            }
        });
    }
});

// Function to update the total amount
function updateTotal() {
    let total = 0;
    $(".PurchaseTbl tbody tr").each(function () {
        let rowTotal = parseFloat($(this).find("td:nth-child(5)").text().replace('Rs.', ''));
        total += rowTotal;
    });
    $("#total").val(total.toFixed(2));
    $("#discount").val('');
    $("#newTotal").val('');
    $("#paidAmount").val('');
    $("#balance").val('');
}

// Function to update new total after applying discount percentage
function updateNewTotal() {
    let total = parseFloat($("#total").val()) || 0;
    let discountPercent = parseFloat($("#discount").val()) || 0;

    if (discountPercent > 0) {
        let discountAmount = (total * discountPercent) / 100;
        let newTotal = total - discountAmount;
        $("#newTotal").val(newTotal.toFixed(2));
    } else {
        $("#newTotal").val('');
    }
}

// Function to update balance based on paid amount
function updateBalance() {
    let newTotal = parseFloat($("#newTotal").val()) || 0;
    let paidAmount = parseFloat($("#paidAmount").val()) || 0;

    if (paidAmount > 0) {
        let balance = paidAmount - newTotal;
        $("#balance").val(balance.toFixed(2));
    } else {
        $("#balance").val('');
    }
}
// Update new total when discount is changed
$("#discount").on('input', function () {
    updateNewTotal();
});

// Update balance when paid amount is changed
$("#paidAmount").on('input', function () {
    updateBalance();
});

// Delete button function
$(document).on('click', '.btnDelete', function () {
    let row = $(this).closest('tr');
    let itemId = row.find("th").text();
    let orderQty = parseInt(row.find("td:nth-child(4)").text());

    // Restore quantity on hand
    $.ajax({
        url: `http://localhost:8080/possystem/item?id=${itemId}`,
        method: 'GET',
        success: function(item) {
            item.qtyOnHand += orderQty;

            // Update item quantity on the server
            $.ajax({
                url: `http://localhost:8080/possystem/item`,
                method: 'PUT',
                contentType: 'application/json',
                data: JSON.stringify({
                    itemId: item.itemId,
                    description: item.description,
                    unitPrice: item.unitPrice,
                    qtyOnHand: item.qtyOnHand
                }),
                success: function() {
                    console.log('Item quantity restored successfully.');
                },
                error: function(err) {
                    console.error('Error restoring item quantity:', err);
                }
            });
        },
        error: function(err) {
            console.error('Error fetching item details:', err);
        }
    });

    // Remove row from table
    row.remove();

    // Update total
    updateTotal();
});
// Function to clear form inputs
function clearFormInputs() {
    $("#customerIdDropdown").val('');
    $("#customerName").val('');
    $("#itemIdDropdown").val('');
    $("#itemOdDescription").val('');
    $("#itemOdUnitPrice").val('');
    $("#itemOdQty").val('');
    $("#itemOdOrderQty").val('');
    $("#itemOdTotPrice").val('');
    $("#total").val('');
    $("#discount").val('');
    $("#newTotal").val('');
    $("#paidAmount").val('');
    $("#balance").val('');
    $(".PurchaseTbl tbody").empty();
}


//purchase order function
$("#btnpurchase").on('click', function () {
    let orderId = $("#orderId").val(); // Use the auto-generated order ID
    let customerId = $("#customerIdDropdown").val();
    let orderDate = $("#orderDate").val();
    let total = parseFloat($("#total").val());
    let discount = parseFloat($("#discount").val());
    let newTotal = parseFloat($("#newTotal").val());
    let paidAmount = parseFloat($("#paidAmount").val());
    let balance = parseFloat($("#balance").val());

    let orderItems = [];
    $(".PurchaseTbl tbody tr").each(function () {
        let itemId = $(this).find("th").text();
        let description = $(this).find("td:nth-child(2)").text();
        let unitPrice = parseFloat($(this).find("td:nth-child(3)").text().replace('Rs.', ''));
        let orderQty = parseInt($(this).find("td:nth-child(4)").text());
        let total = parseFloat($(this).find("td:nth-child(5)").text());

        let orderItem = {
            itemId: itemId,
            description: description,
            unitPrice: unitPrice,
            orderQty: orderQty,
            total: total
        };
        orderItems.push(orderItem);
    });

    // Send order data to the order endpoint
    $.ajax({
        url: 'http://localhost:8080/possystem/order',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            orderId: orderId,
            customerId: customerId,
            orderDate: orderDate,
            total: total,
            discount: discount,
            newTotal: newTotal,
            paidAmount: paidAmount,
            balance: balance
        }),
        success: function(response) {
            // If order creation is successful, send each order detail individually
            orderItems.forEach(function (orderItem) {
                $.ajax({
                    url: 'http://localhost:8080/possystem/order-detail',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                        orderId: orderId,
                        itemId: orderItem.itemId,
                        qty: orderItem.orderQty,
                        unitPrice: orderItem.unitPrice,
                        total: orderItem.total
                    }),
                    success: function() {
                        console.log('Order detail submitted successfully for item ID:', orderItem.itemId);
                    },
                    error: function(err) {
                        console.error('Error submitting order detail for item ID:', orderItem.itemId, err);
                    }
                });
            });

            // Display the bill in the modal
            let billContent = `
                <p>Order ID: ${orderId}</p>
                <p>Customer ID: ${customerId}</p>
                <p>Order Date: ${orderDate}</p>
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Item ID</th>
                            <th scope="col">Description</th>
                            <th scope="col">Unit Price</th>
                            <th scope="col">Order Quantity</th>
                            <th scope="col">Total</th>
                        </tr>
                    </thead>
                    <tbody>
            `;

            $(".PurchaseTbl tbody tr").each(function () {
                let itemId = $(this).find("th").text();
                let description = $(this).find("td:nth-child(2)").text();
                let unitPrice = $(this).find("td:nth-child(3)").text();
                let orderQty = $(this).find("td:nth-child(4)").text();
                let total = $(this).find("td:nth-child(5)").text();

                billContent += `
                    <tr>
                        <td>${itemId}</td>
                        <td>${description}</td>
                        <td>${unitPrice}</td>
                        <td>${orderQty}</td>
                        <td>${total}</td>
                    </tr>
                `;
            });

            billContent += `
                    </tbody>
                </table>
                <p>Total: Rs. ${total.toFixed(2)}</p>
                <p>Discount: ${discount}%</p>
                <p>New Total: Rs. ${newTotal.toFixed(2)}</p>
                <p>Paid Amount: Rs. ${paidAmount.toFixed(2)}</p>
                <p>Balance: Rs. ${balance.toFixed(2)}</p>
            `;

            $("#billContent").html(billContent);
            $('#billModal').modal('show');

            // Increment the order ID counter
            loadNextOrderId();
            clearFormInputs();
        },
        error: function(err) {
            console.error('Error submitting order:', err);
        }
    });
});






