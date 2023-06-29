export class ParcelLockerDTO {

    id: number

    postCode: number

    county: string

    city: string

    street: string

    amountOfBoxes: number

    constructor(id: number, postCode: number, county: string, city: string, street: string, amountOfBoxes: number){
        this.id = id;
        this.postCode = postCode;
        this.county = county;
        this.city = city;
        this.street = street;
        this.amountOfBoxes = amountOfBoxes;

    }
}
