import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParcelPickingUpComponent } from './parcel-picking-up.component';

describe('ParcelPickingUpComponent', () => {
  let component: ParcelPickingUpComponent;
  let fixture: ComponentFixture<ParcelPickingUpComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParcelPickingUpComponent]
    });
    fixture = TestBed.createComponent(ParcelPickingUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
