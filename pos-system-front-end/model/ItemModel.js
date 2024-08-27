export default class ItemModel{
    constructor(itemid, description, unitprice, qty) {
        this._itemid = itemid;
        this._description = description;
        this._unitprice = unitprice;
        this._qty = qty;
    }

    get itemid() {
        return this._itemid;
    }

    get description() {
        return this._description;
    }

    get unitprice() {
        return this._unitprice;
    }

    get qty() {
        return this._qty;
    }

    set itemid(itemid) {
        this._itemid = itemid;
    }

    set description(description) {
        this._description = description;
    }

    set unitprice(unitprice) {
        this._unitprice = unitprice;
    }

    set qty(qty) {
        this._qty = qty;
    }

}
