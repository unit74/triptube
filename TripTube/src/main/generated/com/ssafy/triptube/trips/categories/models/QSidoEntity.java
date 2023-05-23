package com.ssafy.triptube.trips.categories.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSidoEntity is a Querydsl query type for SidoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSidoEntity extends EntityPathBase<SidoEntity> {

    private static final long serialVersionUID = -1759068972L;

    public static final QSidoEntity sidoEntity = new QSidoEntity("sidoEntity");

    public final NumberPath<Integer> sidoCode = createNumber("sidoCode", Integer.class);

    public final StringPath sidoName = createString("sidoName");

    public QSidoEntity(String variable) {
        super(SidoEntity.class, forVariable(variable));
    }

    public QSidoEntity(Path<? extends SidoEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSidoEntity(PathMetadata metadata) {
        super(SidoEntity.class, metadata);
    }

}

