import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICat } from 'app/shared/model/cat.model';

@Component({
    selector: 'jhi-cat-detail',
    templateUrl: './cat-detail.component.html'
})
export class CatDetailComponent implements OnInit {
    cat: ICat;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ cat }) => {
            this.cat = cat;
        });
    }

    previousState() {
        window.history.back();
    }
}
