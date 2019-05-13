import { Directive, Input, OnChanges, SimpleChanges } from '@angular/core';
import { AbstractControl, FormControl, NG_VALIDATORS, Validator, ValidatorFn, Validators } from '@angular/forms';

/** A hero's name can't match the given regular expression */
export function forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } => {
    const forbidden = nameRe.test(control.value);
    return forbidden ? { jhiForbiddenName: { value: control.value } } : null;
  };
}

@Directive({
  selector: '[jhiForbiddenName][ngModel]',
  providers: [{ provide: NG_VALIDATORS, useExisting: ForbiddenValidatorDirective, multi: true }]
})
export class ForbiddenValidatorDirective implements Validator {
  @Input() jhiForbiddenName: string;

  validate(control: FormControl): { [key: string]: any } {
    console.log('xxxxxxx-------xxxxxxxxx');
    return this.jhiForbiddenName ? forbiddenNameValidator(new RegExp(this.jhiForbiddenName, 'i'))(control) : null;
  }
}

/*
import { Directive, forwardRef } from '@angular/core';
import { NG_VALIDATORS, AbstractControl, ValidationErrors, Validator, FormControl } from '@angular/forms';

@Directive({
   selector: '[validCreditCard]',
   // We add our directive to the list of existing validators
   providers: [
     { provide: NG_VALIDATORS, useExisting: CreditCardValidator, multi: true }
   ]
})
export class CreditCardValidator implements Validator {*/
