import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cartao-credito',
        loadChildren: './cartao-credito/cartao-credito.module#BestMealCartaoCreditoModule'
      },
      {
        path: 'cartao-recompensa',
        loadChildren: './cartao-recompensa/cartao-recompensa.module#BestMealCartaoRecompensaModule'
      },
      {
        path: 'cartao-recompensa',
        loadChildren: './cartao-recompensa/cartao-recompensa.module#BestMealCartaoRecompensaModule'
      },
      {
        path: 'menu',
        loadChildren: './menu/menu.module#BestMealMenuModule'
      },
      {
        path: 'produto',
        loadChildren: './produto/produto.module#BestMealProdutoModule'
      },
      {
        path: 'municipio',
        loadChildren: './municipio/municipio.module#BestMealMunicipioModule'
      },
      {
        path: 'municipio',
        loadChildren: './municipio/municipio.module#BestMealMunicipioModule'
      },
      {
        path: 'municipio',
        loadChildren: './municipio/municipio.module#BestMealMunicipioModule'
      },
      {
        path: 'pessoa',
        loadChildren: './pessoa/pessoa.module#BestMealPessoaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BestMealEntityModule {}
