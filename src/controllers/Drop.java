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
public class Drop {

    private String LessonId;
    private String LessonName;

    public Drop(String LessonId, String LessonName) {
        this.LessonId = LessonId;
        this.LessonName = LessonName;
    }

    /**
     * @return the LessonId
     */
    public String getLessonId() {
        return LessonId;
    }

    /**
     * @param LessonId the LessonId to set
     */
    public void setLessonId(String LessonId) {
        this.LessonId = LessonId;
    }

    /**
     * @return the LessonName
     */
    public String getLessonName() {
        return LessonName;
    }

    /**
     * @param LessonName the LessonName to set
     */
    public void setLessonName(String LessonName) {
        this.LessonName = LessonName;
    }

}
