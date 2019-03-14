export interface ICat {
    id?: number;
    name?: string;
    price?: number;
    author?: string;
}

export class Cat implements ICat {
    constructor(public id?: number, public name?: string, public price?: number, public author?: string) {}
}
