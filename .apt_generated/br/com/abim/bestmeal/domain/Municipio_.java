package br.com.abim.bestmeal.domain;

import br.com.abim.bestmeal.domain.enumeration.UF;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Municipio.class)
public abstract class Municipio_ {

	public static volatile SingularAttribute<Municipio, UF> uf;
	public static volatile SingularAttribute<Municipio, Long> id;
	public static volatile SingularAttribute<Municipio, String> nomeMunicipio;

	public static final String UF = "uf";
	public static final String ID = "id";
	public static final String NOME_MUNICIPIO = "nomeMunicipio";

}

