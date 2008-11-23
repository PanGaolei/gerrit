// Copyright 2008 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.gerrit.client.reviewdb;

import com.google.gwtorm.client.Column;
import com.google.gwtorm.client.IntKey;
import com.google.gwtorm.client.StringKey;

import java.sql.Timestamp;

/** Preferences and information about a single user. */
public final class Account {
  /** Globally unique key to identify a user. */
  public static class OpenId extends StringKey<com.google.gwtorm.client.Key<?>> {
    @Column
    protected String openidIdentity;

    protected OpenId() {
    }

    public OpenId(final String id) {
      openidIdentity = id;
    }

    @Override
    public String get() {
      return openidIdentity;
    }
  }

  /** Key local to Gerrit to identify a user. */
  public static class Id extends IntKey<com.google.gwtorm.client.Key<?>> {
    @Column(name = "account_id")
    protected int id;

    protected Id() {
    }

    public Id(final int id) {
      this.id = id;
    }

    @Override
    public int get() {
      return id;
    }
  }

  @Column
  protected Id localId;

  /** Identity from the OpenID provider the user authenticates through. */
  @Column
  protected OpenId openidIdentity;

  /** Date and time the user registered with the review server. */
  @Column
  protected Timestamp registeredOn;

  /** Full name of the user ("Given-name Surname" style). */
  @Column(notNull = false)
  protected String fullName;

  /** Email address the user prefers to be contacted through. */
  @Column(notNull = false)
  protected String preferredEmail;

  protected Account() {
  }

  /**
   * Create a new account.
   * 
   * @param identity identity assigned by the OpenID provider.
   * @param newId unique id, see {@link ReviewDb#nextAccountId()}.
   */
  public Account(final Account.OpenId identity, final Account.Id newId) {
    openidIdentity = identity;
    localId = newId;
    registeredOn = new Timestamp(System.currentTimeMillis());
  }

  /** Get local id of this account, to link with in other entities */
  public Account.Id getId() {
    return localId;
  }

  /** Get the full name of the user ("Given-name Surname" style). */
  public String getFullName() {
    return fullName;
  }

  /** Set the full name of the user ("Given-name Surname" style). */
  public void setFullName(final String name) {
    fullName = name;
  }

  /** Email address the user prefers to be contacted through. */
  public String getPreferredEmail() {
    return preferredEmail;
  }

  /** Set the email address the user prefers to be contacted through. */
  public void setPreferredEmail(final String addr) {
    preferredEmail = addr;
  }

  /** Get the date and time the user first registered. */
  public Timestamp getRegisteredOn() {
    return registeredOn;
  }
}
