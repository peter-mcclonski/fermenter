package org.bitbucket.fermenter.ale.mda.generator.angular;

import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.bitbucket.fermenter.mda.PackageManager;
import org.bitbucket.fermenter.mda.metamodel.DefaultModelInstanceRepository;
import org.bitbucket.fermenter.mda.metamodel.ModelInstanceRepositoryManager;
import org.bitbucket.fermenter.mda.metamodel.element.BaseFieldDecorator;
import org.bitbucket.fermenter.mda.metamodel.element.Entity;
import org.bitbucket.fermenter.mda.metamodel.element.Field;

import com.google.common.base.CaseFormat;

public class AngularField extends BaseFieldDecorator implements Field {

    private DefaultModelInstanceRepository metadataRepository = ModelInstanceRepositoryManager
            .getMetamodelRepository(DefaultModelInstanceRepository.class);

    public AngularField(Field wrapped) {
        super(wrapped);
    }

    public String getAngularType() {
        String type = AngularGeneratorUtil.TYPE_NOT_FOUND;
        if (wrapped != null) {
            type = AngularGeneratorUtil.getAngularType(wrapped.getType());
        }
        return type;
    }

    /**
     * {@inheritDoc}
     */
    public String getUppercasedGenerator() {
        return getGenerator().name().toUpperCase();
    }

    public String getCapitalizedName() {
        return StringUtils.capitalize(getName());
    }

    public String getUppercasedType() {
        return getType().toUpperCase();
    }

    /**
     * {@inheritDoc}
     */
    public Boolean isEnumeration() {
        return metadataRepository.getEnumeration(getPackage(), getType()) != null;
    }

    public String getTypeLowerHyphen() {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_HYPHEN, getType());
    }

    Field getFieldObject() {
        return wrapped;
    }

    public boolean isExternal() {
        String currentProject = metadataRepository.getArtifactId();
        String basePackage = PackageManager.getBasePackage(currentProject);
        Map<String, Entity> referenceEntities = metadataRepository.getEntities(getPackage());
        Entity referenceEntity = referenceEntities.get(getType());

        String currentPackage = referenceEntity.getPackage();
        return !StringUtils.isBlank(currentPackage) && !currentPackage.equals(basePackage);
    }

    public String getPatterns() {
        StringBuilder sb = new StringBuilder(100);
        boolean isFirst = true;
        for (String format : getValidation().getFormats()) {
            if (!isFirst) {
                sb.append(", ");
            }

            sb.append("\"");
            sb.append(StringEscapeUtils.escapeJava(format));
            sb.append("\"");

            isFirst = false;
        }

        return sb.toString();
    }

}
