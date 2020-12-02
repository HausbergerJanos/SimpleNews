package eu.codeyard.simplenews.business.domain.util;

public interface EntityMapper<Entity, DomainModel> {

    public DomainModel mapFromEntity(Entity entity);

    public Entity mapToEntity(DomainModel domainModel);
}
