import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewWorksPageComponent } from './view-works-page.component';

describe('ViewWorksPageComponent', () => {
  let component: ViewWorksPageComponent;
  let fixture: ComponentFixture<ViewWorksPageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewWorksPageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewWorksPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
