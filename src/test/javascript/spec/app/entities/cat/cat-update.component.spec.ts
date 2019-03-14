/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { GolaTestModule } from '../../../test.module';
import { CatUpdateComponent } from 'app/entities/cat/cat-update.component';
import { CatService } from 'app/entities/cat/cat.service';
import { Cat } from 'app/shared/model/cat.model';

describe('Component Tests', () => {
    describe('Cat Management Update Component', () => {
        let comp: CatUpdateComponent;
        let fixture: ComponentFixture<CatUpdateComponent>;
        let service: CatService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [GolaTestModule],
                declarations: [CatUpdateComponent]
            })
                .overrideTemplate(CatUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CatUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CatService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Cat(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cat = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Cat();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.cat = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
