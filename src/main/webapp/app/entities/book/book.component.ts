import { Component, OnInit } from '@angular/core';
import { BookService } from 'app/entities/book/book.service';
import { HttpResponse } from '@angular/common/http';
import { Book, IBook } from 'app/entities/book/book.model';

@Component({
    selector: 'jhi-book',
    templateUrl: './book.component.html'
})
export class BookComponent implements OnInit {
    books: IBook[];
    constructor(private bookService: BookService) {}

    ngOnInit(): void {
        this.bookService.getAllBooks().subscribe((res: HttpResponse<IBook[]>) => {
            this.books = res.body;
        });
    }
}
