package com.ask.test.domain.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigris.atlas.messages.Message;
import org.tigris.atlas.messages.MessageUtils;
import org.tigris.atlas.messages.Messages;
import org.tigris.atlas.service.ServiceResponse;

import com.ask.test.domain.enumeration.SimpleDomainEnumeration;
import com.ask.test.domain.transfer.SimpleDomain;
import com.ask.test.domain.transfer.TransferObjectFactory;
import com.ask.test.domain.transfer.ValidationExample;
import com.ask.test.domain.transfer.ValidationExampleChild;

/**
 * Contains common integration test logic for this project.
 */
public final class TestUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger("TestUtils");

	public static final String TEST_DOMAIN_GAV_COORDINATES = "com.ask.fermenter:fermenter-test-domain:1-SNAPSHOT";

	public static final String FERMENTER_HIBERNATE_GAV_COORDINATES = "com.ask.fermenter:fermenter-hibernate:jar:tests:1-SNAPSHOT";

	public static final String HSQLDB_GAV_COORDINATES = "org.hsqldb:hsqldb:2.3.2";

	private TestUtils() {
		// prevent instantiation
	}

	public static SimpleDomain createRandomSimpleDomain() {
		SimpleDomain domain = TransferObjectFactory.createSimpleDomain();
		domain.setName(RandomStringUtils.randomAlphanumeric(25));
		domain.setTheDate1(new Date());
		domain.setTheLong1(RandomUtils.nextLong());
		domain.setType(RandomStringUtils.random(5));
		domain.setAnEnumeratedValue(SimpleDomainEnumeration.values()[RandomUtils.nextInt(SimpleDomainEnumeration
				.values().length)]);
		return domain;
	}

	public static SimpleDomain createRandomSimpleDomain(double bigDecimalAttributeValue) {
		SimpleDomain simpleDomain = createRandomSimpleDomain();
		simpleDomain.setBigDecimalValue(BigDecimal.valueOf(bigDecimalAttributeValue));
		return simpleDomain;
	}

	public static ValidationExample createRandomValidationExample() {
		ValidationExample domain = TransferObjectFactory.createValidationExample();
		domain.setRequiredField(RandomStringUtils.randomAlphabetic(20));
		return domain;
	}

	public static ValidationExampleChild createRandomValidationExampleChild() {
		ValidationExampleChild domain = TransferObjectFactory.createValidationExampleChild();
		domain.setRequiredField(RandomStringUtils.randomAlphabetic(20));
		return domain;
	}

	public static void assertNoErrorMessages(ServiceResponse response) {
		if (response != null) {
			Messages messages = response.getMessages();
			boolean hasErrorMessages = messages.hasErrorMessages();
			if (hasErrorMessages) {
				for (Message message : messages.getErrorMessages()) {
					LOGGER.error(MessageUtils.getSummaryMessage(message.getKey(), message.getInserts(),
							SimpleDomain.class));
				}
			}
			assertFalse(hasErrorMessages);
		}
	}

	public static void assertErrorMessagesInResponse(ServiceResponse response, int expectedNumErrorMessages) {
		assertNotNull("Service response wrapper was unexpectedly null", response);
		Messages messages = response.getMessages();
		assertNotNull("Messages object on service response wrapper was unexpected null", messages);
		assertEquals("An unexpected number of error messages were found", expectedNumErrorMessages,
				messages.getErrorMessageCount());
	}

	/**
	 * Rounds the given {@link BigDecimal} using the same scale utilized by the default DECIMAL/NUMERIC SQL type for
	 * HSQLDB, which is numeric(19,2).
	 * 
	 * @param bigDecimal
	 *            decimal value to round.
	 * @return {@link BigDecimal} rounded using a scale of 2.
	 */
	public static BigDecimal roundToHSQLDBDefaultDecimalType(BigDecimal bigDecimal) {
		return bigDecimal.setScale(2, RoundingMode.HALF_UP);
	}
}
