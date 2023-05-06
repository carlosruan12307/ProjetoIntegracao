import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OffcanvasUserComponent } from './offcanvas-user.component';

describe('OffcanvasUserComponent', () => {
  let component: OffcanvasUserComponent;
  let fixture: ComponentFixture<OffcanvasUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ OffcanvasUserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OffcanvasUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
