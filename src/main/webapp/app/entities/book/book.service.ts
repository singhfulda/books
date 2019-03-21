import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { IBook } from 'app/entities/book/book.model';

type EntityArrayResponseType = HttpResponse<IBook[]>;
type EntityResponseType = HttpResponse<IBook>;

@Injectable({providedIn: 'root'})
export class BookService {
    constructor(private http: HttpClient) {}

    getAllBooks(): Observable<EntityArrayResponseType> {
        return this.http.get<IBook[]>('api/books', { observe: 'response' });
    }

    put(book: IBook): Observable<EntityResponseType> {
        return this.http.put<IBook>('api/books', book, { observe: 'response'});
    }

    getbyid(id: number): Observable<EntityResponseType> {
        return this.http.get<IBook>(`api/books/${id}`, {observe: 'response'});
    }

    delete(id: number): Observable<any> {
        return this.http.delete<any>(`api/books/${id}`, {observe: 'response'});
    }

    post(book: IBook): Observable<EntityResponseType> {
        return this.http.post<IBook>('api/books', book, { observe: 'response'});
    }
}