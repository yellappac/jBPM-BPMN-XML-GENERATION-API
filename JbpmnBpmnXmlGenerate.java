
package org.syncoms.bpm.workflow.api;

import org.jbpm.bpmn2.xml.XmlBPMNProcessDumper;

import org.jbpm.ruleflow.core.RuleFlowNodeContainerFactory;
import org.jbpm.ruleflow.core.RuleFlowProcessFactory;

public class JbpmnBpmnXmlGenerate {

	public static final void main(String[] args) {
		try {

			RuleFlowProcessFactory factory = RuleFlowProcessFactory
					.createProcess("Orderprocess");

			// creating process dynamically
			factory = factory.name("Orderprocess")
					.version("1.0")
					.packageName("org.jbpmn.bpm");

			// adding start node
			factory.startNode(1)
			.name("start")
			.done();

			// service paycheck task
			RuleFlowNodeContainerFactory payServicetask1 = factory
					.workItemNode(2)
					.name("paycheck")
					.workName("paycheck")
					.workParameter("URL","http://192.168.7.105:8080/Rest_Handler_WebService_Project/api/RestHandlerService/check")
					.done();

		
			// Split node
			
			RuleFlowNodeContainerFactory splitNode = factory.splitNode(3)
					.name("PayGate")					
					.type(2)					
					.constraint(5, "Fraudcheck", "1", "2"," true" )
					.done();
			
     
			// adding end node
			factory.endNode(4)
			.name("end")
			.done();
			
			
			// service Fraudcheck task
    		RuleFlowNodeContainerFactory fraudservicetask2 = 
    				factory.workItemNode(5)
    				.name("Fraudcheck")
    				.workName("Fraudcheck")
    				.workParameter("URL", "http://www.facebook.com")
    				.done();;

    	
    		
    		// service Inventorycheck task
    		RuleFlowNodeContainerFactory inventoryservicetask2 = 
    				factory.workItemNode(6)
    				.name("Inventorycheck")
    				.workName("Inventorycheck")
    				.workParameter("URL", "http://www.twitter.com")
    				.done();
    		
    		
    		// adding end node
    		factory.endNode(7)
    		.name("end")
    		.done();

    				

			// making connections
			factory.connection(1, 2);
			factory.connection(2, 3);
			factory.connection(3, 5);
			factory.connection(5, 4);
			factory.connection(3, 6);
			factory.connection(6, 7);
		
			// We can get xml for the process using following code.
			String asXml = XmlBPMNProcessDumper.INSTANCE.dump(
					factory.getProcess(), XmlBPMNProcessDumper.NO_META_DATA);
			System.out.println(asXml);

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
