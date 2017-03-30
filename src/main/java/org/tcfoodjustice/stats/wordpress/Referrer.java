
package org.tcfoodjustice.stats.wordpress;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "days"
})
public class Referrer {

    @JsonProperty("days")
    private Map<String, Map<String, Object>>  days;

    @JsonProperty("days")
    public  Map<String, Map<String, Object>>  getDays() {
        return days;
    }

    @JsonProperty("days")
    public void setDays( Map<String, Map<String, Object>>  days) {
        this.days = days;
    }

    /**
     * Method to return groups from response object
     * @return
     */
    public List<Group> getGroups() throws IOException {
        if(days.isEmpty()) {
            return new ArrayList<>();
        }
        Map<String,Object> groups = (Map<String,Object> ) days.values().toArray()[0];
        if(groups.isEmpty()){
            return new ArrayList<>();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        //we need to parse out this awkward object
        //todo-refactor to customize serialization
        Object groupsObject = groups.values().toArray()[0];
        String groupsString = objectMapper.writeValueAsString(groupsObject);
        List<Group> groupList = objectMapper.readValue(groupsString, new TypeReference<List<Group>>(){});
        return groupList;
    }

    @Override
    public String toString() {
        return "Referrer{" +
                "days=" + days +
                '}';
    }
}
