/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

 package org.apache.ranger.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.ranger.plugin.util.SearchFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RangerSearchUtil extends SearchUtil {
	final static Logger logger = LoggerFactory.getLogger(RangerSearchUtil.class);
	
	public SearchFilter getSearchFilter(@Nonnull HttpServletRequest request, List<SortField> sortFields) {
		Validate.notNull(request, "request");
		SearchFilter ret = new SearchFilter();

		if (MapUtils.isEmpty(request.getParameterMap())) {
			ret.setParams(new HashMap<String, String>());
		}

		ret.setParam(SearchFilter.SERVICE_TYPE, request.getParameter(SearchFilter.SERVICE_TYPE));
		ret.setParam(SearchFilter.SERVICE_TYPE_DISPLAY_NAME, request.getParameter(SearchFilter.SERVICE_TYPE_DISPLAY_NAME));
		ret.setParam(SearchFilter.SERVICE_TYPE_ID, request.getParameter(SearchFilter.SERVICE_TYPE_ID));
		ret.setParam(SearchFilter.SERVICE_NAME, request.getParameter(SearchFilter.SERVICE_NAME));
		ret.setParam(SearchFilter.SERVICE_DISPLAY_NAME, request.getParameter(SearchFilter.SERVICE_DISPLAY_NAME));
		ret.setParam(SearchFilter.SERVICE_NAME_PARTIAL, request.getParameter(SearchFilter.SERVICE_NAME_PARTIAL));
		ret.setParam(SearchFilter.SERVICE_DISPLAY_NAME_PARTIAL, request.getParameter(SearchFilter.SERVICE_DISPLAY_NAME_PARTIAL));
		ret.setParam(SearchFilter.SERVICE_ID, request.getParameter(SearchFilter.SERVICE_ID));
		ret.setParam(SearchFilter.POLICY_NAME, request.getParameter(SearchFilter.POLICY_NAME));
		ret.setParam(SearchFilter.POLICY_NAME_PARTIAL, request.getParameter(SearchFilter.POLICY_NAME_PARTIAL));
		ret.setParam(SearchFilter.POLICY_ID, request.getParameter(SearchFilter.POLICY_ID));
		ret.setParam(SearchFilter.IS_ENABLED, request.getParameter(SearchFilter.IS_ENABLED));
		ret.setParam(SearchFilter.IS_RECURSIVE, request.getParameter(SearchFilter.IS_RECURSIVE));
		ret.setParam(SearchFilter.USER, request.getParameter(SearchFilter.USER));
		ret.setParam(SearchFilter.GROUP, request.getParameter(SearchFilter.GROUP));
		ret.setParam(SearchFilter.ROLE, request.getParameter(SearchFilter.ROLE));
		ret.setParam(SearchFilter.POL_RESOURCE, request.getParameter(SearchFilter.POL_RESOURCE));
		ret.setParam(SearchFilter.RESOURCE_SIGNATURE, request.getParameter(SearchFilter.RESOURCE_SIGNATURE));
		ret.setParam(SearchFilter.POLICY_TYPE, request.getParameter(SearchFilter.POLICY_TYPE));
		ret.setParam(SearchFilter.POLICY_LABEL, request.getParameter(SearchFilter.POLICY_LABEL));
		ret.setParam(SearchFilter.POLICY_LABELS_PARTIAL, request.getParameter(SearchFilter.POLICY_LABELS_PARTIAL));
		ret.setParam(SearchFilter.PLUGIN_HOST_NAME, request.getParameter(SearchFilter.PLUGIN_HOST_NAME));
		ret.setParam(SearchFilter.PLUGIN_APP_TYPE, request.getParameter(SearchFilter.PLUGIN_APP_TYPE));
		ret.setParam(SearchFilter.PLUGIN_ENTITY_TYPE, request.getParameter(SearchFilter.PLUGIN_ENTITY_TYPE));
		ret.setParam(SearchFilter.PLUGIN_IP_ADDRESS, request.getParameter(SearchFilter.PLUGIN_IP_ADDRESS));
		ret.setParam(SearchFilter.ZONE_NAME, request.getParameter(SearchFilter.ZONE_NAME));
		ret.setParam(SearchFilter.ZONE_NAME_PARTIAL, request.getParameter(SearchFilter.ZONE_NAME_PARTIAL));
		ret.setParam(SearchFilter.ZONE_ID, request.getParameter(SearchFilter.ZONE_ID));
		ret.setParam(SearchFilter.TAG_SERVICE_ID, request.getParameter(SearchFilter.TAG_SERVICE_ID));
		ret.setParam(SearchFilter.ROLE_NAME, request.getParameter(SearchFilter.ROLE_NAME));
		ret.setParam(SearchFilter.ROLE_ID, request.getParameter(SearchFilter.ROLE_ID));
		ret.setParam(SearchFilter.GROUP_NAME, request.getParameter(SearchFilter.GROUP_NAME));
		ret.setParam(SearchFilter.USER_NAME, request.getParameter(SearchFilter.USER_NAME));
		ret.setParam(SearchFilter.ROLE_NAME_PARTIAL, request.getParameter(SearchFilter.ROLE_NAME_PARTIAL));
		ret.setParam(SearchFilter.GROUP_NAME_PARTIAL, request.getParameter(SearchFilter.GROUP_NAME_PARTIAL));
		ret.setParam(SearchFilter.USER_NAME_PARTIAL, request.getParameter(SearchFilter.USER_NAME_PARTIAL));
		ret.setParam(SearchFilter.CLUSTER_NAME, request.getParameter(SearchFilter.CLUSTER_NAME));
		ret.setParam(SearchFilter.FETCH_ZONE_UNZONE_POLICIES, request.getParameter(SearchFilter.FETCH_ZONE_UNZONE_POLICIES));
		ret.setParam(SearchFilter.FETCH_TAG_POLICIES, request.getParameter(SearchFilter.FETCH_TAG_POLICIES));
		for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
			String name = e.getKey();
			String[] values = e.getValue();

			if (!StringUtils.isEmpty(name) && !ArrayUtils.isEmpty(values)
					&& name.startsWith(SearchFilter.RESOURCE_PREFIX)) {
				ret.setParam(name, values[0]);
			}
		}
		ret.setParam(SearchFilter.RESOURCE_MATCH_SCOPE, request.getParameter(SearchFilter.RESOURCE_MATCH_SCOPE));

		ret.setParam(SearchFilter.DATASET_NAME, request.getParameter(SearchFilter.DATASET_NAME));
		ret.setParam(SearchFilter.DATASET_NAME_PARTIAL, request.getParameter(SearchFilter.DATASET_NAME_PARTIAL));
		ret.setParam(SearchFilter.DATASET_ID, request.getParameter(SearchFilter.DATASET_ID));
		ret.setParam(SearchFilter.PROJECT_NAME, request.getParameter(SearchFilter.PROJECT_NAME));
		ret.setParam(SearchFilter.PROJECT_NAME_PARTIAL, request.getParameter(SearchFilter.PROJECT_NAME_PARTIAL));
		ret.setParam(SearchFilter.PROJECT_ID, request.getParameter(SearchFilter.PROJECT_ID));
		ret.setParam(SearchFilter.DATA_SHARE_NAME, request.getParameter(SearchFilter.DATA_SHARE_NAME));
		ret.setParam(SearchFilter.DATA_SHARE_NAME_PARTIAL, request.getParameter(SearchFilter.DATA_SHARE_NAME_PARTIAL));
		ret.setParam(SearchFilter.DATA_SHARE_ID, request.getParameter(SearchFilter.DATA_SHARE_ID));
		ret.setParam(SearchFilter.EXCLUDE_DATASET_ID, request.getParameter(SearchFilter.EXCLUDE_DATASET_ID));
		ret.setParam(SearchFilter.SHARED_RESOURCE_ID, request.getParameter(SearchFilter.SHARED_RESOURCE_ID));
		ret.setParam(SearchFilter.SHARED_RESOURCE_NAME, request.getParameter(SearchFilter.SHARED_RESOURCE_NAME));
		ret.setParam(SearchFilter.SHARED_RESOURCE_NAME_PARTIAL, request.getParameter(SearchFilter.SHARED_RESOURCE_NAME_PARTIAL));
		ret.setParam(SearchFilter.PROFILE_NAME, request.getParameter(SearchFilter.PROFILE_NAME));
		ret.setParam(SearchFilter.OWNER_NAME, request.getParameter(SearchFilter.OWNER_NAME));
		ret.setParam(SearchFilter.OWNER_TYPE, request.getParameter(SearchFilter.OWNER_TYPE));
		ret.setParam(SearchFilter.GDS_PERMISSION, request.getParameter(SearchFilter.GDS_PERMISSION));
		ret.setParam(SearchFilter.CREATE_TIME_START, request.getParameter(SearchFilter.CREATE_TIME_START));
		ret.setParam(SearchFilter.CREATE_TIME_END, request.getParameter(SearchFilter.CREATE_TIME_END));
		ret.setParam(SearchFilter.UPDATE_TIME_START, request.getParameter(SearchFilter.UPDATE_TIME_START));
		ret.setParam(SearchFilter.UPDATE_TIME_END, request.getParameter(SearchFilter.UPDATE_TIME_END));
		ret.setParam(SearchFilter.RESOURCE_CONTAINS, request.getParameter(SearchFilter.RESOURCE_CONTAINS));

		extractCommonCriteriasForFilter(request, ret, sortFields);

		return ret;
	}

	public SearchFilter getSearchFilterFromLegacyRequestForRepositorySearch(HttpServletRequest request, List<SortField> sortFields) {
		if (request == null) {
			return null;
		}

		SearchFilter ret = new SearchFilter();

		if (MapUtils.isEmpty(request.getParameterMap())) {
			ret.setParams(new HashMap<String, String>());
		}

		ret.setParam(SearchFilter.SERVICE_NAME, request.getParameter("name"));
		ret.setParam(SearchFilter.IS_ENABLED, request.getParameter("status"));
		String serviceType = request.getParameter("type");
		if (serviceType != null) {
			serviceType = serviceType.toLowerCase();
		}
		ret.setParam(SearchFilter.SERVICE_TYPE,serviceType);
		extractCommonCriteriasForFilter(request, ret, sortFields);

		return ret;
	}


	public SearchFilter getSearchFilterFromLegacyRequest(HttpServletRequest request, List<SortField> sortFields) {
		Validate.notNull(request, "request");
		SearchFilter ret = new SearchFilter();

		if (MapUtils.isEmpty(request.getParameterMap())) {
			ret.setParams(new HashMap<String, String>());
		}

		String repositoryType = request.getParameter("repositoryType");

		if (repositoryType != null) {
			repositoryType = repositoryType.toLowerCase();
		}

		String repositoryId = request.getParameter("repositoryId");
		if(repositoryId == null) {
			repositoryId = request.getParameter("assetId");
		}

		ret.setParam(SearchFilter.SERVICE_TYPE, repositoryType);
		ret.setParam(SearchFilter.SERVICE_NAME, request.getParameter("repositoryName"));
		ret.setParam(SearchFilter.SERVICE_ID, repositoryId);
		ret.setParam(SearchFilter.POLICY_NAME, request.getParameter("policyName"));
		ret.setParam(SearchFilter.USER, request.getParameter("userName"));
		ret.setParam(SearchFilter.GROUP, request.getParameter("groupName"));
		ret.setParam(SearchFilter.IS_ENABLED, request.getParameter("isEnabled"));
		ret.setParam(SearchFilter.IS_RECURSIVE, request.getParameter("isRecursive"));
		ret.setParam(SearchFilter.POL_RESOURCE, request.getParameter(SearchFilter.POL_RESOURCE));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "path", request.getParameter("resourceName"));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "database", request.getParameter("databases"));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "table", request.getParameter("tables"));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "udf", request.getParameter("udfs"));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "column", request.getParameter("columns"));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "column-family", request.getParameter("columnFamilies"));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "topology", request.getParameter("topologies"));
		ret.setParam(SearchFilter.RESOURCE_PREFIX + "service", request.getParameter("services"));

		extractCommonCriteriasForFilter(request, ret, sortFields);

		return ret;
	}

	public SearchFilter extractCommonCriteriasForFilter(HttpServletRequest request, SearchFilter ret, List<SortField> sortFields) {
		int startIndex = restErrorUtil.parseInt(request.getParameter(SearchFilter.START_INDEX), 0,
				"Invalid value for parameter startIndex", MessageEnums.INVALID_INPUT_DATA, null,
				SearchFilter.START_INDEX);
		startIndex = startIndex < 0 ? 0 : startIndex;
		ret.setStartIndex(startIndex);

		int pageSize = restErrorUtil.parseInt(request.getParameter(SearchFilter.PAGE_SIZE),
				configUtil.getDefaultMaxRows(), "Invalid value for parameter pageSize",
				MessageEnums.INVALID_INPUT_DATA, null, SearchFilter.PAGE_SIZE);
		ret.setMaxRows(validatePageSize(pageSize));

		if (request.getParameter(SearchFilter.POLICY_TYPE) != null) {
			int policyType = restErrorUtil.parseInt(request.getParameter(SearchFilter.POLICY_TYPE), 0,
					"Invalid value for parameter policyType", MessageEnums.INVALID_INPUT_DATA, null,
					SearchFilter.POLICY_TYPE);
			ret.setParam(SearchFilter.POLICY_TYPE, Integer.toString(policyType));
		}

		ret.setGetCount(restErrorUtil.parseBoolean(request.getParameter("getCount"), true));
		String sortBy = restErrorUtil.validateString(request.getParameter(SearchFilter.SORT_BY),
				StringUtil.VALIDATION_ALPHA, "Invalid value for parameter sortBy", MessageEnums.INVALID_INPUT_DATA,
				null, SearchFilter.SORT_BY);

		if (!StringUtils.isEmpty(sortBy)) {
			boolean sortSet = false;

			for (SortField sortField : sortFields) {
				if (sortField.getParamName().equalsIgnoreCase(sortBy)) {
					ret.setSortBy(sortField.getParamName());
					sortSet = true;
					break;
				}
			}

			if (!sortSet) {
				logger.info("Invalid or unsupported sortBy field passed. sortBy=" + sortBy, new Throwable());
			}
		}

		String sortType = restErrorUtil.validateString(request.getParameter("sortType"),
				StringUtil.VALIDATION_ALPHA, "Invalid value for parameter sortType",
				MessageEnums.INVALID_INPUT_DATA, null, "sortType");
		ret.setSortType(sortType);

		if(ret.getParams() == null) {
			ret.setParams(new HashMap<String, String>());
		}
		return ret;
	}

	public Query createSearchQuery(EntityManager em, String queryStr, String sortClause,
			SearchFilter searchCriteria, List<SearchField> searchFields,
			boolean isCountQuery) {
		return createSearchQuery(em, queryStr, sortClause, searchCriteria, searchFields, false, isCountQuery);
	}
	
	public Query createSearchQuery(EntityManager em, String queryStr, String sortClause,
			SearchFilter searchCriteria, List<SearchField> searchFields,
			boolean hasAttributes, boolean isCountQuery) {

		StringBuilder queryClause = buildWhereClause(searchCriteria, searchFields);
		super.addOrderByClause(queryClause, sortClause);
		Query query = em.createQuery(queryStr + queryClause);
		resolveQueryParams(query, searchCriteria, searchFields);

		final boolean skipPagination = isCountQuery || Boolean.parseBoolean(searchCriteria.getParam(SearchFilter.RETRIEVE_ALL_PAGES));

		if (!skipPagination) {
			query.setFirstResult(searchCriteria.getStartIndex());
			updateQueryPageSize(query, searchCriteria);
		}

		return query;
	}
	
	private StringBuilder buildWhereClause(SearchFilter searchCriteria, List<SearchField> searchFields) {
		return buildWhereClause(searchCriteria, searchFields, false);
	}

	private StringBuilder buildWhereClause(SearchFilter searchCriteria,
			List<SearchField> searchFields,
			boolean excludeWhereKeyword) {

		StringBuilder whereClause = new StringBuilder(excludeWhereKeyword ? "" : "WHERE 1 = 1 ");

		List<String> joinTableList = new ArrayList<String>();

		for (SearchField searchField : searchFields) {
			int startWhereLen = whereClause.length();

			if (searchField.getFieldName() == null && searchField.getCustomCondition() == null) {
				continue;
			}

			if (searchField.getDataType() == SearchField.DATA_TYPE.INTEGER) {
				Integer paramVal = restErrorUtil.parseInt(searchCriteria.getParam(searchField.getClientFieldName()),
						"Invalid value for " + searchField.getClientFieldName(),
						MessageEnums.INVALID_INPUT_DATA, null, searchField.getClientFieldName());

				Number intFieldValue = paramVal != null ? (Number) paramVal : null;
				if (intFieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ")
						           .append(searchField.getFieldName())
						           .append(getSqlOperator(searchField.getSearchType()))
						           .append(":").append(searchField.getClientFieldName());
					} else {
						whereClause.append(" and ").append(searchField.getCustomCondition());
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.STRING) {
				String strFieldValue = searchCriteria.getParam(searchField.getClientFieldName());
				if (strFieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ").append("LOWER(").append(searchField.getFieldName()).append(")")
						           .append(getSqlOperator(searchField.getSearchType()))
						           .append(":").append(searchField.getClientFieldName());
					} else {
						whereClause.append(" and ").append(searchField.getCustomCondition());
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.BOOLEAN) {
				Boolean boolFieldValue = restErrorUtil.parseBoolean(searchCriteria.getParam(searchField.getClientFieldName()),
						"Invalid value for " + searchField.getClientFieldName(),
						MessageEnums.INVALID_INPUT_DATA, null, searchField.getClientFieldName());
				
				if (boolFieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ")
						           .append(searchField.getFieldName())
						           .append(getSqlOperator(searchField.getSearchType()))
						           .append(":").append(searchField.getClientFieldName());
					} else {
						whereClause.append(" and ").append(searchField.getCustomCondition());
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.DATE) {
				Date fieldValue = restErrorUtil.parseDate(searchCriteria.getParam(searchField.getClientFieldName()),
						"Invalid value for " + searchField.getClientFieldName(), MessageEnums.INVALID_INPUT_DATA,
						null, searchField.getClientFieldName(), null);
				if (fieldValue != null) {
					if (searchField.getCustomCondition() == null) {
						whereClause.append(" and ").append(searchField.getFieldName())
						           .append(getSqlOperator(searchField.getSearchType()))
						           .append(":").append(searchField.getClientFieldName());
					} else {
						whereClause.append(" and ").append(searchField.getCustomCondition());
					}
				}
			}

			if (whereClause.length() > startWhereLen && searchField.getJoinTables() != null) {
				for (String table : searchField.getJoinTables()) {
					if (!joinTableList.contains(table)) {
						joinTableList.add(table);
					}
				}
				whereClause.append(" and (").append(searchField.getJoinCriteria()).append(")");
			}
		}
		for (String joinTable : joinTableList) {
			whereClause.insert(0, ", " + joinTable + " ");
		}
		
		return whereClause;
	}
	
	protected void resolveQueryParams(Query query, SearchFilter searchCriteria, List<SearchField> searchFields) {

		for (SearchField searchField : searchFields) {

			if (searchField.getDataType() == SearchField.DATA_TYPE.INTEGER) {
				Integer paramVal = restErrorUtil.parseInt(searchCriteria.getParam(searchField.getClientFieldName()),
						"Invalid value for " + searchField.getClientFieldName(),
						MessageEnums.INVALID_INPUT_DATA, null, searchField.getClientFieldName());
				
				Number intFieldValue = paramVal != null ? (Number) paramVal : null;
				if (intFieldValue != null) {
					query.setParameter(searchField.getClientFieldName(), intFieldValue);
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.STRING) {
				String strFieldValue = searchCriteria.getParam(searchField.getClientFieldName());
				if (strFieldValue != null) {
					if (searchField.getSearchType() == SearchField.SEARCH_TYPE.FULL) {
						query.setParameter(searchField.getClientFieldName(), strFieldValue.trim().toLowerCase());
					} else {
						query.setParameter(searchField.getClientFieldName(), "%" + strFieldValue.trim().toLowerCase() + "%");
					}
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.BOOLEAN) {
				Boolean boolFieldValue = restErrorUtil.parseBoolean(searchCriteria.getParam(searchField.getClientFieldName()),
						"Invalid value for " + searchField.getClientFieldName(),
						MessageEnums.INVALID_INPUT_DATA, null, searchField.getClientFieldName());
				
				if (boolFieldValue != null) {
					query.setParameter(searchField.getClientFieldName(), boolFieldValue);
				}
			} else if (searchField.getDataType() == SearchField.DATA_TYPE.DATE) {
				Date fieldValue = restErrorUtil.parseDate(searchCriteria.getParam(searchField.getClientFieldName()),
						"Invalid value for " + searchField.getClientFieldName(), MessageEnums.INVALID_INPUT_DATA,
						null, searchField.getClientFieldName(), null);
				if (fieldValue != null) {
					query.setParameter(searchField.getClientFieldName(), fieldValue);
				}
			}
		}
	}
	
	public void updateQueryPageSize(Query query, SearchFilter searchCriteria) {
		int pageSize = super.validatePageSize(searchCriteria.getMaxRows());
		query.setMaxResults(pageSize);

		query.setHint("eclipselink.jdbc.max-rows", "" + pageSize);
	}
	
	public String constructSortClause(SearchFilter searchCriteria, List<SortField> sortFields) {
		String ret         = null;
		String sortBy      = searchCriteria.getSortBy();
		String sortType    = getSortType(searchCriteria);
		String querySortBy = null;
		
		if (!stringUtil.isEmpty(sortBy)) {
			sortBy = sortBy.trim();

			for (SortField sortField : sortFields) {
				if (sortBy.equalsIgnoreCase(sortField.getParamName())) {
					querySortBy = sortField.getFieldName();
					// Override the sortBy using the normalized value
					searchCriteria.setSortBy(sortField.getParamName());
					break;
				}
			}
		}

		if (querySortBy == null) {
			for (SortField sortField : sortFields) {
				if (sortField.isDefault()) {
					querySortBy = sortField.getFieldName();
					// Override the sortBy using the default value
					searchCriteria.setSortBy(sortField.getParamName());

					if(sortType == null) {
						sortType = sortField.getDefaultOrder().name();
					}

					searchCriteria.setSortType(sortType);
					break;
				}
			}
		}

		if (querySortBy != null) {
			String querySortType = stringUtil.isEmpty(sortType) ? RangerConstants.DEFAULT_SORT_ORDER : sortType;

			searchCriteria.setSortType(querySortType.toLowerCase());

			ret = " ORDER BY " + querySortBy + " " + querySortType;
		}

		return ret;
	}

	private String getSortType(SearchFilter searchCriteria) {
		String ret      = null;
		String sortType = searchCriteria.getSortType();

		if (!stringUtil.isEmpty(sortType)) {
			if ("asc".equalsIgnoreCase(sortType) || "desc".equalsIgnoreCase(sortType)) {
				ret = sortType;
			} else {
				logger.error("Invalid sortType. sortType=" + sortType);
			}
		}

		return ret;
	}
}
