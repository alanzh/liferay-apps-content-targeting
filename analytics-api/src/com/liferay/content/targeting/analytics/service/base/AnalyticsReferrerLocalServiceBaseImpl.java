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

package com.liferay.content.targeting.analytics.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.content.targeting.analytics.model.AnalyticsReferrer;
import com.liferay.content.targeting.analytics.service.AnalyticsReferrerLocalService;
import com.liferay.content.targeting.analytics.service.persistence.AnalyticsEventFinder;
import com.liferay.content.targeting.analytics.service.persistence.AnalyticsEventPersistence;
import com.liferay.content.targeting.analytics.service.persistence.AnalyticsReferrerPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.ClassNamePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the analytics referrer local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.content.targeting.analytics.service.impl.AnalyticsReferrerLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.content.targeting.analytics.service.impl.AnalyticsReferrerLocalServiceImpl
 * @see com.liferay.content.targeting.analytics.service.AnalyticsReferrerLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class AnalyticsReferrerLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AnalyticsReferrerLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.content.targeting.analytics.service.AnalyticsReferrerLocalServiceUtil} to access the analytics referrer local service.
	 */

	/**
	 * Adds the analytics referrer to the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsReferrer the analytics referrer
	 * @return the analytics referrer that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AnalyticsReferrer addAnalyticsReferrer(
		AnalyticsReferrer analyticsReferrer) {
		analyticsReferrer.setNew(true);

		return analyticsReferrerPersistence.update(analyticsReferrer);
	}

	/**
	 * Creates a new analytics referrer with the primary key. Does not add the analytics referrer to the database.
	 *
	 * @param analyticsReferrerId the primary key for the new analytics referrer
	 * @return the new analytics referrer
	 */
	@Override
	public AnalyticsReferrer createAnalyticsReferrer(long analyticsReferrerId) {
		return analyticsReferrerPersistence.create(analyticsReferrerId);
	}

	/**
	 * Deletes the analytics referrer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsReferrerId the primary key of the analytics referrer
	 * @return the analytics referrer that was removed
	 * @throws PortalException if a analytics referrer with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AnalyticsReferrer deleteAnalyticsReferrer(long analyticsReferrerId)
		throws PortalException {
		return analyticsReferrerPersistence.remove(analyticsReferrerId);
	}

	/**
	 * Deletes the analytics referrer from the database. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsReferrer the analytics referrer
	 * @return the analytics referrer that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AnalyticsReferrer deleteAnalyticsReferrer(
		AnalyticsReferrer analyticsReferrer) {
		return analyticsReferrerPersistence.remove(analyticsReferrer);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(AnalyticsReferrer.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return analyticsReferrerPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.analytics.model.impl.AnalyticsReferrerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return analyticsReferrerPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.analytics.model.impl.AnalyticsReferrerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return analyticsReferrerPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return analyticsReferrerPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return analyticsReferrerPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public AnalyticsReferrer fetchAnalyticsReferrer(long analyticsReferrerId) {
		return analyticsReferrerPersistence.fetchByPrimaryKey(analyticsReferrerId);
	}

	/**
	 * Returns the analytics referrer with the primary key.
	 *
	 * @param analyticsReferrerId the primary key of the analytics referrer
	 * @return the analytics referrer
	 * @throws PortalException if a analytics referrer with the primary key could not be found
	 */
	@Override
	public AnalyticsReferrer getAnalyticsReferrer(long analyticsReferrerId)
		throws PortalException {
		return analyticsReferrerPersistence.findByPrimaryKey(analyticsReferrerId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(com.liferay.content.targeting.analytics.service.AnalyticsReferrerLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AnalyticsReferrer.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("analyticsReferrerId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(com.liferay.content.targeting.analytics.service.AnalyticsReferrerLocalServiceUtil.getService());
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(AnalyticsReferrer.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"analyticsReferrerId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(com.liferay.content.targeting.analytics.service.AnalyticsReferrerLocalServiceUtil.getService());
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AnalyticsReferrer.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("analyticsReferrerId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return analyticsReferrerLocalService.deleteAnalyticsReferrer((AnalyticsReferrer)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return analyticsReferrerPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the analytics referrers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.content.targeting.analytics.model.impl.AnalyticsReferrerModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of analytics referrers
	 * @param end the upper bound of the range of analytics referrers (not inclusive)
	 * @return the range of analytics referrers
	 */
	@Override
	public List<AnalyticsReferrer> getAnalyticsReferrers(int start, int end) {
		return analyticsReferrerPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of analytics referrers.
	 *
	 * @return the number of analytics referrers
	 */
	@Override
	public int getAnalyticsReferrersCount() {
		return analyticsReferrerPersistence.countAll();
	}

	/**
	 * Updates the analytics referrer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param analyticsReferrer the analytics referrer
	 * @return the analytics referrer that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AnalyticsReferrer updateAnalyticsReferrer(
		AnalyticsReferrer analyticsReferrer) {
		return analyticsReferrerPersistence.update(analyticsReferrer);
	}

	/**
	 * Returns the analytics event local service.
	 *
	 * @return the analytics event local service
	 */
	public com.liferay.content.targeting.analytics.service.AnalyticsEventLocalService getAnalyticsEventLocalService() {
		return analyticsEventLocalService;
	}

	/**
	 * Sets the analytics event local service.
	 *
	 * @param analyticsEventLocalService the analytics event local service
	 */
	public void setAnalyticsEventLocalService(
		com.liferay.content.targeting.analytics.service.AnalyticsEventLocalService analyticsEventLocalService) {
		this.analyticsEventLocalService = analyticsEventLocalService;
	}

	/**
	 * Returns the analytics event persistence.
	 *
	 * @return the analytics event persistence
	 */
	public AnalyticsEventPersistence getAnalyticsEventPersistence() {
		return analyticsEventPersistence;
	}

	/**
	 * Sets the analytics event persistence.
	 *
	 * @param analyticsEventPersistence the analytics event persistence
	 */
	public void setAnalyticsEventPersistence(
		AnalyticsEventPersistence analyticsEventPersistence) {
		this.analyticsEventPersistence = analyticsEventPersistence;
	}

	/**
	 * Returns the analytics event finder.
	 *
	 * @return the analytics event finder
	 */
	public AnalyticsEventFinder getAnalyticsEventFinder() {
		return analyticsEventFinder;
	}

	/**
	 * Sets the analytics event finder.
	 *
	 * @param analyticsEventFinder the analytics event finder
	 */
	public void setAnalyticsEventFinder(
		AnalyticsEventFinder analyticsEventFinder) {
		this.analyticsEventFinder = analyticsEventFinder;
	}

	/**
	 * Returns the analytics referrer local service.
	 *
	 * @return the analytics referrer local service
	 */
	public AnalyticsReferrerLocalService getAnalyticsReferrerLocalService() {
		return analyticsReferrerLocalService;
	}

	/**
	 * Sets the analytics referrer local service.
	 *
	 * @param analyticsReferrerLocalService the analytics referrer local service
	 */
	public void setAnalyticsReferrerLocalService(
		AnalyticsReferrerLocalService analyticsReferrerLocalService) {
		this.analyticsReferrerLocalService = analyticsReferrerLocalService;
	}

	/**
	 * Returns the analytics referrer persistence.
	 *
	 * @return the analytics referrer persistence
	 */
	public AnalyticsReferrerPersistence getAnalyticsReferrerPersistence() {
		return analyticsReferrerPersistence;
	}

	/**
	 * Sets the analytics referrer persistence.
	 *
	 * @param analyticsReferrerPersistence the analytics referrer persistence
	 */
	public void setAnalyticsReferrerPersistence(
		AnalyticsReferrerPersistence analyticsReferrerPersistence) {
		this.analyticsReferrerPersistence = analyticsReferrerPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.content.targeting.analytics.model.AnalyticsReferrer",
			analyticsReferrerLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.content.targeting.analytics.model.AnalyticsReferrer");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return AnalyticsReferrerLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return AnalyticsReferrer.class;
	}

	protected String getModelClassName() {
		return AnalyticsReferrer.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = analyticsReferrerPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.content.targeting.analytics.service.AnalyticsEventLocalService.class)
	protected com.liferay.content.targeting.analytics.service.AnalyticsEventLocalService analyticsEventLocalService;
	@BeanReference(type = AnalyticsEventPersistence.class)
	protected AnalyticsEventPersistence analyticsEventPersistence;
	@BeanReference(type = AnalyticsEventFinder.class)
	protected AnalyticsEventFinder analyticsEventFinder;
	@BeanReference(type = com.liferay.content.targeting.analytics.service.AnalyticsReferrerLocalService.class)
	protected AnalyticsReferrerLocalService analyticsReferrerLocalService;
	@BeanReference(type = AnalyticsReferrerPersistence.class)
	protected AnalyticsReferrerPersistence analyticsReferrerPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.service.ClassNameLocalService.class)
	protected com.liferay.portal.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}