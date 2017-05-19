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
package jp.issei.omizu.weightcalendar.domain;

import java.util.Date;

/**
 * Class that represents a PhysicalMeasurement in the domain layer.
 */
public class PhysicalMeasurement {

  private final int userId;

  public PhysicalMeasurement(int userId) {
    this.userId = userId;
  }

  private String coverUrl;
  private String fullName;
  private String email;
  private String description;
  private int followers;

  private Date date;
  private String weight;
  private String bodyFatPercentage;
  private String bodyTemperature;


  public int getUserId() {
    return userId;
  }

  public String getCoverUrl() {
    return coverUrl;
  }

  public void setCoverUrl(String coverUrl) {
    this.coverUrl = coverUrl;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getFollowers() {
    return followers;
  }

  public void setFollowers(int followers) {
    this.followers = followers;
  }


  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getWeight() { return weight; }

  public void setWeight(String weight) {
    this.weight = weight;
  }

  public String getBodyFatPercentage() { return bodyFatPercentage; }

  public void setBodyFatPercentage(String bodyFatPercentage) {
    this.bodyFatPercentage = bodyFatPercentage;
  }

  public String getBodyTemperature() { return bodyTemperature; }

  public void setBodyTemperature(String bodyTemperature) {
    this.bodyTemperature = bodyTemperature;
  }


}
