import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { GolaSharedModule } from 'app/shared';
import { RouterModule } from '@angular/router';
import { bookRoute } from 'app/entities/book/book.route';
import { BookComponent } from 'app/entities/book/book.component';
import { BookService } from 'app/entities/book/book.service';

const ENTITY_STATES = [...bookRoute];

@NgModule({
    declarations: [BookComponent],
    imports: [GolaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    exports: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksModule {}
