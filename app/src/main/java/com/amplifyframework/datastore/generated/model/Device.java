package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
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

/** This is an auto generated class representing the Device type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Devices", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "imei", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Administrador" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "Empresa" }, provider = "userPools", operations = { ModelOperation.READ, ModelOperation.UPDATE })
})
@Index(name = "undefined", fields = {"id"})
@Index(name = "byImei", fields = {"imei"})
@Index(name = "byCompany", fields = {"companyID"})
@Index(name = "byBus", fields = {"busID"})
@Index(name = "byRoute", fields = {"routeID"})
public final class Device implements Model {
  public static final QueryField ID = field("Device", "id");
  public static final QueryField IMEI = field("Device", "imei");
  public static final QueryField CIVICA_SERIAL = field("Device", "civicaSerial");
  public static final QueryField COMPANY = field("Device", "companyID");
  public static final QueryField BUS = field("Device", "busID");
  public static final QueryField ROUTE = field("Device", "routeID");
  public static final QueryField STATUS = field("Device", "status");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String imei;
  private final @ModelField(targetType="String") String civicaSerial;
  private final @ModelField(targetType="Company") @BelongsTo(targetName = "companyID", targetNames = {"companyID"}, type = Company.class) Company company;
  private final @ModelField(targetType="Bus") @BelongsTo(targetName = "busID", targetNames = {"busID"}, type = Bus.class) Bus bus;
  private final @ModelField(targetType="Route") @BelongsTo(targetName = "routeID", targetNames = {"routeID"}, type = Route.class) Route route;
  private final @ModelField(targetType="Use") @HasMany(associatedWith = "device", type = Use.class) List<Use> uses = null;
  private final @ModelField(targetType="Boolean") Boolean status;
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
  
  public String getImei() {
      return imei;
  }
  
  public String getCivicaSerial() {
      return civicaSerial;
  }
  
  public Company getCompany() {
      return company;
  }
  
  public Bus getBus() {
      return bus;
  }
  
  public Route getRoute() {
      return route;
  }
  
  public List<Use> getUses() {
      return uses;
  }
  
  public Boolean getStatus() {
      return status;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Device(String id, String imei, String civicaSerial, Company company, Bus bus, Route route, Boolean status) {
    this.id = id;
    this.imei = imei;
    this.civicaSerial = civicaSerial;
    this.company = company;
    this.bus = bus;
    this.route = route;
    this.status = status;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Device device = (Device) obj;
      return ObjectsCompat.equals(getId(), device.getId()) &&
              ObjectsCompat.equals(getImei(), device.getImei()) &&
              ObjectsCompat.equals(getCivicaSerial(), device.getCivicaSerial()) &&
              ObjectsCompat.equals(getCompany(), device.getCompany()) &&
              ObjectsCompat.equals(getBus(), device.getBus()) &&
              ObjectsCompat.equals(getRoute(), device.getRoute()) &&
              ObjectsCompat.equals(getStatus(), device.getStatus()) &&
              ObjectsCompat.equals(getCreatedAt(), device.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), device.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getImei())
      .append(getCivicaSerial())
      .append(getCompany())
      .append(getBus())
      .append(getRoute())
      .append(getStatus())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Device {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("imei=" + String.valueOf(getImei()) + ", ")
      .append("civicaSerial=" + String.valueOf(getCivicaSerial()) + ", ")
      .append("company=" + String.valueOf(getCompany()) + ", ")
      .append("bus=" + String.valueOf(getBus()) + ", ")
      .append("route=" + String.valueOf(getRoute()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ImeiStep builder() {
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
  public static Device justId(String id) {
    return new Device(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      imei,
      civicaSerial,
      company,
      bus,
      route,
      status);
  }
  public interface ImeiStep {
    BuildStep imei(String imei);
  }
  

  public interface BuildStep {
    Device build();
    BuildStep id(String id);
    BuildStep civicaSerial(String civicaSerial);
    BuildStep company(Company company);
    BuildStep bus(Bus bus);
    BuildStep route(Route route);
    BuildStep status(Boolean status);
  }
  

  public static class Builder implements ImeiStep, BuildStep {
    private String id;
    private String imei;
    private String civicaSerial;
    private Company company;
    private Bus bus;
    private Route route;
    private Boolean status;
    public Builder() {
      
    }
    
    private Builder(String id, String imei, String civicaSerial, Company company, Bus bus, Route route, Boolean status) {
      this.id = id;
      this.imei = imei;
      this.civicaSerial = civicaSerial;
      this.company = company;
      this.bus = bus;
      this.route = route;
      this.status = status;
    }
    
    @Override
     public Device build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Device(
          id,
          imei,
          civicaSerial,
          company,
          bus,
          route,
          status);
    }
    
    @Override
     public BuildStep imei(String imei) {
        Objects.requireNonNull(imei);
        this.imei = imei;
        return this;
    }
    
    @Override
     public BuildStep civicaSerial(String civicaSerial) {
        this.civicaSerial = civicaSerial;
        return this;
    }
    
    @Override
     public BuildStep company(Company company) {
        this.company = company;
        return this;
    }
    
    @Override
     public BuildStep bus(Bus bus) {
        this.bus = bus;
        return this;
    }
    
    @Override
     public BuildStep route(Route route) {
        this.route = route;
        return this;
    }
    
    @Override
     public BuildStep status(Boolean status) {
        this.status = status;
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
    private CopyOfBuilder(String id, String imei, String civicaSerial, Company company, Bus bus, Route route, Boolean status) {
      super(id, imei, civicaSerial, company, bus, route, status);
      Objects.requireNonNull(imei);
    }
    
    @Override
     public CopyOfBuilder imei(String imei) {
      return (CopyOfBuilder) super.imei(imei);
    }
    
    @Override
     public CopyOfBuilder civicaSerial(String civicaSerial) {
      return (CopyOfBuilder) super.civicaSerial(civicaSerial);
    }
    
    @Override
     public CopyOfBuilder company(Company company) {
      return (CopyOfBuilder) super.company(company);
    }
    
    @Override
     public CopyOfBuilder bus(Bus bus) {
      return (CopyOfBuilder) super.bus(bus);
    }
    
    @Override
     public CopyOfBuilder route(Route route) {
      return (CopyOfBuilder) super.route(route);
    }
    
    @Override
     public CopyOfBuilder status(Boolean status) {
      return (CopyOfBuilder) super.status(status);
    }
  }
  

  public static class DeviceIdentifier extends ModelIdentifier<Device> {
    private static final long serialVersionUID = 1L;
    public DeviceIdentifier(String id) {
      super(id);
    }
  }
  
}
