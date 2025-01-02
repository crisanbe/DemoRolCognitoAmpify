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
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrador" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Empresa" }, provider = "userPools", operations = { ModelOperation.READ, ModelOperation.UPDATE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Dispositivo" }, provider = "userPools", operations = { ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
@Index(name = "companiesByUsername", fields = {"username"})
public final class Company implements Model {
  public static final QueryField ID = field("Company", "id");
  public static final QueryField USERNAME = field("Company", "username");
  public static final QueryField NAME = field("Company", "name");
  public static final QueryField RUTA_CIVICA = field("Company", "rutaCivica");
  public static final QueryField COMMISSION_PER_TICKET = field("Company", "commissionPerTicket");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String rutaCivica;
  private final @ModelField(targetType="Float") Double commissionPerTicket;
  private final @ModelField(targetType="Device") @HasMany(associatedWith = "company", type = Device.class) List<Device> devices = null;
  private final @ModelField(targetType="Bus") @HasMany(associatedWith = "company", type = Bus.class) List<Bus> buses = null;
  private final @ModelField(targetType="Route") @HasMany(associatedWith = "company", type = Route.class) List<Route> routes = null;
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
  
  public String getUsername() {
      return username;
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
  
  public List<Device> getDevices() {
      return devices;
  }
  
  public List<Bus> getBuses() {
      return buses;
  }
  
  public List<Route> getRoutes() {
      return routes;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Company(String id, String username, String name, String rutaCivica, Double commissionPerTicket) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.rutaCivica = rutaCivica;
    this.commissionPerTicket = commissionPerTicket;
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
              ObjectsCompat.equals(getUsername(), company.getUsername()) &&
              ObjectsCompat.equals(getName(), company.getName()) &&
              ObjectsCompat.equals(getRutaCivica(), company.getRutaCivica()) &&
              ObjectsCompat.equals(getCommissionPerTicket(), company.getCommissionPerTicket()) &&
              ObjectsCompat.equals(getCreatedAt(), company.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), company.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getName())
      .append(getRutaCivica())
      .append(getCommissionPerTicket())
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
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("rutaCivica=" + String.valueOf(getRutaCivica()) + ", ")
      .append("commissionPerTicket=" + String.valueOf(getCommissionPerTicket()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UsernameStep builder() {
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
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      username,
      name,
      rutaCivica,
      commissionPerTicket);
  }
  public interface UsernameStep {
    NameStep username(String username);
  }
  

  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Company build();
    BuildStep id(String id);
    BuildStep rutaCivica(String rutaCivica);
    BuildStep commissionPerTicket(Double commissionPerTicket);
  }
  

  public static class Builder implements UsernameStep, NameStep, BuildStep {
    private String id;
    private String username;
    private String name;
    private String rutaCivica;
    private Double commissionPerTicket;
    public Builder() {
      
    }
    
    private Builder(String id, String username, String name, String rutaCivica, Double commissionPerTicket) {
      this.id = id;
      this.username = username;
      this.name = name;
      this.rutaCivica = rutaCivica;
      this.commissionPerTicket = commissionPerTicket;
    }
    
    @Override
     public Company build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Company(
          id,
          username,
          name,
          rutaCivica,
          commissionPerTicket);
    }
    
    @Override
     public NameStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
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
    private CopyOfBuilder(String id, String username, String name, String rutaCivica, Double commissionPerTicket) {
      super(id, username, name, rutaCivica, commissionPerTicket);
      Objects.requireNonNull(username);
      Objects.requireNonNull(name);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
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
  }
  

  public static class CompanyIdentifier extends ModelIdentifier<Company> {
    private static final long serialVersionUID = 1L;
    public CompanyIdentifier(String id) {
      super(id);
    }
  }
  
}
