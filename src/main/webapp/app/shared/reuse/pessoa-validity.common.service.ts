import { Injectable } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { PessoaService } from 'app/entities/pessoa';
import { CustomCPFCNPJValidatorService } from '../validators/cpf-cnpj-validators.service';

@Injectable({ providedIn: 'root' })
export class PessoaValidityCommonService {
  constructor(protected pessoaService: PessoaService, protected customCPFCNPJValidatorService: CustomCPFCNPJValidatorService) {}

  createEditForm(fb: FormBuilder): FormGroup {
    const editForm = fb.group({
      id: [],
      tipo: [],
      cpf: [
        null,
        [Validators.pattern('^[0-9]{3}.?[0-9]{3}.?[0-9]{3}-?[0-9]{2}$'), this.customCPFCNPJValidatorService.isValidCpf()],
        [this.customCPFCNPJValidatorService.existingCpfValidator(this.pessoaService)] // Validação assincrona do cpf
      ],
      cnpj: [
        null,
        [Validators.pattern('^[0-9]{2}.?[0-9]{3}.?[0-9]{3}/?[0-9]{4}-?[0-9]{2}$'), this.customCPFCNPJValidatorService.isValidCnpj()],
        [this.customCPFCNPJValidatorService.existingCnpjValidator(this.pessoaService)] // Validação assincrona do cnpj
      ],
      primeiroNome: [null, [Validators.required, Validators.minLength(2), Validators.maxLength(20)]],
      nomeMeio: [],
      sobreNome: [],
      titulo: [],
      saudacao: [],
      cep: [null, [Validators.pattern('^[0-9]{2}.[0-9]{3}-[0-9]{3}$')]],
      tipoLogradouro: [],
      nomeLogradouro: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(100)]],
      complemento: [null, [Validators.required, Validators.minLength(0), Validators.maxLength(30)]],
      municipio: []
    });
    return editForm;
  }
  /**
   * Este método atribui validações específicas para pessoa física e jurídica
   * @param editForm
   * retorna o editForm recebido
   */
  setPessoaReValidity(editForm: FormGroup): FormGroup {
    const nomeMeio = editForm.get('nomeMeio');
    const sobreNome = editForm.get('sobreNome');
    const titulo = editForm.get('titulo');
    editForm.get('tipo').valueChanges.subscribe(tipo => {
      if (tipo === 'FISICA') {
        nomeMeio.setValidators([Validators.required, Validators.minLength(2), Validators.maxLength(30)]);
        sobreNome.setValidators([Validators.required, Validators.minLength(2), Validators.maxLength(30)]);
        titulo.setValidators([Validators.required, Validators.minLength(3), Validators.maxLength(15)]);
      } else {
        nomeMeio.setValidators([]);
        sobreNome.setValidators([]);
        titulo.setValidators([]);
        nomeMeio.setValue(null);
        sobreNome.setValue(null);
        titulo.setValue(null);
      }
      nomeMeio.updateValueAndValidity();
      sobreNome.updateValueAndValidity();
      titulo.updateValueAndValidity();
    });
    return editForm;
  }
}
