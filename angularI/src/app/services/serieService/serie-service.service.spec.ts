import { TestBed } from '@angular/core/testing';

import { SerieServiceService } from './serie-service.service';

describe('SerieServiceService', () => {
  let service: SerieServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SerieServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
