/*
 * Copyright 2014 The Kuali Foundation
 * 
 * Licensed under the Educational Community License, Version 1.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.kuali.student.enrollment.class2.academicrecord.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.mock.MockService;
import org.kuali.student.enrollment.academicrecord.dto.GPAInfo;
import org.kuali.student.enrollment.academicrecord.dto.LoadInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentCredentialRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentProgramRecordInfo;
import org.kuali.student.enrollment.academicrecord.dto.StudentTestScoreRecordInfo;
import org.kuali.student.enrollment.academicrecord.service.AcademicRecordService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Kuali Student Team
 */
public class AcademicRecordServiceMapImpl implements
        AcademicRecordService, MockService {

    private static final Logger log = LoggerFactory
            .getLogger(AcademicRecordServiceMapImpl.class);

    //Mock datastructures
    private Map<String, GPAInfo> gpasMap = new LinkedHashMap<String, GPAInfo>();
    private List<StudentCourseRecordInfo> courseRecordInfoList = new ArrayList<StudentCourseRecordInfo>();        //to be replaced with studentToCourseRecordsMap
    private Map<String, LoadInfo> loadsMap = new LinkedHashMap<String, LoadInfo>();
    private Map<String, StudentCredentialRecordInfo> studentCredentialRecordsMap = new LinkedHashMap<String, StudentCredentialRecordInfo>();
    private Map<String, StudentTestScoreRecordInfo> studentTestScoreRecordsMap = new LinkedHashMap<String, StudentTestScoreRecordInfo>();

    private Map<String, List<StudentCourseRecordInfo>> studentToCourseRecordsMap = new HashMap<String, List<StudentCourseRecordInfo>>();
    private Map<String, List<StudentProgramRecordInfo>> studentToProgramRecordsMap = new LinkedHashMap<String, List<StudentProgramRecordInfo>>();
    private Map<String, List<StudentCourseRecordInfo>> termToCourseRecordsMap = new HashMap<String, List<StudentCourseRecordInfo>>();
    private Set<StudentCourseRecordInfo> studentCourseRecordsSet = new HashSet<StudentCourseRecordInfo>();

    // this is a bit of a hack until the record can contain the course id directly
    private Map<String, String> courseIdToCourseCodeMap = new HashMap<String, String>();

    private int countProgramId = 1;

    /* (non-Javadoc)
     * @see org.kuali.student.common.mock.MockService#clear()
     */
    @Override
    public void clear() {
        studentToCourseRecordsMap.clear();
        termToCourseRecordsMap.clear();
        courseIdToCourseCodeMap.clear();
        studentCourseRecordsSet.clear();

        gpasMap.clear();
        courseRecordInfoList.clear();
        loadsMap.clear();
        studentToProgramRecordsMap.clear();
        studentCredentialRecordsMap.clear();
        studentTestScoreRecordsMap.clear();
    }

    /**
     * Store a course record for the term specified.  The caller is responsible for filling in the object correctly.
     *
     * @param studentId    the student who completed the course
     * @param termId       the term the course is from
     * @param courseRecord the course record itself.
     */
    public void storeStudentCourseRecord(String studentId, String termId, String courseId, StudentCourseRecordInfo courseRecord) {

        studentCourseRecordsSet.add(courseRecord);

        courseIdToCourseCodeMap.put(courseId, courseRecord.getCourseCode());

        // link to student
        List<StudentCourseRecordInfo> studentCourseList = studentToCourseRecordsMap.get(studentId);

        if (studentCourseList == null) {
            studentCourseList = new ArrayList<StudentCourseRecordInfo>();
            studentToCourseRecordsMap.put(studentId, studentCourseList);
        }

        studentCourseList.add(courseRecord);

        // link to term

        List<StudentCourseRecordInfo> termCourseList = termToCourseRecordsMap.get(termId);

        if (termCourseList == null) {
            termCourseList = new ArrayList<StudentCourseRecordInfo>();
            termToCourseRecordsMap.put(termId, termCourseList);
        }

        termCourseList.add(courseRecord);
    }

    /**
     * Store a program record for the term specified.  The caller is responsible for filling in the object correctly.
     *
     * @param studentId    the student who completed the course
     * @param programId       the id of the program
     * @param programRecord the course record itself.
     */
    public void storeStudentProgramRecord(String studentId, String programId, StudentProgramRecordInfo programRecord) {

        // link to student
        List<StudentProgramRecordInfo> studentProgramList = studentToProgramRecordsMap.get(studentId);

        if (studentProgramList == null) {
            studentProgramList = new ArrayList<StudentProgramRecordInfo>();
            studentToProgramRecordsMap.put(studentId, studentProgramList);
        }

        studentProgramList.add(programRecord);

    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getAttemptedCourseRecordsForTerm(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getAttemptedCourseRecordsForTerm(
            String personId,
            String termId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList) {
            if (courseRecord.getPersonId().equals(personId) && courseRecord.getTermName().equals(termId)) {
                courseRecords.add(courseRecord);
            }
        }
        return courseRecords;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecords(
            String personId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        if (!studentToCourseRecordsMap.keySet().contains(personId))
            throw new DoesNotExistException("No course records for student Id = " + personId);

        return studentToCourseRecordsMap.get(personId);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecordsForCourse(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForCourse(
            String personId, String courseId, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<StudentCourseRecordInfo> resultsList = new ArrayList<StudentCourseRecordInfo>();

        if (!studentToCourseRecordsMap.keySet().contains(personId))
            throw new DoesNotExistException("No course records for student Id = " + personId);

        // Should not throw exception, course could still exist in cluservice and i do want to check if any student has completed it.
        //if (!courseIdToCourseCodeMap.keySet().contains(courseId))
        //    throw new DoesNotExistException("No course records for course id = " + courseId);

        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);

        String courseCode = courseIdToCourseCodeMap.get(courseId);

        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {

            if (studentCourseRecordInfo.getCourseCode().equals(courseCode))
                resultsList.add(studentCourseRecordInfo);

        }

        return resultsList;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCompletedCourseRecordsForTerm(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCourseRecordInfo> getCompletedCourseRecordsForTerm(
            String personId,
            String termId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        List<StudentCourseRecordInfo> courseRecords = new ArrayList<StudentCourseRecordInfo>();
        for (StudentCourseRecordInfo courseRecord : courseRecordInfoList) {
            if (courseRecord.getPersonId().equals(personId) && courseRecord.getTermName().equals(termId) && (courseRecord.getAssignedGradeValue() != null || courseRecord.getAdministrativeGradeValue() != null)) {
                courseRecords.add(courseRecord);
            }
        }
        return courseRecords;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getGPAForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getGPAForTerm(String personId,
                                 String termId,
                                 String calculationTypeKey,
                                 ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa1");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPA(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPA(
            String personId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa3");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#calculateGPA(java.util.List<org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo>, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo calculateGPA(List<StudentCourseRecordInfo> studentCourseRecordInfoList, String calculationTypeKey, ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        //This is a mock GPA calculation
        float totalCredits = 0.0f;
        float gradePoints = 0.0f;
        for (StudentCourseRecordInfo info : studentCourseRecordInfoList){
            float creditsForGPA = Float.parseFloat(info.getCreditsForGPA());
            gradePoints += Float.parseFloat(info.getCalculatedGradeValue())*creditsForGPA;
            totalCredits += creditsForGPA;
        }

        GPAInfo gpa = new GPAInfo();
        gpa.setCalculationTypeKey(calculationTypeKey);
        gpa.setScaleKey("1");
        gpa.setValue(String.valueOf(gradePoints/totalCredits));
        return gpa;
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPAForProgram(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPAForProgram(
            String personId,
            String programId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa2");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getCumulativeGPAForTermAndProgram(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public GPAInfo getCumulativeGPAForTermAndProgram(
            String personId,
            String programId,
            String termKey,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return gpasMap.get("gpa2");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getLoadForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LoadInfo getLoadForTerm(
            String personId,
            String termId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return loadsMap.get("mediumLoad");
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getProgramRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentProgramRecordInfo> getProgramRecords(
            String personId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {

        List<StudentProgramRecordInfo> resultsList = new ArrayList<StudentProgramRecordInfo>();

        if (!studentToProgramRecordsMap.keySet().contains(personId))
            throw new DoesNotExistException("No program records for student Id = " + personId);

        return studentToProgramRecordsMap.get(personId);

    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getAwardedCredentials(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentCredentialRecordInfo> getAwardedCredentials(
            String personId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return Collections.singletonList(studentCredentialRecordsMap.get("1"));
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getTestScoreRecords(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecords(
            String personId,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return Collections.singletonList(studentTestScoreRecordsMap.get("1"));
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getTestScoreRecordsByType(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<StudentTestScoreRecordInfo> getTestScoreRecordsByType(
            String personId,
            String testTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        return Collections.singletonList(studentTestScoreRecordsMap.get("2"));
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCreditsForTerm(java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCreditsForTerm(
            String personId,
            String termId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Integer credits = 0;
        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            credits += Integer.parseInt(studentCourseRecordInfo.getCreditsEarned());
        }
        return String.valueOf(credits);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCredits(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCredits(
            String personId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Integer credits = 0;
        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            credits += Integer.parseInt(studentCourseRecordInfo.getCreditsEarned());
        }
        return String.valueOf(credits);
    }

    /* (non-Javadoc)
     * @see org.kuali.student.enrollment.academicrecord.service.AcademicRecordService#getEarnedCumulativeCreditsForProgramAndTerm(java.lang.String, java.lang.String, java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public String getEarnedCumulativeCreditsForProgramAndTerm(
            String personId,
            String programId,
            String termId,
            String calculationTypeKey,
            ContextInfo contextInfo)
            throws DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        Integer credits = 0;
        List<StudentCourseRecordInfo> records = studentToCourseRecordsMap.get(personId);
        for (StudentCourseRecordInfo studentCourseRecordInfo : records) {
            credits += Integer.parseInt(studentCourseRecordInfo.getCreditsEarned());
        }
        return String.valueOf(credits);
    }

    public StudentProgramRecordInfo createStudentProgramRecord(String studentId,
                                                               StudentProgramRecordInfo studentProgramRecord,
                                                               ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        String programId = generateProgramId();
        studentProgramRecord.setProgramId(programId);
        String classStanding = calculateClassStanding(studentProgramRecord);
        studentProgramRecord.setClassStanding(classStanding);
        storeStudentProgramRecord(studentId, programId, studentProgramRecord);
        return studentProgramRecord;
    }

    public StudentProgramRecordInfo updateStudentProgramRecord(String studentProgramRecordId,
                                                               StudentProgramRecordInfo studentProgramRecord,
                                                               ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        String classStanding = calculateClassStanding(studentProgramRecord);
        studentProgramRecord.setClassStanding(classStanding);
        String studentId = null;
        String programId = studentProgramRecord.getProgramId();
        storeStudentProgramRecord(studentId, programId, studentProgramRecord);
        return studentProgramRecord;
    }

    public StatusInfo deleteStudentProgramRecord(String studentProgramRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StudentCourseRecordInfo createStudentCourseRecord(String studentId, String courseRegistrationId,
                                                             StudentCourseRecordInfo studentCourseRecord,
                                                             ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        String termId = studentCourseRecord.getTermId();
        String courseOfferingId = studentCourseRecord.getCourseOfferingId();
        storeStudentCourseRecord(studentId, termId, courseRegistrationId, studentCourseRecord);
        return studentCourseRecord;
    }

    public StudentCourseRecordInfo updateStudentCourseRecord(String studentCourseRecordId,
                                                             StudentCourseRecordInfo studentCourseRecord,
                                                             ContextInfo contextInfo) throws
            DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StatusInfo deleteStudentCourseRecord(String studentCourseRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StudentCredentialRecordInfo createStudentCredentialRecord(String studentId,
                                                                    StudentCredentialRecordInfo studentCredentialRecord,
                                                                    ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        studentCredentialRecordsMap.put(studentId, studentCredentialRecord);
        return studentCredentialRecord;
    }

    public StudentCredentialRecordInfo updateStudentCredentialRecord(String studentCredentialRecordId,
                                                                    StudentCredentialRecordInfo studentCredentialRecord,
                                                                    ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StatusInfo deleteStudentCredentialRecord(String studentCredentialRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StudentTestScoreRecordInfo createStudentTestScoreRecord(String studentId,
                                                                   StudentTestScoreRecordInfo studentTestScoreRecord,
                                                                   ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        studentTestScoreRecordsMap.put(studentId, studentTestScoreRecord);
        return studentTestScoreRecord;
    }

    public StudentTestScoreRecordInfo updateStudentTestScoreRecord(String studentTestScoreRecordId,
                                                                   StudentTestScoreRecordInfo studentTestScoreRecord,
                                                                   ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StatusInfo deleteStudentTestScoreRecord(String studentTestScoreRecordId, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public GPAInfo createGPA(String studentId, String programId, String resultScaleId, String atpId,
                             GPAInfo gpa, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException, ReadOnlyException {
        gpasMap.put(studentId, gpa);
        return gpa;
    }

    public GPAInfo updateGPA(String gpaId, GPAInfo gpa, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StatusInfo deleteGPA(String gpaId, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public LoadInfo createLoad(String studentId, String atpId, LoadInfo load, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        loadsMap.put(studentId, load);
        return load;
    }

    public LoadInfo updateLoad(String loadId, LoadInfo load, ContextInfo contextInfo)
            throws DataValidationErrorException, DoesNotExistException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException, ReadOnlyException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    public StatusInfo deleteLoad(String loadId, ContextInfo contextInfo) throws DataValidationErrorException,
            DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        throw new UnsupportedOperationException("This method is not yet supported.");
    }

    // calculate class standing based on credits earned for this
    // poc but will likely be based on a ges rule in the future
    private String calculateClassStanding(StudentProgramRecordInfo studentProgramRecord)
            throws InvalidParameterException {
        String classStanding = null;
        try {
            int creditsEarned = Integer.valueOf(studentProgramRecord.getCreditsEarned());
            if(creditsEarned <= AcademicRecordServiceConstants.FRESHMAN_THRESHOLD) {
                classStanding = ClassStanding.FRESHMAN.getDescription();
            } else if(creditsEarned <= AcademicRecordServiceConstants.SOPHOMORE_THRESHOLD) {
                classStanding = ClassStanding.SOPHOMORE.getDescription();
            } else if(creditsEarned <= AcademicRecordServiceConstants.JUNIOR_THRESHOLD) {
                classStanding = ClassStanding.JUNIOR.getDescription();
            } else if(creditsEarned >= AcademicRecordServiceConstants.SENIOR_THRESHOLD) {
                classStanding = ClassStanding.SENIOR.getDescription();
            }
            return classStanding;
        } catch(NumberFormatException e) {
            throw new InvalidParameterException("Credits Earned value is not valid: "
                    + studentProgramRecord.getCreditsEarned());
        }
    }

    // simple sequence generator for testing
    private String generateProgramId() {
        return Integer.toString(this.countProgramId++);
    }
}
