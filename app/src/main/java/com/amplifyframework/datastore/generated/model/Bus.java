package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the Bus type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Buses", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "busOwner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, provider = "iam", operations = { ModelOperation.READ, ModelOperation.UPDATE })
})
@Index(name = "byCompany", fields = {"companyID"})
public final class Bus implements Model {
  public static final QueryField ID = field("Bus", "id");
  public static final QueryField PLATE = field("Bus", "plate");
  public static final QueryField STATUS = field("Bus", "status");
  public static final QueryField BUS_OWNER = field("Bus", "busOwner");
  public static final QueryField COMPANY = field("Bus", "companyID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String plate;
  private final @ModelField(targetType="Boolean") Boolean status;
  private final @ModelField(targetType="String") String busOwner;
  private final @ModelField(targetType="Company") @BelongsTo(targetName = "companyID", targetNames = {"companyID"}, type = Company.class) Company company;
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
  
  public String getPlate() {
      return plate;
  }
  
  public Boolean getStatus() {
      return status;
  }
  
  public String getBusOwner() {
      return busOwner;
  }
  
  public Company getCompany() {
      return company;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Bus(String id, String plate, Boolean status, String busOwner, Company company) {
    this.id = id;
    this.plate = plate;
    this.status = status;
    this.busOwner = busOwner;
    this.company = company;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Bus bus = (Bus) obj;
      return ObjectsCompat.equals(getId(), bus.getId()) &&
              ObjectsCompat.equals(getPlate(), bus.getPlate()) &&
              ObjectsCompat.equals(getStatus(), bus.getStatus()) &&
              ObjectsCompat.equals(getBusOwner(), bus.getBusOwner()) &&
              ObjectsCompat.equals(getCompany(), bus.getCompany()) &&
              ObjectsCompat.equals(getCreatedAt(), bus.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), bus.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPlate())
      .append(getStatus())
      .append(getBusOwner())
      .append(getCompany())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Bus {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("plate=" + String.valueOf(getPlate()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("busOwner=" + String.valueOf(getBusOwner()) + ", ")
      .append("company=" + String.valueOf(getCompany()) + ", ")
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
  public static Bus justId(String id) {
    return new Bus(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      plate,
      status,
      busOwner,
      company);
  }
  public interface BuildStep {
    Bus build();
    BuildStep id(String id);
    BuildStep plate(String plate);
    BuildStep status(Boolean status);
    BuildStep busOwner(String busOwner);
    BuildStep company(Company company);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String plate;
    private Boolean status;
    private String busOwner;
    private Company company;
    public Builder() {
      
    }
    
    private Builder(String id, String plate, Boolean status, String busOwner, Company company) {
      this.id = id;
      this.plate = plate;
      this.status = status;
      this.busOwner = busOwner;
      this.company = company;
    }
    
    @Override
     public Bus build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Bus(
          id,
          plate,
          status,
          busOwner,
          company);
    }
    
    @Override
     public BuildStep plate(String plate) {
        this.plate = plate;
        return this;
    }
    
    @Override
     public BuildStep status(Boolean status) {
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep busOwner(String busOwner) {
        this.busOwner = busOwner;
        return this;
    }
    
    @Override
     public BuildStep company(Company company) {
        this.company = company;
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
    private CopyOfBuilder(String id, String plate, Boolean status, String busOwner, Company company) {
      super(id, plate, status, busOwner, company);
      
    }
    
    @Override
     public CopyOfBuilder plate(String plate) {
      return (CopyOfBuilder) super.plate(plate);
    }
    
    @Override
     public CopyOfBuilder status(Boolean status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder busOwner(String busOwner) {
      return (CopyOfBuilder) super.busOwner(busOwner);
    }
    
    @Override
     public CopyOfBuilder company(Company company) {
      return (CopyOfBuilder) super.company(company);
    }
  }
  

  public static class BusIdentifier extends ModelIdentifier<Bus> {
    private static final long serialVersionUID = 1L;
    public BusIdentifier(String id) {
      super(id);
    }
  }
  
}
