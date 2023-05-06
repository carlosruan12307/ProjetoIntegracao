import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalMoviesComponent } from './modal-movies.component';

describe('ModalMoviesComponent', () => {
  let component: ModalMoviesComponent;
  let fixture: ComponentFixture<ModalMoviesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ModalMoviesComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalMoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
