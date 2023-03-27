package com.aem.aemfeb.core.schedulers;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.Workflow;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import com.aem.aemfeb.core.customconfigs.WorkflowStatusConfiguration;
import com.aem.aemfeb.core.service.EmailService;
import com.aem.aemfeb.core.service.ResourceResolverService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import java.time.LocalDateTime;

@Component(immediate = true, service = Runnable.class)
@Designate(ocd = WorkflowStatusConfiguration.class)
public class WorkflowStatusScheduler implements Runnable {

	protected static final String NAME = "Workflow Status Scheduler";
	private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowStatusScheduler.class);
	@Reference
	Scheduler scheduler;
	
	@Reference
	WorkflowService workflowService;
	
	@Reference
	ResourceResolverService resourceResolverService;
	
	@Reference
	EmailService emailService;
	
	private String schedulerName;
	private String toEmail;
	private String ccEmail;
	private String fromEmail;
	private String subject;

	@Activate
	protected void activate(WorkflowStatusConfiguration configuration) {
		LOGGER.debug("{}: initializing properties for scheduler");
		this.schedulerName = configuration.schedulerName();
		LOGGER.debug(" name of the scheduler: {}", schedulerName);
		// Details for email
		this.toEmail = configuration.toEmail();
		this.ccEmail = configuration.ccEmail();
		this.fromEmail = configuration.fromEmail();
		this.subject = configuration.subject();
	}

	@Modified
	protected void modified(WorkflowStatusConfiguration configuration) {
		LOGGER.info(" modifying scheduler with name: {}", schedulerName);
		// Remove the scheduler registered with old configuration
		removeScheduler(configuration);
		// Add the scheduler registered with new configuration
		addScheduler(configuration);
	}

	@Deactivate
	protected void deactivate(WorkflowStatusConfiguration configuration) {
		LOGGER.debug(" removing scheduler: {}", schedulerName);
		removeScheduler(configuration);
	}

	private void addScheduler(WorkflowStatusConfiguration configuration) {
		// Check if the scheduler has enable flag set to true
		if (configuration.enabled()) {
			LOGGER.info("scheduler: {} is enabled", schedulerName);
			// Configure the scheduler to use cron expression and some other properties
			ScheduleOptions scheduleOptions = scheduler.EXPR(configuration.cronExpression());
			scheduleOptions.name(schedulerName);
			scheduleOptions.canRunConcurrently(false);
			// Scheduling the job
			scheduler.schedule(this, scheduleOptions);
			LOGGER.info("scheduler {} is added", schedulerName);
		} else {
			LOGGER.info(" scheduler {} is disabled",schedulerName);
			removeScheduler(configuration);
		}
	}

	private void removeScheduler(WorkflowStatusConfiguration configuration) {
		LOGGER.info(" removing scheduler {}",schedulerName);
		scheduler.unschedule(configuration.schedulerName());
	}

	private String getWorkflowStatus() {
		// This string will store the status for all workflows and other data
		StringBuilder workflowDetails = new StringBuilder();
		try {
			// Get the JCR session
			Session session = resourceResolverService.getResourceResolver().adaptTo(Session.class);
			// Get the workflow session
			WorkflowSession workflowSession = workflowService.getWorkflowSession(session);
			// States by which we want to query the workflows
			String[] states = { "RUNNING", "COMPLETED" };
			// Get the list of all the workflows by states
			Workflow[] workflows = workflowSession.getWorkflows(states);
			// Loop through all the workflows
			for (Workflow workflow : workflows) {
				workflowDetails.append("ID: ").append(workflow.getId()).append("\n").append("Payload: ")
						.append(workflow.getWorkflowData().getPayload()).append("\n").append("State: ")
						.append(workflow.getState()).append("\n");
			}
			
			LOGGER.info(workflowDetails.toString());
		} catch (WorkflowException e) {
			LOGGER.error("exception occurred: {}", e.getMessage());
		}
		return workflowDetails.toString();
	}

	@Override
	public void run() {
		// Getting the workflow status
		String workflowStatus = getWorkflowStatus();
		LOGGER.info(workflowStatus +"128");
		// Make the content ready
		String content = "Hi, " + "\n" + "It is successfull: " + LocalDateTime.now() + "\n"
				+ workflowStatus;
		// Send emails	
		LOGGER.info(content);
		emailService.sendEmail(toEmail, ccEmail, fromEmail, subject, content);
		LOGGER.info("{}: workflow status email is sent");
	}
}