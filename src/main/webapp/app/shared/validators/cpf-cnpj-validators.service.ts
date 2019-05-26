import { Injectable } from '@angular/core';
import { PessoaService } from 'app/entities/pessoa';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { IPessoa } from '../model/pessoa.model';
import { AsyncValidatorFn, AbstractControl, ValidatorFn, ValidationErrors } from '@angular/forms';

/**
 * Classe para fazer validações no dv de cpf e cnpj
 *
 */

@Injectable({ providedIn: 'root' })
export class CustomCPFCNPJValidatorService {
  /**
   * Método pafa validar o cpf.
   * O cpf deve estar com máscara.
   * A funcao retira a máscara
   */
  isValidCpf(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      if (control.value) {
        const cpf = control.value.replace(/\D/g, ''); // retirar mascara do cpf
        let numbers, digits, sum, i, result, equalDigits;
        equalDigits = 1;
        if (cpf.length < 11) {
          return null;
        }

        for (i = 0; i < cpf.length - 1; i++) {
          if (cpf.charAt(i) !== cpf.charAt(i + 1)) {
            equalDigits = 0;
            break;
          }
        }

        if (!equalDigits) {
          numbers = cpf.substring(0, 9);
          digits = cpf.substring(9);
          sum = 0;
          for (i = 10; i > 1; i--) {
            sum += numbers.charAt(10 - i) * i;
          }

          result = sum % 11 < 2 ? 0 : 11 - (sum % 11);

          if (result !== Number(digits.charAt(0))) {
            return { cpfNotValid: { value: control.value } };
          }
          numbers = cpf.substring(0, 10);
          sum = 0;

          for (i = 11; i > 1; i--) {
            sum += numbers.charAt(11 - i) * i;
          }
          result = sum % 11 < 2 ? 0 : 11 - (sum % 11);

          if (result !== Number(digits.charAt(1))) {
            return { cpfNotValid: { value: control.value } };
          }
          return null;
        } else {
          return { cpfNotValid: { value: control.value } };
        }
      }
      return null;
    };
  }
  /**
   * Método pafa validar o cnpj.
   * O cpf deve estar com máscara.
   * A funcao retira a máscara
   */

  isValidCnpj(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } => {
      if (control.value) {
        const cnpj = control.value.replace(/\D/g, ''); // retirar mascara do cpf
        let tamanho, numeros, digitos, soma, pos, i, resultado;

        if (cnpj.length < 14) {
          return null;
        }
        // Elimina CNPJs invalidos conhecidos
        if (
          cnpj === '00000000000000' ||
          cnpj === '11111111111111' ||
          cnpj === '22222222222222' ||
          cnpj === '33333333333333' ||
          cnpj === '44444444444444' ||
          cnpj === '55555555555555' ||
          cnpj === '66666666666666' ||
          cnpj === '77777777777777' ||
          cnpj === '88888888888888' ||
          cnpj === '99999999999999'
        ) {
          return { cnpjNotValid: { value: control.value } };
        }
        // Valida DVs
        tamanho = cnpj.length - 2;
        numeros = cnpj.substring(0, tamanho);
        digitos = cnpj.substring(tamanho);
        soma = 0;
        pos = tamanho - 7;
        for (i = tamanho; i >= 1; i--) {
          soma += numeros.charAt(tamanho - i) * pos--;
          if (pos < 2) {
            pos = 9;
          }
        }
        resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        if (resultado !== Number(digitos.charAt(0))) {
          return { cnpjNotValid: { value: control.value } };
        }
        tamanho = tamanho + 1;
        numeros = cnpj.substring(0, tamanho);
        soma = 0;
        pos = tamanho - 7;
        for (i = tamanho; i >= 1; i--) {
          soma += numeros.charAt(tamanho - i) * pos--;
          if (pos < 2) {
            pos = 9;
          }
        }
        resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
        if (resultado !== Number(digitos.charAt(1))) {
          return { cnpjNotValid: { value: control.value } };
        }

        return null;
      }
    };
  }
  /**
   *
   * @param pessoaService
   * Test if cpf is already in use by another person
   */

  existingCpfValidator(pessoaService: PessoaService): AsyncValidatorFn {
    return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
      return pessoaService
        .findWithCpf(control.value)
        .pipe(map(res => (control.value && res && res.body > 0 ? { cpfAlreadyInUse: { value: control.value } } : null)));
    };
  }

  /**
   *
   * @param pessoaService
   */
  existingCnpjValidator(pessoaService: PessoaService): AsyncValidatorFn {
    return (control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> => {
      return pessoaService
        .findWithCnpj(control.value)
        .pipe(map(res => (control.value && res && res.body > 0 ? { cnpjAlreadyInUse: { value: control.value } } : null)));
    };
  }
}
