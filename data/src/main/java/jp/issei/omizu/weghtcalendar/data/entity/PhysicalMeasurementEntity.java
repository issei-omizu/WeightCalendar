/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.issei.omizu.weghtcalendar.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import lombok.Getter;
import lombok.Setter;

/**
 * PhysicalMeasurement Entity used in the data layer.
 */
@Getter
@Setter
public class PhysicalMeasurementEntity extends RealmObject {

  @SerializedName("id")
  private int userId;

  @SerializedName("cover_url")
  private String coverUrl;

  @SerializedName("full_name")
  private String fullname;

  @SerializedName("description")
  private String description;

  @SerializedName("followers")
  private int followers;

  @SerializedName("email")
  private String email;


  private Date date;
  private String weight;
  private String bodyFatPercentage;
  private String bodyTemperature;


  public PhysicalMeasurementEntity() {
    //empty
  }

//  public int getUserId() {
//    return userId;
//  }
//
//  public void setUserId(int userId) {
//    this.userId = userId;
//  }
//
//  public String getCoverUrl() {
//    return coverUrl;
//  }
//
//  public String getFullname() {
//    return fullname;
//  }
//
//  public void setFullname(String fullname) {
//    this.fullname = fullname;
//  }
//
//  public String getDescription() {
//    return description;
//  }
//
//  public int getFollowers() {
//    return followers;
//  }
//
//  public String getEmail() {
//    return email;
//  }
//
//
//
//  public Date getDate() {
//    return date;
//  }
//
//  public void setDate(Date date) {
//    this.date = date;
//  }
//
//  public String getWeight() { return weight; }
//
//  public void setWeight(String weight) {
//    this.weight = weight;
//  }
//
//  public String getBodyFatPercentage() { return bodyFatPercentage; }
//
//  public void setBodyFatPercentage(String bodyFatPercentage) {
//    this.bodyFatPercentage = bodyFatPercentage;
//  }
//
//  public String getBodyTemperature() { return bodyTemperature; }
//
//  public void setBodyTemperature(String bodyTemperature) {
//    this.bodyTemperature = bodyTemperature;
//
//}

}
