<%--
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
--%>

<%@ include file="/html/init.jsp" %>

<aui:nav>
	<c:if test="<%= CampaignPermission.contains(permissionChecker, campaign, ActionKeys.UPDATE) %>">
		<liferay-portlet:renderURL var="redirectURL">
			<portlet:param name="mvcRenderCommandName" value="<%= ContentTargetingMVCCommand.EDIT_CAMPAIGN %>" />
			<portlet:param name="campaignId" value="<%= String.valueOf(campaignId) %>" />
			<portlet:param name="className" value="<%= Campaign.class.getName() %>" />
			<portlet:param name="classPK" value="<%= String.valueOf(campaignId) %>" />
			<portlet:param name="tabs2" value="promotions" />
		</liferay-portlet:renderURL>

		<liferay-portlet:renderURL var="addTacticURL">
			<portlet:param name="mvcRenderCommandName" value="<%= ContentTargetingMVCCommand.EDIT_TACTIC %>" />
			<portlet:param name="campaignId" value="<%= String.valueOf(campaignId) %>" />
			<portlet:param name="redirect" value="<%= redirectURL.toString() %>" />
		</liferay-portlet:renderURL>

		<aui:nav-item
			href="<%= addTacticURL %>"
			iconCssClass="icon-plus"
			label='<%= LanguageUtil.get(portletConfig.getResourceBundle(locale), "add-promotion") %>'
		/>

		<aui:nav-item
			cssClass="hide"
			iconCssClass="icon-remove"
			id="deleteTactics"
			label='<%= LanguageUtil.get(portletConfig.getResourceBundle(locale), "delete") %>'
		/>
	</c:if>
</aui:nav>