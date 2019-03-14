import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Cat } from 'app/shared/model/cat.model';
import { CatService } from './cat.service';
import { CatComponent } from './cat.component';
import { CatDetailComponent } from './cat-detail.component';
import { CatUpdateComponent } from './cat-update.component';
import { CatDeletePopupComponent } from './cat-delete-dialog.component';
import { ICat } from 'app/shared/model/cat.model';

@Injectable({ providedIn: 'root' })
export class CatResolve implements Resolve<ICat> {
    constructor(private service: CatService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Cat> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Cat>) => response.ok),
                map((cat: HttpResponse<Cat>) => cat.body)
            );
        }
        return of(new Cat());
    }
}

export const catRoute: Routes = [
    {
        path: 'cat',
        component: CatComponent,
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
        path: 'cat/:id/view',
        component: CatDetailComponent,
        resolve: {
            cat: CatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golaApp.cat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cat/new',
        component: CatUpdateComponent,
        resolve: {
            cat: CatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golaApp.cat.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'cat/:id/edit',
        component: CatUpdateComponent,
        resolve: {
            cat: CatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golaApp.cat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const catPopupRoute: Routes = [
    {
        path: 'cat/:id/delete',
        component: CatDeletePopupComponent,
        resolve: {
            cat: CatResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'golaApp.cat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
