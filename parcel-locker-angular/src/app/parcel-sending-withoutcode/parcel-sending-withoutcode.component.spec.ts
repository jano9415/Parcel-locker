import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParcelSendingWithoutcodeComponent } from './parcel-sending-withoutcode.component';

describe('ParcelSendingWithoutcodeComponent', () => {
  let component: ParcelSendingWithoutcodeComponent;
  let fixture: ComponentFixture<ParcelSendingWithoutcodeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParcelSendingWithoutcodeComponent]
    });
    fixture = TestBed.createComponent(ParcelSendingWithoutcodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
