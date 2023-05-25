package com.ssafy.triptube.trips.attractions.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttractionInfoEntity is a Querydsl query type for AttractionInfoEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttractionInfoEntity extends EntityPathBase<AttractionInfoEntity> {

    private static final long serialVersionUID = -1909197572L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttractionInfoEntity attractionInfoEntity = new QAttractionInfoEntity("attractionInfoEntity");

    public final StringPath addr1 = createString("addr1");

    public final StringPath addr2 = createString("addr2");

    public final QAttractionDescriptionEntity attractionDescription;

    public final NumberPath<Integer> contentId = createNumber("contentId", Integer.class);

    public final NumberPath<Integer> contentTypeId = createNumber("contentTypeId", Integer.class);

    public final StringPath firstImage = createString("firstImage");

    public final StringPath firstImage2 = createString("firstImage2");

    public final NumberPath<Integer> gugunCode = createNumber("gugunCode", Integer.class);

    public final NumberPath<java.math.BigDecimal> latitude = createNumber("latitude", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> longitude = createNumber("longitude", java.math.BigDecimal.class);

    public final StringPath mlevel = createString("mlevel");

    public final NumberPath<Integer> readcount = createNumber("readcount", Integer.class);

    public final NumberPath<Integer> sidoCode = createNumber("sidoCode", Integer.class);

    public final StringPath tel = createString("tel");

    public final StringPath title = createString("title");

    public final StringPath zipcode = createString("zipcode");

    public QAttractionInfoEntity(String variable) {
        this(AttractionInfoEntity.class, forVariable(variable), INITS);
    }

    public QAttractionInfoEntity(Path<? extends AttractionInfoEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttractionInfoEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttractionInfoEntity(PathMetadata metadata, PathInits inits) {
        this(AttractionInfoEntity.class, metadata, inits);
    }

    public QAttractionInfoEntity(Class<? extends AttractionInfoEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attractionDescription = inits.isInitialized("attractionDescription") ? new QAttractionDescriptionEntity(forProperty("attractionDescription")) : null;
    }

}

