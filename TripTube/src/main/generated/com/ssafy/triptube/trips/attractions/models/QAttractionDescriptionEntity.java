package com.ssafy.triptube.trips.attractions.models;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAttractionDescriptionEntity is a Querydsl query type for AttractionDescriptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttractionDescriptionEntity extends EntityPathBase<AttractionDescriptionEntity> {

    private static final long serialVersionUID = -1233174060L;

    public static final QAttractionDescriptionEntity attractionDescriptionEntity = new QAttractionDescriptionEntity("attractionDescriptionEntity");

    public final NumberPath<Integer> contentId = createNumber("contentId", Integer.class);

    public final StringPath homepage = createString("homepage");

    public final StringPath overview = createString("overview");

    public final StringPath telname = createString("telname");

    public QAttractionDescriptionEntity(String variable) {
        super(AttractionDescriptionEntity.class, forVariable(variable));
    }

    public QAttractionDescriptionEntity(Path<? extends AttractionDescriptionEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAttractionDescriptionEntity(PathMetadata metadata) {
        super(AttractionDescriptionEntity.class, metadata);
    }

}

