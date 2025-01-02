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

/** This is an auto generated class representing the Route type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Routes", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PRIVATE, operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "admin" }, provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.READ, ModelOperation.UPDATE, ModelOperation.DELETE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "empresa" }, provider = "userPools", operations = { ModelOperation.READ, ModelOperation.UPDATE }),
  @AuthRule(allow = AuthStrategy.GROUPS, groupClaim = "cognito:groups", groups = { "dispositivo" }, provider = "userPools", operations = { ModelOperation.READ })
})
@Index(name = "undefined", fields = {"id"})
@Index(name = "byCompany", fields = {"companyID"})
public final class Route implements Model {
  public static final QueryField ID = field("Route", "id");
  public static final QueryField NAME = field("Route", "name");
  public static final QueryField DESCRIPTION = field("Route", "description");
  public static final QueryField COMPANY = field("Route", "companyID");
  public static final QueryField POINTS = field("Route", "points");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="Company") @BelongsTo(targetName = "companyID", targetNames = {"companyID"}, type = Company.class) Company company;
  private final @ModelField(targetType="Device") @HasMany(associatedWith = "route", type = Device.class) List<Device> devices = null;
  private final @ModelField(targetType="Point") List<Point> points;
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
  
  public String getDescription() {
      return description;
  }
  
  public Company getCompany() {
      return company;
  }
  
  public List<Device> getDevices() {
      return devices;
  }
  
  public List<Point> getPoints() {
      return points;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Route(String id, String name, String description, Company company, List<Point> points) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.company = company;
    this.points = points;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Route route = (Route) obj;
      return ObjectsCompat.equals(getId(), route.getId()) &&
              ObjectsCompat.equals(getName(), route.getName()) &&
              ObjectsCompat.equals(getDescription(), route.getDescription()) &&
              ObjectsCompat.equals(getCompany(), route.getCompany()) &&
              ObjectsCompat.equals(getPoints(), route.getPoints()) &&
              ObjectsCompat.equals(getCreatedAt(), route.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), route.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getCompany())
      .append(getPoints())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Route {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("company=" + String.valueOf(getCompany()) + ", ")
      .append("points=" + String.valueOf(getPoints()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Route justId(String id) {
    return new Route(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      company,
      points);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Route build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep company(Company company);
    BuildStep points(List<Point> points);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private Company company;
    private List<Point> points;
    public Builder() {
      
    }
    
    private Builder(String id, String name, String description, Company company, List<Point> points) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.company = company;
      this.points = points;
    }
    
    @Override
     public Route build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Route(
          id,
          name,
          description,
          company,
          points);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep company(Company company) {
        this.company = company;
        return this;
    }
    
    @Override
     public BuildStep points(List<Point> points) {
        this.points = points;
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
    private CopyOfBuilder(String id, String name, String description, Company company, List<Point> points) {
      super(id, name, description, company, points);
      Objects.requireNonNull(name);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder company(Company company) {
      return (CopyOfBuilder) super.company(company);
    }
    
    @Override
     public CopyOfBuilder points(List<Point> points) {
      return (CopyOfBuilder) super.points(points);
    }
  }
  

  public static class RouteIdentifier extends ModelIdentifier<Route> {
    private static final long serialVersionUID = 1L;
    public RouteIdentifier(String id) {
      super(id);
    }
  }
  
}
