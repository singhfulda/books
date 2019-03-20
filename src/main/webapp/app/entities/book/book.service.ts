import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IBook } from 'app/entities/book/book.model';

type EntityArrayResponseType = HttpResponse<IBook[]>;

@Injectable({ providedIn: 'root' })
export class BookService {
    constructor(private http: HttpClient) {}

    getAllBooks(): Observable<EntityArrayResponseType> {
        return this.http.get<IBook[]>('api/books', { observe: 'response' });
    }
}
