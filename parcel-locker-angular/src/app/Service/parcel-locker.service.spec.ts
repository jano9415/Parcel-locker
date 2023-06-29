import { TestBed } from '@angular/core/testing';

import { ParcelLockerService } from './parcel-locker.service';

describe('ParcelLockerService', () => {
  let service: ParcelLockerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParcelLockerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
