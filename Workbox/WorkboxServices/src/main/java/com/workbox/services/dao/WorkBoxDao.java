package com.workbox.services.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.workbox.services.dto.TasksCountDTO;
import com.workbox.services.response.dto.TrackingResponse;
import com.workbox.util.common.dto.ResponseMessage;
import com.workbox.util.common.dto.WorkBoxDto;
import com.workbox.util.common.dto.WorkboxResponseDto;
import com.workbox.util.component.NoResultFault;
import com.workbox.util.component.PMCConstant;
import com.workbox.util.component.ServicesUtil;
import com.workbox.util.component.UserManagementUtil;

@Repository("WorkBoxDao")
@Transactional
public class WorkBoxDao {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkBoxDao.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	public Session getSession() {
		try {
			return sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			logger.error("[Workbox][BaseDao][session][error] " + e.getMessage());
			return sessionFactory.openSession();
		}
	}
	
	public WorkboxResponseDto getWorkboxFilterData(String processName, String requestId, String createdBy, String createdAt, Integer skipCount, Integer maxCount, Integer page, String orderType, String orderBy, String status) {
		WorkboxResponseDto workboxResponseDto = new WorkboxResponseDto();
		ResponseMessage message = new ResponseMessage();
		String taskOwner = null;
		taskOwner = UserManagementUtil.getLoggedInUser().getName();
		if (!ServicesUtil.isEmpty(taskOwner)) {
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
				String dataQuery = "SELECT pe.REQUEST_ID AS REQUEST_ID, pe.NAME AS PROCESS_NAME ,te.EVENT_ID AS TASK_ID, te.DESCRIPTION AS DESCRIPTION, te.NAME AS TASK_NAME, te.SUBJECT AS TASK_SUBJECT, pe.STARTED_BY_DISP AS STARTED_BY, te.CREATED_AT AS TASK_CREATED_AT, te.STATUS AS TASK_STATUS,te.CUR_PROC AS CUR_PROC,ts.SLA AS SLA, te.PROCESS_ID AS PROCESS_ID, te.URL AS URL,te.COMP_DEADLINE AS SLA_DUE_DATE, Te.FORWARDED_BY AS FORWARDED_BY, Te.FORWARDED_AT AS FORWARDED_AT, Pct.PROCESS_DISPLAY_NAME AS PROCESS_DISPLAY_NAME FROM TASK_EVENTS te LEFT JOIN PROCESS_CONFIG_TB Pct ON TE.PROC_NAME = PCT.PROCESS_NAME LEFT JOIN TASK_SLA ts ON te.NAME = ts.TASK_DEF, PROCESS_EVENTS pe, TASK_OWNERS tw WHERE pe.PROCESS_ID = te.PROCESS_ID AND tw.EVENT_ID = te.EVENT_ID AND pe.STATUS = 'RUNNING'";
				String query = "";
				if (!ServicesUtil.isEmpty(status)) {
					query = query + " AND te.STATUS = '" + status + "' AND tw.TASK_OWNER = '" + taskOwner + "'";
					if (status.equals("READY")) {
						query = query + "and te.status='READY'";
					} else if (status.equals("RESERVED")) {
						query = query + "AND  te.CUR_PROC = '" + taskOwner + "'";
					}
				} else {
					query = query + " AND te.STATUS <> 'COMPLETED' AND(tw.TASK_OWNER = '" + taskOwner
							+ "' and (te.status='READY' OR te.CUR_PROC = '" + taskOwner + "'))";
				}
				if (!ServicesUtil.isEmpty(processName)) {
					query = query + " AND pe.NAME";
					if (processName.indexOf(",") != -1) {
						query = query + " IN (";
						String[] processes = processName.split(",");
						for (String process : processes) {
							query = query + "'" + process + "',";
						}
						query = query.substring(0, query.length() - 1);
						query = query + ")";
					} else {
						query = query + " = '" + processName + "'";
					}
				}
				if (!ServicesUtil.isEmpty(requestId)) {
					query = query + " AND pe.REQUEST_ID LIKE '%" + requestId + "%'";
				}
				if (!ServicesUtil.isEmpty(createdBy)) {
					query = query + " AND pe.STARTED_BY = '" + createdBy + "'";
				}
				if (!ServicesUtil.isEmpty(createdAt)) {
					query = query + " AND to_char(cast(te.CREATED_AT as date),'MM/DD/YYYY')= '" + createdAt + "'";
				}

				String countQuery = " SELECT  COUNT(*) AS COUNT FROM TASK_EVENTS te, PROCESS_EVENTS pe, TASK_OWNERS tw WHERE pe.PROCESS_ID = te.PROCESS_ID AND tw.EVENT_ID = te.EVENT_ID AND pe.STATUS = 'IN_PROGRESS'"
						+ query;
				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][countQuery]" + countQuery);
				Query cq = this.getSession().createSQLQuery(countQuery.trim());
				BigDecimal count = ServicesUtil.getBigDecimal(cq.uniqueResult());

				if (ServicesUtil.isEmpty(orderType) && ServicesUtil.isEmpty(orderBy))
					query = query + " ORDER BY 8 DESC";
				else {
					if (!ServicesUtil.isEmpty(orderType) && !ServicesUtil.isEmpty(orderBy)) {

						if (orderType.equals(PMCConstant.ORDER_TYPE_CREATED_AT)) {
							if (orderBy.equals(PMCConstant.ORDER_BY_ASC))
								query = query + " ORDER BY 8 ASC";
							else {
								if (orderBy.equals(PMCConstant.ORDER_BY_DESC))
									query = query + " ORDER BY 8 DESC";
							}
						} else if (orderType.equals(PMCConstant.ORDER_TYPE_SLA_DUE_DATE)) {
							if (orderBy.equals(PMCConstant.ORDER_BY_ASC))
								query = query + " ORDER BY 14 ASC";
							else {
								if (orderBy.equals(PMCConstant.ORDER_BY_DESC))
									query = query + " ORDER BY 14 DESC";
							}
						}
					} else {
						if (orderType.equals(PMCConstant.ORDER_TYPE_CREATED_AT))
							query = query + " ORDER BY 8 ASC";
						else {
							if (orderType.equals(PMCConstant.ORDER_TYPE_SLA_DUE_DATE))
								query = query + " ORDER BY 14 ASC";
						}
					}

				}

				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][query]" + query);

