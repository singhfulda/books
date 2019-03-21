import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../book.service';
import { IBook, Book } from '../book.model';

@Component({
  selector: 'jhi-book-detail',
  templateUrl: './book-detail.component.html',
  styles: []
})
export class BookDetailComponent implements OnInit {
     id?: number | string ;
     book?: IBook;

  constructor(private route: ActivatedRoute,
    private bookService: BookService,
    private router: Router) { }

  ngOnInit() {
    this.route.params.subscribe( params => {
       this.id = params['id'];
       if (this.id === 'new' ) {
           this.book = new Book();
       } else {
        this.bookService.getbyid(Number(this.id)).subscribe( result => {
          this.book = result.body;
     });
    }
    });
  }
  update() {
    this.bookService.put(this.book).subscribe( result => {
      this.book = result.body;
    });
  }
  delete() {
    this.bookService.delete(Number(this.id)).subscribe( result => {
        this.router.navigate(['../'], { relativeTo: this.route});
    });
  }
  create() {
    this.bookService.post(this.book).subscribe( result => {
      this.book = result.body;
    });
  }
}
