/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { CartaoRecompensaService } from 'app/entities/cartao-recompensa/cartao-recompensa.service';
import { ICartaoRecompensa, CartaoRecompensa, SituacaoCartao } from 'app/shared/model/cartao-recompensa.model';

describe('Service Tests', () => {
  describe('CartaoRecompensa Service', () => {
    let injector: TestBed;
    let service: CartaoRecompensaService;
    let httpMock: HttpTestingController;
    let elemDefault: ICartaoRecompensa;
    let expectedResult;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = {};
      injector = getTestBed();
      service = injector.get(CartaoRecompensaService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new CartaoRecompensa(0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, SituacaoCartao.ATIVO);
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

      it('should create a CartaoRecompensa', async () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new CartaoRecompensa(null))
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject({ body: expected });
      });

      it('should update a CartaoRecompensa', async () => {
        const returnedFromService = Object.assign(
          {
            nomeCartao: 'BBBBBB',
            numero: 'BBBBBB',
            validade: 'BBBBBB',
            pontuacao: 1,
            situacao: 'BBBBBB'
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

      it('should return a list of CartaoRecompensa', async () => {
        const returnedFromService = Object.assign(
          {
            nomeCartao: 'BBBBBB',
            numero: 'BBBBBB',
            validade: 'BBBBBB',
            pontuacao: 1,
            situacao: 'BBBBBB'
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

      it('should delete a CartaoRecompensa', async () => {
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
