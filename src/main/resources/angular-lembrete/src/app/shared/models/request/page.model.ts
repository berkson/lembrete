import { Sort } from './sort.model';

export class Page {
  constructor(
    public content: any,
    public empty: boolean,
    public first: boolean,
    public last: boolean,
    public number: number,
    public numberOfElements: number,
    public size: number,
    public sort: Sort,
    public totalElements: number,
    public totalPages: number
  ) {}
}
