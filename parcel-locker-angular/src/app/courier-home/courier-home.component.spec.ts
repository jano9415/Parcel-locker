import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourierHomeComponent } from './courier-home.component';

describe('CourierHomeComponent', () => {
  let component: CourierHomeComponent;
  let fixture: ComponentFixture<CourierHomeComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourierHomeComponent]
    });
    fixture = TestBed.createComponent(CourierHomeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
