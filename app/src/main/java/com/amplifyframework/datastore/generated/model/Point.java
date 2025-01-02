package com.amplifyframework.datastore.generated.model;


import androidx.core.util.ObjectsCompat;

import java.util.Objects;
import java.util.List;

/** This is an auto generated class representing the Point type in your schema. */
public final class Point {
  private final Double lat;
  private final Double lng;
  private final String name;
  public Double getLat() {
      return lat;
  }
  
  public Double getLng() {
      return lng;
  }
  
  public String getName() {
      return name;
  }
  
  private Point(Double lat, Double lng, String name) {
    this.lat = lat;
    this.lng = lng;
    this.name = name;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Point point = (Point) obj;
      return ObjectsCompat.equals(getLat(), point.getLat()) &&
              ObjectsCompat.equals(getLng(), point.getLng()) &&
              ObjectsCompat.equals(getName(), point.getName());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getLat())
      .append(getLng())
      .append(getName())
      .toString()
      .hashCode();
  }
  
  public static LatStep builder() {
      return new Builder();
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(lat,
      lng,
      name);
  }
  public interface LatStep {
    LngStep lat(Double lat);
  }
  

  public interface LngStep {
    BuildStep lng(Double lng);
  }
  

  public interface BuildStep {
    Point build();
    BuildStep name(String name);
  }
  

  public static class Builder implements LatStep, LngStep, BuildStep {
    private Double lat;
    private Double lng;
    private String name;
    public Builder() {
      
    }
    
    private Builder(Double lat, Double lng, String name) {
      this.lat = lat;
      this.lng = lng;
      this.name = name;
    }
    
    @Override
     public Point build() {
        
        return new Point(
          lat,
          lng,
          name);
    }
    
    @Override
     public LngStep lat(Double lat) {
        Objects.requireNonNull(lat);
        this.lat = lat;
        return this;
    }
    
    @Override
     public BuildStep lng(Double lng) {
        Objects.requireNonNull(lng);
        this.lng = lng;
        return this;
    }
    
    @Override
     public BuildStep name(String name) {
        this.name = name;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(Double lat, Double lng, String name) {
      super(lat, lng, name);
      Objects.requireNonNull(lat);
      Objects.requireNonNull(lng);
    }
    
    @Override
     public CopyOfBuilder lat(Double lat) {
      return (CopyOfBuilder) super.lat(lat);
    }
    
    @Override
     public CopyOfBuilder lng(Double lng) {
      return (CopyOfBuilder) super.lng(lng);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
  }
  
}