				dataQuery = dataQuery + query;
				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][dataQuery]" + dataQuery);
				//Query q = em.getEntityManager().createNativeQuery(dataQuery.trim(), "workBoxResults");
				Query q = this.getSession().createSQLQuery(dataQuery.trim());

				if (!ServicesUtil.isEmpty(maxCount) && maxCount > 0 && !ServicesUtil.isEmpty(skipCount)
						&& skipCount >= 0) {
					int first = skipCount;
					int last = maxCount;
					q.setFirstResult(first);
					q.setMaxResults(last);
				}
				if (!ServicesUtil.isEmpty(page) && page > 0) {
					int first = (page - 1) * PMCConstant.PAGE_SIZE;
					int last = PMCConstant.PAGE_SIZE;
					q.setFirstResult(first);
					q.setMaxResults(last);
				}

				@SuppressWarnings("unchecked")
				List<Object[]> resultList = q.list();
				if (ServicesUtil.isEmpty(resultList)) {
					try {
						throw new NoResultFault("NO RECORD FOUND");
					} catch (NoResultFault e) {
						System.err.println("NO RESULT FOUND");
						message.setStatus("NO RESULT FOUND");
						message.setStatusCode("1");
						workboxResponseDto.setResponseMessage(message);
					}
				} else {
					List<WorkBoxDto> workBoxDtos = new ArrayList<WorkBoxDto>();
					System.err.println("ResultList - " + resultList.size());
					for (Object[] obj : resultList) {
						WorkBoxDto workBoxDto = new WorkBoxDto();
						workBoxDto.setRequestId(obj[0] == null ? null : (String) obj[0]);
						workBoxDto.setProcessName(obj[1] == null ? null : (String) obj[1]);
						workBoxDto.setTaskId(obj[2] == null ? null : (String) obj[2]);
						workBoxDto.setTaskDescription(obj[3] == null ? null : (String) obj[3]);
						workBoxDto.setName(obj[4] == null ? null : (String) obj[4]);
						workBoxDto.setSubject(obj[5] == null ? null : (String) obj[5]);
						workBoxDto.setStartedBy(obj[6] == null ? null : (String) obj[6]);
						workBoxDto.setCreatedAt(obj[7] == null ? null : simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[7])));
						workBoxDto.setStatus(obj[8] == null ? null : (String) obj[8]);
						workBoxDto.setSlaDisplayDate(obj[13] == null ? null : simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[13])));
						workBoxDto.setDetailURL(obj[12] == null ? null : ((String) obj[12]));
						if (!ServicesUtil.isEmpty(obj[13]) && !ServicesUtil.isEmpty(obj[7])) {
							System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][ created]" + obj[7]);
							Calendar created = ServicesUtil.timeStampToCal(obj[7]);
							Calendar slaDate = ServicesUtil.timeStampToCal(obj[13]);
							String timeLeftString = ServicesUtil.getSLATimeLeft(slaDate);
							if (timeLeftString.equals("Breach")) {
								workBoxDto.setBreached(true);
							} else {
								workBoxDto.setBreached(false);
								workBoxDto.setTimeLeftDisplayString(timeLeftString);
								workBoxDto.setTimePercentCompleted(
										ServicesUtil.getPercntTimeCompleted(created, slaDate));
							}
						}
						workBoxDto.setSla(obj[10] == null ? null : (String) obj[10]);
						workBoxDto.setProcessId(obj[11] == null ? null : (String) obj[11]);
					//	workBoxDto.setCustomAttributes(customAttributes.getCustomAttributes(workBoxDto.getTaskId()));
						workBoxDto.setForwardedBy(obj[14] == null ? null : (String) obj[14]);
						workBoxDto.setForwardedAt(obj[15] == null ? null
								: simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[15])));
						workBoxDto.setProcessDisplayName(obj[16] == null ? (String) obj[1] : (String) obj[16]);
						// workBoxDto.setUrl();
						workBoxDtos.add(workBoxDto);
					}
					workboxResponseDto.setPageCount(PMCConstant.PAGE_SIZE);
					workboxResponseDto.setCount(count);
					workboxResponseDto.setWorkBoxDtos(workBoxDtos);
					message.setStatus("Success");
					message.setStatusCode("0");
					message.setMessage("Process Details Fetched Successfully");
					workboxResponseDto.setResponseMessage(message);
				}
				return workboxResponseDto;
		}
		message.setStatus("FAILURE");
		message.setStatusCode("1");
		message.setMessage("NO USER FOUND");
		workboxResponseDto.setResponseMessage(message);
		return workboxResponseDto;
	}
	
	public WorkboxResponseDto getWorkboxCompletedFilterData(String processName, String requestId, String createdBy, String createdAt, String completedAt, String period, Integer skipCount, Integer maxCount, Integer page) {
		System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData] method invoked ");
		WorkboxResponseDto workboxResponseDto = new WorkboxResponseDto();
		ResponseMessage message = new ResponseMessage();
		String taskOwner = UserManagementUtil.getLoggedInUser().getName();

		if (!ServicesUtil.isEmpty(taskOwner)) {
			System.err.println("[PMC][WorkBoxFacade][getWorkboxCompletedFilterData][getLoggedInUser] " + taskOwner);
				SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
				String dataQuery = "SELECT pe.REQUEST_ID AS REQUEST_ID, pe.NAME AS PROCESS_NAME ,te.EVENT_ID AS TASK_ID, te.DESCRIPTION AS DESCRIPTION, te.NAME AS TASK_NAME, te.SUBJECT AS TASK_SUBJECT, pe.STARTED_BY_DISP AS STARTED_BY, te.CREATED_AT AS TASK_CREATED_AT, te.STATUS AS TASK_STATUS,te.CUR_PROC AS CUR_PROC,te.COMPLETED_AT AS COMPLETED_AT, te.PROCESS_ID AS PROCESS_ID, TE.COMP_DEADLINE AS SLA_DATE, PCT.PROCESS_DISPLAY_NAME as DISP_NAME FROM TASK_EVENTS te, PROCESS_EVENTS pe left join PROCESS_CONFIG_TB pct on pe.NAME = pct.PROCESS_NAME, TASK_OWNERS tw WHERE pe.PROCESS_ID = te.PROCESS_ID AND tw.EVENT_ID = te.EVENT_ID AND pe.STATUS IN('IN_PROGRESS','COMPLETED') AND te.STATUS = 'COMPLETED'";
				String query="";
				if (!ServicesUtil.isEmpty(taskOwner)) {
					query = query + " AND tw.TASK_OWNER = '" + taskOwner + "' AND  te.CUR_PROC = '" + taskOwner + "'";
				}
				if (!ServicesUtil.isEmpty(processName)) {
					query = query + " AND pe.NAME";
					if(processName.indexOf(",")!=-1){
						query = query +" IN (";
						String[] processes = processName.split(",");
						for(String process:processes){
							query = query +"'"+process+"',";	
						}
						query = query.substring(0, query.length()-1);
						query = query +")";
					}
					else{
						query = query +" = '" + processName + "'";
					}
				}
				if (!ServicesUtil.isEmpty(requestId)) {
					query = query + " AND pe.REQUEST_ID LIKE '%" + requestId + "%'";
				}
				if (!ServicesUtil.isEmpty(createdBy)) {
					query = query + " AND pe.STARTED_BY = '" + createdBy + "'";
				}
				if (!ServicesUtil.isEmpty(createdAt)) {
					query = query + " AND to_char(cast(te.CREATED_AT as date),'MM/DD/YYYY')= '" + createdAt + "'";
				}
				if (!ServicesUtil.isEmpty(completedAt)) {
					query = query + " AND to_char(cast(te.COMPLETED_AT as date),'MM/DD/YYYY')= '" + completedAt + "'";
				}
				else{
					Calendar calendar = GregorianCalendar.getInstance();
					Date startDate = null, endDate = null;
					calendar.add(Calendar.DAY_OF_MONTH, -(PMCConstant.COMPLETED_RANGE - 1));
					startDate = ServicesUtil.setInitialTime(calendar.getTime());
					endDate = new Date();
					DateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yy hh:mm:ss a");
					query = query + " AND te.COMPLETED_AT between '" + dateFormatter.format(startDate) + "' and '" + dateFormatter.format(endDate) + "'";
				}


				String countQuery = " SELECT  COUNT(*) AS COUNT FROM TASK_EVENTS te, PROCESS_EVENTS pe, TASK_OWNERS tw WHERE pe.PROCESS_ID = te.PROCESS_ID AND tw.EVENT_ID = te.EVENT_ID AND te.STATUS = 'COMPLETED'" + query; 
				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][countQuery]" + countQuery);
			//	Query cq = em.createNativeQuery(countQuery.trim(), "workBoxCountResult");
				Query cq=this.getSession().createSQLQuery(countQuery.trim());
				BigDecimal count = new BigDecimal((Long) cq.uniqueResult());
				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][countQuery][Result]" + count);
				query = query + " ORDER BY 8 DESC";
				System.err.println("[PMC][WorkBoxFacade][getWorkboxCompletedFilterData][query]" + query);


				dataQuery = dataQuery + query;
				System.err.println("[PMC][WorkBoxFacade][getWorkboxCompletedFilterData][dataQuery]" + dataQuery);
				Query q = this.getSession().createSQLQuery(dataQuery.trim());


				if (!ServicesUtil.isEmpty(maxCount) && maxCount > 0 && !ServicesUtil.isEmpty(skipCount)
						&& skipCount >= 0) {
					int first = skipCount;
					int last = maxCount;
					q.setFirstResult(first);
					q.setMaxResults(last);
				}
				if (!ServicesUtil.isEmpty(page) && page > 0) {
					int first = (page - 1) * PMCConstant.PAGE_SIZE;
					int last = PMCConstant.PAGE_SIZE;
					q.setFirstResult(first);
					q.setMaxResults(last);
				}

				@SuppressWarnings("unchecked")
				List<Object[]> resultList = q.list();
				System.err.println("[PMC][WorkBoxFacade][getWorkboxFilterData][countQuery][Result]" + resultList);
				if (ServicesUtil.isEmpty(resultList)) {
					try {
						throw new NoResultFault("NO RECORD FOUND");
					} catch (NoResultFault e) {
						System.err.println("NO RESULT FOUND");
						message.setStatus("NO RESULT FOUND");
						message.setStatusCode("1");
						workboxResponseDto.setResponseMessage(message);
					}
				} else {
					List<WorkBoxDto> workBoxDtos = new ArrayList<WorkBoxDto>();
					System.err.println("ResultList - " + resultList.size());
					for (Object[] obj : resultList) {
						WorkBoxDto workBoxDto = new WorkBoxDto();
						workBoxDto.setRequestId(obj[0] == null ? null : (String) obj[0]);
						workBoxDto.setProcessName(obj[1] == null ? null : (String) obj[1]);
						workBoxDto.setTaskId(obj[2] == null ? null : (String) obj[2]);
						workBoxDto.setTaskDescription(obj[3] == null ? null : (String) obj[3]);
						workBoxDto.setName(obj[4] == null ? null : (String) obj[4]);
						workBoxDto.setSubject(obj[5] == null ? null : (String) obj[5]);
						workBoxDto.setStartedBy(obj[6] == null ? null : (String) obj[6]);
						workBoxDto.setCreatedAt(obj[7] == null ? null : simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[7])));
						workBoxDto.setStatus(obj[8] == null ? null : (String) obj[8]);
						workBoxDto.setCompletedAt(obj[10] == null ? null : simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[10])));
						workBoxDto.setProcessId(obj[11] == null ? null : (String) obj[11]);
						workBoxDto.setSlaDisplayDate(obj[12] == null ? null : simpleDateFormat1.format(ServicesUtil.resultAsDate(obj[12])));
						workBoxDto.setProcessDisplayName(obj[13] == null ? (String)obj[1] : (String) obj[13]);
						workBoxDto.setTaskOwner(taskOwner);
						
						/* Sla Breached Details */
						
						if(!ServicesUtil.isEmpty(obj[12]) && !ServicesUtil.isEmpty(obj[10]) ){
							System.err.println("[PMC][WorkBoxDao][getWorkboxCompletedFilterData][completed]" + obj[10]);
							Calendar completed = ServicesUtil.timeStampToCal(obj[10]);
							Calendar slaDate = ServicesUtil.timeStampToCal(obj[12]);
							//workBoxDto.setSlaDisplayDate(simpleDateFormat1.format(slaDate.getTime()));
							//String timeLeftString = ServicesUtil.getSLATimeLeft(slaDate);
							Integer calCompare = slaDate.compareTo(completed);
							if(calCompare == 1){
								workBoxDto.setBreached(false);
							}
							else if (calCompare == -1){
								workBoxDto.setBreached(true);
								Long diff = completed.getTimeInMillis() - slaDate.getTimeInMillis();
								int diffSeconds = (int) (diff / 1000 % 60);
								int diffMinutes = (int) (diff / (60 * 1000) % 60);
								int diffHours = (int) (diff / (60 * 60 * 1000));
								int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
								String timePassedString = "" + diffDays + " days :" + diffHours + " hrs :" + diffMinutes + " min :" + diffSeconds + "sec";
								System.err.println("[PMC][WorkboxCompletedFilterData][timePassedString] : "+timePassedString);
								/*if(diffMinutes < 60){
									//workBoxDto.setTimeLeftDisplayString("Completed After "+diffMinutes+" Minutes");
								} else if(diffMinutes > 60){
									
									//workBoxDto.setTimeLeftDisplayString("Completed After "+diffHours+" Hours");
								} else if (diffMinutes > 1440){
									
									//workBoxDto.setTimeLeftDisplayString("Completed After "+diffDays+" Days");
								}*/
								//workBoxDto.setTimePercentCompleted(ServicesUtil.getPercntTimeCompleted(completed,slaDate));
								//workBoxDto.setTimeLeftDisplayString(ServicesUtil.getCompletedTimePassed(slaDate, completed));
								//workBoxDto.setTimeLeftDisplayString(timePassedString);
								
							}
						workBoxDtos.add(workBoxDto);
					}
					workboxResponseDto.setPageCount(PMCConstant.PAGE_SIZE);
					workboxResponseDto.setCount(count);
					workboxResponseDto.setWorkBoxDtos(workBoxDtos);
					message.setStatus("Success");	
					message.setStatusCode("0");
					message.setMessage("Completed Tasks Fetched Successfully");
					workboxResponseDto.setResponseMessage(message);
				}
				return workboxResponseDto;
			}
		}
		message.setStatus("FAILURE");
		message.setStatusCode("1");
		message.setMessage("NO USER FOUND");
		workboxResponseDto.setResponseMessage(message);
		return workboxResponseDto;
	}

	public TrackingResponse getTrackingResponse() {
		TrackingResponse response = new TrackingResponse();
		ResponseMessage respMessage = new ResponseMessage();
		String userId = null;
		userId = UserManagementUtil.getLoggedInUser().getName();
		DateFormat newDf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(!ServicesUtil.isEmpty(userId)){
			
			BigDecimal compOnSlaCount = null;
			BigDecimal compOffSlaCount = null;
			BigDecimal inProgOnSlaCount = null;
			BigDecimal inProgOffSlaCount = null;
			BigDecimal inProgApproachingCount = null;

			List<TasksCountDTO> responseList = new ArrayList<TasksCountDTO>();

			String completedCountQry1 = "SELECT COUNT(EVENT_ID) AS COUNT FROM TASK_EVENTS WHERE STATUS = 'COMPLETED' AND COMP_DEADLINE <= COMPLETED_AT and CUR_PROC = '"
					+ userId + "'";
			String completedCountQry2 = "SELECT COUNT(EVENT_ID) AS COUNT FROM TASK_EVENTS WHERE STATUS = 'COMPLETED' AND COMP_DEADLINE > COMPLETED_AT and CUR_PROC = '"
					+ userId + "'";

			Query comCountQry1 = this.getSession().createSQLQuery(completedCountQry1);
			compOffSlaCount = ServicesUtil.getBigDecimal( comCountQry1.uniqueResult());

			Query comCountQry2 = this.getSession().createSQLQuery(completedCountQry2);
			compOnSlaCount = ServicesUtil.getBigDecimal(comCountQry2.uniqueResult());

			System.err.println("Completed off Sla Count : " + compOffSlaCount);
			System.err.println("Completed on Sla Count : " + compOnSlaCount);
			
			Date currDate = new Date();

			String inProgressCountQry1 = "SELECT COUNT(DISTINCT(TE.EVENT_ID)) AS COUNT " +
					"FROM TASK_EVENTS TE " +
					"JOIN TASK_OWNERS TW " +
					"ON TE.EVENT_ID    = TW.EVENT_ID " +
					"AND ((TE.STATUS   = 'RESERVED' " +
					"AND TE.CUR_PROC   = '"+userId+"'  AND TW.IS_PROCESSED = '1') " +
					"OR (TE.STATUS     = 'READY' " +
					"AND TW.TASK_OWNER ='"+userId+"')) " +
					//"AND (TO_DATE('"+df.format(currDate)+"', 'DD/MM/YY hh:mi:ss AM') > TE.COMP_DEADLINE)";
					"AND ('"+newDf.format(currDate)+"' > to_seconddate(TE.COMP_DEADLINE))";
			
			Query resInProgressCountQry1 = this.getSession().createSQLQuery(inProgressCountQry1);
			inProgOffSlaCount = ServicesUtil.getBigDecimal( resInProgressCountQry1.uniqueResult());
			System.err.println("[pmc][tracking] : "+inProgOnSlaCount);
			
			String inProgressCountQry2 = "SELECT COUNT(DISTINCT(TE.EVENT_ID)) AS COUNT " +
					"FROM TASK_EVENTS TE " +
					"JOIN TASK_OWNERS TW " +
					"ON TE.EVENT_ID    = TW.EVENT_ID " +
					"AND ((TE.STATUS   = 'RESERVED' " +
					"AND TE.CUR_PROC   = '"+userId+"' AND TW.IS_PROCESSED = '1') " +
					"OR (TE.STATUS     = 'READY' " +
					"AND TW.TASK_OWNER ='"+userId+"')) " +
					"AND ('"+newDf.format(currDate)+"'  < add_days(to_seconddate(TE.COMP_DEADLINE), -1))";
			
			Query resInProgressCountQry2 = this.getSession().createSQLQuery(inProgressCountQry2);
			inProgOnSlaCount = ServicesUtil.getBigDecimal(resInProgressCountQry2.uniqueResult());
			System.err.println("[pmc][tracking] : "+inProgOnSlaCount);
			
			String inProgressApproching = "SELECT COUNT(DISTINCT(TE.EVENT_ID)) AS COUNT " +
					"FROM TASK_EVENTS TE " +
					"JOIN TASK_OWNERS TW " +
					"ON TE.EVENT_ID    = TW.EVENT_ID " +
					"AND ((TE.STATUS   = 'RESERVED' " +
					"AND TE.CUR_PROC   = '"+userId+"' AND TW.IS_PROCESSED = '1') " +
					"OR (TE.STATUS     = 'READY' " +
					"AND TW.TASK_OWNER ='"+userId+"')) " +
					"AND ('"+newDf.format(currDate)+"' > add_days(to_seconddate(TE.COMP_DEADLINE), - 1)) AND ('"+newDf.format(currDate)+"' <= to_seconddate(TE.COMP_DEADLINE))";
			
			Query resInProgressApproaching = this.getSession().createSQLQuery(inProgressApproching);
			inProgApproachingCount = ServicesUtil.getBigDecimal(resInProgressApproaching.uniqueResult());
			System.err.println("[pmc][tracking] : "+inProgApproachingCount);

			if ((!ServicesUtil.isEmpty(compOnSlaCount) && (!ServicesUtil.isEmpty(compOffSlaCount)
					&& (!ServicesUtil.isEmpty(inProgOnSlaCount) && (!ServicesUtil.isEmpty(inProgOffSlaCount)))))) {
				
				TasksCountDTO resp = new TasksCountDTO();
				resp.setStatus("Completed - Past Due");
				resp.setCount(compOffSlaCount);
				responseList.add(resp);
				TasksCountDTO resp1 = new TasksCountDTO();
				resp1.setStatus("Completed - On Track");
				resp1.setCount(compOnSlaCount);
				responseList.add(resp1);
				TasksCountDTO resp2 = new TasksCountDTO();
				resp2.setStatus("In Progress - Past Due");
				resp2.setCount(inProgOffSlaCount);
				responseList.add(resp2);
				TasksCountDTO resp3 = new TasksCountDTO();
				resp3.setStatus("In Progress - On Track");
				resp3.setCount(inProgOnSlaCount);
				responseList.add(resp3);
				TasksCountDTO resp4 = new TasksCountDTO();
				resp4.setStatus("In Progress - Approaching");
				resp4.setCount(inProgApproachingCount);
				responseList.add(resp4);
				
				
				response.setTasksCount(responseList);
				respMessage.setMessage("Tasks Count Sent Successfully");
				respMessage.setStatus("Success");
				respMessage.setStatusCode("0");
				response.setResponseMessage(respMessage);
				return response;
			} else {
				respMessage.setMessage(PMCConstant.NO_RESULT);
				respMessage.setStatus("Failure");
				respMessage.setStatusCode("1");
				response.setResponseMessage(respMessage);
			}
			respMessage.setMessage("No Logged In User Found");
			respMessage.setStatus("Failure");
			respMessage.setStatusCode("1");
			response.setResponseMessage(respMessage);
		}
		return response;
	}
}
