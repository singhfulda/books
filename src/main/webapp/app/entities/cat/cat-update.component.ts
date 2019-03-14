import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ICat } from 'app/shared/model/cat.model';
import { CatService } from './cat.service';

@Component({
    selector: 'jhi-cat-update',
    templateUrl: './cat-update.component.html'
})
export class CatUpdateComponent implements OnInit {
    cat: ICat;
    isSaving: boolean;

    constructor(private catService: CatService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ cat }) => {
            this.cat = cat;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.cat.id !== undefined) {
            this.subscribeToSaveResponse(this.catService.update(this.cat));
        } else {
            this.subscribeToSaveResponse(this.catService.create(this.cat));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICat>>) {
        result.subscribe((res: HttpResponse<ICat>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
