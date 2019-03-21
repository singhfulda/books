export interface IBook {
    id?: number;
    name?: string;
    author?: string;
    price?: number;
    pages?: number;
}

export class Book implements IBook {
    constructor(public id?: number, public name?: string, public author?: string, public price?: number, public pages?: number) {}
}
