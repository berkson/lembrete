import { Sort } from './sort.model';

export class Pageable {
  constructor(
    public offset: number,
    public pageNumber: number,
    public pageSize: number,
    public paged: boolean,
    public sort: Sort,
    public unpaged: boolean
  ) {}
}
