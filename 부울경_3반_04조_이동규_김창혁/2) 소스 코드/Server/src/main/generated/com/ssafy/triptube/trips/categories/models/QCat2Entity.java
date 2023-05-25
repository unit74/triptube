package com.ssafy.triptube.trips.categories.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCat2Entity is a Querydsl query type for Cat2Entity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCat2Entity extends EntityPathBase<Cat2Entity> {

    private static final long serialVersionUID = -1818041777L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCat2Entity cat2Entity = new QCat2Entity("cat2Entity");

    public final QCat1Entity cat1Code;

    public final StringPath cat2Code = createString("cat2Code");

    public final StringPath cat2Name = createString("cat2Name");

    public QCat2Entity(String variable) {
        this(Cat2Entity.class, forVariable(variable), INITS);
    }

    public QCat2Entity(Path<? extends Cat2Entity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCat2Entity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCat2Entity(PathMetadata metadata, PathInits inits) {
        this(Cat2Entity.class, metadata, inits);
    }

    public QCat2Entity(Class<? extends Cat2Entity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cat1Code = inits.isInitialized("cat1Code") ? new QCat1Entity(forProperty("cat1Code")) : null;
    }

}

