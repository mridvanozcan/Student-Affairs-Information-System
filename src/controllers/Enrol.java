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
public class Enrol {

    private String CourseId;
    private String CourseName;
    private String CourseInfo;

    public Enrol(String CourseId, String CourseName, String CourseInfo) {
        this.CourseId = CourseId;
        this.CourseName = CourseName;
        this.CourseInfo = CourseInfo;
    }


    

    /**
     * @return the CourseId
     */
    public String getCourseId() {
        return CourseId;
    }

    /**
     * @param CourseId the CourseId to set
     */
    public void setCourseId(String CourseId) {
        this.CourseId = CourseId;
    }

    /**
     * @return the CourseName
     */
    public String getCourseName() {
        return CourseName;
    }

    /**
     * @param CourseName the CourseName to set
     */
    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    /**
     * @return the CourseInfo
     */
    public String getCourseInfo() {
        return CourseInfo;
    }

    /**
     * @param CourseInfo the CourseInfo to set
     */
    public void setCourseInfo(String CourseInfo) {
        this.CourseInfo = CourseInfo;
    }

}
