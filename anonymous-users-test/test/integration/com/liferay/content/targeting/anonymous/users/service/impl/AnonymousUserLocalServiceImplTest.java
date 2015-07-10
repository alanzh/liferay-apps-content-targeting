/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.content.targeting.anonymous.users.service.impl;

import com.liferay.content.targeting.anonymous.users.model.AnonymousUser;
import com.liferay.content.targeting.anonymous.users.service.AnonymousUserLocalService;
import com.liferay.content.targeting.service.test.service.ServiceTestUtil;
import com.liferay.content.targeting.service.test.util.TestPropsValues;
import com.liferay.osgi.util.service.ServiceTrackerUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;

import java.util.Calendar;
import java.util.Date;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleException;

/**
 * @author Eduardo Garcia
 */
@RunWith(Arquillian.class)
public class AnonymousUserLocalServiceImplTest {

	@Before
	public void setUp() {
		try {
			_bundle.start();
		}
		catch (BundleException e) {
			e.printStackTrace();
		}

		_anonymousUserLocalService = ServiceTrackerUtil.getService(
			AnonymousUserLocalService.class, _bundle.getBundleContext());
	}

	@Test
	public void testAddAndDeleteAnonymousUser() throws Exception {
		int initAnonymousUsersCount =
			_anonymousUserLocalService.getAnonymousUsersCount();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		AnonymousUser anonymousUser =
			_anonymousUserLocalService.addAnonymousUser(
			1, "127.0.0.1", StringPool.BLANK, serviceContext);

		Assert.assertEquals(
			initAnonymousUsersCount + 1,
			_anonymousUserLocalService.getAnonymousUsersCount());

		_anonymousUserLocalService.deleteAnonymousUser(anonymousUser);

		Assert.assertEquals(
			initAnonymousUsersCount,
			_anonymousUserLocalService.getAnonymousUsersCount());
	}

	@Test
	public void testAddUpdateFetchAnonymousUser() throws Exception {
		int initAnonymousUsersCount =
			_anonymousUserLocalService.getAnonymousUsersCount();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		AnonymousUser anonymousUser =
			_anonymousUserLocalService.addAnonymousUser(
			1, "127.0.0.1", StringPool.BLANK, serviceContext);

		Assert.assertEquals(
			initAnonymousUsersCount + 1,
			_anonymousUserLocalService.getAnonymousUsersCount());

		_anonymousUserLocalService.updateAnonymousUser(
			anonymousUser.getAnonymousUserId(), TestPropsValues.getUserId(),
			"127.0.0.2","",serviceContext);

		AnonymousUser updatedAnonymousUser =
			_anonymousUserLocalService.fetchAnonymousUserByUserId(
				TestPropsValues.getUserId());

		Assert.assertEquals(anonymousUser, updatedAnonymousUser);

		_anonymousUserLocalService.deleteAnonymousUser(anonymousUser);

		Assert.assertEquals(
			initAnonymousUsersCount,
			_anonymousUserLocalService.getAnonymousUsersCount());
	}

	@Test
	public void testCleanupAnonymousUser() throws Exception {
		int initAnonymousUsersCount =
			_anonymousUserLocalService.getAnonymousUsersCount();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext();

		AnonymousUser anonymousUser =
			_anonymousUserLocalService.addAnonymousUser(
			1, "127.0.0.1", StringPool.BLANK, serviceContext);

		Assert.assertEquals(
			initAnonymousUsersCount + 1,
			_anonymousUserLocalService.getAnonymousUsersCount());

		anonymousUser.setCreateDate(getMockCreateDate());

		_anonymousUserLocalService.updateAnonymousUser(anonymousUser);

		/* TODO: add cleanup call */
	}

	protected Date getMockCreateDate() {
		Calendar calendar = Calendar.getInstance();

		calendar.setTime(new Date());

		int maxAge = 5;
		/*TODO: uncomment after merge of WCM-399
		int maxAge = PortletPropsValues.ANALYTICS_EVENTS_MAX_AGE+1;
		*/

		calendar.add(Calendar.DAY_OF_YEAR, -maxAge);

		return calendar.getTime();
	}

	private AnonymousUserLocalService _anonymousUserLocalService;

	@ArquillianResource
	private Bundle _bundle;

}