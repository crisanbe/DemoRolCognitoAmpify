package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.HasMany;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Company type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Companies", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "companyOwner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, provider = "iam", operations = { ModelOperation.READ, ModelOperation.UPDATE })
})
public final class Company implements Model {
  public static final QueryField ID = field("Company", "id");
  public static final QueryField NAME = field("Company", "name");
  public static final QueryField RUTA_CIVICA = field("Company", "rutaCivica");
  public static final QueryField COMMISSION_PER_TICKET = field("Company", "commissionPerTicket");
  public static final QueryField LAST_EDITOR_ID = field("Company", "lastEditorId");
  public static final QueryField COMPANY_OWNER = field("Company", "companyOwner");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String name;
  private final @ModelField(targetType="String") String rutaCivica;
  private final @ModelField(targetType="Float") Double commissionPerTicket;
  private final @ModelField(targetType="Int") Integer lastEditorId;
  private final @ModelField(targetType="String") String companyOwner;
  private final @ModelField(targetType="Device") @HasMany(associatedWith = "company", type = Device.class) List<Device> devices = null;
  private final @ModelField(targetType="Bus") @HasMany(associatedWith = "company", type = Bus.class) List<Bus> buses = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getRutaCivica() {
      return rutaCivica;
  }
  
  public Double getCommissionPerTicket() {
      return commissionPerTicket;
  }
  
  public Integer getLastEditorId() {
      return lastEditorId;
  }
  
  public String getCompanyOwner() {
      return companyOwner;
  }
  
  public List<Device> getDevices() {
      return devices;
  }
  
  public List<Bus> getBuses() {
      return buses;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Company(String id, String name, String rutaCivica, Double commissionPerTicket, Integer lastEditorId, String companyOwner) {
    this.id = id;
    this.name = name;
    this.rutaCivica = rutaCivica;
    this.commissionPerTicket = commissionPerTicket;
    this.lastEditorId = lastEditorId;
    this.companyOwner = companyOwner;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Company company = (Company) obj;
      return ObjectsCompat.equals(getId(), company.getId()) &&
              ObjectsCompat.equals(getName(), company.getName()) &&
              ObjectsCompat.equals(getRutaCivica(), company.getRutaCivica()) &&
              ObjectsCompat.equals(getCommissionPerTicket(), company.getCommissionPerTicket()) &&
              ObjectsCompat.equals(getLastEditorId(), company.getLastEditorId()) &&
              ObjectsCompat.equals(getCompanyOwner(), company.getCompanyOwner()) &&
              ObjectsCompat.equals(getCreatedAt(), company.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), company.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getRutaCivica())
      .append(getCommissionPerTicket())
      .append(getLastEditorId())
      .append(getCompanyOwner())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Company {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("rutaCivica=" + String.valueOf(getRutaCivica()) + ", ")
      .append("commissionPerTicket=" + String.valueOf(getCommissionPerTicket()) + ", ")
      .append("lastEditorId=" + String.valueOf(getLastEditorId()) + ", ")
      .append("companyOwner=" + String.valueOf(getCompanyOwner()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Company justId(String id) {
    return new Company(
      id,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      rutaCivica,
      commissionPerTicket,
      lastEditorId,
      companyOwner);
  }
  public interface BuildStep {
    Company build();
    BuildStep id(String id);
    BuildStep name(String name);
    BuildStep rutaCivica(String rutaCivica);
    BuildStep commissionPerTicket(Double commissionPerTicket);
    BuildStep lastEditorId(Integer lastEditorId);
    BuildStep companyOwner(String companyOwner);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String name;
    private String rutaCivica;
    private Double commissionPerTicket;
    private Integer lastEditorId;
    private String companyOwner;
    public Builder() {
      
    }
    
    private Builder(String id, String name, String rutaCivica, Double commissionPerTicket, Integer lastEditorId, String companyOwner) {
      this.id = id;
      this.name = name;
      this.rutaCivica = rutaCivica;
      this.commissionPerTicket = commissionPerTicket;
      this.lastEditorId = lastEditorId;
      this.companyOwner = companyOwner;
    }
    
    @Override
     public Company build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Company(
          id,
          name,
          rutaCivica,
          commissionPerTicket,
          lastEditorId,
          companyOwner);
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep rutaCivica(String rutaCivica) {
        this.rutaCivica = rutaCivica;
        return this;
    }
    
    @Override
     public BuildStep commissionPerTicket(Double commissionPerTicket) {
        this.commissionPerTicket = commissionPerTicket;
        return this;
    }
    
    @Override
     public BuildStep lastEditorId(Integer lastEditorId) {
        this.lastEditorId = lastEditorId;
        return this;
    }
    
    @Override
     public BuildStep companyOwner(String companyOwner) {
        this.companyOwner = companyOwner;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String name, String rutaCivica, Double commissionPerTicket, Integer lastEditorId, String companyOwner) {
      super(id, name, rutaCivica, commissionPerTicket, lastEditorId, companyOwner);
      
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder rutaCivica(String rutaCivica) {
      return (CopyOfBuilder) super.rutaCivica(rutaCivica);
    }
    
    @Override
     public CopyOfBuilder commissionPerTicket(Double commissionPerTicket) {
      return (CopyOfBuilder) super.commissionPerTicket(commissionPerTicket);
    }
    
    @Override
     public CopyOfBuilder lastEditorId(Integer lastEditorId) {
      return (CopyOfBuilder) super.lastEditorId(lastEditorId);
    }
    
    @Override
     public CopyOfBuilder companyOwner(String companyOwner) {
      return (CopyOfBuilder) super.companyOwner(companyOwner);
    }
  }
  

  public static class CompanyIdentifier extends ModelIdentifier<Company> {
    private static final long serialVersionUID = 1L;
    public CompanyIdentifier(String id) {
      super(id);
    }
  }
  
}
