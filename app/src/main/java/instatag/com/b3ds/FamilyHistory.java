package instatag.com.b3ds;

/**
 * Created by RahulReign on 09-04-2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FamilyHistory {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("member_name")
    @Expose
    private String memberName;
    @SerializedName("relationship_id")
    @Expose
    private String relationshipId;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("disease_id")
    @Expose
    private String diseaseId;
    @SerializedName("current_status")
    @Expose
    private String currentStatus;
    @SerializedName("year")
    @Expose
    private String year;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("added_by")
    @Expose
    private String addedBy;
    @SerializedName("updated_by")
    @Expose
    private String updatedBy;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("created")
    @Expose
    private String created;
    @SerializedName("modified")
    @Expose
    private String modified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRelationshipId() {
        return relationshipId;
    }

    public void setRelationshipId(String relationshipId) {
        this.relationshipId = relationshipId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

}
