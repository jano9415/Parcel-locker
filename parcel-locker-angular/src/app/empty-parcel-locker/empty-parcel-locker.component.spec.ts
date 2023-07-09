import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmptyParcelLockerComponent } from './empty-parcel-locker.component';

describe('EmptyParcelLockerComponent', () => {
  let component: EmptyParcelLockerComponent;
  let fixture: ComponentFixture<EmptyParcelLockerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EmptyParcelLockerComponent]
    });
    fixture = TestBed.createComponent(EmptyParcelLockerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
