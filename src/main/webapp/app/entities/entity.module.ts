import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { GolaCatModule } from './cat/cat.module';
import { BooksModule } from 'app/entities/book/books.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        GolaCatModule,BooksModule
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolaEntityModule {}
