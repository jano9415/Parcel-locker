import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OpenBoxesComponent } from './open-boxes.component';

describe('OpenBoxesComponent', () => {
  let component: OpenBoxesComponent;
  let fixture: ComponentFixture<OpenBoxesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [OpenBoxesComponent]
    });
    fixture = TestBed.createComponent(OpenBoxesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
