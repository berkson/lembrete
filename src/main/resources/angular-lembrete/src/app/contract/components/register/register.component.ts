import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { Router } from '@angular/router';
import {
  ApiError,
  CnpjValidator,
  Company,
  CompanyService,
  Contract,
  ContractService,
  ContractType,
  ContractTypeService,
  CpfValidator,
  ErrorMessages,
  Interested,
  Messages,
  MessageService,
  PastOrPresentValidator,
  ValidationError,
} from 'src/app/shared';
import * as moment from 'moment';
import { map } from 'rxjs';
import { InterestedService } from 'src/app/shared/services/contract/interested.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss'],
})
export class RegisterComponent implements OnInit {
  basicForm: FormGroup = new FormGroup({});
  companyForm: FormGroup = new FormGroup({});
  contactForm: FormGroup = new FormGroup({});
  types: Array<ContractType> = [];
  contract: Contract;
  _show: boolean;
  _maxValidity: number;
  @ViewChild('contractNumberInput') contractNumberInput!: ElementRef;

  constructor(
    private router: Router,
    private fb: FormBuilder,
    private cotractTypeService: ContractTypeService,
    private contractService: ContractService,
    private interestedService: InterestedService,
    private companyService: CompanyService,
    private messageService: MessageService,
    private ref: ChangeDetectorRef
  ) {
    this.contract = new Contract(0);
    this._show = false;
    this._maxValidity = 0;
  }

  ngOnInit(): void {
    this.createForms();
    this.getTypes();
  }
  getTypes() {
    this.cotractTypeService.getAllContractTypes().subscribe({
      next: (data) => {
        this.types = data.contract_types;
      },
    });
  }

  get maxValidity() {
    return this._maxValidity;
  }

  get showContractTime() {
    return this._show;
  }

  get contractPlaceHolder(): string {
    return 20 + '/' + moment().year();
  }

  /**
   * Mostra o campo de tempo de contrato e seta o valor máximo desse campo
   * @param event evento de seleção
   */
  typeSelected(event?: any): void {
    if (event.value !== undefined) {
      this._show = true;
      this._maxValidity = event.value.max_validity;
      this.ref.detectChanges();
    } else {
      this._show = false;
      this._maxValidity = 0;
      this.ref.detectChanges();
    }
  }

  get interested() {
    return this.contactForm.get('interested') as FormArray;
  }

  get interestedSize() {
    return this.interested.length;
  }

  addInterested() {
    this.interested.push(
      this.fb.group({
        cpf: ['', [CpfValidator]],
        name: ['', [Validators.minLength(3)]],
        email: ['', [Validators.email]],
        phones: this.fb.array([this.fb.control('')]),
      })
    );
  }

  removeInterested(index: number) {
    this.interested.removeAt(index);
  }

  interestedPhones(index: number) {
    return this.interested.at(index).get('phones') as FormArray;
  }

  interestedPhonesSize(index: number) {
    return this.interestedPhones(index).length;
  }

  addPhone(index: number) {
    this.interestedPhones(index).push(this.fb.control(''));
  }

  removePhone(index: number) {
    let size = this.interestedPhonesSize(index);
    if (size > 1) this.interestedPhones(index).removeAt(size - 1);
  }

  createForms() {
    this.companyForm = this.fb.group({
      cnpj: ['', [CnpjValidator]],
      name: ['', [Validators.minLength(5), Validators.maxLength(255)]],
    });
    this.contactForm = this.fb.group({
      interested: this.fb.array([
        this.fb.group({
          cpf: ['', [CpfValidator]],
          name: ['', [Validators.minLength(3), Validators.maxLength(255)]],
          email: ['', [Validators.email]],
          phones: this.fb.array([this.fb.control('')]),
        }),
      ]),
    });
    this.basicForm = this.fb.group({
      contractNumber: [
        '',
        [Validators.required, Validators.pattern('^([\\d])+/([2][\\d]{3})$')],
      ],
      initialDate: ['', [Validators.required, PastOrPresentValidator]],
      contractType: ['', [Validators.required]],
      finalDate: ['', [Validators.required, Validators.max(60)]],
    });
  }

