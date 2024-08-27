export default class CustomerModel{
    constructor(customerid, name, address, contact) {
        this._customerid = customerid;
        this._name = name;
        this._address = address;
        this._contact = contact;
    }

    get customerid() {
        return this._customerid;
    }

    get name() {
        return this._name;
    }

    get address() {
        return this._address;
    }

    get contact() {
        return this._contact;
    }

    set customerid(customerid) {
        this._customerid = customerid;
    }

    set name(name) {
        this._name = name;
    }

    set address(address) {
        this._address = address;
    }

    set contact(contact) {
        this._contact = contact;
    }

}
