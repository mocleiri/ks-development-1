package org.kuali.student.ap.framework.context.support;

import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.type.EntityTypeContactInfo;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.UserSessionHelper;

public class DefaultUserSessionHelper implements UserSessionHelper {

	private static final Logger logger = Logger
			.getLogger(UserSessionHelper.class);

	@Override
	public boolean isAdviser() {
		return false;
	}

	@Override
	public String getStudentId() {
		UserSession session = GlobalVariables.getUserSession();
		String studentId;
		if (isAdviser()) {
			studentId = (String) session
					.retrieveObject(PlanConstants.SESSION_KEY_STUDENT_ID);
			if (studentId == null) {
				throw new RuntimeException(
						"User is in adviser mode, but no student id was set in the session. (This shouldn't happen and should be reported).");
			}
		} else {
			studentId = session.getPerson().getPrincipalId();
		}
		return studentId;
	}

	@Override
	public String getStudentName() {
		UserSession session = GlobalVariables.getUserSession();

		return session.getPerson().getFirstName().substring(0, 1).toUpperCase()
				+ session
						.getPerson()
						.getFirstName()
						.substring(1,
								session.getPerson().getFirstName().length())
				+ " "
				+ session.getPerson().getLastName().substring(0, 1)
						.toUpperCase();
	}

	@Override
	public synchronized String getName(String principleId) {
		Person person = null;
		try {
			person = KimApiServiceLocator.getPersonService().getPerson(principleId);
		} catch (Exception e) {
			logger.error("Could not load the Person Information", e);
		}
		if (person != null) {
			return String.format("%s %s", person.getFirstName(),
					person.getLastName());
		} else {
			return null;
		}
	}

	@Override
	public String getNameCapitalized(String principleId) {

		try {
			Person person = KimApiServiceLocator.getPersonService().getPerson(principleId);
			return person.getName().toUpperCase();

			// String firstName = capitalize(person.getFirstName());
			// String middleName = capitalize(person.getMiddleName());
			// String lastName = capitalize(person.getLastName());

			// return firstName + " " + middleName + " " + lastName;
		} catch (Exception e) {
			logger.error("Could not load the Person Information", e);
		}

		return null;

	}

	@Override
	public String capitalize(String value) {
		if (value == null)
			return null;
		if (value.length() == 0)
			return value;
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}

	@Override
	public String getMailAddress(String principleId) {
		try {
			Person user = GlobalVariables.getUserSession().getPerson();
			String emailAddress = user.getEmailAddress();

			Entity entity = KimApiServiceLocator.getIdentityService().getEntityByPrincipalId(
					principleId);
			if (entity == null) {
				return null;
			}
			List<EntityTypeContactInfo> contactInfos = entity
					.getEntityTypeContactInfos();
			for (EntityTypeContactInfo ci : contactInfos) {
				emailAddress = ci.getDefaultEmailAddress().getEmailAddress();
				/*
				 * for (EntityEmail e : ci.getEmailAddresses()) { // FIXME:
				 * Probably want to make this more deterministic. if
				 * (e.getEmailType().getName().equals("Student")) { emailAddress
				 * = e.getEmailAddress(); } if
				 * (e.getEmailType().getName().equals("Employee")) {
				 * emailAddress = e.getEmailAddress(); } }
				 */
			}
			return emailAddress;
		} catch (Exception e) {
			logger.error("Could not get the Email Address for the student" + e);
			return null;
		}
	}

	@Override
	public String getAuditSystemKey() {
		return getStudentId();
	}

	@Override
	public boolean isStudent() {
		Person person = null;
		boolean isStudent = false;
		try {
			person = KimApiServiceLocator.getPersonService().getPerson(getStudentId());
		} catch (Exception e) {
			logger.error("Could not load the Person Information", e);
		}
		if (person != null) {
			if (person.getExternalIdentifiers().containsKey("systemKey")) {
				isStudent = true;
			}
		}
		return isStudent;
	}

}