  private treatAndAddDates() {
    let baseDate = moment(
      this.basicForm.get('initialDate')?.value,
      'DD/MM/YYYY'
    );
    this.contract.initialDate = baseDate.format('YYYY-MM-DD');
    this.contract.finalDate = baseDate
      .add(this.basicForm.get('finalDate')?.value, 'months')
      .format('YYYY-MM-DD');
  }

  private AddBasicInfo() {
    this.treatAndAddDates();
    this.contract.contractNumber = this.basicForm.get('contractNumber')?.value;
    this.contract.contractType = this.basicForm.get('contractType')?.value;
  }

  private AddCompanyInfo() {
    this.contract.company = new Company(
      0,
      this.companyForm.get('cnpj')?.value,
      this.companyForm.get('name')?.value
    );
  }

  private AddInterested() {
    this.contract.interested = [];
    this.interested.controls.forEach((c) => {
      let person = new Interested(
        0,
        c.value.cpf,
        c.value.name,
        c.value.email,
        c.value.phones
      );
      this.contract.interested?.push(person);
    });
  }

  checkContract() {
    if (
      this.basicForm.get('contractNumber') !== null &&
      this.basicForm.get('contractNumber')?.value !== '' &&
      this.basicForm.get('contractNumber')?.valid
    ) {
      this.contractService
        .checkIfContractExists(this.basicForm.get('contractNumber')!.value)
        .subscribe({
          next: (data) => {
            if (data.body) {
              this.messageService.snackErrorMessage(
                ErrorMessages.contractAlreadyExists(
                  this.basicForm.controls['contractNumber']!.value
                )
              );
              this.basicForm.controls['contractNumber'].setValue('');
              this.contractNumberInput.nativeElement.focus();
            }
          },
          error: (err) => {
            try {
              let errors: ApiError[] = err.error.errors;
              this.messageService.showSnackErrors(errors);
            } catch (e) {
              this.messageService.snackErrorMessage(ErrorMessages.tryAgain);
            }
          },
        });
    }
  }

  loadCompany() {
    if (
      this.companyForm.get('cnpj')?.value !== null &&
      this.companyForm.get('cnpj')?.value !== '' &&
      this.companyForm.get('cnpj')?.valid
    ) {
      this.companyService
        .getCompany(this.companyForm.get('cnpj')!.value)
        .subscribe({
          next: (data) => {
            this.companyForm.get('name')?.setValue(data.name);
          },
          error: (err) => {
            if (err.error.errors[0].status === 400) {
              this.companyForm.get('name')?.setValue('');
            }
          },
        });
    }
  }

  loadInterested(index: number) {
    if (
      this.interested.controls[index].get('cpf') !== null &&
      this.interested.controls[index].get('cpf')?.value !== '' &&
      this.interested.controls[index].get('cpf')?.valid
    ) {
      this.interestedService
        .getInterested(this.interested.controls[index].get('cpf')?.value)
        .subscribe({
          next: (data) => {
            this.interested.controls[index].get('name')!.setValue(data.name);
            this.interested.controls[index].get('email')!.setValue(data.email);
            for (let i = 0; i < data.phones.length; i++) {
              if (i > 0) this.interestedPhones(index).push(this.fb.control(''));
              this.interestedPhones(index).controls[i].setValue(
                data.phones[i].tel
              );
            }
          },
          error: (err) => {
            if (err.error.errors[0].status === 400) {
              this.interested.controls[index].get('name')!.setValue('');
              this.interested.controls[index].get('email')!.setValue('');
              this.interestedPhones(index).controls[0].setValue('');
              if (this.interestedPhones(index).controls.length > 1) {
                this.interestedPhones(index).controls.splice(
                  1,
                  this.interestedPhones(index).controls.length - 1
                );
              }
            }
          },
        });
    }
  }

  register() {
    this.AddBasicInfo();
    this.AddCompanyInfo();
    this.AddInterested();
    if (
      !this.basicForm.valid ||
      !this.companyForm.valid ||
      !this.contactForm.valid
    ) {
      return;
    }
    this.contractService
      .registerContract(this.contract)
      .pipe(
        map((response) => {
          if (response.status == 200) {
            this.messageService.snackSuccessMessage(
              Messages.registerContractSuccess
            );
            this.navigateToList();
          }
        })
      )
      .subscribe({
        error: (err) => {
          this.messageService.handleValidationErrors(err.error.errors);
        },
      });
  }
  navigateToList() {
    this.router.navigate(['/contract']);
  }
}
