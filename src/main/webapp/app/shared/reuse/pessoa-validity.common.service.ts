import { Injectable } from '@angular/core';
import { Validators, FormGroup, FormBuilder } from '@angular/forms';
import { PessoaService } from 'app/entities/pessoa';
import { CustomCPFCNPJValidatorService } from '../validators/cpf-cnpj-validators.service';
import { Observable } from 'rxjs';
import { HttpParams, HttpResponse, HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';

type EntityResponseType = HttpResponse<any>;

@Injectable({ providedIn: 'root' })
export class PessoaValidityCommonService {
  public resourceUrl = SERVER_API_URL + 'api/pessoas';
  pessoa: any;
  tipoPessoa: string;

  constructor(protected http: HttpClient, protected customCPFCNPJValidatorService: CustomCPFCNPJValidatorService) {}

  createEditForm(fb: FormBuilder): FormGroup {
    const editForm = fb.group({
      id: [],
      tipo: [],
      cpf: [
        null,
        [Validators.pattern('^[0-9]{3}.?[0-9]{3}.?[0-9]{3}-?[0-9]{2}$'), this.customCPFCNPJValidatorService.isValidCpf()],
        [this.customCPFCNPJValidatorService.existingCpfValidator(this)] // Validação assincrona do cpf
      ],
      cnpj: [
        null,
        [Validators.pattern('^[0-9]{2}.?[0-9]{3}.?[0-9]{3}/?[0-9]{4}-?[0-9]{2}$'), this.customCPFCNPJValidatorService.isValidCnpj()],
        [this.customCPFCNPJValidatorService.existingCnpjValidator(this)] // Validação assincrona do cnpj
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
    const cpf = editForm.get('cpf');
    const cnpj = editForm.get('cnpj');

    editForm.get('tipo').valueChanges.subscribe(tipo => {
      if (tipo === 'FISICA') {
        nomeMeio.setValidators([Validators.required, Validators.minLength(2), Validators.maxLength(30)]);
        sobreNome.setValidators([Validators.required, Validators.minLength(2), Validators.maxLength(30)]);
        titulo.setValidators([Validators.required, Validators.minLength(3), Validators.maxLength(15)]);
        cnpj.setValue(null);
      } else {
        nomeMeio.setValidators([]);
        sobreNome.setValidators([]);
        titulo.setValidators([]);
        nomeMeio.setValue(null);
        sobreNome.setValue(null);
        titulo.setValue(null);
        cpf.setValue(null);
      }
      nomeMeio.updateValueAndValidity();
      sobreNome.updateValueAndValidity();
      titulo.updateValueAndValidity();
    });
    return editForm;
  }

  findWithCpf(cpf: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.pessoa && this.pessoa.id ? String(this.pessoa.id) : '0';
    parametros = parametros.set('cpf', cpf);
    parametros = parametros.append('id', id);
    parametros = parametros.append('tipoPessoa', this.tipoPessoa);
    const withcpf = 'withcpf';
    const url = `${this.resourceUrl}/${withcpf}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  findWithCnpj(cnpj: string): Observable<EntityResponseType> {
    let parametros: HttpParams = new HttpParams();
    const id = this.pessoa && this.pessoa.id ? String(this.pessoa.id) : '0';
    parametros = parametros.set('cnpj', cnpj);
    parametros = parametros.append('id', id);
    parametros = parametros.append('tipoPessoa', this.tipoPessoa);
    const withcnpj = 'withcnpj';
    const url = `${this.resourceUrl}/${withcnpj}`;
    const retorno = this.http.get<any>(url, { params: parametros, observe: 'response' });
    return retorno;
  }
  setPessoa(pessoa: any) {
    this.pessoa = pessoa;
  }
  setTipoPessoa(tipoPessoa: string) {
    this.tipoPessoa = tipoPessoa;
  }
}
