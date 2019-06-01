package br.com.abim.bestmeal.domain;

import br.com.abim.bestmeal.domain.enumeration.TipoLogradouro;
import br.com.abim.bestmeal.domain.enumeration.TipoPessoa;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Fornecedor.class)
public abstract class Fornecedor_ {

	public static volatile SingularAttribute<Fornecedor, String> primeiroNome;
	public static volatile SingularAttribute<Fornecedor, TipoPessoa> tipo;
	public static volatile SingularAttribute<Fornecedor, String> nomeLogradouro;
	public static volatile SingularAttribute<Fornecedor, Municipio> municipio;
	public static volatile SingularAttribute<Fornecedor, String> titulo;
	public static volatile SingularAttribute<Fornecedor, String> cnpj;
	public static volatile SingularAttribute<Fornecedor, String> sobreNome;
	public static volatile SingularAttribute<Fornecedor, String> cep;
	public static volatile SingularAttribute<Fornecedor, String> complemento;
	public static volatile SingularAttribute<Fornecedor, String> saudacao;
	public static volatile SingularAttribute<Fornecedor, String> cpf;
	public static volatile SingularAttribute<Fornecedor, Long> id;
	public static volatile SingularAttribute<Fornecedor, TipoLogradouro> tipoLogradouro;
	public static volatile SingularAttribute<Fornecedor, String> nomeMeio;

	public static final String PRIMEIRO_NOME = "primeiroNome";
	public static final String TIPO = "tipo";
	public static final String NOME_LOGRADOURO = "nomeLogradouro";
	public static final String MUNICIPIO = "municipio";
	public static final String TITULO = "titulo";
	public static final String CNPJ = "cnpj";
	public static final String SOBRE_NOME = "sobreNome";
	public static final String CEP = "cep";
	public static final String COMPLEMENTO = "complemento";
	public static final String SAUDACAO = "saudacao";
	public static final String CPF = "cpf";
	public static final String ID = "id";
	public static final String TIPO_LOGRADOURO = "tipoLogradouro";
	public static final String NOME_MEIO = "nomeMeio";

}

