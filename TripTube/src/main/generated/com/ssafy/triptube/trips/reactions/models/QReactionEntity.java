package com.ssafy.triptube.trips.reactions.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReactionEntity is a Querydsl query type for ReactionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReactionEntity extends EntityPathBase<ReactionEntity> {

    private static final long serialVersionUID = 960530926L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReactionEntity reactionEntity = new QReactionEntity("reactionEntity");

    public final com.ssafy.triptube.trips.attractions.models.QAttractionInfoEntity attractionInfo;

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> reactionId = createNumber("reactionId", Long.class);

    public final EnumPath<ReactionEntity.Type> type = createEnum("type", ReactionEntity.Type.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final com.ssafy.triptube.users.models.QUserEntity user;

    public QReactionEntity(String variable) {
        this(ReactionEntity.class, forVariable(variable), INITS);
    }

    public QReactionEntity(Path<? extends ReactionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReactionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReactionEntity(PathMetadata metadata, PathInits inits) {
        this(ReactionEntity.class, metadata, inits);
    }

    public QReactionEntity(Class<? extends ReactionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attractionInfo = inits.isInitialized("attractionInfo") ? new com.ssafy.triptube.trips.attractions.models.QAttractionInfoEntity(forProperty("attractionInfo"), inits.get("attractionInfo")) : null;
        this.user = inits.isInitialized("user") ? new com.ssafy.triptube.users.models.QUserEntity(forProperty("user")) : null;
    }

}

