/*   
 *   <Student Affairs Information System>  
 *
 *   Copyright (C) <2017>  <M.Ridvan Ozcan>
 *
 *   This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package controllers;

/**
 *
 * @author M.Ridvan Ozcan
 */
public class CourseList {
    private String Id;
    private String courseName;
    private String capacity;
    private String AssistantId;
    private String time;

    public CourseList(String Id, String courseName, String capacity, String AssistantId, String time) {
        this.Id = Id;
        this.courseName = courseName;
        this.capacity = capacity;
        this.AssistantId = AssistantId;
        this.time = time;
    }

    /**
     * @return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return the courseName
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * @param courseName the courseName to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * @return the capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * @param capacity the capacity to set
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * @return the AssistantId
     */
    public String getAssistantId() {
        return AssistantId;
    }

    /**
     * @param AssistantId the AssistantId to set
     */
    public void setAssistantId(String AssistantId) {
        this.AssistantId = AssistantId;
    }

    /**
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(String time) {
        this.time = time;
    }
    
    
}
