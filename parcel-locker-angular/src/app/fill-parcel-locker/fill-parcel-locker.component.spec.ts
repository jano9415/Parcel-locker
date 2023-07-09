import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FillParcelLockerComponent } from './fill-parcel-locker.component';

describe('FillParcelLockerComponent', () => {
  let component: FillParcelLockerComponent;
  let fixture: ComponentFixture<FillParcelLockerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FillParcelLockerComponent]
    });
    fixture = TestBed.createComponent(FillParcelLockerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
