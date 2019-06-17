package com.nk.sample.workflows;

package de.schwarzkopf.workflows;

import java.util.Collections;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.osgi.framework.Constants;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.adobe.granite.workflow.WorkflowException;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.Route;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.replication.Agent;
import com.day.cq.replication.AgentFilter;
import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationOptions;
import com.day.cq.replication.Replicator;


@Component
   
    
@Service
  
@Properties({
    @Property(name = Constants.SERVICE_DESCRIPTION, value = "Replication to Author process step."),
    @Property(name = "process.label", value = "Author to Author Replication process step") })
public class AuthorReplicationProcess implements WorkflowProcess 
{

	Logger logger = LoggerFactory.getLogger(AuthorReplicationProcess.class);
	
	@Reference
	private ResourceResolverFactory resolverFactory;
	 
	@Reference
	private Replicator replicator;
	   
	private Session session;
	
	
	@Override
	public void execute(WorkItem item, WorkflowSession wfsession, MetaDataMap args) throws WorkflowException {
		
		logger.info("AuthorReplicationProcess called");
		try
		{
		    
		    Session session = wfsession.adaptTo(Session.class);
		    String path = item.getWorkflowData().getPayload().toString();
		     
		    ReplicationOptions options = new ReplicationOptions();
		    
		    //filter specific agent to be selected by id
			AgentFilter agentFilter = new AgentFilter() {
				@Override
				public boolean isIncluded(Agent agent) {
					if(agent.getId().toLowerCase().contains("author-to-author-replication")) {
						return true;
					}
					return false;
				}

			};

		    options.setSuppressVersions(true);
		    options.setFilter(agentFilter);
		    options.setSynchronous(true);
		    options.setSuppressStatusUpdate(false);  
		    replicator.replicate(session,ReplicationActionType.ACTIVATE,path,options);  
		     
		    Route route = wfsession.getRoutes(item, true).get(0);
		    wfsession.complete(item, route);
		}
		catch(Exception e)
		{
		    logger.error("Error: " +e.getMessage()) ;  
		}
		
	}
	
}