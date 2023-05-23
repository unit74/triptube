package com.ssafy.triptube.trips.categories.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCat1Entity is a Querydsl query type for Cat1Entity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCat1Entity extends EntityPathBase<Cat1Entity> {

    private static final long serialVersionUID = 1589421838L;

    public static final QCat1Entity cat1Entity = new QCat1Entity("cat1Entity");

    public final StringPath cat1Code = createString("cat1Code");

    public final StringPath cat1Name = createString("cat1Name");

    public QCat1Entity(String variable) {
        super(Cat1Entity.class, forVariable(variable));
    }

    public QCat1Entity(Path<? extends Cat1Entity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCat1Entity(PathMetadata metadata) {
        super(Cat1Entity.class, metadata);
    }

}

