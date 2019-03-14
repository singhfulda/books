import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GolaSharedModule } from 'app/shared';
import {
    CatComponent,
    CatDetailComponent,
    CatUpdateComponent,
    CatDeletePopupComponent,
    CatDeleteDialogComponent,
    catRoute,
    catPopupRoute
} from './';

const ENTITY_STATES = [...catRoute, ...catPopupRoute];

@NgModule({
    imports: [GolaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [CatComponent, CatDetailComponent, CatUpdateComponent, CatDeleteDialogComponent, CatDeletePopupComponent],
    entryComponents: [CatComponent, CatUpdateComponent, CatDeleteDialogComponent, CatDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GolaCatModule {}
