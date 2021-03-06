import { Injectable } from '@angular/core';
import { Routes } from '@angular/router';

import { JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { BookComponent } from 'app/entities/book/book.component';
import { BookDetailComponent } from './book-detail/book-detail.component';

export const bookRoute: Routes = [
    {
        path: 'book',
        component: BookComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'golaApp.cat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'book/:id',
        component: BookDetailComponent,
        canActivate: [UserRouteAccessService]
    }
];
