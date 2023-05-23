package com.ssafy.triptube.trips.categories.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCat3Entity is a Querydsl query type for Cat3Entity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCat3Entity extends EntityPathBase<Cat3Entity> {

    private static final long serialVersionUID = -930538096L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCat3Entity cat3Entity = new QCat3Entity("cat3Entity");

    public final QCat2Entity cat2Code;

    public final StringPath cat3Code = createString("cat3Code");

    public final StringPath cat3Name = createString("cat3Name");

    public QCat3Entity(String variable) {
        this(Cat3Entity.class, forVariable(variable), INITS);
    }

    public QCat3Entity(Path<? extends Cat3Entity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCat3Entity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCat3Entity(PathMetadata metadata, PathInits inits) {
        this(Cat3Entity.class, metadata, inits);
    }

    public QCat3Entity(Class<? extends Cat3Entity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cat2Code = inits.isInitialized("cat2Code") ? new QCat2Entity(forProperty("cat2Code"), inits.get("cat2Code")) : null;
    }

}

