package com.ssafy.triptube.trips.histories.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHistoryEntity is a Querydsl query type for HistoryEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHistoryEntity extends EntityPathBase<HistoryEntity> {

    private static final long serialVersionUID = 406688525L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHistoryEntity historyEntity = new QHistoryEntity("historyEntity");

    public final com.ssafy.triptube.trips.attractions.models.QAttractionInfoEntity attractionInfo;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> historyId = createNumber("historyId", Long.class);

    public final StringPath searchText = createString("searchText");

    public final EnumPath<HistoryEntity.Type> type = createEnum("type", HistoryEntity.Type.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.ssafy.triptube.users.models.QUserEntity user;

    public QHistoryEntity(String variable) {
        this(HistoryEntity.class, forVariable(variable), INITS);
    }

    public QHistoryEntity(Path<? extends HistoryEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHistoryEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHistoryEntity(PathMetadata metadata, PathInits inits) {
        this(HistoryEntity.class, metadata, inits);
    }

    public QHistoryEntity(Class<? extends HistoryEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attractionInfo = inits.isInitialized("attractionInfo") ? new com.ssafy.triptube.trips.attractions.models.QAttractionInfoEntity(forProperty("attractionInfo"), inits.get("attractionInfo")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.triptube.users.models.QUserEntity(forProperty("user")) : null;
    }

}

