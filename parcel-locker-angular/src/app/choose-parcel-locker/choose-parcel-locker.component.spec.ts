import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChooseParcelLockerComponent } from './choose-parcel-locker.component';

describe('ChooseParcelLockerComponent', () => {
  let component: ChooseParcelLockerComponent;
  let fixture: ComponentFixture<ChooseParcelLockerComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ChooseParcelLockerComponent]
    });
    fixture = TestBed.createComponent(ChooseParcelLockerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
