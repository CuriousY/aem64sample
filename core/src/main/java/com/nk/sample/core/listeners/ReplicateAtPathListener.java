package com.nk.sample.core.listeners;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.observation.Event;
import javax.jcr.observation.EventIterator;
import javax.jcr.observation.EventListener;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.LoggerFactory;

import com.day.cq.workflow.WorkflowException;
import com.day.cq.workflow.WorkflowService;
import com.day.cq.workflow.WorkflowSession;
import com.day.cq.workflow.exec.Route;
import com.day.cq.workflow.exec.WorkItem;
import com.day.cq.workflow.exec.Workflow;
import com.day.cq.workflow.exec.WorkflowData;
import com.day.cq.workflow.exec.filter.WorkItemFilter;
import com.day.cq.workflow.model.WorkflowModel;

import org.slf4j.Logger;

@Component(immediate = true)
@Service(EventListener.class)

public class ReplicateAtPathListener implements EventListener {

	Logger log = LoggerFactory.getLogger(this.getClass());

	private Session adminSession;

	@Reference
	SlingRepository repository;

	@Reference
	private WorkflowService workflowService;

	private String WORKFLOW_MODEL_PATH = "/etc/workflow/models/replication-after-listener/jcr:content/model";

	@Activate
	public void activate(ComponentContext context) throws Exception {
		try {
			adminSession = repository.loginAdministrative(null);
			adminSession.getWorkspace().getObservationManager().addEventListener(this,
					Event.PROPERTY_ADDED | Event.NODE_ADDED | Event.PROPERTY_CHANGED, // binary combination of event
																						// types
					"/content/we-retail", true, null, null, false);

		} catch (RepositoryException e) {
			log.error("unable to register session ReplicateAtPathListener", e);
			throw new Exception(e);
		}
	}

	@Deactivate
	public void deactivate() {
		if (adminSession != null) {
			adminSession.logout();
		}
	}

	@Override
	public void onEvent(EventIterator eventIterator) {
		try {
			while (eventIterator.hasNext()) {
				log.info("node modified: {} ReplicateAtPathListener", eventIterator.nextEvent().getPath());

				WorkflowSession wfSession = workflowService.getWorkflowSession(adminSession);

				// Get the workflow model
				WorkflowModel wfModel = wfSession.getModel(WORKFLOW_MODEL_PATH);

				String path = eventIterator.nextEvent().getPath();
				
				WorkflowData wfData = wfSession.newWorkflowData("JCR_PATH", path);

				// Run the Workflow.
				wfSession.startWorkflow(wfModel, wfData);


			}
		} catch (RepositoryException | WorkflowException e) {
			log.error("Error in workflow execution ReplicateAtPathListener", e);
		}
	}

}