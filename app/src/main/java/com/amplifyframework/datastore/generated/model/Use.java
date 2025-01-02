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

/** This is an auto generated class representing the Use type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Uses", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "admin" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "empresa" }, provider = "userPools", operations = { ModelOperation.READ }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "dispositivo" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE })
})
@Index(name = "undefined", fields = {"id"})
@Index(name = "byDevice", fields = {"deviceID"})
public final class Use implements Model {
  public static final QueryField ID = field("Use", "id");
  public static final QueryField CARD_NUMBER = field("Use", "cardNumber");
  public static final QueryField BALANCE = field("Use", "balance");
  public static final QueryField SEQUENCE_NUMBER = field("Use", "sequenceNumber");
  public static final QueryField STATUS = field("Use", "status");
  public static final QueryField DEVICE = field("Use", "deviceID");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String cardNumber;
  private final @ModelField(targetType="Float") Double balance;
  private final @ModelField(targetType="Int") Integer sequenceNumber;
  private final @ModelField(targetType="Boolean") Boolean status;
  private final @ModelField(targetType="Device") @BelongsTo(targetName = "deviceID", targetNames = {"deviceID"}, type = Device.class) Device device;
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
  
  public String getCardNumber() {
      return cardNumber;
  }
  
  public Double getBalance() {
      return balance;
  }
  
  public Integer getSequenceNumber() {
      return sequenceNumber;
  }
  
  public Boolean getStatus() {
      return status;
  }
  
  public Device getDevice() {
      return device;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Use(String id, String cardNumber, Double balance, Integer sequenceNumber, Boolean status, Device device) {
    this.id = id;
    this.cardNumber = cardNumber;
    this.balance = balance;
    this.sequenceNumber = sequenceNumber;
    this.status = status;
    this.device = device;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Use use = (Use) obj;
      return ObjectsCompat.equals(getId(), use.getId()) &&
              ObjectsCompat.equals(getCardNumber(), use.getCardNumber()) &&
              ObjectsCompat.equals(getBalance(), use.getBalance()) &&
              ObjectsCompat.equals(getSequenceNumber(), use.getSequenceNumber()) &&
              ObjectsCompat.equals(getStatus(), use.getStatus()) &&
              ObjectsCompat.equals(getDevice(), use.getDevice()) &&
              ObjectsCompat.equals(getCreatedAt(), use.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), use.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getCardNumber())
      .append(getBalance())
      .append(getSequenceNumber())
      .append(getStatus())
      .append(getDevice())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Use {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("cardNumber=" + String.valueOf(getCardNumber()) + ", ")
      .append("balance=" + String.valueOf(getBalance()) + ", ")
      .append("sequenceNumber=" + String.valueOf(getSequenceNumber()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("device=" + String.valueOf(getDevice()) + ", ")
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
  public static Use justId(String id) {
    return new Use(
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
      cardNumber,
      balance,
      sequenceNumber,
      status,
      device);
  }
  public interface BuildStep {
    Use build();
    BuildStep id(String id);
    BuildStep cardNumber(String cardNumber);
    BuildStep balance(Double balance);
    BuildStep sequenceNumber(Integer sequenceNumber);
    BuildStep status(Boolean status);
    BuildStep device(Device device);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private String cardNumber;
    private Double balance;
    private Integer sequenceNumber;
    private Boolean status;
    private Device device;
    public Builder() {
      
    }
    
    private Builder(String id, String cardNumber, Double balance, Integer sequenceNumber, Boolean status, Device device) {
      this.id = id;
      this.cardNumber = cardNumber;
      this.balance = balance;
      this.sequenceNumber = sequenceNumber;
      this.status = status;
      this.device = device;
    }
    
    @Override
     public Use build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Use(
          id,
          cardNumber,
          balance,
          sequenceNumber,
          status,
          device);
    }
    
    @Override
     public BuildStep cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }
    
    @Override
     public BuildStep balance(Double balance) {
        this.balance = balance;
        return this;
    }
    
    @Override
     public BuildStep sequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
        return this;
    }
    
    @Override
     public BuildStep status(Boolean status) {
        this.status = status;
        return this;
    }
    
    @Override
     public BuildStep device(Device device) {
        this.device = device;
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
    private CopyOfBuilder(String id, String cardNumber, Double balance, Integer sequenceNumber, Boolean status, Device device) {
      super(id, cardNumber, balance, sequenceNumber, status, device);
      
    }
    
    @Override
     public CopyOfBuilder cardNumber(String cardNumber) {
      return (CopyOfBuilder) super.cardNumber(cardNumber);
    }
    
    @Override
     public CopyOfBuilder balance(Double balance) {
      return (CopyOfBuilder) super.balance(balance);
    }
    
    @Override
     public CopyOfBuilder sequenceNumber(Integer sequenceNumber) {
      return (CopyOfBuilder) super.sequenceNumber(sequenceNumber);
    }
    
    @Override
     public CopyOfBuilder status(Boolean status) {
      return (CopyOfBuilder) super.status(status);
    }
    
    @Override
     public CopyOfBuilder device(Device device) {
      return (CopyOfBuilder) super.device(device);
    }
  }
  

  public static class UseIdentifier extends ModelIdentifier<Use> {
    private static final long serialVersionUID = 1L;
    public UseIdentifier(String id) {
      super(id);
    }
  }
  
}
