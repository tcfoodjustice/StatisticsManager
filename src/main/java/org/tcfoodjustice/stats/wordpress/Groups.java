package org.tcfoodjustice.stats.wordpress;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by andrew.larsen on 3/26/2017.
 */
public class Groups {

    @JsonProperty("groups")
    private List<Group> groups;
    @JsonProperty("other_views")
    private Integer otherViews;
    @JsonProperty("total_views")
    private Integer totalViews;

    public Integer getOtherViews() {
        return otherViews;
    }

    public void setOtherViews(Integer otherViews) {
        this.otherViews = otherViews;
    }

    public Integer getTotalViews() {
        return totalViews;
    }

    public void setTotalViews(Integer totalViews) {
        this.totalViews = totalViews;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

}
