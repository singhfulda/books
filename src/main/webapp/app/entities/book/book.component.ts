import { Component, OnInit } from '@angular/core';
import { BookService } from 'app/entities/book/book.service';
import { HttpResponse } from '@angular/common/http';
import { Book, IBook } from 'app/entities/book/book.model';
import { ActivatedRoute, Router } from '@angular/router';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

@Component({
    selector: 'jhi-book',
    templateUrl: './book.component.html'
})
export class BookComponent implements OnInit {
    id?: number | string;
    books: IBook[];
    constructor(private bookService: BookService, private route: ActivatedRoute, private router: Router) {}

    ngOnInit(): void {
        this.funct();
    }

    funct() {
        this.bookService.getAllBooks().subscribe((res: HttpResponse<IBook[]>) => {
            this.books = res.body;
        });
    }
    delete(id: number) {
        this.bookService.delete(Number(id)).subscribe(result => {
            this.funct();
        });
    }
}
