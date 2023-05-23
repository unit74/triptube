package com.ssafy.triptube.trips.categories.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QGugunEntity is a Querydsl query type for GugunEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGugunEntity extends EntityPathBase<GugunEntity> {

    private static final long serialVersionUID = 622279141L;

    public static final QGugunEntity gugunEntity = new QGugunEntity("gugunEntity");

    public final NumberPath<Integer> gugunCode = createNumber("gugunCode", Integer.class);

    public final StringPath gugunName = createString("gugunName");

    public final NumberPath<Integer> sidoCode = createNumber("sidoCode", Integer.class);

    public QGugunEntity(String variable) {
        super(GugunEntity.class, forVariable(variable));
    }

    public QGugunEntity(Path<? extends GugunEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QGugunEntity(PathMetadata metadata) {
        super(GugunEntity.class, metadata);
    }

}

