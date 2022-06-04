import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';

import { InterestedService } from './interested.service';

describe('InterestedService', () => {
  let service: InterestedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule, RouterTestingModule.withRoutes([])],
    });
    service = TestBed.inject(InterestedService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
