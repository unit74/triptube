package com.ssafy.triptube.trips.categories.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QContentTypeEntity is a Querydsl query type for ContentTypeEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QContentTypeEntity extends EntityPathBase<ContentTypeEntity> {

    private static final long serialVersionUID = 534357734L;

    public static final QContentTypeEntity contentTypeEntity = new QContentTypeEntity("contentTypeEntity");

    public final NumberPath<Integer> contentTypeId = createNumber("contentTypeId", Integer.class);

    public final StringPath contentTypeName = createString("contentTypeName");

    public QContentTypeEntity(String variable) {
        super(ContentTypeEntity.class, forVariable(variable));
    }

    public QContentTypeEntity(Path<? extends ContentTypeEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContentTypeEntity(PathMetadata metadata) {
        super(ContentTypeEntity.class, metadata);
    }

}

