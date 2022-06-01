import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { Contract, ContractService, HttpUtilService } from 'src/app/shared';
import { Page } from 'src/app/shared/models/request';
import { AditiveDialogComponent, ContractDialogComponent } from '../dialogs';

@Component({
  selector: 'app-listing',
  templateUrl: './listing.component.html',
  styleUrls: ['./listing.component.scss'],
})
export class ListingComponent implements OnInit {
  dataSource: MatTableDataSource<Contract>;
  columns!: string[];
  _itemCount: number = 0;
  _index: number = 0;
  _memoryIndex: number = 0;

  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;

  constructor(
    private contractService: ContractService,
    private httpUtils: HttpUtilService,
    private dialog: MatDialog
  ) {
    this.dataSource = new MatTableDataSource();
  }

  ngOnInit(): void {
    if (this.isAdmin()) {
      this.columns = [
        'id',
        'contract_number',
        'initial_date',
        'final_date',
        'contract_type',
        'company',
        'actions',
      ];
    } else {
      this.columns = [
        'contract_number',
        'initial_date',
        'final_date',
        'contract_type',
        'company',
        'actions',
      ];
    }
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  ngAfterViewInit(): void {
    this.queryData();
  }

  get itemCount() {
    return this._itemCount;
  }
  get index() {
    return this._index;
  }

  order() {
    this.queryData(0);
  }

  showButton(document: any): boolean {
    let contract: Contract = document;
    return this.contractService.canAdditivate(contract);
  }

  // TODO: Conclude dialog html
  queryData(firstPage?: number) {
    this.contractService
      .listAllContracts(
        firstPage !== undefined ? firstPage : this.paginator.pageIndex,
        this.sort.direction,
        this.sort.active ? this.sort.active : ''
      )
      .subscribe({
        next: (data) => {
          const page: Page = data;
          this._itemCount = page.totalElements;
          this._index = page.number;
          const contracts: [] = page.content.map((obj: any) =>
            Contract.fromObject(obj)
          );
          this.dataSource.data = contracts;
          this.dataSource._updatePaginator(page.totalElements);
        },
      });
  }

  isAdmin(): boolean {
    return this.httpUtils.isAdmin();
  }

  printRow(row: any): void {
    let contract: Contract = row as Contract;
    let dialogRef = this.dialog.open(ContractDialogComponent, {
      width: '80%',
      data: contract,
    });
  }

  showDialog(event: any, document: any): void {
    event.stopPropagation();
    let contract: Contract = document as Contract;
    let config: MatDialogConfig = {
      panelClass: 'additive-dialog-panel',
      data: contract,
    };
    let dialogRef = this.dialog.open(AditiveDialogComponent, config);
    dialogRef
      .afterClosed()
      .subscribe({ complete: () => this.queryData(this._index) });
  }
}
