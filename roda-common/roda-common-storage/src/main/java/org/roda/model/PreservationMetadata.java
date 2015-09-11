package org.roda.model;

import org.roda.storage.StoragePath;

public class PreservationMetadata {

  private final String id;
  private final String aipId;
  private final String representationID;
  private final StoragePath storagePath;

  public PreservationMetadata(String id, String aipId, String representationId, StoragePath storagePath) {
    super();
    this.id = id;
    this.aipId = aipId;
    this.representationID = representationId;
    this.storagePath = storagePath;
  }

  /**
   * @return the id
   */
  public String getId() {
    return id;
  }

  /**
   * @return the aipId
   */
  public String getAipId() {
    return aipId;
  }

  /**
   * @return the storagePath
   */
  public StoragePath getStoragePath() {
    return storagePath;
  }

  @Override
  public String toString() {
    return "PreservationMetadata [id=" + id + ", aipId=" + aipId + ", storagePath=" + storagePath + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((aipId == null) ? 0 : aipId.hashCode());
    result = prime * result + ((representationID == null) ? 0 : representationID.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((storagePath == null) ? 0 : storagePath.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    PreservationMetadata other = (PreservationMetadata) obj;
    if (aipId == null) {
      if (other.aipId != null) {
        return false;
      }
    } else if (!aipId.equals(other.aipId)) {
      return false;
    }
    if (representationID == null) {
      if (other.representationID != null) {
        return false;
      }
    } else if (!representationID.equals(other.representationID)) {
      return false;
    }
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    if (storagePath == null) {
      if (other.storagePath != null) {
        return false;
      }
    } else if (!storagePath.equals(other.storagePath)) {
      return false;
    }
    return true;
  }

}
