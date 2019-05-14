import { AbstractControl, ValidatorFn } from '@angular/forms';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class CustomNameValidatorService {
  forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const forbidden = nameRe.test(control.value);
      return forbidden ? { jhiForbiddenName: { value: control.value } } : null;
    };
  }
}
