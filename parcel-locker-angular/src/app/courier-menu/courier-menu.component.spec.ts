import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CourierMenuComponent } from './courier-menu.component';

describe('CourierMenuComponent', () => {
  let component: CourierMenuComponent;
  let fixture: ComponentFixture<CourierMenuComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CourierMenuComponent]
    });
    fixture = TestBed.createComponent(CourierMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
