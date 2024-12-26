package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.annotations.HasOne;
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
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "deviceOwner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.PRIVATE, provider = "iam", operations = { ModelOperation.READ, ModelOperation.UPDATE })
})
@Index(name = "byCompany", fields = {"companyID"})
public final class Device implements Model {
  public static final QueryField ID = field("Device", "id");
  public static final QueryField IMEI = field("Device", "imei");
  public static final QueryField CIVICA_SERIAL = field("Device", "civicaSerial");
  public static final QueryField LAST_EDITOR_ID = field("Device", "lastEditorId");
  public static final QueryField DEVICE_OWNER = field("Device", "deviceOwner");
  public static final QueryField COMPANY = field("Device", "companyID");
  public static final QueryField DEVICE_BUS_ID = field("Device", "deviceBusId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String imei;
  private final @ModelField(targetType="String") String civicaSerial;
  private final @ModelField(targetType="Int") Integer lastEditorId;
  private final @ModelField(targetType="String") String deviceOwner;
  private final @ModelField(targetType="Company") @BelongsTo(targetName = "companyID", targetNames = {"companyID"}, type = Company.class) Company company;
  private final @ModelField(targetType="Bus") @HasOne(associatedWith = "id", type = Bus.class) Bus bus = null;
  private final @ModelField(targetType="Use") @HasMany(associatedWith = "device", type = Use.class) List<Use> uses = null;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  private final @ModelField(targetType="ID") String deviceBusId;
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
  
  public Integer getLastEditorId() {
      return lastEditorId;
  }
  
  public String getDeviceOwner() {
      return deviceOwner;
  }
  
  public Company getCompany() {
      return company;
  }
  
  public Bus getBus() {
      return bus;
  }
  
  public List<Use> getUses() {
      return uses;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  public String getDeviceBusId() {
      return deviceBusId;
  }
  
  private Device(String id, String imei, String civicaSerial, Integer lastEditorId, String deviceOwner, Company company, String deviceBusId) {
    this.id = id;
    this.imei = imei;
    this.civicaSerial = civicaSerial;
    this.lastEditorId = lastEditorId;
    this.deviceOwner = deviceOwner;
    this.company = company;
    this.deviceBusId = deviceBusId;
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
              ObjectsCompat.equals(getLastEditorId(), device.getLastEditorId()) &&
              ObjectsCompat.equals(getDeviceOwner(), device.getDeviceOwner()) &&
              ObjectsCompat.equals(getCompany(), device.getCompany()) &&
              ObjectsCompat.equals(getCreatedAt(), device.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), device.getUpdatedAt()) &&
              ObjectsCompat.equals(getDeviceBusId(), device.getDeviceBusId());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getImei())
      .append(getCivicaSerial())
      .append(getLastEditorId())
      .append(getDeviceOwner())
      .append(getCompany())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .append(getDeviceBusId())
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
      .append("lastEditorId=" + String.valueOf(getLastEditorId()) + ", ")
      .append("deviceOwner=" + String.valueOf(getDeviceOwner()) + ", ")
      .append("company=" + String.valueOf(getCompany()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()) + ", ")
      .append("deviceBusId=" + String.valueOf(getDeviceBusId()))
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
      lastEditorId,
      deviceOwner,
      company,
      deviceBusId);
  }
  public interface BuildStep {
    Device build();
    BuildStep id(String id);
    BuildStep imei(String imei);
    BuildStep civicaSerial(String civicaSerial);
    BuildStep lastEditorId(Integer lastEditorId);
    BuildStep deviceOwner(String deviceOwner);
    BuildStep company(Company company);
    BuildStep deviceBusId(String deviceBusId);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String imei;
    private String civicaSerial;
    private Integer lastEditorId;
    private String deviceOwner;
    private Company company;
    private String deviceBusId;
    public Builder() {
      
    }
    
    private Builder(String id, String imei, String civicaSerial, Integer lastEditorId, String deviceOwner, Company company, String deviceBusId) {
      this.id = id;
      this.imei = imei;
      this.civicaSerial = civicaSerial;
      this.lastEditorId = lastEditorId;
      this.deviceOwner = deviceOwner;
      this.company = company;
      this.deviceBusId = deviceBusId;
    }
    
    @Override
     public Device build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Device(
          id,
          imei,
          civicaSerial,
          lastEditorId,
          deviceOwner,
          company,
          deviceBusId);
    }
    
    @Override
     public BuildStep imei(String imei) {
        this.imei = imei;
        return this;
    }
    
    @Override
     public BuildStep civicaSerial(String civicaSerial) {
        this.civicaSerial = civicaSerial;
        return this;
    }
    
    @Override
     public BuildStep lastEditorId(Integer lastEditorId) {
        this.lastEditorId = lastEditorId;
        return this;
    }
    
    @Override
     public BuildStep deviceOwner(String deviceOwner) {
        this.deviceOwner = deviceOwner;
        return this;
    }
    
    @Override
     public BuildStep company(Company company) {
        this.company = company;
        return this;
    }
    
    @Override
     public BuildStep deviceBusId(String deviceBusId) {
        this.deviceBusId = deviceBusId;
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
    private CopyOfBuilder(String id, String imei, String civicaSerial, Integer lastEditorId, String deviceOwner, Company company, String deviceBusId) {
      super(id, imei, civicaSerial, lastEditorId, deviceOwner, company, deviceBusId);
      
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
     public CopyOfBuilder lastEditorId(Integer lastEditorId) {
      return (CopyOfBuilder) super.lastEditorId(lastEditorId);
    }
    
    @Override
     public CopyOfBuilder deviceOwner(String deviceOwner) {
      return (CopyOfBuilder) super.deviceOwner(deviceOwner);
    }
    
    @Override
     public CopyOfBuilder company(Company company) {
      return (CopyOfBuilder) super.company(company);
    }
    
    @Override
     public CopyOfBuilder deviceBusId(String deviceBusId) {
      return (CopyOfBuilder) super.deviceBusId(deviceBusId);
    }
  }
  

  public static class DeviceIdentifier extends ModelIdentifier<Device> {
    private static final long serialVersionUID = 1L;
    public DeviceIdentifier(String id) {
      super(id);
    }
  }
  
}
