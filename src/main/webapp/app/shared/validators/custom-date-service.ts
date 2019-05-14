import { AbstractControl, ValidatorFn } from '@angular/forms';
import { Injectable } from '@angular/core';

/**
 * Classe para fazer validações com datas
 *
 */

@Injectable({ providedIn: 'root' })
export class CustomDateValidatorService {
  /**
   *
   * @param mesano //mes e ano mínimo
   * Este método valida se mm/yyyy informado é maior ou igual a um mínimo
   */
  expireDateValidator(mesano: string): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      const mesMin = mesano.substring(0, 2);
      const anoMin = mesano.substring(3, 7);
      if (control.value) {
        // se nao for nulo
        const mesInf = control.value.substring(0, 2);
        const anoInf = control.value.substring(3, 7);
        const isInvalid = anoInf < anoMin || (anoInf === anoMin && mesInf < mesMin);
        return isInvalid ? { validadeCart: { value: control.value } } : null;
      } else {
        return null;
      }
    };
  }
}
