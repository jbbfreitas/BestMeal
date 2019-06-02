/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { RestauranteService } from 'app/entities/restaurante/restaurante.service';
import { IRestaurante, Restaurante, TipoPessoa, TipoLogradouro } from 'app/shared/model/restaurante.model';

describe('Service Tests', () => {
  describe('Restaurante Service', () => {
    let injector: TestBed;
    let service: RestauranteService;
    let httpMock: HttpTestingController;
    let elemDefault: IRestaurante;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(RestauranteService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Restaurante(
        0,
        'image/png',
        'AAAAAAA',
        TipoPessoa.FISICA,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        TipoLogradouro.RUA,
        'AAAAAAA',
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: elemDefault });
      });

      it('should create a Restaurante', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Restaurante(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a Restaurante', async () => {
        const returnedFromService = Object.assign(
          {
            logo: 'BBBBBB',
            tipo: 'BBBBBB',
            cpf: 'BBBBBB',
            cnpj: 'BBBBBB',
            primeiroNome: 'BBBBBB',
            nomeMeio: 'BBBBBB',
            sobreNome: 'BBBBBB',
            saudacao: 'BBBBBB',
            titulo: 'BBBBBB',
            cep: 'BBBBBB',
            tipoLogradouro: 'BBBBBB',
            nomeLogradouro: 'BBBBBB',
            complemento: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should return a list of Restaurante', async () => {
        const returnedFromService = Object.assign(
          {
            logo: 'BBBBBB',
            tipo: 'BBBBBB',
            cpf: 'BBBBBB',
            cnpj: 'BBBBBB',
            primeiroNome: 'BBBBBB',
            nomeMeio: 'BBBBBB',
            sobreNome: 'BBBBBB',
            saudacao: 'BBBBBB',
            titulo: 'BBBBBB',
            cep: 'BBBBBB',
            tipoLogradouro: 'BBBBBB',
            nomeLogradouro: 'BBBBBB',
            complemento: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query(expected)
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Restaurante', async () => {
        const rxPromise = service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
