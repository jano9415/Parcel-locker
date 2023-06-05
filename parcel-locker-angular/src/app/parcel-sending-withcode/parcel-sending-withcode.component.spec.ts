import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ParcelSendingWithcodeComponent } from './parcel-sending-withcode.component';

describe('ParcelSendingWithcodeComponent', () => {
  let component: ParcelSendingWithcodeComponent;
  let fixture: ComponentFixture<ParcelSendingWithcodeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ParcelSendingWithcodeComponent]
    });
    fixture = TestBed.createComponent(ParcelSendingWithcodeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
