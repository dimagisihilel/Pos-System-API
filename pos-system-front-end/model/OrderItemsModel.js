export default class OrderItemsModel{
    constructor(itemId, description, unitPrice, quantityOnHand, orderQuantity, totalPrice) {
        this._itemId = itemId;
        this._description = description;
        this._unitPrice = unitPrice;
        this._quantityOnHand = quantityOnHand;
        this._orderQuantity = orderQuantity;
        this._totalPrice = totalPrice;
    }

    // Getters
    get itemId() {
        return this._itemId;
    }

    get description() {
        return this._description;
    }

    get unitPrice() {
        return this._unitPrice;
    }

    get quantityOnHand() {
        return this._quantityOnHand;
    }

    get orderQuantity() {
        return this._orderQuantity;
    }

    get totalPrice() {
        return this._totalPrice;
    }

    // Setters
    set itemId(itemId) {
        this._itemId = itemId;
    }

    set description(description) {
        this._description = description;
    }

    set unitPrice(unitPrice) {
        this._unitPrice = unitPrice;
    }

    set quantityOnHand(quantityOnHand) {
        this._quantityOnHand = quantityOnHand;
    }

    set orderQuantity(orderQuantity) {
        this._orderQuantity = orderQuantity;
    }

    set totalPrice(totalPrice) {
        this._totalPrice = totalPrice;
    }



}
