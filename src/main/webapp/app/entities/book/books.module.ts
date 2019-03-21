import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { GolaSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { bookRoute } from 'app/entities/book/book.route';
import { BookComponent } from 'app/entities/book/book.component';
import { BookService } from 'app/entities/book/book.service';
import { BookDetailComponent } from './book-detail/book-detail.component';

const ENTITY_STATES = [...bookRoute];

@NgModule({
    declarations: [BookComponent, BookDetailComponent],
    imports: [GolaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    exports: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksModule {}
